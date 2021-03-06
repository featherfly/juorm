
package cn.featherfly.hammer.expression.condition;

import java.util.function.Function;

/**
 * <p>
 * repository condition expression
 * </p>
 *
 * @author zhongj
 */
public interface RepositoryConditionsGroupExpression<C extends RepositoryConditionsGroupExpression<C, L>,
        L extends LogicExpression<C, L>> extends RepositoryConditionsExpression<C, L> {

    /**
     * <p>
     * 在当前内部开启一个新的条件逻辑组,需要手动调用endGroup回到上一级表达式
     * </p>
     *
     * @return 新条件逻辑组
     */
    C group();

    /**
     * <p>
     * 把group对应的表达式组加入当前,无需调用endGroup.
     * </p>
     *
     * @param group the group function
     * @return 条件组结束后的逻辑表达式
     */
    L group(Function<C, L> group);
}
