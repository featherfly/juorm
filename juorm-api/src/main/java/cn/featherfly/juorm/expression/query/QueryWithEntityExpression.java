
package cn.featherfly.juorm.expression.query;

import java.util.Collection;

import cn.featherfly.common.lang.function.SerializableFunction;
import cn.featherfly.juorm.expression.RepositoryConditionGroupLogicExpression;
import cn.featherfly.juorm.expression.RepositoryWhereExpression;
import cn.featherfly.juorm.expression.condition.RepositoryConditionsGroupExpression;

/**
 * <p>
 * dsl for query data
 * </p>
 *
 * @author zhongj
 */
public interface QueryWithEntityExpression<QW extends QueryWithExpression<QW, QWO, QWE, C, L>,
        QWO extends QueryWithOnExpression<QW, QWO, QWE, C, L>,
        QWE extends QueryWithEntityExpression<QW, QWO, QWE, C, L>, C extends RepositoryConditionsGroupExpression<C, L>,
        L extends RepositoryConditionGroupLogicExpression<C, L>>
        extends RepositoryWhereExpression<C, L>, QueryWithExpression<QW, QWO, QWE, C, L> {

    /**
     * <p>
     * 添加查询出来的属性
     * </p>
     *
     * @param propertyName propertyName
     * @return QueryWithEntityExpression
     */
    QWE fetch(String propertyName);

    /**
     * <p>
     * 添加查询出来的属性
     * </p>
     *
     * @param propertyNames propertyNames
     * @return QueryWithEntityExpression
     */
    QWE fetch(String... propertyNames);

    /**
     * <p>
     * 添加查询出来的属性
     * </p>
     *
     * @param propertyName propertyName
     * @return QueryWithEntityExpression
     */
    <T, R> QWE fetch(SerializableFunction<T, R> propertyName);

    /**
     * <p>
     * 添加查询出来的属性
     * </p>
     *
     * @param propertyNames propertyNames
     * @return QueryWithEntityExpression
     */
    <T, R> QWE fetch(@SuppressWarnings("unchecked") SerializableFunction<T, R>... propertyNames);

    /**
     * <p>
     * 添加查询出来的属性
     * </p>
     *
     * @param propertyNames propertyNames
     * @return QueryWithEntityExpression
     */
    QWE fetch(Collection<String> propertyNames);

    /**
     * 添加查询出来的所有属性
     *
     * @return QueryWithExpression
     */
    QW fetch();
}