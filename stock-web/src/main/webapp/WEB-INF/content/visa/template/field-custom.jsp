<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="decorator" content="none" />

<script type="text/javascript">
	$(function() {
		var fieldCustom = new SYQ.FieldCustom({
			el:$("#field-custom"),
			getFieldPageUrl:'./visaTemplate/getFieldPage',
			updateCategoryUrl:'./visaTemplate/updateCategory',
			deleteCategoryUrl:'./visaTemplate/deleteCategory',
			isCategoryExistedUrl: '${isCategoryExistedUrl}'
		});
		$('.category-title:first',fieldCustom.el).click();
	});
</script>

<div id="field-custom">
    <div class="aps-tree-container field-custom-left">
        <div class="aps-tree-container-header category-header">
           <div class="category-header-create">
               <button type="button" class="create-btn category-create-btn">字段类别</button>
           </div>
        </div>
        <div class="aps-tree-container-body category-body">
            <c:forEach items="${fieldCategorys}" var="fieldCategory">
              <div class="field-category" categoryId="${fieldCategory.id }" categoryName="${fieldCategory.name }">
                    <div class="category-action display-none">
                        <button type="button" class="category-delete-btn"></button>
                    </div>
                    <div class="category-title" title="双击可编辑">
                       <div class="aps-tree-container-text-overflow category-title-text">
                          ${fieldCategory.name }
                       </div>
                    </div>
                    <div class="category-title-update">
                        <input maxlength="30" class="input-text"/>
                    </div>
                    <div class="clear-both"></div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div id="aps-console-field-view-switch" class="aps-console-column-exspand">
       <div></div>
    </div>
    <div class="field-custom-right">
    
    </div>
    <div class="clear-both"></div>
</div>
