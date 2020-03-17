
package cn.featherfly.hammer.tpl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * TplExecution
 * </p>
 *
 * @author zhongj
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface TplParam {

    String value() default "";

    TplParamType type() default TplParamType.QUERY;

}