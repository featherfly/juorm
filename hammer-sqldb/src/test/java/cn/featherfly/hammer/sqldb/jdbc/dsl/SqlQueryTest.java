
package cn.featherfly.hammer.sqldb.jdbc.dsl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import cn.featherfly.common.structure.page.PaginationResults;
import cn.featherfly.hammer.expression.SimpleRepository;
import cn.featherfly.hammer.sqldb.SqldbHammerException;
import cn.featherfly.hammer.sqldb.jdbc.JdbcTestBase;
import cn.featherfly.hammer.sqldb.jdbc.dsl.query.SqlQuery;
import cn.featherfly.hammer.sqldb.jdbc.vo.Role;
import cn.featherfly.hammer.sqldb.jdbc.vo.Tree2;
import cn.featherfly.hammer.sqldb.jdbc.vo.User;
import cn.featherfly.hammer.sqldb.jdbc.vo.UserInfo;
import cn.featherfly.hammer.sqldb.jdbc.vo.UserRole2;

/**
 * <p>
 * SqlQueryTest
 * </p>
 *
 * @author zhongj
 */
public class SqlQueryTest extends JdbcTestBase {

    @Test
    void test0() {
        SqlQuery query = new SqlQuery(jdbc, metadata);
        List<Map<String, Object>> list = query.find("user")
                .property("username", "password", "age").sort().asc("age")
                .list();
        int age = Integer.MIN_VALUE;
        for (Map<String, Object> map : list) {
            Integer a = (Integer) map.get("age");
            System.err.println(age + "    " + a);
            assertTrue(age <= a);
            age = a;
        }
    }

    @Test
    void test1() {
        SqlQuery query = new SqlQuery(jdbc, metadata);
        query.find("user").list(User.class);
        query.find("user").property("username", "password", "age")
                .list(User.class);
        query.find("user").property("username", "password", "age").where()
                .eq("username", "yufei").and().eq("password", "123456").and()
                .group().gt("age", 18).and().lt("age", 60).list(User.class);
    }

    @Test
    void test2() {
        SqlQuery query = new SqlQuery(jdbc, metadata);
        query.find("user").property("username", "password", "age").where()
                .eq("username", "yufei").and().eq("password", "123456").and()
                .group().gt("age", 18).and().lt("age", 60).list(User.class);
    }

    @Test
    void test3() {
        SqlQuery query = new SqlQuery(jdbc, metadata);
        query.find(new SimpleRepository("user", "u")).where()
                .eq("username", "yufei").and().eq("password", "123456").and()
                .group().gt("age", 18).and().lt("age", 60).list(User.class);
    }

    @Test
    void test4() {
        SqlQuery query = new SqlQuery(jdbc, metadata);
        query.find(new SimpleRepository("user", "u"))
                .property("username", "password", "age").where()
                .eq("username", "yufei").and().eq("password", "123456").and()
                .group().gt("age", 18).and().lt("age", 60).list(User.class);
    }

    @Test
    void testCount() {
        SqlQuery query = new SqlQuery(jdbc, mappingFactory);
        Long number = query.find("user").count();
        System.out.println("count:" + number);
        assertTrue(number > 0);

        Long number2 = query.find(User.class).count();
        System.out.println("count:" + number2);
        assertTrue(number2 > 0);
        assertEquals(number, number2);

        number = query.find("user").where().eq("age", 5).count();
        assertTrue(number == 2);

        number2 = query.find(User.class).where().eq("age", 5).count();
        assertTrue(number2 == 2);
        assertEquals(number, number2);

    }

