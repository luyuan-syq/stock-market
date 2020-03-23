package com.syq.biz.field.impl;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import com.syq.pagination.field.FieldValue;


public class FieldValueImpl implements FieldValue{
	
	private String value;
	private boolean isChecked;
	
	@SuppressWarnings("unused")
	private static final XMLFormat<FieldValueImpl> XML = new XMLFormat<FieldValueImpl>(FieldValueImpl.class) {

		@Override
		public void read(InputElement xml, FieldValueImpl fieldValueImpl)
				throws XMLStreamException {
		  fieldValueImpl.isChecked = xml.getAttribute("isChecked", false);
		  fieldValueImpl.value = xml.getText().toString();
		}

		@Override
		public void write(FieldValueImpl fieldValueImpl, OutputElement xml)
				throws XMLStreamException {
			try{
				xml.setAttribute("isChecked", fieldValueImpl.isChecked);
				xml.addText(fieldValueImpl.value);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	};

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isChecked() {
		return isChecked;
	}

}
