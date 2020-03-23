package com.syq.visa;
/**
 * 
 * @author shaoyuqi
 *
 */
public class PrincipalFieldValueBean {
	
	private String fieldID;
	private String fieldValue;
	private Object property;
	
	public PrincipalFieldValueBean(String fieldID,String fieldValue) {
		this.fieldID = fieldID;
		this.fieldValue = fieldValue;
	}
	
	public boolean equals(Object obj) {
		if(null == obj)
			return false;
		if(this == obj)
			return true;
		if(obj instanceof PrincipalFieldValueBean) {
			PrincipalFieldValueBean s = (PrincipalFieldValueBean)obj;
			if(s.getFieldID().equals(fieldID)){
				return true;
			}
			return false;
		}
		return false;
	}
	
	public int hashCode() {
		return this.fieldID.hashCode();
	}
	
	
	//------------------------------getAndset-----------------------------------------
	public String getFieldID() {
		return fieldID;
	}
	public void setFieldID(String fieldID) {
		this.fieldID = fieldID;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public Object getProperty() {
		return property;
	}
	public void setProperty(Object property) {
		this.property = property;
	}
}
