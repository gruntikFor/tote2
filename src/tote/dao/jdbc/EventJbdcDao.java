package tote.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import tote.dao.EventDao;
import tote.entity.Event;

public class EventJbdcDao extends JdbcDaoSupport implements EventDao {

    private final RowMapper<Event> rowMapper = new RowMapper<Event>() {

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
    public void create(Event obj) {
        getJdbcTemplate().update("INSERT INTO event (event_Name, event_Date, cat_Id) VALUES (?,?,?)", obj.getName(),
                obj.getDate(), obj.getIdCategory());
    }

    @Override
    public Event read(Long id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM event WHERE event_Id = ?", rowMapper, id);
    }

    @Override
    public void update(Event obj) {
        getJdbcTemplate().update("UPDATE event SET event_Name = ?, event_Date= ?, cat_Id = ? WHERE event_Id = ?",
                obj.getName(), obj.getDate(), obj.getIdCategory(), obj.getId());
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update("DELETE FROM event WHERE id = ?", id);
    }

    @Override
    public List<Event> findAll() {
        return getJdbcTemplate().query("SELECT * FROM event", rowMapper);
    }

}
