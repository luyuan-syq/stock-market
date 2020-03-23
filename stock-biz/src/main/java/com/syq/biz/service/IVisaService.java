package com.syq.biz.service;

import java.util.List;

import com.syq.biz.bo.ExpressDeliveryBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.VisaPrincipalBo;
import com.syq.pagination.common.TaskPo;
import com.syq.pagination.field.FieldCategory;
import com.syq.pagination.field.PrincipalField;
import com.syq.util.constant.VisaStatus;
import com.syq.visa.PrincipalFieldValueBean;


public interface IVisaService {
  
   List<FieldCategory> getSysFieldCategorys(String code);
   
   List<FieldCategory> getFieldCategorys(String code);
   
   VisaPrincipalBo getById(long id);
   
   void replaceDefaultValueWithUserValue(PrincipalField pf,String userValue) throws Exception;
   
   void uploadVisaAttribute(VisaPrincipalBo visaPrincipalBo,List<PrincipalFieldValueBean> principalFieldValueBeans );
   
   void assertVisaPrincipal(Long personId,Long taskId,List<String> templateCodes);
   
   VisaPrincipalBo getVisaPrincipal(Long personId,Long taskId,String templateCode);
   
   VisaPrincipalBo getVisaPrincipal(Long visaPrincipalId);
   
   boolean audit(Long principalId,int status,String auditMsg);
   
   ExpressDeliveryBo selectExpressDeliveryByTaskId(Long taskId);
   
   boolean deliverySave(ExpressDeliveryBo bo);
   
   boolean deliveryUpdate(ExpressDeliveryBo bo);
   
   boolean updateVisaStatus(Long visaPrincipal, VisaStatus status);
   
   boolean updateVisaStatusByTaskId(Long taskId,VisaStatus status);
   
   int getTaskCount(TaskPo taskPo);

   List<TaskBo> selectTaskByCondition(TaskPo taskPo);
   
   int getVisaPrincipalCount(TaskPo taskPo);

   List<TaskBo> selectVisaPrincipalByCondition(TaskPo taskPo);
   
   
   int getVisaPrincipalCountForToDo(TaskPo taskPo);

   List<TaskBo> selectVisaPrincipalByConditionForToDo(TaskPo taskPo);
   
   
   int getNewVisaPrincipalCount(TaskPo taskPo);

   List<TaskBo> selectNewVisaPrincipalByCondition(TaskPo taskPo);


}
