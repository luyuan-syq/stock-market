package com.syq.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syq.biz.bo.VisaCountryBo;
import com.syq.biz.bo.VisaFileFlowBo;
import com.syq.biz.bo.VisaFileTemplateBo;
import com.syq.biz.bo.VisaPrincipalBo;
import com.syq.biz.domain.VisaFileTemplate;
import com.syq.biz.mapper.VisaCountryMapper;
import com.syq.biz.mapper.VisaFileTemplateMapper;
import com.syq.biz.mapper.VisaMapper;
import com.syq.biz.service.IVisaFileTemplateService;
import com.syq.pagination.common.VisaFileTemplatePo;
import com.syq.util.constant.FileFlowStatus;

@Service
public class VisaFileTemplateService implements IVisaFileTemplateService{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(VisaFileTemplateService.class);
  
  @Autowired
  private VisaFileTemplateMapper visaFileTemplateMapper;
  
  @Autowired
  private VisaMapper visaMapper;
  
  @Autowired
  private VisaCountryMapper visaCountryMapper;

  @Override
  public List<VisaFileTemplate> selectVisaFileTemplateByCondition(VisaFileTemplatePo po) {
    return visaFileTemplateMapper.selectByCondition(po);
  }

  @Override
  public Long save(VisaFileTemplate visaFileTemplate) {
    visaFileTemplateMapper.save(visaFileTemplate);
    return visaFileTemplate.getId();
  }

  @Override
  public int deleteByIds(Long[] ids) {
    return visaFileTemplateMapper.delete(ids);
  }

  @Override
  public List<VisaFileTemplate> selectVisaFileTemplateByCountry(VisaFileTemplatePo po) {
    return visaFileTemplateMapper.selectByCountry(po);
  }

  @Override
  public List<VisaFileTemplateBo> selectForFlow(VisaFileTemplatePo po) {
    return visaFileTemplateMapper.selectForFlow(po);
  }

  @Override
  public boolean updateFileStatus(VisaFileFlowBo bo) {
    return 1 == visaFileTemplateMapper.updateVisaFileFlowStatus(bo);
  }
  
  @Override
  public boolean assertVisaFileFlow(VisaFileFlowBo bo) {
    VisaFileFlowBo vffBo = visaFileTemplateMapper.selectVisaFileFlow(bo.getCafileId(),bo.getPrincipalId());
    if(vffBo == null) {
      //插入记录
      visaFileTemplateMapper.insertVisaFileFlow(bo);
    }
    return true;
  }

  @Override
  public List<VisaFileFlowBo> selectVisaFileFlow(VisaFileFlowBo bo) {
    
    return visaFileTemplateMapper.selectVisaFileFlowByCondtion(bo);
  }

  @Override
  public List<VisaFileTemplateBo> selectForSubmit(VisaFileTemplatePo po) {
    return visaFileTemplateMapper.selectForSubmit(po);
  }

  @Override
  public Long selectCountryAndFile(Long countryId, Long fileId) {
    return visaFileTemplateMapper.selectCountryAndFile(countryId,fileId);
  }
  
  public int selectCountCAFByFileId(Long fileId) {
    return visaFileTemplateMapper.selectCountCAFByFileId(fileId);
  }

  @Override
  public int saveCountryAndFile(Long countryId, Long fileId) {
    return visaFileTemplateMapper.saveCountryAndFile(countryId,fileId);
  }

  @Override
  public boolean deleteCountryAndFile(Long countryId, Long fileId) {
    int i = visaFileTemplateMapper.deleteCountryAndFile(countryId,fileId);
    return i == 1;
  }

  @Override
  public boolean deleteTextValue(Long countryId, Long fileId) {
    int fileResult = visaFileTemplateMapper.delete(new Long[]{fileId});
    if(fileResult == 1) {
      return 1 == visaFileTemplateMapper.deleteCountryAndFile(countryId,fileId);
    }
    return false;
  }

  @Override
  public VisaFileTemplateBo selectVisaFileTemplateBoById(Long id) {

    return visaFileTemplateMapper.selectVisaFileTemplateBoById(id);
  }

  @Override
  public boolean assertAllFlowPass(VisaFileFlowBo bo) {
    
    
    VisaFileFlowBo boTemp = new VisaFileFlowBo();
    boTemp.setPrincipalId(bo.getPrincipalId());
    List<VisaFileFlowBo> boes = visaFileTemplateMapper.selectVisaFileFlowByCondtion(boTemp);
    int count =0;
    for(VisaFileFlowBo vff:boes) {
      if(FileFlowStatus.AUDIT_PASS.getCode() <= vff.getFlowStatus()) {
        count++;
      }
    }
    if(count == boes.size()) {
      return true;
    }
    return false;
  }
  
  @Override
  public boolean assertAllFlowAudit(VisaFileFlowBo bo) {
    
    VisaPrincipalBo vpbo = visaMapper.getVisaPrincipal(bo.getPrincipalId());
    VisaCountryBo visaCountryBo = visaCountryMapper.selectByCode(vpbo.getTemplateCode());
    int cafCount = visaFileTemplateMapper.selectCountCAFByCountryId(visaCountryBo.getId());
    
    VisaFileFlowBo boTemp = new VisaFileFlowBo();
    boTemp.setPrincipalId(bo.getPrincipalId());
    List<VisaFileFlowBo> boes = visaFileTemplateMapper.selectVisaFileFlowByCondtion(boTemp);
    if(cafCount > boes.size()) {
      return false;
    }
    
    int count =0;
    for(VisaFileFlowBo vff:boes) {
      if(FileFlowStatus.AUDIT_WAIT.getCode() <= vff.getFlowStatus()) {
        count++;
      }
    }
    if(count == boes.size()) {
      return true;
    }
    return false;
  }

  @Override
  public VisaFileFlowBo selectVisaFileFlowById(Long id) {
    
    return visaFileTemplateMapper.selectVisaFileFlowById(id);
  }
}
