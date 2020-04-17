package tote.dao.jdbc;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import tote.dao.RoleDao;
import tote.entity.Role;

public class RoleJdbcDao extends JdbcDaoSupport implements RoleDao {

    private RowMapper<Role> rowMapper;

    public void setRowMapper(RowMapper<Role> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Role> findAll() {
        return getJdbcTemplate().query("SELECT * FROM roles", rowMapper);
    }

    @Override
    public void deleteUsersRoles(final Long uid) {
        getJdbcTemplate().update("DELETE FROM authorities WHERE user_Id = ?", uid);
    }

    @Override
    public void addUsersRole(Long uid, Long rid) {
        getJdbcTemplate().update("INSERT INTO authorities (user_Id, role_Id) VALUES(?, ?)", uid, rid);
    }

    @Override
    public Role getRole(String name) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM roles WHERE rolename = ?", rowMapper, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
