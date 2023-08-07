package org.example.annotation;

import java.lang.annotation.*;

/***
 * @Description
 * @Author zhucui
 * @DateTime 2023/8/7 23:52
 ***/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {
}
