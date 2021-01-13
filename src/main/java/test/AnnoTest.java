package test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author GuoYongMing
 * @Date 2021/1/13 13:49
 * @Version 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface AnnoTest {

        String topic() default "";


}
