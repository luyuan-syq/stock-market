package com.syq.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syq.biz.bo.VisaFileFlowBo;
import com.syq.biz.bo.VisaFileTemplateBo;
import com.syq.biz.domain.VisaFileTemplate;
import com.syq.pagination.common.VisaFileTemplatePo;

@Repository
public interface VisaFileTemplateMapper {
  
  List<VisaFileTemplate> selectByCondition(VisaFileTemplatePo po);
  
  Long save(VisaFileTemplate po);
  
  int delete(Long[] ids);
  
  List<VisaFileTemplate> selectByCountry(VisaFileTemplatePo po);
  
  List<VisaFileTemplateBo> selectForFlow(VisaFileTemplatePo po);
  
  int updateFileStatus(VisaFileTemplateBo bo);
  
  VisaFileFlowBo selectVisaFileFlow(Long cafileId,Long principalId);
  
  int insertVisaFileFlow(VisaFileFlowBo bo);
  
  int updateVisaFileFlowStatus(VisaFileFlowBo bo);
  
  List<VisaFileFlowBo> selectVisaFileFlowByCondtion(VisaFileFlowBo bo);
  
  List<VisaFileTemplateBo> selectForSubmit(VisaFileTemplatePo po);
  
  Long selectCountryAndFile(Long countryId,Long fileId);
  
  int saveCountryAndFile(Long countryId,Long fileId);
  
  int selectCountCAFByFileId(Long fileId);
  
  int deleteCountryAndFile(Long countryId,Long fileId);
  
  VisaFileTemplateBo selectVisaFileTemplateBoById(Long id);
  
  VisaFileFlowBo selectVisaFileFlowById(Long id);
  
  int selectCountCAFByCountryId(Long countryId);
  
}
