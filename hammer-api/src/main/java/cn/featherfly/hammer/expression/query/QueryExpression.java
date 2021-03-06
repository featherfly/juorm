
package cn.featherfly.hammer.expression.query;

import cn.featherfly.hammer.expression.ConditionGroupExpression;
import cn.featherfly.hammer.expression.ConditionGroupLogicExpression;
import cn.featherfly.hammer.expression.Repository;
import cn.featherfly.hammer.expression.RepositoryConditionGroupLogicExpression;
import cn.featherfly.hammer.expression.TypeConditionGroupExpression;
import cn.featherfly.hammer.expression.TypeConditionGroupLogicExpression;
import cn.featherfly.hammer.expression.condition.RepositoryConditionsGroupExpression;

/**
 * <p>
 * dsl for query
 * </p>
 *
 * @author zhongj
 */
public interface QueryExpression<Q extends QueryEntityExpression<QP, QW, QWO, QWE, C, L, RC, RL>,
        QP extends QueryEntityPropertiesExpression<QP, QW, QWO, QWE, C, L, RC, RL>,
        QW extends QueryWithExpression<QW, QWO, QWE, RC, RL>, QWO extends QueryWithOnExpression<QW, QWO, QWE, RC, RL>,
        QWE extends QueryWithEntityExpression<QW, QWO, QWE, RC, RL>, C extends ConditionGroupExpression<C, L>,
        L extends ConditionGroupLogicExpression<C, L>, RC extends RepositoryConditionsGroupExpression<RC, RL>,
        RL extends RepositoryConditionGroupLogicExpression<RC, RL>,
        TQ extends TypeQueryEntityExpression<TQP, TQW, TQWE, TC, TL, RTC, RTL>,
        TQP extends TypeQueryEntityPropertiesExpression<TQP, TQW, TQWE, TC, TL, RTC, RTL>,
        TQW extends TypeQueryWithExpression<TQW, TQWE, RTC, RTL>,
        TQWE extends TypeQueryWithEntityExpression<TQW, TQWE, RTC, RTL>,
        TC extends TypeConditionGroupExpression<TC, TL>, TL extends TypeConditionGroupLogicExpression<TC, TL>,
        RTC extends RepositoryConditionsGroupExpression<RTC, RTL>,
        RTL extends RepositoryConditionGroupLogicExpression<RTC, RTL>> {
    /**
     * find repository
     *
     * @param repository repository
     * @return QueryEntity
     */
    Q find(Repository repository);

    // /**
    // * find repository
    // *
    // * @param repository repository
    // * @return QueryEntity
    // */
    // <T, R> Q find(SerializableFunction<T, R> repository);

    /**
     * find repository
     *
     * @param repository repository
     * @return QueryEntity
     */
    Q find(String repository);

    /**
     * find reposit type
     *
     * @param repositType repositType
     * @return QueryEntity
     */
    TQ find(Class<?> repositType);
}
