package com.syq.biz.service;

import java.util.List;

import com.syq.biz.bo.VisaCountryBo;
import com.syq.biz.bo.VisaCountryBoData;


public interface IVisaCountryService {
 
  List<VisaCountryBo> selectAll();
  
  VisaCountryBo selectByCode(String code);
  
  VisaCountryBo selectById(long id);
  
  boolean save(VisaCountryBo bo);
  
  boolean deleteById(long id);
  
  boolean update(VisaCountryBo bo);
  
  List<VisaCountryBoData> queryVisaCountryData();
  
  List<VisaCountryBo> queryVisaCountryNamesForCode(List<String> codeList);
}
