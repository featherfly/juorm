
package cn.featherfly.juorm.jdbc.dsl.execute;

import cn.featherfly.juorm.dsl.Repository;
import cn.featherfly.juorm.dsl.execute.ExecutableConditionGroupExpression;
import cn.featherfly.juorm.dsl.execute.ExecutableExecutableUpdate;
import cn.featherfly.juorm.jdbc.Jdbc;
import cn.featherfly.juorm.sql.dml.builder.basic.SqlUpdateSetBasicBuilder;
import cn.featherfly.juorm.sql.model.UpdateColumnElement.SetType;

/**
 * <p>
 * SqlExecutableUpdate
 * </p>
 *
 * @author zhongj
 */
public class SqlExecutableUpdate
        implements SqlUpdate, ExecutableExecutableUpdate<SqlExecutableUpdate> {

    private String tableName;

    private Jdbc jdbc;

    private SqlUpdateSetBasicBuilder builder;

    /**
     * @param tableName
     *            tableName
     * @param jdbc
     *            jdbc
     */
    public SqlExecutableUpdate(String tableName, Jdbc jdbc) {
        this.tableName = tableName;
        this.jdbc = jdbc;
        builder = new SqlUpdateSetBasicBuilder(jdbc.getDialect(), tableName);
    }

    /**
     * @param repository
     * @param jdbc
     */
    public SqlExecutableUpdate(Repository repository, Jdbc jdbc) {
        this(repository.name(), jdbc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlExecutableUpdate set(String name, Object value) {
        builder.setValue(name, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <N extends Number> SqlExecutableUpdate increase(String name,
            N value) {
        builder.setValue(name, value, SetType.INCR);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExecutableConditionGroupExpression where() {
        return new SqlUpdateExpression(jdbc, tableName, builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int execute() {
        return new SqlUpdateExpression(jdbc, tableName, builder).execute();
    }
}
