
package cn.featherfly.juorm.rdb.jdbc.dsl.query;

import java.util.Map;

import cn.featherfly.juorm.dsl.query.QueryEntity;
import cn.featherfly.juorm.dsl.query.QueryEntityProperties;

/**
 * <p>
 * SqlQueryEntity
 * </p>
 *
 * @author zhongj
 */
public interface SqlQueryEntity extends QueryEntity {

    /**
     * <p>
     * 添加select的列
     * </p>
     *
     * @param columnName propertyName
     * @return QueryEntityPropertiesExpression
     */
    QueryEntityProperties propertyAlias(String columnName, String asName);

    /**
     * <p>
     * 批量添加select的列
     * </p>
     *
     * @param columnNameMap key is columnName, value is asName
     * @return QueryEntityPropertiesExpression
     */
    QueryEntityProperties propertyAlias(Map<String, String> columnNameMap);

}