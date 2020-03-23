package com.syq.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syq.biz.service.IModuleService;

public class ModuleService implements IModuleService{
  /**
   * 默认生成的该类的LOG记录器，使用slf4j组件。避免产生编译警告，使用protected修饰符。
   */
  protected final static Logger LOG = LoggerFactory.getLogger(ModuleService.class);

  @Override
  public void initModule() {
//    List<Module> modules = InitModule.initModule("module.xml");
//    for(Module m : modules) {
//        Module mfpojo = findModuleByUUID(m.getUuid());
//        mfpojo = updateModuleByNewModule(mfpojo,m);
//        
//        Set<Module> childrenModule = m.getChildren();
//        if(childrenModule != null) {
//            Iterator<Module> it = childrenModule.iterator();
//            while(it.hasNext()) {
//                Module mt = it.next();
//                Module mspojo = findModuleByUUID(mt.getUuid());
//                if(mspojo != null)
//                    mfpojo.getChildren().remove(mspojo);
//                mspojo = updateModuleByNewModule(mspojo,mt);
//                if(mfpojo.getChildren() == null)
//                    mfpojo.setChildren(new HashSet<Module>());
//                mfpojo.getChildren().add(mspojo);
//                
//                Set<Right> secondRights = mt.getRights();
//                if(secondRights != null) {
//                    Iterator<Right> itr = secondRights.iterator();
//                    while(itr.hasNext()) {
//                        Right rt = itr.next();
//                        Right rpojo = findRightByUUID(rt.getUuid());
//                        if(mspojo.getRights() == null)
//                            mspojo.setRights(new HashSet<Right>());
//                        if(rpojo != null)
//                            mspojo.getRights().remove(rpojo);
//                        rpojo = updateRightByNewRight(rpojo,rt);
//                        
//                        mspojo.getRights().add(rpojo);
//                    }
//                }
//            }
//        }
//        Set<Right> firstRights = m.getRights();
//        if(firstRights != null) {
//            Iterator<Right> it = firstRights.iterator();
//            while(it.hasNext()) {
//                Right rt = it.next();
//                Right rpojo = findRightByUUID(rt.getUuid());
//                if(mfpojo.getRights() == null)
//                    mfpojo.setRights(new HashSet<Right>());
//                if(rpojo != null)
//                    mfpojo.getRights().remove(rpojo);
//                rpojo = updateRightByNewRight(rpojo,rt);
//                mfpojo.getRights().add(rpojo);
//            }
//        }
//        commonDAO.saveOrUpdate(mfpojo);
//    }
    
  }
}
