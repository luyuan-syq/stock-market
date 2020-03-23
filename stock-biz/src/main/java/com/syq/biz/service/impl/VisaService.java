package com.syq.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syq.biz.bo.ExpressDeliveryBo;
import com.syq.biz.bo.TaskBo;
import com.syq.biz.bo.VisaPrincipalBo;
import com.syq.biz.domain.ExpressDelivery;
import com.syq.biz.domain.VisaPrincipal;
import com.syq.biz.factory.SYQServiceFactory;
import com.syq.biz.field.impl.FieldValueImpl;
import com.syq.biz.mapper.ExpressDeliveryMapper;
import com.syq.biz.mapper.VisaMapper;
import com.syq.biz.service.IVisaService;
import com.syq.biz.service.IVisaTemplateFieldService;
import com.syq.pagination.common.TaskPo;
import com.syq.pagination.field.FieldCategory;
import com.syq.pagination.field.FieldValue;
import com.syq.util.constant.VisaStatus;
import com.syq.util.convert.BeanUtil;
import com.syq.visa.PrincipalFieldValueBean;

@Service
public class VisaService implements IVisaService {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(VisaService.class);

  @Autowired
  private VisaMapper visaMapper;
  
  @Autowired
  private ExpressDeliveryMapper expressDeliveryMapper;
  

  public List<FieldCategory> getSysFieldCategorys(String code) {
    IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(code);
    List<FieldCategory> fieldCategorys = visaTemplateFieldService.getFieldCategorys(false);
    return fieldCategorys;
  }

  public List<FieldCategory> getFieldCategorys(String code) {
    IVisaTemplateFieldService visaTemplateFieldService = getIVisaTemplateFieldService(code);
    List<FieldCategory> fieldCategorys = visaTemplateFieldService.getFieldCategorys(false);
    return fieldCategorys;
  }

  private IVisaTemplateFieldService getIVisaTemplateFieldService(String template) {
    IVisaTemplateFieldService visaTemplateFieldService =
        SYQServiceFactory.getService(VisaTemplateFieldService.class, template);
    return visaTemplateFieldService;
  }

  @Override
  public VisaPrincipalBo getById(long id) {
    return visaMapper.selectById(id);
  }

  public void replaceDefaultValueWithUserValue(com.syq.pagination.field.PrincipalField pf,
      String userValue) throws Exception {
    if ((pf.getDisplayType().equals("text")) || (pf.getDisplayType().equals("textarea"))) {
      pf.getValues().clear();
      FieldValue fvalue = new FieldValueImpl();
      fvalue.setValue(userValue);
      fvalue.setChecked(true);
      pf.getValues().add(fvalue);
    } else if ((pf.getDisplayType().equals("radio")) || (pf.getDisplayType().equals("select"))) {
      for (FieldValue fv : pf.getValues()) {
        if ((!StringUtils.isBlank(fv.getValue())) && (fv.getValue().equals(userValue))) {
          fv.setChecked(true);
        } else
          fv.setChecked(false);
      }
    } else {
      String[] splitUserValues;
      if (pf.getDisplayType().equals("checkbox")) {
        if (StringUtils.isBlank(userValue)) {
          userValue = "";
        }
        splitUserValues = userValue.split(",");
        for (FieldValue fv : pf.getValues()) {
          for (String splitUserValue : splitUserValues) {
            if (fv.getValue().equals(splitUserValue)) {
              fv.setChecked(true);
              break;
            }
            fv.setChecked(false);
          }
        }
      } else
        throw new Exception("不支持的属性类型 " + pf.getType());
    }
  }

  @Override
  @SuppressWarnings("all")
  public void uploadVisaAttribute(VisaPrincipalBo visaPrincipalBo,
      List<PrincipalFieldValueBean> principalFieldValueBeans) {
    Map map = new HashMap();
    map.put("pricipalId", visaPrincipalBo.getId());
    VisaPrincipalBo bo = null;
    for(PrincipalFieldValueBean bean : principalFieldValueBeans) {
      map.put("bean", bean);
      bo = visaMapper.selectVisaAttribute(map);
      if(bo == null) {
        visaMapper.saveVisaAttribute(map);
      }else {
        visaMapper.updateVisaAttribute(map);
      }
    }
  }

