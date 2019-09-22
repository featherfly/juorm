
package cn.featherfly.juorm.expression.query;

import java.util.Collection;

import cn.featherfly.common.lang.function.SerializableFunction;
import cn.featherfly.juorm.expression.ConditionGroupExpression;
import cn.featherfly.juorm.expression.ConditionGroupLogicExpression;
import cn.featherfly.juorm.expression.WhereExpression;

/**
 * <p>
 * dsl for query data
 * </p>
 *
 * @author zhongj
 */
public interface TypeQueryEntityExpression<
        Q extends TypeQueryEntityPropertiesExpression<Q, QW, QWE, C, L>,
        QW extends TypeQueryWithExpression<QW, QWE, C, L>,
        QWE extends TypeQueryWithEntityExpression<QW, QWE, C, L>,
        C extends ConditionGroupExpression<C, L>,
        L extends ConditionGroupLogicExpression<C, L>>
        extends WhereExpression<C, L>, TypeQueryWithExpression<QW, QWE, C, L>,
        TypeQueryListExecutor, TypeQueryConditionLimit {
    /**
     * 设置id
     *
     * @param propertyName
     * @return
     */
    Q id(String propertyName);

    /**
     * 设置id
     *
     * @param propertyName
     * @return
     */
    <T, R> Q id(SerializableFunction<T, R> propertyName);

    /**
     * <p>
     * 添加select的列
     * </p>
     *
     * @param propertyName
     *            propertyName
     * @return QueryEntityPropertiesExpression
     */
    Q property(String propertyName);

    /**
     * <p>
     * 批量添加select的列
     * </p>
     *
     * @param propertyNames
     *            propertyNames
     * @return QueryEntityPropertiesExpression
     */
    Q property(String... propertyNames);

    /**
     * <p>
     * 添加select的列
     * </p>
     *
     * @param propertyName
     *            propertyName
     * @return QueryEntityPropertiesExpression
     */
    <T, R> Q property(SerializableFunction<T, R> propertyName);

    /**
     * <p>
     * 批量添加select的列
     * </p>
     *
     * @param propertyNames
     *            propertyNames
     * @return QueryEntityPropertiesExpression
     */
    <T, R> Q property(
            @SuppressWarnings("unchecked") SerializableFunction<T, R>... propertyNames);

    /**
     * <p>
     * 批量添加select的列
     * </p>
     *
     * @param propertyNames
     *            propertyNames
     * @return QueryEntityPropertiesExpression
     */
    Q property(Collection<String> propertyNames);
}
