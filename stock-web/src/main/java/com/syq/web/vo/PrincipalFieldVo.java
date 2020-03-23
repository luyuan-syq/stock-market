package com.syq.web.vo;

import java.util.List;


public class PrincipalFieldVo {
  
  private String fieldName;
  
  private String fieldType;
  
  private String displayType;
  
  private boolean required;
  
  private boolean visible;
  
  private boolean editable;
  
  private boolean columnVisible;
  
  private String validateDescription;
  
  private String validateRule;
  
  private List<String> defaultValues;
  
  private List<Boolean> checkeds;
  
  private String categoryId;
  
  private String fieldId;
  
  private String code;
  
  

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getFieldType() {
    return fieldType;
  }

  public void setFieldType(String fieldType) {
    this.fieldType = fieldType;
  }

  public String getDisplayType() {
    return displayType;
  }

  public void setDisplayType(String displayType) {
    this.displayType = displayType;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public boolean isColumnVisible() {
    return columnVisible;
  }

  public void setColumnVisible(boolean columnVisible) {
    this.columnVisible = columnVisible;
  }

  public String getValidateDescription() {
    return validateDescription;
  }

  public void setValidateDescription(String validateDescription) {
    this.validateDescription = validateDescription;
  }

  public String getValidateRule() {
    return validateRule;
  }

  public void setValidateRule(String validateRule) {
    this.validateRule = validateRule;
  }

  public List<String> getDefaultValues() {
    return defaultValues;
  }

  public void setDefaultValues(List<String> defaultValues) {
    this.defaultValues = defaultValues;
  }

  public List<Boolean> getCheckeds() {
    return checkeds;
  }

  public void setCheckeds(List<Boolean> checkeds) {
    this.checkeds = checkeds;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public String getFieldId() {
    return fieldId;
  }

  public void setFieldId(String fieldId) {
    this.fieldId = fieldId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
  
  
}