  @Override
  public void assertVisaPrincipal(Long personId, Long taskId,List<String> templateCodes) {
    VisaPrincipal principal = new VisaPrincipal();
    principal.setPersonId(personId);
    principal.setTaskId(taskId);
    
    for(String templateCode : templateCodes) {
      
      VisaPrincipalBo bo = visaMapper.selectByPersonIdTaskIdAndCode(personId, taskId,templateCode);
      principal.setTemplateCode(templateCode);
      if(bo == null) {
        principal.setFlowStatus(VisaStatus.NOT_HANDLED.getCode());
        boolean isOk = visaMapper.insert(principal);
      }else{
      }
    }
  }

  @Override
  public VisaPrincipalBo getVisaPrincipal(Long personId, Long taskId, String templateCode) {
    VisaPrincipalBo bo = visaMapper.selectByPersonIdTaskIdAndCode(personId, taskId,templateCode);
    return bo;
  }

  @Override
  public boolean audit(Long principalId, int status, String auditMsg) {
    VisaPrincipal visaPrincipal = new VisaPrincipal();
    visaPrincipal.setFlowStatus(status);
    visaPrincipal.setFlowMsg(auditMsg);
    visaPrincipal.setId(principalId);
    return  visaMapper.audit(visaPrincipal);
  }

  @Override
  public ExpressDeliveryBo selectExpressDeliveryByTaskId(Long taskId) {
    return expressDeliveryMapper.selectDeliveryBoByTaskId(taskId);
  }

  @Override
  public boolean deliverySave(ExpressDeliveryBo bo) {
    
    ExpressDelivery ed = new ExpressDelivery();
    BeanUtil.copyProperties(bo, ed);
    expressDeliveryMapper.save(ed);
    return true;
  }

  @Override
  public boolean deliveryUpdate(ExpressDeliveryBo bo) {
    ExpressDelivery ed = new ExpressDelivery();
    BeanUtil.copyProperties(bo, ed);
    expressDeliveryMapper.update(ed);
    return true;
  }

  @Override
  public boolean updateVisaStatus(Long visaPrincipal, VisaStatus status) {
    
    visaMapper.batchUpdateStatus(visaPrincipal, status.getCode());
    return true;
  }

  @Override
  public int getTaskCount(TaskPo taskPo) {
    
    return visaMapper.getTaskCount(taskPo);
  }

  @Override
  public List<TaskBo> selectTaskByCondition(TaskPo taskPo) {
    return visaMapper.selectTaskByCondition(taskPo);
  }

  @Override
  public int getVisaPrincipalCount(TaskPo taskPo) {
   
    return visaMapper.getVisaPrincipalCount(taskPo);
  }

  @Override
  public List<TaskBo> selectVisaPrincipalByCondition(TaskPo taskPo) {
    
    return visaMapper.selectVisaPrincipalByCondition(taskPo);
  }

  @Override
  public VisaPrincipalBo getVisaPrincipal(Long visaPrincipalId) {
    
    return visaMapper.getVisaPrincipal(visaPrincipalId);
  }

  @Override
  public boolean updateVisaStatusByTaskId(Long taskId, VisaStatus status) {
    visaMapper.updateVisaStatusByTaskId(taskId,status.getCode());
    return true;
  }
  
  @Override
  public int getVisaPrincipalCountForToDo(TaskPo taskPo) {
   
    return visaMapper.getVisaPrincipalCountForTodo(taskPo);
  }

  @Override
  public List<TaskBo> selectVisaPrincipalByConditionForToDo(TaskPo taskPo) {
    
    return visaMapper.selectVisaPrincipalByConditionForTodo(taskPo);
  }

  @Override
  public int getNewVisaPrincipalCount(TaskPo taskPo) {
    return visaMapper.getNewVisaPrincipalCount(taskPo);
  }

  @Override
  public List<TaskBo> selectNewVisaPrincipalByCondition(TaskPo taskPo) {
    return visaMapper.selectNewVisaPrincipalByCondition(taskPo);
  }

}
