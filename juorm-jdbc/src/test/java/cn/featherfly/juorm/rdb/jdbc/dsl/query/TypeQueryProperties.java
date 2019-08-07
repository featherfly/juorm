
package cn.featherfly.juorm.rdb.jdbc.dsl.query;

import cn.featherfly.juorm.rdb.jdbc.mapping.MappingFactory;

/**
 * <p>
 * TypeQueryEntity
 * </p>
 *
 * @author zhongj
 */
public abstract class TypeQueryProperties<E,
        C extends TypeQueryConditionGroupExpression<E, C>,
        Q extends TypeQueryProperties<E, C, Q>>
        extends TypeQueryEntity<E, C, Q> {

    private SqlQueryEntityProperties queryEntityProperties;

    /**
     * 
     * @param queryEntityProperties
     * @param mappingFactory
     */
    public TypeQueryProperties(SqlQueryEntityProperties queryEntityProperties,
            MappingFactory mappingFactory) {
        super(queryEntityProperties, mappingFactory);
        this.queryEntityProperties = queryEntityProperties;
    }

    @SuppressWarnings("unchecked")
    public Q property(String propertyName) {
        queryEntityProperties.property(propertyName);
        setProperty = true;
        return (Q) this;
    }

    @SuppressWarnings("unchecked")
    public Q property(String columnName, String asName) {
        queryEntityProperties.property(columnName, asName);
        setProperty = true;
        return (Q) this;
    }

}