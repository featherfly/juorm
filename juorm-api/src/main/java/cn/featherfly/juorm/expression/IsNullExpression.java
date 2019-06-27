
package cn.featherfly.juorm.expression;

/**
 * <p>
 * IsNullExpression
 * </p>
 *
 * @author zhongj
 */
public interface IsNullExpression<C extends ConditionExpression, L extends LogicExpression<C, L>> extends ConditionExpression {

    /**
     * 包含指定，sql中的in
     *
     * @param name
     *            参数名称
     * @return LogicExpression
     */
    L isn(String name);
}