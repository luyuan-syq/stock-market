package com.syq.util;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * 对JSON操作的工具类
 * @author shao_yuqi@itrus.com.cn
 *
 */
public class JsonUtil {
	
  /** 记录日志的变量 */
  private static final Logger  logger        = LoggerFactory.getLogger(JsonUtil.class);
  /** 静态ObjectMapper */
  private ObjectMapper         mapper;

  private static JsonUtil jsonUtil = new JsonUtil();

  private static final int     ARRAY_MAX     = 1024;
  
  /**
   * 私有构造函数
   */
  private JsonUtil() {
      mapper = new ObjectMapper();
      // mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
  
  
  /**
   * 获得ObjectMapper实例
   * 
   * @return ObjectMapper
   */
  public static ObjectMapper getInstance() {
      return jsonUtil.mapper;
  }
  
  /**
   * JSON对象转换为JavaBean
   * 
   * @param json JSON对象
   * @param valueType Bean类
   * @return 泛型对象
   */
  public static <T> T jsonToBean(String json,
                                 Class<T> valueType) {
      if (json == null || json.length() == 0) {
          return null;
      }
      try {
          return getInstance().readValue(json, valueType);
      } catch (JsonParseException e) {
          logger.error(e.getMessage(), e);
      } catch (JsonMappingException e) {
          logger.error(e.getMessage(), e);
      } catch (IOException e) {
          logger.error(e.getMessage(), e);
      }
      return null;
  }

  /**
   * JavaBean转换为JSON字符串
   * 
   * @param bean JavaBean对象
   * @return json字符串
   */
  public static String beanToJson(Object bean) {
      StringWriter sw = new StringWriter();
      JsonGenerator gen = null;
      try {
          gen = new JsonFactory().createJsonGenerator(sw);
          getInstance().writeValue(gen, bean);
          gen.close();
      } catch (IOException e) {
          logger.error(e.getMessage(), e);
      }

      return sw.toString();
  }

  /**
   * 
   * 功能描述: <br>
   * 〈功能详细描述〉
   * 
   * @param <T>
   * 
   * @param json
   * @param type
   * @return
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonParseException
   * @see [相关类/方法](可选)
   * @since [产品/模块版本](可选)
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> jsonToList(String json,
                                       Class<T> clazz) {
      T[] t = (T[]) Array.newInstance(clazz, ARRAY_MAX);
      try {
          t = (T[]) getInstance().readValue(json, t.getClass());
          return (List<T>) Arrays.asList(t);
      } catch (JsonGenerationException e) {
          logger.error(e.getMessage(), e);
      } catch (JsonMappingException e) {
          logger.error(e.getMessage(), e);
      } catch (IOException e) {
          logger.error(e.getMessage(), e);
      }
      return null;
  }

  /**
   * 
   * 功能描述: <br>
   * 〈功能详细描述〉
   * 
   * @param t
   * @return
   * @see [相关类/方法](可选)
   * @since [产品/模块版本](可选)
   */
  public static String listToJson(List<?> t) {
      try {
          return getInstance().writeValueAsString(t);
      } catch (JsonGenerationException e) {
          logger.error(e.getMessage(), e);
      } catch (JsonMappingException e) {
          logger.error(e.getMessage(), e);
      } catch (IOException e) {
          logger.error(e.getMessage(), e);
      }
      return null;
  }

}
