package com.syq.biz.service;

import java.util.List;

import com.syq.exception.FieldServiceException;
import com.syq.pagination.field.FieldCategory;
import com.syq.pagination.field.FieldValue;
import com.syq.pagination.field.PrincipalField;


public interface IVisaTemplateFieldService {

  public static final String TEXT_INPUT = "textInput";
  public static final String TEXT_SELECT = "textSelect";

  public FieldCategory newFieldCategory(String paramString) throws FieldServiceException;

  public PrincipalField newField(String paramString1, String paramString2)
      throws FieldServiceException;

  public FieldValue newFieldValue(String paramString);

  public void removeFieldCategory(String paramString) throws FieldServiceException;

  public void removeField(String paramString1, String paramString2) throws FieldServiceException;

  public void updateFieldCategory(FieldCategory paramFieldCategory);

  public FieldCategory getFieldCategory(String paramString) throws FieldServiceException;

  public List<FieldCategory> getFieldCategorys();

  public PrincipalField getField(String paramString1, String paramString2)
      throws FieldServiceException;

  public List<FieldCategory> getFieldCategorys(boolean paramBoolean);

  public boolean isFieldExisted(String paramString1, String paramString2);

  public boolean isCategoryExisted(String paramString);
}
