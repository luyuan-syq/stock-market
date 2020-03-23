package com.syq.biz.service;

import java.util.List;

import com.syq.biz.bo.VisaFileFlowBo;
import com.syq.biz.bo.VisaFileTemplateBo;
import com.syq.biz.domain.VisaFileTemplate;
import com.syq.pagination.common.VisaFileTemplatePo;


public interface IVisaFileTemplateService {
  
  List<VisaFileTemplate> selectVisaFileTemplateByCondition(VisaFileTemplatePo po);
  
  Long save(VisaFileTemplate visaFileTemplate);
  
  int deleteByIds(Long[] ids);
  
  List<VisaFileTemplate> selectVisaFileTemplateByCountry(VisaFileTemplatePo po);
  
  List<VisaFileTemplateBo> selectForFlow(VisaFileTemplatePo po);
  
  boolean updateFileStatus(VisaFileFlowBo bo);
  
  boolean assertAllFlowPass(VisaFileFlowBo bo);
  
  boolean assertAllFlowAudit(VisaFileFlowBo bo);
  
  boolean assertVisaFileFlow(VisaFileFlowBo bo);
  
  List<VisaFileFlowBo> selectVisaFileFlow(VisaFileFlowBo bo);
  
  List<VisaFileTemplateBo> selectForSubmit(VisaFileTemplatePo po);
  
  Long selectCountryAndFile(Long countryId,Long fileId);
  
  int saveCountryAndFile(Long countryId,Long fileId);
  
  int selectCountCAFByFileId(Long fileId);
  
  boolean deleteCountryAndFile(Long countryId,Long fileId);
  
  boolean deleteTextValue(Long countryId,Long fileId);
  
  VisaFileTemplateBo selectVisaFileTemplateBoById(Long id);
  
  VisaFileFlowBo selectVisaFileFlowById(Long id);

}
