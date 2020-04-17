package tote.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import tote.dao.CategoryDao;
import tote.entity.Category;
import tote.entity.Event;

public class CategoryJdbcDao extends JdbcDaoSupport implements CategoryDao {

    private final RowMapper<Category> rowMapper = new RowMapper<Category>() {
        @Override
        public Category mapRow(ResultSet rs, int arg1) throws SQLException {
            Category categ = new Category();
            categ.setId(rs.getLong("cat_Id"));
            categ.setName(rs.getString("cat_Name"));
            categ.setEvents(eventsByCatId(categ.getId()));
            return categ;
        }

    };

    private final RowMapper<Event> eventMapper = new RowMapper<Event>() {
        @Override
        public Event mapRow(ResultSet rs, int arg1) throws SQLException {
            Event ev = new Event();
            ev.setId(rs.getLong("event_Id"));
            ev.setName(rs.getString("event_Name"));
            ev.setDate(rs.getString("event_Date"));
            ev.setIdCategory(rs.getLong("cat_Id"));
            return ev;
        }
    };

    @Override
    public void create(Category obj) {
        getJdbcTemplate().update("INSERT INTO category (cat_Name) VALUES (?)", obj.getName());
    }

    @Override
    public Category read(Long id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM category WHERE cat_Id = ?", rowMapper, id);
    }

    @Override
    public void update(Category obj) {
        getJdbcTemplate().update("UPDATE category SET cat_Name = ? WHERE cat_Id = ?", obj.getId());
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update("DELETE FROM category WHERE id = ?", id);
    }

    @Override
    public List<Category> findAll() {
        return getJdbcTemplate().query("SELECT * FROM category", rowMapper);
    }

    @Override
    public List<Event> eventsByCatId(Long id) {
        return getJdbcTemplate().query("SELECT * FROM event WHERE cat_Id = ?", eventMapper, id);
    }

}
