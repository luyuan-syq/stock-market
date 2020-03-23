package com.syq.web.controller;

import static com.syq.web.common.WebConst.MESSAGE_KEY;
import static com.syq.web.common.WebConst.SUCCESS_KEY;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.base.Strings;
import com.syq.biz.factory.SYQServiceFactory;
import com.syq.biz.service.IVisaTemplateFieldService;
import com.syq.biz.service.impl.VisaTemplateFieldService;
import com.syq.exception.FieldServiceException;
import com.syq.pagination.field.FieldCategory;
import com.syq.pagination.field.FieldValidate;
import com.syq.pagination.field.FieldValue;
import com.syq.pagination.field.PrincipalField;
import com.syq.web.vo.PrincipalFieldVo;

@Controller
@RequestMapping("/visaTemplate")
public class VisaTemplateController {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(VisaTemplateController.class);

  private static final String FIELD_CREATE_PAGE = "/visa/template/field-create";

  private static final String FIELD_UPDATE_PAGE = "/visa/template/field-update";

  @RequestMapping(value = "/template", method = RequestMethod.GET)
  public ModelAndView setting(String code) {
    ModelAndView view = new ModelAndView("/visa/template/template");
    view.addObject("code", code);

    return view;
  }

  @RequestMapping(value = "/getCustomField", method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView getCustomField(String code) {
    ModelAndView view = new ModelAndView("/visa/template/field-custom");

    IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(code);
    List<FieldCategory> fieldCategorys = visaTemplateFieldService.getFieldCategorys();

    view.addObject("fieldCategorys", fieldCategorys);
    view.addObject("code", code);

    return view;
  }


  @RequestMapping(value = "/getFieldPage", method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView getFieldPage(String code,String categoryId) {
    ModelAndView view = new ModelAndView("/visa/template/field-content");

    IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(code);
    FieldCategory category = null;
    try {
      category = visaTemplateFieldService.getFieldCategory(categoryId);
    } catch (FieldServiceException e) {
      LOG.error("getFieldPage error ", e);
    }

    view.addObject("category", category);
    view.addObject("categoryId", categoryId);
    view.addObject("code", code);

    return view;
  }

  @RequestMapping(value = "/createField", method = {RequestMethod.POST})
  public ModelAndView createField(PrincipalFieldVo vo) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    try {
      if (Strings.isNullOrEmpty(vo.getCategoryId())) {
        throw new FieldServiceException("categoryId不能为空");
      }

      IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(vo.getCode());
      FieldCategory category = visaTemplateFieldService.getFieldCategory(vo.getCategoryId());
      PrincipalField field = visaTemplateFieldService.newField(vo.getCategoryId(), vo.getFieldId());
      setFieldInfo(field, vo);
      category.getFields().add(field);
      visaTemplateFieldService.updateFieldCategory(category);
      view.addObject(SUCCESS_KEY, true);
    } catch (FieldServiceException e) {
      String message = e.getMessage();
      LOG.error(message, e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, "创建属性错误");
    }
    return view;
  }


  /**
   * 跳转到修改PrincipalField页面
   */
  @RequestMapping(value = "/getFieldUpdatePage", method = {RequestMethod.GET})
  public ModelAndView getFieldUpdatePage(String categoryId, String fieldId,String code) {
    ModelAndView view = new ModelAndView(FIELD_UPDATE_PAGE);
    IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(code);
    try {
      PrincipalField field = visaTemplateFieldService.getField(categoryId, fieldId);
      view.addObject("categoryId", categoryId);
      view.addObject("fieldId", fieldId);
      view.addObject("field", field);
      view.addObject("code", code);
    } catch (FieldServiceException e) {
      LOG.error(e.getMessage(), e);
    }
    return view;
  }


  /**
   * 修改PrincipalField
   */
  @RequestMapping(value = "/updateField", method = {RequestMethod.POST})
  public ModelAndView updateField(PrincipalFieldVo vo) {

    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    try {
      if (Strings.isNullOrEmpty(vo.getCategoryId())) {
        throw new FieldServiceException("categoryId不能为空");
      }

      IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(vo.getCode());
      FieldCategory category = visaTemplateFieldService.getFieldCategory(vo.getCategoryId());
      PrincipalField field = visaTemplateFieldService.getField(vo.getCategoryId(), vo.getFieldId());
      List<PrincipalField> fields = category.getFields();
      int index = fields.indexOf(field);
      PrincipalField currentField = (PrincipalField) fields.get(index);
      setFieldInfo(currentField, vo);
      visaTemplateFieldService.updateFieldCategory(category);
      view.addObject(SUCCESS_KEY, true);
    } catch (FieldServiceException e) {
      String message = e.getMessage();
      LOG.error(message, e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, "创建属性错误");
    }
    return view;
  }


  /**
   * 删除PrincipalField
   */
  @RequestMapping(value = "/deleteFields", method = {RequestMethod.GET})
  public ModelAndView deleteFields(String categoryId, String[] fieldIds,String code) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    try {
      IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(code);
      for (int i = 0; i < fieldIds.length; i++) {
        visaTemplateFieldService.removeField(categoryId, fieldIds[i]);
      }
      view.addObject(SUCCESS_KEY, true);
    } catch (FieldServiceException e) {
      LOG.error(e.getMessage(), e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, "创建属性错误");
    }
    return view;
  }


