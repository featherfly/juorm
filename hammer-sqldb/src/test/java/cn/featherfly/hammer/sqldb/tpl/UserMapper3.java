
package cn.featherfly.hammer.sqldb.tpl;

import java.util.List;
import java.util.Map;

import cn.featherfly.common.structure.page.Page;
import cn.featherfly.common.structure.page.PaginationResults;
import cn.featherfly.hammer.GenericHammer;
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
public interface UserMapper3 extends GenericHammer<User> {
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

    default User getByUsernameAndPassword(String username, String pwd) {
        return query().where().eq("username", username).and().eq("pwd", pwd).single();
    }

    default User getByUsernameAndPassword2(String username, String pwd) {
        return query().where().eq("username", username).and().eq("password", pwd).single();
    }

    default User getByUsernameAndPassword3(String username, String pwd) {
        return query().where().property("username").eq(username).and().property("pwd").eq(pwd).single();
    }

    default int updatePasswordByUsername(String username, String pwd) {
        //return update().set("password", pwd).where().eq("username", username).execute();
        return update().set(User::getPwd, pwd).where().eq(User::getUsername, username).execute();
    }

    default int deleteByUsername(String username) {
        //return delete().where().eq("username", username);
        return delete().where().eq(User::getUsername, username).execute();
    }
}