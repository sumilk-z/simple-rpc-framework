package org.example.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/7 10:37
 ***/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RpcService {
    /**
     * service group
     * @return
     */
    String group() default "";

    /**
     * service version
     * @return
     */
    String version() default "";
}
