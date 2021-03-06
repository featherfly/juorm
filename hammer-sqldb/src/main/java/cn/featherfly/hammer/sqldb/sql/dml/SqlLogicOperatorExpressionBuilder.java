
package cn.featherfly.hammer.sqldb.sql.dml;

import cn.featherfly.common.db.builder.SqlBuilder;
import cn.featherfly.common.repository.operate.LogicOperator;
import cn.featherfly.hammer.expression.condition.LogicOperatorExpression;

/**
 * <p>
 * sql logic expression
 * </p>
 *
 * @author zhongj
 */
public class SqlLogicOperatorExpressionBuilder extends LogicOperatorExpression implements SqlBuilder {

    /**
     * @param logicOperator 逻辑运算符
     */
    public SqlLogicOperatorExpressionBuilder(LogicOperator logicOperator) {
        super(logicOperator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String build() {
        return getLogicOperator().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String expression() {
        return build();
    }
}
