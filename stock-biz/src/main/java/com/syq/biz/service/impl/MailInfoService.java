package com.syq.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syq.biz.domain.MailInfo;
import com.syq.biz.mapper.MailInfoMapper;
import com.syq.biz.service.IMailInfoService;

@Service
public class MailInfoService implements IMailInfoService {
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(MailInfoService.class);

  @Autowired
  private MailInfoMapper mailInfoMapper;

  @Override
  public boolean saveOrUpdate(MailInfo mailInfo) {
    return mailInfoMapper.saveOrUpdate(mailInfo);
  }

  @Override
  public MailInfo select() {
    return mailInfoMapper.select(null);
  }

//  public Map<String, Object> sendMail(MailInfo mailInfo, String toAccount, String message,
//      String subject) {
//    Map<String, Object> result = new HashMap<String, Object>();
//    try {
//      Authenticator auth = new Authenticator() {
//        public PasswordAuthentication getPasswordAuthentication() {
//          return new PasswordAuthentication(userName, password);
//        }
//      };
//      Session session = Session.getInstance(prepareProperties(), auth);
//      MimeMessage msg = new MimeMessage(session);
//      msg.setFrom(new InternetAddress(adminMail));
//      Address[] addresses = new Address[nodes.size()];
//      for (int i = 0; i < nodes.size(); i++) {
//        Address address = new InternetAddress(nodes.get(i));
//        addresses[i] = address;
//      }
//      msg.setRecipients(RecipientType.TO, addresses);
//      msg.setSubject(subject);
//      MimeMultipart mimeMultipart = new MimeMultipart();
//      msg.setContent(mimeMultipart);
//      MimeBodyPart part = new MimeBodyPart();
//      part.setContent(message, "text/html;charset=utf-8");
//      mimeMultipart.addBodyPart(part);
//      Transport.send(msg);
//      result.put("isok", true);
//    } catch (Exception e) {
//      result.put("isok", false);
//      result.put("message", e.getMessage());
//      logger.error(e.getMessage(), e);
//    }
//    return result;
//  }


}
