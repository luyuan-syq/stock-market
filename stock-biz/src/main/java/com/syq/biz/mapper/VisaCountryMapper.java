package com.syq.biz.mapper;

import java.util.List;

import com.syq.biz.bo.VisaCountryBo;
import com.syq.biz.bo.VisaCountryBoData;
import com.syq.biz.domain.VisaCountry;


public interface VisaCountryMapper extends BaseMapper<VisaCountry>{
  
  List<VisaCountryBo> selectByCondition();
  
  VisaCountryBo selectByCode(String code);
  
  List<VisaCountryBoData> queryVisaCountryData();
  
  List<VisaCountryBo> queryVisaCountryNamesForCode(List<String> codeList);
  
}
