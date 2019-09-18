
package cn.featherfly.juorm.rdb.jdbc;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.featherfly.common.lang.RandomUtils;
import cn.featherfly.juorm.Juorm;
import cn.featherfly.juorm.rdb.config.SqlDbConfigurator;
import cn.featherfly.juorm.rdb.jdbc.vo.Article;
import cn.featherfly.juorm.rdb.jdbc.vo.Role;
import cn.featherfly.juorm.rdb.jdbc.vo.User;
import cn.featherfly.juorm.rdb.jdbc.vo.UserRole;

/**
 * <p>
 * SqlOrmTest
 * </p>
 *
 * @author zhongj
 */
public class JuormJdbcTest2 extends JdbcTestBase {

    Juorm juorm;

    @BeforeClass
    void before() {
        //        JuormSqlDbConstant constant = ConstantPool.getDefault().getConstant(JuormSqlDbConstant.class);
        //
        //        juormJdbc = new JuormJdbcImpl(jdbc, constant.getMappingFactory(), constant.getTplConfigFactory(),
        //                constant.getTemplateProcessor());
        juorm = SqlDbConfigurator.getDefault().getJuorm();
    }

    @Test
    public void testSave() {
        Role r = role();
        juorm.save(r);
        assertNotNull(r.getId());
    }

    @Test
    public void testSave2() {
        Article a = new Article();
        a.setTitle("title_" + RandomUtils.getRandomInt(100));
        a.setContent("content_" + RandomUtils.getRandomInt(1000));
        juorm.save(a);
        assertNotNull(a.getId());
    }

    @Test
    public void testSaveMultyKey() {
        UserRole ur = new UserRole();
        ur.setUserId(RandomUtils.getRandomInt(1000));
        ur.setRoleId(RandomUtils.getRandomInt(1000));
        ur.setDescp("descp" + RandomUtils.getRandomInt(1000));
        juorm.save(ur);
    }

    @Test
    public void testUpdate() {
        Role r = new Role();
        r.setId(2);
        r.setName("n_cc" + RandomUtils.getRandomInt(100));
        r.setDescp("d_cc" + RandomUtils.getRandomInt(100));
        juorm.update(r);
    }

    @Test
    public void testUpdateMulityPrimaryKey() {
        UserRole userRole = new UserRole();
        userRole.setRoleId(2);
        userRole.setUserId(2);
        userRole.setDescp("desc_cc" + RandomUtils.getRandomInt(99));
        juorm.update(userRole);
    }

    @Test
    public void testGet() {
        Integer id = 10;
        Role role = juorm.get(id, Role.class);
        assertEquals(role.getId(), id);
        assertNotNull(role.getName());
        System.out.println(role);
    }

    @Test
    public void testGetMulityPrimaryKey() {
        UserRole userRole = new UserRole();
        Integer roleId = 2;
        Integer userId = 2;
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);

        UserRole ur = juorm.get(userRole);

        assertEquals(ur.getUserId(), userRole.getUserId());
        assertEquals(ur.getUserId(), userRole.getRoleId());
        assertNotNull(ur.getDescp());
        System.out.println(ur);
    }

    @Test
    public void testDelete() {
        Role r = new Role();
        r.setId(1);
        juorm.delete(r);
    }

    @Test
    public void testDeleteMulityPrimaryKey() {

        UserRole userRole = new UserRole();
        userRole.setRoleId(11);
        userRole.setUserId(11);

        UserRole ur = juorm.query(UserRole.class).where().eq("role_id", 11).and().eq("user_id", 11).single();
        if (ur == null) {
            juorm.save(userRole);
        }

        juorm.delete(userRole);
    }

    @Test
    public void testDeleter() {
        juorm.delete(Role.class).where().in("id", new Integer[] { 15, 16 }).or().eq("id", 13).or().lt("id", 4)
                .execute();
    }

    @Test
    public void testUpdater() {
        juorm.update(Role.class).set("name", "updater_" + RandomUtils.getRandomInt(90)).property("descp")
                .set("updater_d_" + RandomUtils.getRandomInt(50)).where().eq("id", 4).execute();

    }

    @Test
    public void testUpdaterIncrease() {
        juorm.update(User.class).increase("age", 1).where().eq("id", 1).execute();

        juorm.update(User.class).propertyNumber("age").increase(1).where().eq("id", 2).execute();

    }

    @Test
    public void testQuery() {
        List<Role> roles = juorm.query(Role.class).where().gt("id", 5).and().le("id", 10).list();
        for (Role role : roles) {
            System.out.println(role);
            assertTrue(role.getId() > 5 && role.getId() <= 10);
        }

        Integer id = 10;
        Role r = juorm.query(Role.class).where().eq("id", id).single();
        System.out.println(r);
        assertEquals(r.getId(), id);
    }

    @Test
    public void testQueryLimit() {
        List<Role> roles = juorm.query(Role.class).where().gt("id", 5).and().le("id", 10).limit(2).list();
        assertTrue(roles.size() == 2);
        for (Role role : roles) {
            System.out.println(role);
            assertTrue(role.getId() > 5 && role.getId() <= 10);
        }

        roles = juorm.query(Role.class).where().gt("id", 5).and().le("id", 10).limit(2, 3).list();
        assertTrue(roles.size() == 3);
        for (Role role : roles) {
            System.out.println(role);
            assertTrue(role.getId() > 5 && role.getId() <= 10);
        }

        roles = juorm.query(Role.class).where().eq("id", 4).or().group().gt("id", 5).and().le("id", 10).limit(2, 3)
                .list();
        assertTrue(roles.size() == 3);
        for (Role role : roles) {
            System.out.println(role);
        }
    }

    @Test
    public void testQueryLimit2() {
        List<Role> roles = juorm.query(Role.class).where().eq("id", 4).or().group().gt("id", 5).and().le("id", 10)
                .limit(2, 3).list();
        assertTrue(roles.size() == 3);
        for (Role role : roles) {
            System.out.println(role);
        }
    }

    @Test
    public void testQuerySort() {
        List<Role> roles = juorm.query(Role.class).where().eq("id", 4).or().group().gt("id", 5).and().le("id", 10)
                .sort().asc("id").desc("name").list();
        for (Role role : roles) {
            System.out.println(role);
        }
    }
}
