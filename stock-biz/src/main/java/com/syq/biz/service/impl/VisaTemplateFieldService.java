package com.syq.biz.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javolution.xml.XMLBinding;
import javolution.xml.XMLObjectReader;
import javolution.xml.XMLObjectWriter;
import javolution.xml.stream.XMLStreamException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syq.biz.field.impl.FieldCategoryImpl;
import com.syq.biz.field.impl.FieldCategorysImpl;
import com.syq.biz.field.impl.FieldValidateImpl;
import com.syq.biz.field.impl.FieldValueImpl;
import com.syq.biz.field.impl.FieldValuesImpl;
import com.syq.biz.field.impl.PrincipalFieldImpl;
import com.syq.biz.field.impl.PrincipalFieldsImpl;
import com.syq.biz.service.IVisaTemplateFieldService;
import com.syq.exception.FieldServiceException;
import com.syq.pagination.field.FieldCategory;
import com.syq.pagination.field.FieldValue;
import com.syq.pagination.field.PrincipalField;


public class VisaTemplateFieldService implements IVisaTemplateFieldService{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(VisaTemplateFieldService.class);

  private static final String USRE_REMOVE_FIELD_CATAGORY = "user-remove-field-category";
  private static final String USER_REMOVE_FIELD = "user-remove-field";
  
  private String filePath;
  private XMLBinding binding;
  private static final ReadWriteLock xmlLock = new ReentrantReadWriteLock();
  
  
  public VisaTemplateFieldService(String fileName) {
      System.out.println(this.getClass().getClassLoader().getResource("/"+fileName));
      System.out.println(this.getClass().getClassLoader().getResource("/"));
      System.out.println(this.getClass().getClassLoader().getResource(""));
      
      this.filePath = this.getClass().getClassLoader().getResource("/").getFile()+fileName;
      this.binding = new XMLBinding();
      setupAliases(this.binding);
  }
  
  private void setupAliases(XMLBinding binding) {
      binding.setAlias(FieldCategorysImpl.class, "categorys");
      binding.setAlias(FieldCategoryImpl.class, "category");
      binding.setAlias(PrincipalFieldsImpl.class, "fields");
      binding.setAlias(PrincipalFieldImpl.class, "field");
      binding.setAlias(FieldValuesImpl.class, "values");
      binding.setAlias(FieldValueImpl.class, "value");
      binding.setAlias(FieldValidateImpl.class, "validate");
//      binding.setClassAttribute(null);
  }

  public FieldCategory newFieldCategory(String id)
          throws FieldServiceException {
      if(StringUtils.isEmpty(id)) {
          throw new FieldServiceException("id can not be null or empty string");
      }
      boolean existed = isCategoryExisted(id);
      if(existed)
          throw new FieldServiceException("category already exist id "+ id);
      FieldCategoryImpl fieldCategoryImpl = new FieldCategoryImpl();
      fieldCategoryImpl.setId(id);
      return fieldCategoryImpl;
  }

  public PrincipalField newField(String categoryid, String fieldId)
          throws FieldServiceException {
      if(StringUtils.isEmpty(categoryid) || StringUtils.isEmpty(fieldId)) {
          throw new FieldServiceException("categoryId or fieldId can not be null or empty string");
      }
      boolean existed = isFieldExisted(categoryid,fieldId);
      if(existed)
          throw new FieldServiceException("field already exist id "+ fieldId);
      PrincipalFieldImpl principalFieldImpl = new PrincipalFieldImpl();
      principalFieldImpl.setId(fieldId);
      return principalFieldImpl;
  }

  public FieldValue newFieldValue(String value) {
      FieldValue fieldValueImpl = new FieldValueImpl();
      fieldValueImpl.setValue(value);
      return fieldValueImpl;
  }

  public void removeFieldCategory(String id)
          throws FieldServiceException {
      FieldCategorysImpl categorys = getCategorys();
      FieldCategory category = getFieldCategory(id);
      if(category == null)
          throw new FieldServiceException("remove field category failure: can not find the category");
      categorys.remove(category);
      updateCategorys(categorys);
      logFieldCategoryInfo(category);
  }

  public void removeField(String categoryId, String fieldId)
          throws FieldServiceException {
      if(StringUtils.isEmpty(categoryId) || StringUtils.isEmpty(fieldId))
          throw new FieldServiceException("categoryId or fieldId can not be null");
      FieldCategory category = getFieldCategory(categoryId);
      
      if(category == null)
          throw new FieldServiceException("category can not be found");
      List fields = category.getFields();
      PrincipalFieldImpl field = new PrincipalFieldImpl();
      field.setId(fieldId);
      if(fields.contains(field)) {
          int index = fields.indexOf(field);
          PrincipalField principalField = (PrincipalField)fields.get(index);
          fields.remove(principalField);
          updateFieldCategory(category);
          String description = getFieldInfo(principalField);
      }else
          throw new FieldServiceException("category dose not contain the field");
  }

  
  public void updateFieldCategory(FieldCategory category) {
      FieldCategorysImpl categorys = getCategorys();
      if(categorys.contains(category)) {
          int index = categorys.indexOf(category);
          categorys.set(index, (FieldCategoryImpl)category);
      }else{
          categorys.add((FieldCategoryImpl)category);
      }
      updateCategorys(categorys);
      
  }

