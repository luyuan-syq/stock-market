package com.syq.biz.field.impl;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.apache.commons.lang.StringEscapeUtils;

import com.syq.pagination.field.FieldValidate;


public class FieldValidateImpl implements FieldValidate{
	
	private String value;
	private String description;
	
	private static final XMLFormat XML = new XMLFormat(FieldValidateImpl.class) {

		@Override
		public void read(InputElement xml, Object o)
				throws XMLStreamException {
			FieldValidateImpl g = (FieldValidateImpl)o;
			g.description = StringEscapeUtils.unescapeHtml(xml.getAttribute("description", ""));
			g.value = xml.getText().toString();
		}

		@Override
		public void write(Object o, OutputElement xml)
				throws XMLStreamException {
			try{
				FieldValidateImpl g = (FieldValidateImpl) o;
				xml.setAttribute("description", g.description);
				xml.addText(g.value);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	};

	public String getDescription() {
		return description;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
