//
//package cn.featherfly.hammer.sqldb.jdbc;
//
//import java.math.BigDecimal;
//import java.sql.PreparedStatement;
//import java.util.List;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
//import cn.featherfly.common.db.JdbcUtils;
//import cn.featherfly.common.db.dialect.Dialect;
//import cn.featherfly.common.db.mapping.SqlResultSet;
//import cn.featherfly.common.db.mapping.SqlTypeMappingManager;
//import cn.featherfly.common.lang.ArrayUtils;
//import cn.featherfly.common.repository.mapping.RowMapper;
//
///**
// * <p>
// * Jdbc
// * </p>
// * .
// *
// * @author zhongj
// */
//public class SpringJdbcTemplateImpl implements Jdbc {
//
//    private JdbcTemplate jdbcTemplate;
//
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    /** The data source. */
//    protected DataSource dataSource;
//
//    /** The dialect. */
//    protected Dialect dialect;
//
//    /** The manager. */
//    protected SqlTypeMappingManager manager;
//
//    /** The logger. */
//    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    /**
//     * Instantiates a new spring jdbc template impl.
//     */
//    public SpringJdbcTemplateImpl() {
//        this(new SqlTypeMappingManager());
//    }
//
//    /**
//     * Instantiates a new spring jdbc template impl.
//     *
//     * @param manager the manager
//     */
//    public SpringJdbcTemplateImpl(SqlTypeMappingManager manager) {
//        super();
//        this.manager = manager;
//    }
//
//    /**
//     * Instantiates a new spring jdbc template impl.
//     *
//     * @param dataSource dataSource
//     * @param dialect    dialect
//     */
//    public SpringJdbcTemplateImpl(DataSource dataSource, Dialect dialect) {
//        this(dataSource, dialect, new SqlTypeMappingManager());
//    }
//
//    /**
//     * Instantiates a new spring jdbc template impl.
//     *
//     * @param dataSource dataSource
//     * @param dialect    dialect
//     * @param manager    the manager
//     */
//    public SpringJdbcTemplateImpl(DataSource dataSource, Dialect dialect, SqlTypeMappingManager manager) {
//        super();
//        setDataSource(dataSource);
//        this.dialect = dialect;
//        this.manager = manager;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T execute(ConnectionCallback<T> action) {
//        return jdbcTemplate.execute(
//                (org.springframework.jdbc.core.ConnectionCallback<T>) con -> action.doInConnection(con, manager));
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public DataSource getDataSource() {
//        return dataSource;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Dialect getDialect() {
//        return dialect;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public List<Map<String, Object>> query(String sql, Object... args) {
//        // FIXME 需要优化ArrayUtils.toString(args)在不需要debug时不调用，加入一个logger工具来实现
//        logger.debug("sql -> {}, args -> {}", sql, ArrayUtils.toString(args));
//        return jdbcTemplate.queryForList(sql, args);
//        //        return list(sql, new ColumnMapRowMapper(), args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public List<Map<String, Object>> query(String sql, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        return namedParameterJdbcTemplate.queryForList(sql, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        return namedParameterJdbcTemplate.query(sql, args, (rs, rowNum) -> {
//            return rowMapper.mapRow(new SqlResultSet(rs), rowNum);
//        });
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
//        // FIXME 需要优化ArrayUtils.toString(args)在不需要debug时不调用，加入一个logger工具来实现
//        logger.debug("sql -> {}, args -> {}", sql, ArrayUtils.toString(args));
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//            return rowMapper.mapRow(new SqlResultSet(rs), rowNum);
//        }, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> List<T> query(String sql, Class<T> elementType, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        return namedParameterJdbcTemplate.query(sql, args, new NestedBeanPropertyRowMapper<>(elementType, manager));
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> List<T> query(String sql, Class<T> elementType, Object... args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        return jdbcTemplate.query(sql, new NestedBeanPropertyRowMapper<>(elementType, manager), args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Map<String, Object> querySingle(String sql, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        try {
//            return namedParameterJdbcTemplate.queryForMap(sql, args);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Map<String, Object> querySingle(String sql, Object... args) {
//        // FIXME 需要优化ArrayUtils.toString(args)在不需要debug时不调用，加入一个logger工具来实现
//        logger.debug("sql -> {}, args -> {}", sql, ArrayUtils.toString(args));
//        try {
//            return jdbcTemplate.queryForMap(sql, args);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T querySingle(String sql, Class<T> elementType, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {} , type -> {}", sql, args, elementType.getName());
//        try {
//            return namedParameterJdbcTemplate.queryForObject(sql, args,
//                    new NestedBeanPropertyRowMapper<>(elementType, manager));
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T querySingle(String sql, RowMapper<T> rowMapper, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        try {
//            return namedParameterJdbcTemplate.queryForObject(sql, args, (rs, rowNum) -> {
//                return rowMapper.mapRow(new SqlResultSet(rs), rowNum);
//            });
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T querySingle(String sql, Class<T> elementType, Object... args) {
//        // FIXME 需要优化ArrayUtils.toString(args)在不需要debug时不调用，加入一个logger工具来实现
//        logger.debug("sql -> {}, args -> {} , type -> {}", sql, ArrayUtils.toString(args), elementType.getName());
//        try {
//            return jdbcTemplate.queryForObject(sql, new NestedBeanPropertyRowMapper<>(elementType, manager), args);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T querySingle(String sql, RowMapper<T> rowMapper, Object... args) {
//        // FIXME 需要优化ArrayUtils.toString(args)在不需要debug时不调用，加入一个logger工具来实现
//        logger.debug("sql -> {}, args -> {}", sql, ArrayUtils.toString(args));
//        try {
//            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
//                return rowMapper.mapRow(new SqlResultSet(rs), rowNum);
//            }, args);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Map<String, Object> queryUnique(String sql, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        return namedParameterJdbcTemplate.queryForMap(sql, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Map<String, Object> queryUnique(String sql, Object... args) {
//        // FIXME 需要优化ArrayUtils.toString(args)在不需要debug时不调用，加入一个logger工具来实现
//        logger.debug("sql -> {}, args -> {}", sql, ArrayUtils.toString(args));
//        return jdbcTemplate.queryForMap(sql, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T queryUnique(String sql, RowMapper<T> rowMapper, Object... args) {
//        // FIXME 需要优化ArrayUtils.toString(args)在不需要debug时不调用，加入一个logger工具来实现
//        logger.debug("sql -> {}, args -> {}", sql, ArrayUtils.toString(args));
//        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
//            return rowMapper.mapRow(new SqlResultSet(rs), rowNum);
//        }, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T queryUnique(String sql, RowMapper<T> rowMapper, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        return namedParameterJdbcTemplate.queryForObject(sql, args, (rs, rowNum) -> {
//            return rowMapper.mapRow(new SqlResultSet(rs), rowNum);
//        });
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T queryUnique(String sql, Class<T> elementType, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {} , type -> {}", sql, args, elementType.getName());
//        return namedParameterJdbcTemplate.queryForObject(sql, args,
//                new NestedBeanPropertyRowMapper<>(elementType, manager));
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T queryUnique(String sql, Class<T> elementType, Object... args) {
//        // FIXME 需要优化ArrayUtils.toString(args)在不需要debug时不调用，加入一个logger工具来实现
//        logger.debug("sql -> {}, args -> {} , type -> {}", sql, ArrayUtils.toString(args), elementType.getName());
//        return jdbcTemplate.queryForObject(sql, new NestedBeanPropertyRowMapper<>(elementType, manager), args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public BigDecimal queryBigDecimal(String sql, Map<String, Object> args) {
//        return queryValue(sql, BigDecimal.class, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public BigDecimal queryBigDecimal(String sql, Object... args) {
//        return queryValue(sql, BigDecimal.class, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Double queryDouble(String sql, Map<String, Object> args) {
//        return queryValue(sql, Double.class, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Double queryDouble(String sql, Object... args) {
//        return queryValue(sql, Double.class, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Integer queryInt(String sql, Map<String, Object> args) {
//        return queryValue(sql, Integer.class, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Integer queryInt(String sql, Object... args) {
//        return queryValue(sql, Integer.class, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Long queryLong(String sql, Map<String, Object> args) {
//        return queryValue(sql, Long.class, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Long queryLong(String sql, Object... args) {
//        return queryValue(sql, Long.class, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public String queryString(String sql, Map<String, Object> args) {
//        return queryValue(sql, String.class, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public String queryString(String sql, Object... args) {
//        return queryValue(sql, String.class, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T queryValue(String sql, Class<T> valueType, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {}, type -> {}", sql, args, valueType.getName());
//        return namedParameterJdbcTemplate.queryForObject(sql, args, valueType);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T queryValue(String sql, Class<T> valueType, Object... args) {
//        // FIXME 需要优化ArrayUtils.toString(args)在不需要debug时不调用，加入一个logger工具来实现
//        logger.debug("sql -> {}, args -> {}, type -> {}", sql, ArrayUtils.toString(args), valueType.getName());
//        return jdbcTemplate.queryForObject(sql, valueType, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T queryValue(String sql, RowMapper<T> rowMapper, Object... args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
//            return rowMapper.mapRow(new SqlResultSet(rs), rowNum);
//        }, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public <T> T queryValue(String sql, RowMapper<T> rowMapper, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        return namedParameterJdbcTemplate.queryForObject(sql, args, (rs, rowNum) -> {
//            return rowMapper.mapRow(new SqlResultSet(rs), rowNum);
//        });
//    }
//
//    /**
//     * 设置dataSource.
//     *
//     * @param dataSource dataSource
//     */
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//        jdbcTemplate = new JdbcTemplate(dataSource);
//        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//    }
//
//    /**
//     * 设置dialect.
//     *
//     * @param dialect dialect
//     */
//    public void setDialect(Dialect dialect) {
//        this.dialect = dialect;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public int update(String sql, Object... args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        return jdbcTemplate.update(sql, args);
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public int update(String sql, Map<String, Object> args) {
//        logger.debug("sql -> {}, args -> {}", sql, args);
//        return namedParameterJdbcTemplate.update(sql, args);
//    }
//
//    //    private <E> List<E> list(String sql, org.springframework.jdbc.core.RowMapper<E> rowMapper, Object... args) {
//    //        return jdbcTemplate.query(connection -> {
//    //            PreparedStatement prep = connection.prepareStatement(sql);
//    //            logger.debug("sql -> {}, args -> {}", sql, ArrayUtils.toString(args));
//    //            setParams(prep, args);
//    //            prep.executeUpdate();
//    //            return prep;
//    //        }, rowMapper);
//    //    }
//
//    /**
//     * Sets the params.
//     *
//     * @param prep the prep
//     * @param args the args
//     */
//    protected void setParams(PreparedStatement prep, Object... args) {
//        for (int i = 0; i < args.length; i++) {
//            JdbcUtils.setParameter(prep, i + 1, args[i]);
//        }
//    }
//}