    @Test
    void testLimit() {
        SqlQuery query = new SqlQuery(jdbc, metadata);
        int pageSize = 3;
        Integer total = 10;
        List<Role> roleList = query.find(new SimpleRepository("role")).where()
                .le("id", total).limit(2, pageSize).list(Role.class);
        assertEquals(roleList.size(), pageSize);

        List<Map<String, Object>> roleList2 = query
                .find(new SimpleRepository("role")).where().le("id", total)
                .limit(2, pageSize).list();
        assertEquals(roleList2.size(), pageSize);

        List<Integer> roleList3 = query.find(new SimpleRepository("role"))
                .where().le("id", total).limit(2, pageSize)
                .list((res, rowNum) -> res.getInt("id"));
        assertEquals(roleList3.size(), pageSize);

        PaginationResults<Role> rolePage = query
                .find(new SimpleRepository("role")).where().le("id", total)
                .limit(2, pageSize).pagination(Role.class);
        assertEquals(rolePage.getTotal(), total);
        assertEquals(rolePage.getPageResults().size(), pageSize);

        PaginationResults<Map<String, Object>> rolePage2 = query
                .find(new SimpleRepository("role")).where().le("id", total)
                .limit(2, pageSize).pagination();
        assertEquals(rolePage2.getTotal(), total);
        assertEquals(rolePage2.getPageResults().size(), pageSize);

        PaginationResults<Integer> rolePage3 = query
                .find(new SimpleRepository("role")).where().le("id", total)
                .limit(2, pageSize)
                .pagination((res, rowNum) -> res.getInt("id"));
        assertEquals(rolePage3.getTotal(), total);
        assertEquals(rolePage3.getPageResults().size(), pageSize);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testMapping() {
        SqlQuery query = new SqlQuery(jdbc, mappingFactory);

        query.find(User.class).where().eq("username", "yufei").and()
                .eq("pwd", "123456").and().group().gt("age", 18).and()
                .lt("age", 60).list();

        query.find(User.class).where().eq("username", "yufei").and()
                .eq("pwd", "123456").and().group().gt("age", 18).and()
                .property("age").lt(60).list();

        query.find(User.class).property("username", "pwd", "age").where()
                .eq("username", "yufei").and().eq("pwd", "123456").and().group()
                .gt("age", 18).and().lt("age", 60).list();

        query.find(User.class)
                .property(User::getUsername, User::getPwd, User::getAge).where()
                .eq("username", "yufei").and().eq("pwd", "123456").and().group()
                .gt("age", 18).and().lt("age", 60).list();
        /*
         * query.find(User.class).with(UserInfo.class, UserInfo::getUser)
         * query.find(UserInfo.class).with(UserInfo::getUser, User.class)
         * query.find(UserInfo.class).with(UserInfo::getUser)
         * query.find("user").with("user_info").on("user_id",
         * "id").with("user_role").on("user_id", "id").with("role",
         * "user_role").on("id", "role_id")
         * query.find("user_info").with("user_id", "user")
         */
        query.find(User.class)
                .property(User::getUsername, User::getPwd, User::getAge).where()
                .eq("username", "yufei").and().eq(User::getPwd, "123456").and()
                .group().gt(User::getAge, 18).and().lt(User::getAge, 60).list();
    }

    @Test
    void testNestedMapping() {
        SqlQuery query = new SqlQuery(jdbc, mappingFactory);
        Integer userId = 1;
        UserInfo userInfo = query.find(UserInfo.class).where()
                .eq("user.id", userId).single();
        assertEquals(userInfo.getUser().getId(), userId);
        System.out.println(userInfo);

        String province = "四川";
        userInfo = query.find(UserInfo.class).where()
                .eq("division.province", province).single();
        assertEquals(userInfo.getDivision().getProvince(), province);
        System.out.println(userInfo);
    }

    @Test
    void testJoin() {
        SqlQuery query = new SqlQuery(jdbc, mappingFactory);

        query.find("user").property("username", "password", "age")
                .with("user_info").on("user_id").list();

        query.find("user").property("username", "password", "age")
                .with("user_info").on("user_id").fetch("name").list();

        query.find("user").property("username", "password", "age")
                .with("user_info").on("user_id").fetch().list();

        query.find("user").property("username", "password", "age")
                .with("user_info").on("user_id").fetch("name").list();

        query.find("user").property("username", "password", "age")
                .with("user_info").on("user_id").fetch("name").fetch().list();

        query.find("user").property("username", "password", "age")
                .with("user_info").on("user_id").fetch("name").fetch("descp")
                .list();

        query.find("user").property("username", "password", "age")
                .with("user_role").on("user_id").with("role")
                .on("id", "user_role", "role_id").fetch().list();

        query.find("user").property("username", "password", "age")
                .with(UserInfo.class).on("user_id").list();

        query.find("user").property("username", "password", "age")
                .with(UserInfo.class).on("user_id").fetch().list();

        query.find("tree").with("tree").on("parent_id").list();

        query.find("tree").with("tree").on("parent_id").with("tree")
                .on("parent_id").list();

        query.find("user_info").with("user").on("id", "user_id")
                .fetchAlias("password", "pwd").fetch().list();
    }

    @Test
    void testJoinCondition() {
        SqlQuery query = new SqlQuery(jdbc, mappingFactory);

        List<Map<String, Object>> list = query.find("user")
                .property("username", "password", "age").with("user_info")
                .on("user_id").where().eq("user_info", "name", "羽飞").list();
        assertEquals(list.size(), 1);

        list = query.find("user").property("username", "password", "age")
                .with("user_info").on("user_id").where().eq(1, "name", "羽飞")
                .list();
        assertEquals(list.size(), 1);

        list = query.find("user").property("username", "password", "age")
                .with("user_info").on("user_id").where()
                .property("user_info", "name").eq("羽飞").list();
        assertEquals(list.size(), 1);

        list = query.find("user").property("username", "password", "age")
                .with("user_info").on("user_id").where().property(1, "name")
                .eq("羽飞").list();
        assertEquals(list.size(), 1);
    }

    @Test
    void testJoin2() {
        SqlQuery query = new SqlQuery(jdbc, mappingFactory);

        UserInfo userInfo = query.find(UserInfo.class).with(UserInfo::getUser)
                .where().eq(UserInfo::getId, 1).single();
        System.err.println(userInfo);
        assertNull(userInfo.getUser().getUsername());
        userInfo = query.find(UserInfo.class).with(UserInfo::getUser).fetch()
                .where().eq(UserInfo::getId, 1).single();
        assertNotNull(userInfo.getUser().getUsername());
        System.err.println(userInfo);

        query.find(UserInfo.class).with(UserInfo::getUser)
                .with(UserRole2::getUser).with(UserRole2::getRole).list();

        query.find(Tree2.class).with(Tree2::getParent).list();

        query.find(Tree2.class).with(Tree2::getParent).with(Tree2::getParent, 1)
                .list();
    }

    @Test
    void testJoinCondition2() {
        SqlQuery query = new SqlQuery(jdbc, mappingFactory);
        Integer userInfoId = 1;
        Integer userId = 1;
        User user = null;

        user = query.find(User.class).with(UserInfo::getUser).where()
                .eq(1, "id", userInfoId).single();
        assertEquals(user.getId(), userId);

        user = query.find(User.class).with(UserInfo::getUser).where()
                .eq("user_info", "id", userInfoId).single();
        assertEquals(user.getId(), userId);

        user = query.find(User.class).with(UserInfo::getUser).where()
                .eq(UserInfo.class, "id", userInfoId).single();
        assertEquals(user.getId(), userId);

        user = query.find(User.class).with(UserInfo::getUser).where()
                .eq(User::getId, userId).single();
        assertEquals(user.getId(), userId);

        user = query.find(User.class).with(UserInfo::getUser).where()
                .eq(UserInfo::getId, userInfoId).single();
        assertEquals(user.getId(), userId);

        user = query.find(User.class).with(UserInfo::getUser).where()
                .property(UserInfo::getId).eq(userInfoId).single();
        assertEquals(user.getId(), userId);
    }

    @Test
    void testJoin3() {
        SqlQuery query = new SqlQuery(jdbc, mappingFactory);

        List<Tree2> list1 = query.find(Tree2.class).list();
        list1.forEach(v -> {
            System.err.println(v);
        });

        List<Tree2> list2 = query.find(Tree2.class).with(Tree2::getParent)
                .list();
        list2.forEach(v -> {
            assertNotNull(v.getParent().getId());
            System.err.println(v);
        });

        list2 = query.find(Tree2.class).with(Tree2::getParent)
                .with(Tree2::getParent).list();
        list2.forEach(v -> {
            assertNotNull(v.getParent().getId());
            System.err.println(v);
        });

        List<Tree2> list3 = query.find(Tree2.class).with(Tree2::getParent)
                .fetch().list();
        list3.forEach(v -> {
            assertNotNull(v.getParent().getId());
            assertNotNull(v.getParent().getName());
            System.err.println(v);
        });

        list3 = query.find(Tree2.class).with(Tree2::getParent).fetch()
                .with(Tree2::getParent, 1).fetch().list();
        list3.forEach(v -> {
            assertNotNull(v.getParent().getId());
            assertNotNull(v.getParent().getName());
            System.err.println(v);
        });

        assertTrue(list1.size() > list2.size());

        // query.find(UserInfo.class).with(UserRole2::getUser).list();

        // query.find(User.class).with(UserRole2::getUser);
        // query.find(User.class).with(UserRole2::getUser)
        // .with(UserRole2::getRole);
        // query.find(User.class).with(UserRole2::getUser).with(UserRole2::getRole).where();
        // query.find(UserInfo.class).with(UserInfo::getUser).on(propertyName);
    }

    @Test(expectedExceptions = SqldbHammerException.class)
    void testJoinExceptions() {
        SqlQuery query = new SqlQuery(jdbc, mappingFactory);
        query.find(UserInfo.class).with(UserRole2::getUser).list();
    }

    // @Test
    // void test1111() {
    // ClassMapping<?> classMapping =
    // mappingFactory.getClassMapping(Tree2.class);
    // System.err.println(ClassMappingUtils.getSelectColumnsSql(classMapping,
    // "t0", Dialects.MYSQL, mappingFactory,
    // new HashChainMap<String, String>().putChain("parent", "t1")));
    //
    // }
}