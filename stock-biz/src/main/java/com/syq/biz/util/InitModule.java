//package com.wzax.biz.util;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.dom4j.Document;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.itrus.pojo.Module;
//import com.itrus.pojo.Right;
//import com.syq.biz.domain.WzaxModule;
//import com.syq.biz.domain.WzaxRight;
//
//public class InitModule {
//	
//  /**
//   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
//   */
//  protected final static Logger LOG = LoggerFactory.getLogger(InitModule.class);
//
//	@SuppressWarnings("unchecked")
//	public static List<WzaxModule> initModule(String fileName) {
//		try {
//			InputStream is = InitModule.class.getClassLoader()
//					.getResourceAsStream(fileName);
//			SAXReader sax = new SAXReader();
//			Document document = sax.read(is);
//			Element root = document.getRootElement();
//
//			List<Module> moduleList = new ArrayList<Module>();
//			List<Element> moduleFirstElements = root.elements("module");
//			for (Element moduleFirstElement : moduleFirstElements) {
//			  WzaxModule firstModule = new WzaxModule();
//				firstModule.setUuid(moduleFirstElement.element("uuid")
//						.getText());
//				firstModule.setUuname(moduleFirstElement.element("uuname")
//						.getText());
//				firstModule.setName(moduleFirstElement.element("name")
//						.getText());
//				firstModule.setUrl(moduleFirstElement.element("url").getText());
//
//				List<Element> secondElements = moduleFirstElement
//						.elements("module");
//				for (Element secondmodule : secondElements) {
//				  WzaxModule secondModule = new WzaxModule();
//					secondModule.setUuid(secondmodule.element("uuid")
//							.getText());
//					secondModule.setUuname(secondmodule.element("uuname")
//							.getText());
//					String namsec = secondmodule.element("name")
//							.getText();
//					String urlsec = secondmodule.element("url").getText();
//					secondModule.setName(namsec);
//					secondModule.setUrl(urlsec);
//					List<Element> rightsElements = secondmodule
//							.elements("rights");
//					if (rightsElements != null && rightsElements.size() != 0) {
//						List<Element> rightElements = rightsElements.get(0)
//								.elements("right");
//						if (rightElements != null) {
//							Set<Right> secondRightSet = new HashSet<Right>();
//							for (Element right : rightElements) {
//								Right rightsecond = new Right();
//								Element rightseconduuid = right.element("uuid");
//								Element rightsecondname = right.element("name");
//								Element rightsecondurl = right.element("url");
//								rightsecond.setName(rightsecondname.getText());
//								rightsecond.setUrl(rightsecondurl.getText());
//								rightsecond.setUuid(rightseconduuid.getText());
//
//								secondRightSet.add(rightsecond);
//							}
//							secondModule.setRights(secondRightSet);
//							secondModule.setParent(firstModule);
//							// 一级模块添加二级模块
//							if (firstModule.getChildren() == null)
//								firstModule.setChildren(new HashSet<Module>());
//							firstModule.getChildren().add(secondModule);
//						}
//					}
//				}
//
//				// 一级rights
//				List<Element> firstRights = moduleFirstElement
//						.elements("rights");
//				if (firstRights != null && firstRights.size() != 0) {
//					List<Element> rightTemps = firstRights.get(0).elements(
//							"right");
//					Set<Right> firstRightSet = new HashSet<Right>();
//					for (Element right : rightTemps) {
//						WzaxRight rightfirst = new WzaxRight();
//						Element rightfirstuuid = right.element("uuid");
//						Element rightfirstname = right.element("name");
//						Element rightfirsturl = right.element("url");
//						rightfirst.setName(rightfirstname.getText());
//						rightfirst.setUrl(rightfirsturl.getText());
//						rightfirst.setUuid(rightfirstuuid.getText());
//						firstRightSet.add(rightfirst);
//					}
//					firstModule.setRights(firstRightSet);
//				}
//				moduleList.add(firstModule);
//			}
//			return moduleList;
//		} catch (Exception e) {
//			log.error("加载权限文件出错,原因: "+e.getMessage());
//			e.printStackTrace();
//            return null;
//		}
//	}
//}
