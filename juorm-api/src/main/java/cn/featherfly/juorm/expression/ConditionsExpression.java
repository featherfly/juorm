
package cn.featherfly.juorm.expression;

/**
 * <p>
 * condition expression
 * </p>
 *
 * @author zhongj
 */
public interface ConditionsExpression<C extends ConditionsExpression<C, L>, L extends LogicExpression<C, L>>
        extends ContainsExpression<C, L>, EndWithExpression<C, L>, EqualsExpression<C, L>, GreatEqualsExpressoin<C, L>,
        GreatThanExpressoin<C, L>, InExpression<C, L>, IsNotNullExpression<C, L>, IsNullExpression<C, L>,
        LessEqualsExpressoin<C, L>, LessThanExpressoin<C, L>, NotEqualsExpression<C, L>, NotInExpression<C, L>,
        StartWithExpression<C, L> {
}