  public FieldCategory getFieldCategory(String id)
          throws FieldServiceException {
      FieldCategoryImpl categoryImpl = null;
      if(StringUtils.isEmpty(id)) {
          throw new FieldServiceException("categoryId or fieldId can not be null");
      }
      FieldCategorysImpl categorys = getCategorys();
      FieldCategoryImpl fieldCategoryImpl = null;
      for(int i = 0;i<categorys.size();i++) {
          fieldCategoryImpl = (FieldCategoryImpl)categorys.get(i);
          if(id.equals(fieldCategoryImpl.getId())) {
              categoryImpl = fieldCategoryImpl;
          }
              
      }
      return categoryImpl;
      
  }

  
  public List<FieldCategory> getFieldCategorys() {
      List categoryList = new ArrayList();
      FieldCategorysImpl fieldCategorysImpl = getCategorys();
      if(!fieldCategorysImpl.isEmpty()) {
          categoryList.addAll(fieldCategorysImpl);
      }
      return categoryList;
  }

  public PrincipalField getField(String categoryId, String fieldId) throws FieldServiceException{
      PrincipalField result = null;
      FieldCategory fieldCategory = getFieldCategory(categoryId);
      if(fieldCategory != null) {
          List<PrincipalField> fields = fieldCategory.getFields();
          PrincipalField field = null;
          for(int i = 0;i<fields.size();i++) {
              field = fields.get(i);
              if(fieldId.equals(field.getId())) {
                  return field;
              }
          }
      }
      return result;
  }

  
  public List<FieldCategory> getFieldCategorys(boolean includeEmptyCategory) {
      List categoryList = new ArrayList();
      FieldCategorysImpl fieldCategorysImpl = getCategorys();
      if(!includeEmptyCategory) {
          FieldCategoryImpl fieldCategoryImpl = null;
          for(int i = 0;i<fieldCategorysImpl.size();i++) {
              fieldCategoryImpl = fieldCategorysImpl.get(i);
              List<PrincipalField> fields = fieldCategoryImpl.getFields();
              if(fields.size() != 0) {
                  boolean hasOneVisible = false;
                  PrincipalField field = null;
                  for(int j = 0;j<fields.size();j++) {
                      field = fields.get(j);
                      if(field.isVisible()) {
                          hasOneVisible = true;
                          break;
                      }
                  }
                  if(!hasOneVisible)
                      fieldCategorysImpl.remove(i);
              }else
                  fieldCategorysImpl.remove(i);
          }
      }
      if(!fieldCategorysImpl.isEmpty()) {
          categoryList.addAll(fieldCategorysImpl);
      }
      return categoryList;
  }

  public boolean isFieldExisted(String categoryId, String fieldId) {
      boolean result = false;
      try {
          PrincipalField field = getField(categoryId,fieldId);
          if(field != null)
              return true;
      } catch (FieldServiceException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
      return result;
  }

  public boolean isCategoryExisted(String id) {
      boolean result = false;
      try {
          FieldCategory category = getFieldCategory(id);
          if(category != null)
              return true;
      } catch (FieldServiceException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
      return result;
  }
  /**
   * ����categorys
   * @return
   */
  private FieldCategorysImpl getCategorys() {
      FieldCategorysImpl fieldCategorysImpl = new FieldCategorysImpl();
      try{
          XMLObjectReader reader = XMLObjectReader.newInstance(new FileInputStream(this.filePath));
          reader.setBinding(this.binding);
          Lock readLock = xmlLock.readLock();
          readLock.lock();
          try{
              fieldCategorysImpl = (FieldCategorysImpl)reader.read("categorys",FieldCategorysImpl.class);
          }finally{
              readLock.unlock();
          }
          reader.close();
      }catch(Exception e) {
          e.printStackTrace();
      }
      return fieldCategorysImpl;
  }
  /**
   * ����categorys��xml
   */
  private void updateCategorys(FieldCategorysImpl categorys) {
      try {
          XMLObjectWriter writer = XMLObjectWriter.newInstance(new FileOutputStream(this.filePath));
          writer.setBinding(this.binding);
          Lock writerLock = xmlLock.writeLock();
          writerLock.lock();
          try{
              writer.write(categorys);
          }finally{
              writerLock.unlock();
          }
          writer.close();
      } catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } catch (XMLStreamException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
  }
  
  private void logFieldCategoryInfo(FieldCategory category) {
      HashMap map = new HashMap();
      map.put("id", category.getId());
      map.put("name", category.getName());
      List<PrincipalField> fields = category.getFields();
      PrincipalField field = null;
      for(int i = 0;i<fields.size();i++) {
          field = fields.get(i);
          String fieldInfo = getFieldInfo(field);
          map.put("field", fieldInfo);
          String description = null;
          ObjectMapper mapper = new ObjectMapper();
          try {
              description = mapper.writeValueAsString(map);
          } catch (Exception e) {
              LOG.warn("field category info can not transform to json string",e);
          }
      }
  }
  
  private String getFieldInfo(PrincipalField field) {
      String description = null;
      HashMap map = new HashMap();
      map.put("id", field.getId());
      map.put("name", field.getName());
      ObjectMapper mapper = new ObjectMapper();
      try {
          description = mapper.writeValueAsString(map);
      } catch (Exception e) {
          LOG.warn("field "+field.getId()+":"+field.getName()+" info can not transform to json string",e);
      } 
      return description;
  }
  
 
}
