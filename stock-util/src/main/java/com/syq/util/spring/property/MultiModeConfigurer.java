package com.syq.util.spring.property;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class MultiModeConfigurer extends PropertyPlaceholderConfigurer {
  
  private static final Properties properties = new Properties();

  protected void convertProperties(Properties props) {
    super.convertProperties(props);
    properties.putAll(props);
  }

  public static String getPropertyByKey(String key) {
    return properties.getProperty(key);
  }

  static {
    String argName = "profile";
    String devConf = "dev";
    String profile = System.getProperty(argName);
    if (profile == null) {
      profile = devConf;
    }

    System.setProperty(argName, profile);
    System.out.println("----------------------------------------------");
    System.out.println("You are using the configuration in folder " + profile);
    System.out.println("----------------------------------------------");
    try {
      Thread.sleep(2000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
