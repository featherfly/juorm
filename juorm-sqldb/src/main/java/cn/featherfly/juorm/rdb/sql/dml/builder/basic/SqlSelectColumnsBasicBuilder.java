package cn.featherfly.juorm.rdb.sql.dml.builder.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.featherfly.common.constant.Chars;
import cn.featherfly.common.lang.LangUtils;
import cn.featherfly.juorm.dml.AliasManager;
import cn.featherfly.juorm.mapping.ClassMapping;
import cn.featherfly.juorm.mapping.MappingFactory;
import cn.featherfly.juorm.operator.AggregateFunction;
import cn.featherfly.juorm.rdb.jdbc.mapping.ClassMappingUtils;
import cn.featherfly.juorm.rdb.sql.dialect.Dialect;
import cn.featherfly.juorm.rdb.sql.dml.SqlBuilder;
import cn.featherfly.juorm.rdb.sql.model.SelectColumnElement;

/**
 * <p>
 * sql select basic builder. columns with given table
 * </p>
 *
 * @author zhongj
 */
public class SqlSelectColumnsBasicBuilder implements SqlBuilder {

    protected String tableAlias;

    protected List<SelectColumnElement> columns = new ArrayList<>(0);

    protected Dialect dialect;

    protected cn.featherfly.juorm.mapping.ClassMapping<?> classMapping;

    protected Map<String, String> fetchProperties = new HashMap<>(0);

    protected MappingFactory mappingFactory;

    /**
     * @param dialect
     *            dialect
     */
    public SqlSelectColumnsBasicBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    /**
     * @param dialect
     *            dialect
     * @param tableAlias
     *            table name alias
     */
    public SqlSelectColumnsBasicBuilder(Dialect dialect, String tableAlias) {
        this.dialect = dialect;
        this.tableAlias = tableAlias;
    }

    /**
     * @param dialect
     *            dialect
     * @param classMapping
     *            classMapping
     * @param mappingFactory
     *            mappingFactory
     */
    public SqlSelectColumnsBasicBuilder(Dialect dialect,
            ClassMapping<?> classMapping, MappingFactory mappingFactory) {
        this(dialect, classMapping,
                AliasManager.generateAlias(classMapping.getRepositoryName()),
                mappingFactory);
    }

    /**
     * @param dialect
     *            dialect
     * @param classMapping
     *            classMapping
     * @param tableAlias
     *            table name alias
     * @param mappingFactory
     *            mappingFactory
     */
    public SqlSelectColumnsBasicBuilder(Dialect dialect,
            ClassMapping<?> classMapping, String tableAlias,
            MappingFactory mappingFactory) {
        this.dialect = dialect;
        this.classMapping = classMapping;
        this.tableAlias = tableAlias;
        this.mappingFactory = mappingFactory;
    }

    /**
     * 返回alias
     *
     * @return alias
     */
    public String getTableAlias() {
        return tableAlias;
    }

    /**
     * 设置alias
     *
     * @param tableAlias
     *            tableAlias
     */
    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    /**
     * add column
     *
     * @param column
     *            column
     * @param aggregateFunction
     *            aggregateFunction
     * @return this
     */
    public SqlSelectColumnsBasicBuilder addSelectColumn(String column,
            AggregateFunction aggregateFunction) {
        columns.add(new SelectColumnElement(dialect, column, tableAlias,
                aggregateFunction));
        return this;
    }

    /**
     * add column
     *
     * @param column
     *            column
     * @param aggregateFunction
     *            aggregateFunction
     * @param asName
     *            alias name
     * @return this
     */
    public SqlSelectColumnsBasicBuilder addSelectColumn(String column,
            AggregateFunction aggregateFunction, String asName) {
        columns.add(new SelectColumnElement(dialect, column, tableAlias,
                aggregateFunction, asName));
        return this;
    }

    /**
     * add column
     *
     * @param column
     *            column
     * @return this
     */
    public SqlSelectColumnsBasicBuilder addSelectColumn(String column) {
        columns.add(new SelectColumnElement(dialect, column, tableAlias));
        return this;
    }

    /**
     * add column
     *
     * @param column
     *            column
     * @param asName
     *            asName
     * @return this
     */
    public SqlSelectColumnsBasicBuilder addSelectColumn(String column,
            String asName) {
        columns.add(
                new SelectColumnElement(dialect, column, tableAlias, asName));
        return this;
    }

    /**
     * addColumns
     *
     * @param columns
     *            columns
     * @return this
     */
    public SqlSelectColumnsBasicBuilder addSelectColumns(String... columns) {
        for (String c : columns) {
            addSelectColumn(c);
        }
        return this;
    }

    /**
     * addColumns
     *
     * @param columns
     *            columns
     * @return this
     */
    public SqlSelectColumnsBasicBuilder addSelectColumns(
            Collection<String> columns) {
        for (String c : columns) {
            addSelectColumn(c);
        }
        return this;
    }

    /**
     * addSelectProperty
     * 
     * @param propertyName
     * @param aliasName
     * @return this
     */
    public SqlSelectColumnsBasicBuilder addSelectProperty(String propertyName,
            String aliasName) {
        fetchProperties.put(propertyName, aliasName);
        return this;
    }

    /**
     * addSelectProperties
     * 
     * @param properties
     * @return this
     */
    public SqlSelectColumnsBasicBuilder addSelectProperties(
            Map<String, String> properties) {
        fetchProperties.putAll(properties);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String build() {
        StringBuilder columnsBuilder = new StringBuilder();
        if (columns.isEmpty()) {
            if (classMapping == null) {
                if (LangUtils.isEmpty(tableAlias)) {
                    columnsBuilder.append(Chars.STAR);
                } else {
                    columnsBuilder.append(tableAlias).append(Chars.DOT)
                            .append(Chars.STAR);
                }
            } else {
                if (fetchProperties.isEmpty()) {
                    columnsBuilder.append(ClassMappingUtils.getSelectColumnsSql(
                            classMapping, tableAlias, dialect));
                } else {
                    columnsBuilder.append(ClassMappingUtils.getSelectColumnsSql(
                            classMapping, tableAlias, dialect, mappingFactory,
                            fetchProperties));
                }
            }
        } else {
            for (SelectColumnElement column : columns) {
                // 基础构建器一个实例对应一个table
                column.setTableAlias(tableAlias);
                columnsBuilder.append(column).append(Chars.COMMA)
                        .append(Chars.SPACE);
            }
            columnsBuilder.delete(columnsBuilder.length() - 2,
                    columnsBuilder.length());
        }
        return columnsBuilder.toString();
    }
}