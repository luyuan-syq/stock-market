package com.syq.biz.field.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.syq.pagination.field.FieldCategory;
import com.syq.pagination.field.PrincipalField;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 * 类别(category基本信息等)
 * 格式 《categorys>
 *       <category>
 *           <fields>
 *           </fields>
 *      </category>
 *    <categorys>
 * @author shaoyuqi 
 *
 */
public class FieldCategoryImpl implements FieldCategory{
	
	private String id;
	private String name;
	private int order;
	private List<PrincipalField> fields;

	@SuppressWarnings("unused")
	private static final XMLFormat<FieldCategoryImpl> XML = new XMLFormat<FieldCategoryImpl>(FieldCategoryImpl.class) {

		@Override
		@SuppressWarnings("all")
		public void read(InputElement xml, FieldCategoryImpl g)
				throws XMLStreamException {
			g.id = StringEscapeUtils.unescapeHtml(xml.getAttribute("id", ""));
			g.name = StringEscapeUtils.unescapeHtml(xml.getAttribute("name", ""));
			g.order = Integer.parseInt(StringEscapeUtils.unescapeHtml(xml.getAttribute("order", "-1")));
			
			Object o1 = null;
			do{
				if(!xml.hasNext())
					break;
				o1 = xml.getNext();
				if(o1 instanceof PrincipalFieldsImpl) {
				    
					g.fields.addAll((PrincipalFieldsImpl)o1);
				}
			}while(true);
			
		}

		@Override
		public void write(FieldCategoryImpl g, OutputElement xml)
				throws XMLStreamException {
			try{
				xml.setAttribute("id", g.id);
				xml.setAttribute("name", g.name);
				xml.setAttribute("order", g.order);
				PrincipalFieldsImpl<PrincipalField> f = new PrincipalFieldsImpl<PrincipalField>();
				PrincipalField principalFieldImpl = null;
				for(int i = 0;i<g.fields.size();i++) {
					principalFieldImpl = g.fields.get(i);
					f.add(principalFieldImpl);
				}
			    xml.add(f);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	};
	
	public FieldCategoryImpl() {
		fields = new ArrayList<PrincipalField>();
	}
	
	
	public boolean equals(Object obj) {
		if(null == obj)
			return false;
		if(this == obj)
			return true;
		if(obj instanceof FieldCategoryImpl) {
			FieldCategoryImpl f = (FieldCategoryImpl)obj;
			return f.getId().equals(id);
		}else
			return false;
	}
	
	public int hashCode() {
		int result = 17;
		result = 31 * result + id.hashCode();
		return result;
	}
	
	public void setFields(List<PrincipalField> fields) {
		this.fields = fields;
		
	}

	public List<PrincipalField> getFields() {
		return fields;
	}

	public void setOrder(int order) {
		this.order = order;
		
	}

	public int getOrder() {
		return order;
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


}
