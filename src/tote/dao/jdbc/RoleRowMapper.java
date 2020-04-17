package tote.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tote.entity.Role;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(final ResultSet rs, final int count) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong("role_Id"));
        role.setName(rs.getString("rolename"));
        return role;
    }
}
