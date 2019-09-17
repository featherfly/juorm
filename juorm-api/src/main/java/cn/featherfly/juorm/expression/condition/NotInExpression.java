
package cn.featherfly.juorm.expression.condition;

import cn.featherfly.common.lang.function.SerializableFunction;

/**
 * <p>
 * NotInExpression
 * </p>
 *
 * @author zhongj
 */
public interface NotInExpression<C extends ConditionExpression, L extends LogicExpression<C, L>>
        extends ConditionExpression {

    /**
     * 不包含指定，sql中的not in
     *
     * @param name  参数名称
     * @param value 参数值
     * @return LogicExpression
     */
    L nin(String name, Object value);

    /**
     * 不包含指定，sql中的not in
     *
     * @param name  参数名称
     * @param value 参数值
     * @return LogicExpression
     */
    <T, R> L nin(SerializableFunction<T, R> name, Object value);

}