
package cn.featherfly.hammer.expression.condition;

import java.util.Date;

import cn.featherfly.common.lang.function.ReturnDateFunction;
import cn.featherfly.common.lang.function.ReturnEnumFunction;
import cn.featherfly.common.lang.function.ReturnNumberFunction;
import cn.featherfly.common.lang.function.ReturnStringFunction;
import cn.featherfly.common.lang.function.SerializableFunction;
import cn.featherfly.hammer.expression.condition.property.DateExpression;
import cn.featherfly.hammer.expression.condition.property.EnumExpression;
import cn.featherfly.hammer.expression.condition.property.NumberExpression;
import cn.featherfly.hammer.expression.condition.property.ObjectExpression;
import cn.featherfly.hammer.expression.condition.property.StringExpression;

/**
 * <p>
 * PropertyConditionExpression
 * </p>
 * .
 *
 * @author zhongj
 * @param <C> the generic type
 * @param <L> the generic type
 */
public interface PropertyExpression<C extends ConditionExpression, L extends LogicExpression<C, L>>
        extends ConditionExpression {

    /**
     * Property.
     *
     * @param name the name
     * @return the object expression
     */
    ObjectExpression<C, L> property(String name);

    /**
     * Property string.
     *
     * @param name the name
     * @return the string expression
     */
    StringExpression<C, L> propertyString(String name);

    /**
     * Property number.
     *
     * @param name the name
     * @return the number expression
     */
    NumberExpression<C, L> propertyNumber(String name);

    /**
     * Property date.
     *
     * @param name the name
     * @return the date expression
     */
    DateExpression<C, L> propertyDate(String name);

    /**
     * Property enum.
     *
     * @param name the name
     * @return the enum expression
     */
    EnumExpression<C, L> propertyEnum(String name);

    /**
     * Property.
     *
     * @param <T>  the generic type
     * @param <R>  the generic type
     * @param name the name
     * @return the object expression
     */
    <T, R> ObjectExpression<C, L> property(SerializableFunction<T, R> name);

    /**
     * Property string.
     *
     * @param <T>  the generic type
     * @param name the name
     * @return the string expression
     */
    <T> StringExpression<C, L> property(ReturnStringFunction<T> name);

    /**
     * Property number.
     *
     * @param <T>  the generic type
     * @param <R>  the generic type
     * @param name the name
     * @return the number expression
     */
    <T, R extends Number> NumberExpression<C, L> property(ReturnNumberFunction<T, R> name);

    /**
     * Property date.
     *
     * @param <T>  the generic type
     * @param <R>  the generic type
     * @param name the name
     * @return the date expression
     */
    <T, R extends Date> DateExpression<C, L> property(ReturnDateFunction<T, R> name);

    /**
     * Property enum.
     *
     * @param <T>  the generic type
     * @param <R>  the generic type
     * @param name the name
     * @return the enum expression
     */
    <T, R extends Enum<?>> EnumExpression<C, L> property(ReturnEnumFunction<T, R> name);

    /**
     * Property string.
     *
     * @param <T>  the generic type
     * @param name the name
     * @return the string expression
     * @deprecated use {@link #property(ReturnStringFunction)} instead
     */
    @Deprecated
    <T> StringExpression<C, L> propertyString(SerializableFunction<T, String> name);

    /**
     * Property number.
     *
     * @param <T>  the generic type
     * @param <R>  the generic type
     * @param name the name
     * @return the number expression
     * @deprecated use {@link #property(ReturnNumberFunction)} instead
     */
    @Deprecated
    <T, R extends Number> NumberExpression<C, L> propertyNumber(SerializableFunction<T, R> name);

    /**
     * Property date.
     *
     * @param <T>  the generic type
     * @param <R>  the generic type
     * @param name the name
     * @return the date expression
     * @deprecated use {@link #property(ReturnDateFunction)} instead
     */
    @Deprecated
    <T, R extends Date> DateExpression<C, L> propertyDate(SerializableFunction<T, R> name);

    /**
     * Property enum.
     *
     * @param <T>  the generic type
     * @param <R>  the generic type
     * @param name the name
     * @return the enum expression
     * @deprecated use {@link #property(ReturnEnumFunction)} instead
     */
    @Deprecated
    <T, R extends Enum<?>> EnumExpression<C, L> propertyEnum(SerializableFunction<T, R> name);
}
