package com.syq.web.common;


public interface ErrorCode {
  
  /**
   * 添加用户错误
   */
  String ACCOUNT_ADD = "100001";
  /**
   * 删除用户错误
   */
  String ACCOUNT_DELETE = "100002";
  /**
   * 编辑用户的ID不能为空
   */
  String ACCOUNT_UPDATE_IDISBLANK = "100003";
  /**
   * 根据ID查询不到对应的用户
   */
  String ACCOUNT_UPDATE_IDNOTEXIST = "100004";
  /**
   * 保存用户异常
   */
  String ACCOUNT_UPDATE = "100005";
  
  String GENERATE_TASK_CODE = "100101";
  
  String COUNTRY_ADD = "100201";
  /**
   * 删除国家错误
   */
  String COUNTRY_DELETE = "100202";
  /**
   * 修改国家错误
   */
  String COUNTRY_UPDATE = "100203";
  /**
   * 删除出差人员错误
   */
  String PERSON_DELETE = "100301";
  
  String APPLY_PASSPORT_INFO_ADD = "100401";
  
  String AUDIT_PASSPORT_INFO = "100501";

}
