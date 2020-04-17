package tote.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import tote.dao.ResultDao;
import tote.entity.Result;

public class ResultJdbcDao extends JdbcDaoSupport implements ResultDao {

    private final RowMapper<Result> rowMapper = new RowMapper<Result>() {
        @Override
        public Result mapRow(ResultSet rs, int arg1) throws SQLException {
            Result res = new Result();
            res.setId(rs.getLong("res_Id"));
            res.setName(rs.getString("res_Name"));
            res.setCoef(rs.getDouble("res_Coef"));
            res.setIdEvent(rs.getLong("event_Id"));
            res.setState(rs.getInt("res_state"));

            return res;
        }
    };

    @Override
    public void create(Result obj) {
        getJdbcTemplate().update("INSERT INTO outcome (res_Name, res_Coef, event_Id ,res_state) VALUES (?,?,?,?)",
                obj.getName(), obj.getCoef(), obj.getIdEvent(), obj.getState());
    }

    @Override
    public Result read(Long id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM outcome WHERE res_Id = ?", rowMapper, id);
    }

    @Override
    public void update(Result obj) {
        getJdbcTemplate().update(
                "UPDATE outcome SET res_Name = ?, res_Coef = ?, event_Id = ?, res_state = ? WHERE res_Id = ?",
                obj.getName(), obj.getCoef(), obj.getIdEvent(), obj.getState(), obj.getId());
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update("DELETE FROM outcome WHERE id = ?", id);
    }

    @Override
    public List<Result> findAll() {
        return getJdbcTemplate().query("SELECT * FROM outcome", rowMapper);
    }

    @Override
    public List<Result> findByEventId(Long eventId) {
        return getJdbcTemplate().query("SELECT * FROM outcome WHERE outcome.event_Id = ?", rowMapper, eventId);
    }

}
