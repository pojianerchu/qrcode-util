package com.winway.springUtils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author GuoYongMing
 * @Date 2021/1/20 15:19
 * @Version 1.0
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextProvider.applicationContext = applicationContext;
    }
    /**
     21      * 获取Spring上下文
     22      *
     23      * @return
     24      */
     public static ApplicationContext getApplicationContext() {
                return applicationContext;
             }

             /**
       * 通过name获取Bean
 31      *
 32      * @param name
 33      * @return
 34      */
             public static <T> T getBean(String name) {
                 return (T) getApplicationContext().getBean(name);
             }

             /**
 40      * 通过class获取Bean
 41      *
 42      * @param clazz
 43      * @param <T>
 44      * @return
 45      */
        public static <T> T getBean(Class<T> clazz) {
            return getApplicationContext().getBean(clazz);
        }
       /**
       * 通过name,以及Clazz返回指定的Bean
       *
       * @param name
       * @param clazz
       * @param <T>
       * @return
       */
             public static <T> T getBean(String name, Class<T> clazz) {
                 return getApplicationContext().getBean(name, clazz);
            }




}
