
package cn.featherfly.hammer.sqldb.tpl;

import java.util.List;
import java.util.Map;

import cn.featherfly.common.structure.page.Page;
import cn.featherfly.common.structure.page.PaginationResults;
import cn.featherfly.hammer.Hammer;
import cn.featherfly.hammer.sqldb.jdbc.vo.User;
import cn.featherfly.hammer.tpl.annotation.Mapper;
import cn.featherfly.hammer.tpl.annotation.TplExecution;
import cn.featherfly.hammer.tpl.annotation.TplParam;
import cn.featherfly.hammer.tpl.annotation.TplParamType;

/**
 * <p>
 * UserMapper
 * </p>
 *
 * @author zhongj
 */
@Mapper(namespace = "user")
public interface UserMapper2 extends Hammer {
    User selectByUsername(@TplParam("username") String username);

    Map<String, Object> selectByUsername2(@TplParam("username") String username);

    User selectByUsernameAndPassword(@TplParam("username") String username, @TplParam("password") String pwd);

    Integer selectAvg();

    String selectString();

    List<User> selectByAge2(@TplParam("age") Integer age);

    @TplExecution
    List<User> selectByAge2(@TplParam("age") Integer age, @TplParam(type = TplParamType.PAGE_OFFSET) int offset,
            @TplParam(type = TplParamType.PAGE_LIMIT) int limit);

    @TplExecution
    List<User> selectByAge2(@TplParam("age") Integer age, Page page);

    @TplExecution(name = "selectByAge2")
    PaginationResults<User> selectByAge2Page(@TplParam("age") Integer age,
            @TplParam(type = TplParamType.PAGE_OFFSET) int offset, @TplParam(type = TplParamType.PAGE_LIMIT) int limit);

    @TplExecution(name = "selectByAge2")
    PaginationResults<User> selectByAge2Page(@TplParam("age") Integer age, Page page);

    List<User> selectById(@TplParam("id") Integer id);

    Integer selectAvg2(@TplParam("age") Integer age);

    String selectString2(@TplParam("id") Integer id);

    @TplExecution(namespace = "user_info")
    List<Map<String, Object>> select2();

    @TplExecution(namespace = "user_info", name = "select2")
    List<Map<String, Object>> select2(@TplParam(type = TplParamType.PAGE_OFFSET) int offset,
            @TplParam(type = TplParamType.PAGE_LIMIT) int limit);

    @TplExecution(namespace = "user_info", name = "select2")
    List<Map<String, Object>> select2(Page page);

    @TplExecution(namespace = "user_info", name = "select2")
    PaginationResults<Map<String, Object>> select2Page(@TplParam(type = TplParamType.PAGE_OFFSET) int offset,
            @TplParam(type = TplParamType.PAGE_LIMIT) int limit);

    @TplExecution(namespace = "user_info", name = "select2")
    PaginationResults<Map<String, Object>> select2Page(Page page);

    @TplExecution(namespace = "user_info", name = "selectById")
    List<Map<String, Object>> selectById2(@TplParam("id") Integer id);
}