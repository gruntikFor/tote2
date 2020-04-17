package tote.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import tote.dao.BetDao;
import tote.entity.Bet;
import tote.entity.BetAll;

public class BetJdbcDao extends JdbcDaoSupport implements BetDao {

    private final RowMapper<Bet> rowMapper = new RowMapper<Bet>() {
        @Override
        public Bet mapRow(ResultSet rs, int arg1) throws SQLException {
            Bet bet = new Bet();
            bet.setId(rs.getLong("bet_Id"));
            System.out.println("get bet1: " + bet.getId());
            bet.setBetValue(rs.getBigDecimal("bet_Value"));
            System.out.println("get value1: " + bet.getBetValue());
            bet.setIdUser(rs.getLong("user_Id"));
            System.out.println("get id user: " + bet.getIdUser());
            bet.setIdRes(rs.getLong("res_Id"));
            return bet;
        }

    };
    private final RowMapper<BetAll> rowMapperBet = new RowMapper<BetAll>() {
        @Override
        public BetAll mapRow(ResultSet rs, int arg1) throws SQLException {

            BetAll betAll = new BetAll();
            betAll.setId(rs.getLong("betId"));
            betAll.setCatName(rs.getString("catName"));
            betAll.setEventName(rs.getString("eventName"));
            betAll.setOutcomeName(rs.getString("outcomeName"));
            betAll.setBetValue(rs.getBigDecimal("betValue"));
            betAll.setResCoef(rs.getDouble("resCoef"));
            betAll.setState(rs.getInt("resState"));
            return betAll;
        }

    };

    @Override
    public void create(Bet obj) {
        getJdbcTemplate().update("INSERT INTO bet ( bet_Value, user_Id, res_Id) VALUES (?,?,?)", obj.getBetValue(),
                obj.getIdUser(), obj.getIdRes());
    }

    @Override
    public Bet read(Long id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM bet WHERE bet_Id = ?", rowMapper, id);
    }

    @Override
    public void update(Bet obj) {
        getJdbcTemplate().update("UPDATE bet SET bet_Value = ?, user_Id = ?, res_Id = ? WHERE bet_Id = ?",
                obj.getBetValue(), obj.getIdUser(), obj.getIdRes(), obj.getId());
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update("DELETE FROM bet WHERE id = ?", id);
    }

    @Override
    public List<Bet> findAll() {
        return getJdbcTemplate().query("SELECT * FROM bet", rowMapper);
    }

    @Override
    public List<BetAll> findBetAll(Long userId) {
        return getJdbcTemplate().query(
                " select b.bet_Id as betId, c.cat_Name as catName, e.event_Name as eventName, r.res_Name as outcomeName,"
                        + " b.bet_Value as betValue, r.res_Coef as resCoef, r.res_state as resState from bet as b, outcome as r, event as e,\r\n"
                        + " category as c where b.res_Id=r.res_Id and r.event_Id=e.event_Id and e.cat_Id=c.cat_Id and b.user_Id=?",
                rowMapperBet, userId);
    }

}
