
package cn.featherfly.juorm.rdb.tpl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.featherfly.common.structure.page.Page;
import cn.featherfly.common.structure.page.PaginationResults;
import cn.featherfly.common.structure.page.SimplePagination;
import cn.featherfly.juorm.Juorm;
import cn.featherfly.juorm.rdb.jdbc.JdbcTestBase;
import cn.featherfly.juorm.rdb.jdbc.JuormJdbcImpl;
import cn.featherfly.juorm.rdb.jdbc.vo.User;
import cn.featherfly.juorm.tpl.mapper.TplDynamicExecutorFactory;

/**
 * <p>
 * SqlTplExecutorTest
 * </p>
 *
 * @author zhongj
 */
public class SqlTplDynamicExecutorTest extends JdbcTestBase {

    UserMapper userMapper;

    @BeforeClass
    void setup() {
        TplDynamicExecutorFactory mapperFactory = TplDynamicExecutorFactory.getInstance();
        Juorm juorm = new JuormJdbcImpl(jdbc, mappingFactory, configFactory);
        userMapper = mapperFactory.newInstance(UserMapper.class, juorm);
    }

    @Test
    void testMapperString() {
        String str = userMapper.selectString();
        System.out.println("selectString = " + str);
        assertEquals(str, "yufei");

        str = userMapper.selectString2(2);
        System.out.println("selectString = " + str);
        assertEquals(str, "featherfly");
    }

    @Test
    void testMapperNumber() {
        Integer avg = userMapper.selectAvg();
        System.out.println("avg(age) = " + avg);
        assertTrue(avg > 20);

        avg = userMapper.selectAvg2(40);
        System.out.println("avg(age) = " + avg);
        assertTrue(avg > 40);

    }

    @Test
    void testMapperSingle() {
        String username = "yufei";
        User u = userMapper.selectByUsername(username);
        System.out.println(u);
        assertEquals(u.getUsername(), username);

        String password = "123456";
        u = userMapper.selectByUsernameAndPassword(username, password);
        System.out.println(u);
        assertEquals(u.getUsername(), username);
        assertEquals(u.getPwd(), password);
    }

    @Test
    void testMapperSingleMap() {
        String username = "yufei";
        Map<String, Object> u = userMapper.selectByUsername2(username);
        System.out.println(u);
        assertEquals(u.get("username"), username);
    }

    @Test
    void testMapperList() {
        List<User> us = userMapper.selectByAge2(10);
        System.out.println(us);
        assertEquals(us.size(), 2);
    }

    @Test
    void testMapperListMap() {
        List<Map<String, Object>> us = userMapper.select2();
        System.out.println(us);
        assertEquals(us.size(), 2);

        us = userMapper.selectById2(1);
        System.out.println(us);
        assertEquals(us.size(), 1);
    }

    @Test
    void testMapperPage() {
        int limit = 1;
        int age = 10;
        Page page = new SimplePagination(0, limit);

        List<User> list = userMapper.selectByAge2(age, 0, limit);
        System.out.println(list.size());
        System.out.println(list);
        assertEquals(list.size(), limit);

        list = userMapper.selectByAge2(age, page);
        System.out.println(list.size());
        System.out.println(list);
        assertEquals(list.size(), limit);

        PaginationResults<User> us = userMapper.selectByAge2Page(age, 0, limit);
        System.out.println(us.getResultSize());
        System.out.println(us.getPageResults());
        assertEquals(us.getResultSize(), new Integer(limit));

        us = userMapper.selectByAge2Page(age, page);
        System.out.println(us.getResultSize());
        System.out.println(us.getPageResults());
        assertEquals(us.getResultSize(), new Integer(limit));
    }

    @Test
    void testMapperPageMap() {
        int limit = 1;
        Page page = new SimplePagination(0, limit);

        List<Map<String, Object>> list = userMapper.select2(page);
        System.out.println(list.size());
        System.out.println(list);
        assertEquals(list.size(), limit);

        list = userMapper.select2(0, limit);
        System.out.println(list.size());
        System.out.println(list);
        assertEquals(list.size(), limit);

        PaginationResults<Map<String, Object>> us = userMapper.select2Page(page);
        System.out.println(us.getResultSize());
        System.out.println(us.getPageResults());

        assertEquals(us.getResultSize(), new Integer(limit));

        us = userMapper.select2Page(0, limit);
        System.out.println(us.getResultSize());
        System.out.println(us.getPageResults());

        assertEquals(us.getResultSize(), new Integer(limit));

    }
}
