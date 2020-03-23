package com.syq.pagination.field;

import java.util.List;

public interface FieldCategory {

  public void setFields(List<PrincipalField> paramList);

  public List<PrincipalField> getFields();

  public void setOrder(int paramInt);

  public int getOrder();

  public void setName(String paramString);

  public String getName();

  public String getId();

}
