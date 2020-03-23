package com.syq.biz.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.syq.biz.bo.ApplyPassportInfoBo;
import com.syq.biz.domain.ApplyPassportInfo;

@Repository
public interface ApplyPassportInfoMapper extends BaseMapper<ApplyPassportInfo>{
  
  List<ApplyPassportInfoBo> getApplyPassportInfoByPersonId(Long personId);
}
