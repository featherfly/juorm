
package cn.featherfly.hammer.expression.condition;

/**
 * <p>
 * RepositoryEqualsExpression
 * </p>
 * .
 *
 * @author zhongj
 * @param <C> the generic type
 * @param <L> the generic type
 */
public interface RepositoryEqualsExpression<C extends ConditionExpression, L extends LogicExpression<C, L>>
        extends EqualsExpression<C, L> {

    /**
     * 等于.
     *
     * @param repository 存储库
     * @param name       参数名称
     * @param value      参数值
     * @return LogicExpression
     */
    L eq(String repository, String name, Object value);

    /**
     * 等于.
     *
     * @param <T>        the generic type
     * @param repository 存储库
     * @param name       参数名称
     * @param value      参数值
     * @return LogicExpression
     */
    <T> L eq(Class<T> repository, String name, Object value);

    /**
     * 等于.
     *
     * @param repositoryIndex repository index
     * @param name            参数名称
     * @param value           参数值
     * @return LogicExpression
     */
    L eq(int repositoryIndex, String name, Object value);
}