  /**
   * 设置Principlal Field
   * 
   * @return
   */
  @SuppressWarnings("all")
  private void setFieldInfo(PrincipalField field, PrincipalFieldVo vo) {

    IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(vo.getCode());
    field.setName(vo.getFieldName());
    field.setType(vo.getFieldType());
    field.setDisplayType(vo.getDisplayType());
    field.setRequired(vo.isRequired());
    field.setVisible(vo.isVisible());
    field.setEditable(vo.isEditable());
    field.setColumnVisible(vo.isColumnVisible());
    FieldValidate validate = field.getValidate();
    validate.setDescription(vo.getValidateDescription());
    validate.setValue(vo.getValidateRule());
    List values = field.getValues();
    values.clear();
    if (vo.getDefaultValues() != null && vo.getDefaultValues().size() > 0) {
      for (int i = 0; i < vo.getDefaultValues().size(); i++) {
        String value = (String) vo.getDefaultValues().get(i);
        if (StringUtils.isEmpty(value))
          break;
        boolean isChecked = ((Boolean) vo.getCheckeds().get(i)).booleanValue();
        FieldValue newFieldValue = visaTemplateFieldService.newFieldValue(value);
        newFieldValue.setChecked(isChecked);
        values.add(newFieldValue);
      }
    }
  }

  @RequestMapping(value = "/getCategoryFieldInfo", method = {RequestMethod.POST, RequestMethod.GET})
  public ModelAndView getCategoryFieldInfo(String categoryId,String code) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(code);
    FieldCategory category = null;
    try {
      category = visaTemplateFieldService.getFieldCategory(categoryId);
      List<PrincipalField> fields = category.getFields();
      view.addObject("total", fields.size());
      view.addObject("rows", fields);
    } catch (FieldServiceException e) {
      LOG.error("getFieldPage error ", e);
    }

    view.addObject("category", category);
    view.addObject("categoryId", categoryId);

    return view;
  }

  @RequestMapping(value = "/createFieldCategory", method = {RequestMethod.POST})
  public ModelAndView createFieldCategory(String categoryId, String categoryName,String code) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    try {
      IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(code);
      int size = visaTemplateFieldService.getFieldCategorys().size();
      FieldCategory category = visaTemplateFieldService.newFieldCategory(categoryId);
      category.setName(categoryName);
      category.setOrder(size);
      visaTemplateFieldService.updateFieldCategory(category);
    } catch (Exception e) {
      LOG.error("save task error ", e);
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, "创建字段类别错误");
      return view;
    }

    return view;

  }


  /**
   * 删除字段类别
   * 
   * @return
   */
  @RequestMapping(value = "/deleteCategory", method = {RequestMethod.GET})
  public ModelAndView deleteFieldCategory(String categoryId,String code) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());

    try {
      IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(code);
      visaTemplateFieldService.removeFieldCategory(categoryId);
      view.addObject(SUCCESS_KEY, true);
    } catch (FieldServiceException e) {
      String msg = e.getMessage();
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, msg);
      LOG.error(msg, e);
    }
    return view;
  }

  /**
   * 修改字段列别
   * 
   * @return
   */
  @RequestMapping(value = "/updateCategory", method = {RequestMethod.POST})
  public ModelAndView updateFieldCategory(String categoryId, String categoryName,String code) {
    ModelAndView view = new ModelAndView(new MappingJackson2JsonView());
    view.addObject(SUCCESS_KEY, true);
    try {
      IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(code);
      FieldCategory category = visaTemplateFieldService.getFieldCategory(categoryId);
      category.setName(categoryName);
      visaTemplateFieldService.updateFieldCategory(category);
    } catch (FieldServiceException e) {
      String msg = e.getMessage();
      view.addObject(SUCCESS_KEY, false);
      view.addObject(MESSAGE_KEY, msg);
      LOG.error(msg, e);
    }
    return view;
  }

  @RequestMapping(value = "/getFieldCreatePage", method = {RequestMethod.GET})
  public ModelAndView getFieldCreatePage(String code,String categoryId) {
    ModelAndView view = new ModelAndView(FIELD_CREATE_PAGE);
    view.addObject("categoryId", categoryId);
    view.addObject("code", code);

    return view;

  }

  private IVisaTemplateFieldService getIVisaTemplateFieldService(String template) {
    IVisaTemplateFieldService visaTemplateFieldService =
        SYQServiceFactory.getService(VisaTemplateFieldService.class, template);
    return visaTemplateFieldService;
  }
}
