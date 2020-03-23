package com.syq.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syq.biz.bo.VisaCountryBo;
import com.syq.biz.bo.VisaCountryBoData;
import com.syq.biz.domain.VisaCountry;
import com.syq.biz.mapper.VisaCountryMapper;
import com.syq.biz.service.IVisaCountryService;
import com.syq.util.convert.BeanUtil;

@Service
public class VisaCountryService implements IVisaCountryService{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(VisaCountryService.class);
  
  @Autowired
  private VisaCountryMapper visaCountryMapper;

  @Override
  public List<VisaCountryBo> selectAll() {
    return visaCountryMapper.selectByCondition();
  }
  
  public VisaCountryBo selectByCode(String code) {
    return visaCountryMapper.selectByCode(code);
  }

  public boolean save(VisaCountryBo bo) {
    VisaCountry visaCountry = new VisaCountry();
    BeanUtil.copyProperties(bo, visaCountry);
    //添加配置文件
    
    return 1 == visaCountryMapper.save(visaCountry);
  }
  
  public boolean deleteById(long id) {
    
    return 1 == visaCountryMapper.deleteByIds(new Long[]{id});
  }

  public VisaCountryBo selectById(long id) {
    
    VisaCountry visaCountry = visaCountryMapper.selectById(id);
    
    if(visaCountry == null) {
      return null;
    }
    
    VisaCountryBo bo = new VisaCountryBo();
    BeanUtil.copyProperties(visaCountry, bo);
    return bo;
  }
  
  public boolean update(VisaCountryBo bo) {
    VisaCountry visaCountry = new VisaCountry();
    BeanUtil.copyProperties(bo, visaCountry);
    
    return 1== visaCountryMapper.update(visaCountry);
  }

  @Override
  public List<VisaCountryBoData> queryVisaCountryData() {
    // TODO Auto-generated method stub
    return visaCountryMapper.queryVisaCountryData();
  }

  @Override
  public List<VisaCountryBo> queryVisaCountryNamesForCode(List<String> codeList) {
    // TODO Auto-generated method stub
    List<VisaCountryBo> visaCountryNames = visaCountryMapper.queryVisaCountryNamesForCode(codeList);
    return visaCountryNames;
  }
  
  
}
