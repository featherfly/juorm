
package cn.featherfly.hammer.sqldb.sql.dml;

import cn.featherfly.hammer.dml.builder.BuilderException;
import cn.featherfly.hammer.expression.condition.ParamedExpression;
import cn.featherfly.hammer.operator.QueryOperator;
import cn.featherfly.hammer.sqldb.sql.dialect.Dialect;
import cn.featherfly.hammer.sqldb.sql.model.ConditionColumnElement;

/**
 * <p>
 * sql condition expression sql 条件表达式
 * </p>
 *
 * @author zhongj
 */
public class SqlConditionExpressionBuilder implements ParamedExpression, SqlBuilder {

    private ConditionColumnElement conditionColumnElement;

    /**
     * @param dialect       dialect
     * @param name          名称
     * @param value         值
     * @param queryOperator 查询运算符（查询类型）
     */
    public SqlConditionExpressionBuilder(Dialect dialect, String name, Object value, QueryOperator queryOperator) {
        this(dialect, name, value, queryOperator, null);
    }

    /**
     * @param dialect       dialect
     * @param name          名称
     * @param queryAlias    查询别名
     * @param value         值
     * @param queryOperator 查询运算符（查询类型）
     */
    public SqlConditionExpressionBuilder(Dialect dialect, String name, Object value, QueryOperator queryOperator,
            String queryAlias) {
        if (queryOperator == null) {
            throw new BuilderException("#query.operator.null");
        }
        conditionColumnElement = new ConditionColumnElement(dialect, name, value, queryOperator, queryAlias);
    }

    /**
     * 返回conditionColumnElement
     *
     * @return conditionColumnElement
     */
    public ConditionColumnElement getConditionColumnElement() {
        return conditionColumnElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getParam() {
        return conditionColumnElement.getParam();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String build() {
        return conditionColumnElement.toSql();
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