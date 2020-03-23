package com.syq.web.helper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.RegexValidator;

import com.syq.pagination.field.PrincipalField;
import com.syq.visa.PrincipalFieldValueBean;



/**
 * 
 * @author shaoyuqi
 *
 */
public class FieldValidateHelper {
	
	public static final String CHECKBOX = "checkbox";
	public static final String TEXTAREA = "textarea";
	public static final String SELECT = "select";
	public static final String RADIO = "radio";
	public static final String TEXT = "text";
	public static final String CHECKBOX_VALUE_SEPERATOR = ",";
	
	
	public static PrincipalFieldValueBean validateCustomField(PrincipalField principalField,String[] fieldValues,String fieldName) throws Exception {
		if(!principalField.isVisible())
			return null;
		if(!principalField.isEditable()) 
			return null;
		if(fieldValues == null || ((fieldValues.length == 1) && (fieldValues[0].equals("")))) {
			if(principalField.isRequired()) 
				throw new Exception("FIELD_SHOULD_NOT_BE_EMPTY");
			return new PrincipalFieldValueBean(fieldName, "");
		}
		boolean validatePass = validateByRegXp(principalField,fieldValues);
		if(!validatePass)
			throw new Exception("FIELD_REGULAR_VALIDATE_FAILED");
		//验证单只
		if(principalField.getDisplayType().equals(RADIO) || principalField.getDisplayType().equals(SELECT) || principalField.getDisplayType().equals(TEXT) || principalField.getDisplayType().equals(TEXTAREA)) {
			if(fieldValues.length > 1)
				throw new Exception("TOO_MANY_OPTIONS_SELECT");
			String oneFieldValue = fieldValues[0];
			return new PrincipalFieldValueBean(fieldName, oneFieldValue);
		}
		if(principalField.getDisplayType().equals("checkbox")) {
			return getValueFromCheckBox(fieldValues,fieldName);
		}
		throw new Exception("NOT_SUPPORT_FIELD_TYPE");
			
	}
	/**
	 * 服务端验证表单是否和规范
	 * @param pf
	 * @param fieldValues
	 * @return
	 */
	public static boolean validateByRegXp(PrincipalField pf,String[] fieldValues) {
		boolean caseSensitive = true;
		String regex = pf.getValidate().getValue();
		if(StringUtils.isNotBlank(regex)){
			regex = regex.trim();
			RegexValidator validator = new RegexValidator(regex,caseSensitive);
			for(String fieldValue:fieldValues) {
				//本来是多指验证的，为了不影响效率，暂时以为就一个值处理
				boolean valid = validator.isValid(fieldValue);
				if(valid)
					return true;
			}
			return false;
		}
		return true;
	}
	
	public static  PrincipalFieldValueBean getValueFromCheckBox(String[] fieldValues,String fieldName) {
		StringBuilder catFieldValue = new StringBuilder();
		for(String fieldValue:fieldValues) {
			catFieldValue.append(fieldValue+",");
		}
		if(catFieldValue.length() != 0) {
			catFieldValue.deleteCharAt(catFieldValue.length() - 1);
		}
		String finalFieldValue = catFieldValue.toString();
		return new PrincipalFieldValueBean(fieldName, finalFieldValue);
	}

}
