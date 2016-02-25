package com.wguzgg.raspberry.dao.hibernate.interceptor;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wguzgg.raspberry.annotation.HistoryAble;
import com.wguzgg.raspberry.dao.IDao;
import com.wguzgg.raspberry.dao.IHistoryOperator;
import com.wguzgg.raspberry.data.entity.IEntity;
import com.wguzgg.raspberry.util.Constant;
import com.wguzgg.raspberry.util.GlobalConfigure;
import com.wguzgg.raspberry.util.spring.SpringFactory;

public class HistoryInterceptor extends EmptyInterceptor {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    protected void operateHistory(Object entity, Object[] previousState, String[] propertyNames) {
        if (entity.getClass().isAnnotationPresent(HistoryAble.class)) {
            HistoryAble ha = entity.getClass().getAnnotation(HistoryAble.class);
            IDao dao = null;
            if (dao != null && dao instanceof IHistoryOperator) {
                IHistoryOperator ho = (IHistoryOperator) dao;
                if (entity instanceof IEntity) {
                    try {
                        Object previousEntity = entity.getClass().newInstance();
                        for (int i = 0 ; i < propertyNames.length ; i ++) {
                            if (previousState[i] != null) {
                                BeanUtils.setProperty(previousEntity, propertyNames[i], previousState[i]);
                            }
                        }
                        IEntity history = ho.buildHistory((IEntity) entity, (IEntity) previousEntity);
                        GlobalConfigure globalConfig = SpringFactory.getInstance().getBean(GlobalConfigure.class);
                        String historyHandlerClass = globalConfig.getString(Constant.GLOBAL_CATEGORY.category_default.name(), Constant.GLOBAL_DEFAULT_KEY_ENV_HISTORY_HANDLER_CLASS);
                        if (historyHandlerClass != null) {
                            IHistoryHandler historyHandler = SpringFactory.getInstance().getBean(Class.forName(historyHandlerClass));
                            historyHandler.recordHistory(history);
                        }
                    } catch (InstantiationException e) {
                        log.error("", e);
                    } catch (IllegalAccessException e) {
                        log.error("", e);
                    } catch (InvocationTargetException e) {
                        log.error("", e);
                    } catch (ClassNotFoundException e) {
                        log.error("", e);
                    }
                }
            }
        }
    }
    
    @Override
    public boolean onFlushDirty(Object entity, Serializable id,
            Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types) {
        if (true) {
            operateHistory(entity, previousState, propertyNames);
            return false;
        }
        
        
        
        //System.out.println("--------historyInterceptor.onFlushDirty, class: " + entity.getClass().getName() + ", id: " + id);
        if (currentState != null) {
            //System.out.println("--------------currentState:");
            for (Object obj : currentState) {
                //System.out.println("--------------------------" + obj);
            }
        }
        if (previousState != null) {
            //System.out.println("--------------previousState:");
            for (Object obj : previousState) {
                //System.out.println("--------------------------" + obj);
            }
        }
        if (propertyNames != null) {
            //System.out.println("--------------propertyNames:");
            for (String obj : propertyNames) {
                //System.out.println("--------------------------" + obj);
            }
        }
        if (types != null) {
            //System.out.println("--------------types:");
            for (Type obj : types) {
                //System.out.println("--------------------------" + obj.getName());
            }
        }
        return super.onFlushDirty(entity, id, currentState, previousState,
                propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state,
            String[] propertyNames, Type[] types) {
        if (true) {
            //operateHistory(entity);
            return false;
        }
        
        
        System.out.println("--------historyInterceptor.onsAVE, class: " + entity.getClass().getName() + ", id: " + id);
        if (state != null) {
            System.out.println("--------------state:");
            for (Object obj : state) {
                System.out.println("--------------------------" + obj);
            }
        }
        if (propertyNames != null) {
            System.out.println("--------------propertyNames:");
            for (String obj : propertyNames) {
                System.out.println("--------------------------" + obj);
            }
        }
        if (types != null) {
            System.out.println("--------------types:");
            for (Type obj : types) {
                System.out.println("--------------------------" + obj.getName());
            }
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public String onPrepareStatement(String sql) {
        return super.onPrepareStatement(sql);
    }
    
}
