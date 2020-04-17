package tote.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import tote.dao.UserDao;
import tote.entity.Role;
import tote.entity.User;

public class UserJdbcDao extends JdbcDaoSupport implements UserDao {

    private final RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(final ResultSet rs, final int arg1) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("user_Id"));
            user.setName(rs.getString("username"));
            user.setBillValue(rs.getBigDecimal("bill"));
            user.setPass(rs.getString("password"));
            return user;
        }
    };

    private final RowMapper<Role> roleMapper = new RowMapper<Role>() {
        @Override
        public Role mapRow(final ResultSet rs, final int arg1) throws SQLException {
            Role role = new Role();
            role.setId(rs.getLong("role_Id"));
            role.setName(rs.getString("rolename"));
            return role;
        }
    };

    @Override
    public void create(final User user) {

        getJdbcTemplate().update("INSERT INTO users (username, password, bill) VALUES (?, ?, ?)", user.getName(),
                user.getPass(), user.getBillValue());
        /*
         * final KeyHolder keyHolder = new GeneratedKeyHolder();
         * getJdbcTemplate().update(new PreparedStatementCreator() {
         * @Override public PreparedStatement createPreparedStatement(final Connection
         * conn) throws SQLException { final PreparedStatement ps = conn.
         * prepareStatement("INSERT INTO users (username, password, bill) VALUES (?, ?, ?)"
         * ); ps.setString(1, user.getName()); ps.setString(2, user.getPass());
         * ps.setDouble(3, user.getBillValue()); return ps; } }, keyHolder);
         * user.setId(keyHolder.getKey().longValue());
         */
    }

    /*
     * public User createUser(final User user) { final KeyHolder keyHolder = new
     * GeneratedKeyHolder(); System.out.println("_______________________\n\n");
     * getJdbcTemplate().update(new PreparedStatementCreator() {
     * @Override public PreparedStatement createPreparedStatement(final Connection
     * conn) throws SQLException { final PreparedStatement ps = conn.
     * prepareStatement("INSERT INTO users (username, password, bill) VALUES (?, ?, ?)"
     * ); ps.setString(1, user.getName()); ps.setString(2, user.getPass());
     * ps.setDouble(3, user.getBillValue()); return ps; } }, keyHolder);
     * user.setId(keyHolder.getKey().longValue());
     * System.out.println("_______________________\n\n"); return user; }
     */
    @Override
    public User read(Long id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM users WHERE user_id = ?", rowMapper, id);
    }

    @Override
    public User getUser(String username) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM users WHERE username = ?", rowMapper, username);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            System.out.println("----------------------notfind----------------------\n\n");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(User obj) {
        getJdbcTemplate().update("UPDATE users SET username = ?, password =?, bill = ? WHERE user_Id = ?",
                obj.getName(), obj.getPass(), obj.getBillValue(), obj.getId());
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update("DELETE FROM users WHERE user_Id = ?", id);
    }

    @Override
    public List<User> findAll() {
        return getJdbcTemplate().query("SELECT * FROM users", rowMapper);
    }
    /*
     * @Override public List<Role> getRoles(Long userId) { return
     * getJdbcTemplate().query(
     * "SELECT r.* FROM users u JOIN authorities a ON a.user_id = u.user_id JOIN roles r ON r.role_id = a.role_id WHERE u.user_id = ?"
     * , roleMapper, userId); }
     */
}
