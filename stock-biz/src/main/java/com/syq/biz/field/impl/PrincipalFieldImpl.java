package com.syq.biz.field.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.syq.pagination.field.FieldValidate;
import com.syq.pagination.field.FieldValue;
import com.syq.pagination.field.PrincipalField;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 * 格式
 * <Field>
 *    <validate></validate>
 *    <values>
 *       <value></value>
 *    </values>
 * </Field>
 * @author shaoyuqi
 *
 */
public class PrincipalFieldImpl implements PrincipalField{

	private String id;
	private String name;
	private String type;
	private String displayType;
	private boolean isRequired;
	private boolean isVisiable;
	private boolean isColumnVisible;
	private boolean isEditable;
	private List values;
	private FieldValidate validate;
	
	private static final XMLFormat XML = new XMLFormat(PrincipalFieldImpl.class) {

		@Override
		public void read(InputElement xml, Object o)
				throws XMLStreamException {
			PrincipalFieldImpl g = (PrincipalFieldImpl)o;
			g.id = StringEscapeUtils.unescapeHtml(xml.getAttribute("id", ""));
			g.name = StringEscapeUtils.unescapeHtml(xml.getAttribute("name", ""));
			g.type = StringEscapeUtils.unescapeHtml(xml.getAttribute("type", ""));
			g.displayType = StringEscapeUtils.unescapeHtml(xml.getAttribute("displayType", ""));
			g.isRequired = xml.getAttribute("isRequired", false);
			g.isVisiable = xml.getAttribute("isVisiable", true);
			g.isEditable = xml.getAttribute("isEditable", true);
			g.isColumnVisible = xml.getAttribute("isColumnVisible", true);
			Object o1 = null;
			do{
				if(!xml.hasNext())
					break;
				o1 = xml.getNext();
				if(o1 instanceof FieldValidateImpl) {
					g.validate = (FieldValidateImpl)o1;
				}else if(o1 instanceof FieldValuesImpl) {
					FieldValuesImpl f = (FieldValuesImpl)o1;
					g.values.addAll(f);
				}
			}while(true);
			
		}

		@Override
		public void write(Object o, OutputElement xml)
				throws XMLStreamException {
			PrincipalFieldImpl g = (PrincipalFieldImpl)o;
			xml.setAttribute("id", g.id);
			xml.setAttribute("name", g.name);
			xml.setAttribute("type", g.type);
			xml.setAttribute("displayType", g.displayType);
			xml.setAttribute("isRequired", g.isRequired);
			xml.setAttribute("isVisiable", g.isVisiable);
			xml.setAttribute("isEditable", g.isEditable);
			xml.setAttribute("isColumnVisible", g.isColumnVisible);
			xml.add(g.validate);
			FieldValuesImpl f = new FieldValuesImpl();
			FieldValueImpl fieldValueImpl;
			for(int i = 0;i < g.values.size();i++) {
				fieldValueImpl = (FieldValueImpl)g.values.get(i);
				f.add(fieldValueImpl);
			}
			xml.add(f);
		}
	   	
	};
	
	public PrincipalFieldImpl() {
		values = new ArrayList();
		validate = new FieldValidateImpl();
	}
	
	
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(this == obj)
			return true;
		if(obj instanceof PrincipalFieldImpl) {
			PrincipalFieldImpl p = (PrincipalFieldImpl) obj;
			return p.getId().equals(id);
		}else
			return false;
	}
	
	public int hashCode() {
		int result = 17;
		result = 31 * 17 +id.hashCode();
		return result;
	}
	

	public FieldValidate getValidate() {
		return validate;
	}

	public void setValues(List<FieldValue> values) {
		this.values = values;
		
	}

	public List<FieldValue> getValues() {
		return values;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setVisible(boolean isVisiable) {
		this.isVisiable = isVisiable;
		
	}

	public boolean isVisible() {
		return isVisiable;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	@Override
	public boolean isRequired() {
		return isRequired;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public boolean isColumnVisible() {
		return isColumnVisible;
	}

	public void setColumnVisible(boolean isColumnVisible) {
		this.isColumnVisible = isColumnVisible;
	}

}
