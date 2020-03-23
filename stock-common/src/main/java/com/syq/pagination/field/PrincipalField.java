package com.syq.pagination.field;

import java.util.List;

public interface PrincipalField {

  public static final String TYPE_TEXTINPUT = "textInput";
  public static final String TYPE_TEXTSELECT = "textSelect";
  public static final String DISPLAY_TYPE_RADIO = "radio";
  public static final String DISPLAY_TYPE_SELECT = "select";
  public static final String DISPLAY_TYPE_CHECKBOX = "checkbox";
  public static final String DISPLAY_TYPE_TEXT = "text";
  public static final String DISPALY_TYPE_TEXTAREA = "textarea";

  public FieldValidate getValidate();

  public void setValues(List<FieldValue> paramList);

  public List<FieldValue> getValues();

  // 是否编辑
  public void setEditable(boolean paramBoolean);

  public boolean isEditable();

  // 是否可见
  public void setVisible(boolean paramBoolean);

  public boolean isVisible();

  // 是否必填
  public void setRequired(boolean paramBoolean);

  public boolean isRequired();

  // 显示方式
  public void setDisplayType(String paramString);

  public String getDisplayType();

  // 类型
  public void setType(String paramString);

  public String getType();

  // 名字
  public void setName(String paramString);

  public String getName();

  // ID
  public String getId();

  public boolean isColumnVisible();

  public void setColumnVisible(boolean paramBoolean);



}
