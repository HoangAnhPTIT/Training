package com.hoanganh.DAO.impl;

import java.sql.Timestamp;
import java.util.List;

import com.hoanganh.DAO.ILogDAO;
import com.hoanganh.mapper.LogMapper;
import com.hoanganh.model.LogModel;

public class LogDAO extends AbstractDAO implements ILogDAO {

  @Override
  public Long save(LogModel model) {
    StringBuilder sql = new StringBuilder(
        "INSERT INTO log (player1, player2, point1, point2, cpoint1, cpoint2, timeplay, ingame)");
    sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
    return save(sql.toString(), model.getPlayer1(), model.getPlayer2(), model.getPoint1(), model.getPoint2(),
        model.getcPoint1(), model.getcPoint2(), model.getTimePlay(), model.getInGame());
  }

  @Override
  public Timestamp getLastPlay(Long player1, Long player2) {
    String sql = "SELECT max(timeplay) AS timeplay FROM log WHERE player1=? AND player2=? AND ingame=1";
    return queryTime(sql, new LogMapper(), player1, player2);
  }

  @Override
  public LogModel findByPlayerAndTimePlay(Long player1, Long player2, Timestamp time) {
   String sql = "SELECT * FROM log WHERE player1=? AND player2=? AND timeplay=?";
   List<LogModel> list = query(sql, new LogMapper(), player1, player2, time);
   return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public void updateOutGame(Long id,int status) {
    String sql = "UPDATE log SET ingame=? WHERE id=?";
    save(sql, status, id);
    
  }

  @Override
  public LogModel findByPlayerAndStatus(Long id, int status) {
    String sql = "SELECT * FROM log WHERE ingame=1 AND player1=? OR player2=?";
    List<LogModel> list = query(sql, new LogMapper(), id, status);
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public void update(LogModel model) {
    LogModel modelDB = findOne(model.getId());
    if(modelDB != null) {
      if(model.getcPoint1() == null) {
        model.setcPoint1(modelDB.getcPoint1());
      } else if(model.getcPoint2() == null) {
        model.setcPoint2(modelDB.getcPoint2());
      } else if(model.getPlayer1() == null) {
        model.setPlayer1(modelDB.getPlayer1());
      } else if(model.getPlayer2() ==null) {
        model.setPlayer2(modelDB.getPlayer2());
      } else if(model.getTimePlay() == null) {
        model.setTimePlay(modelDB.getTimePlay());
      } else if(model.getPoint1() == null) {
        model.setPoint1(modelDB.getPoint1());
      } else if(model.getPoint2() == null) {
        model.setPoint2(modelDB.getPoint2());
      }
    }
    String sql = "UPDATE log SET cpoint1=?, cpoint2=?, player1=?, player2=?, timeplay=?, point1=?, point2=? WHERE id=?";
    save(sql, model.getcPoint1(), model.getcPoint2(), model.getPlayer1(), model.getPlayer2(), model.getTimePlay(),
        model.getPoint1(), model.getPoint2(), model.getId());
    
  }

  @Override
  public LogModel findOne(Long id) {
    String sql = "SELECT * FROM log WHERE id=?";
    List<LogModel> models = query(sql, new LogMapper(), id);
    return models.isEmpty() ? null : models.get(0);
  }

  @Override
  public LogModel modelNearest(Long player1, Long player2, Timestamp timeplay) {
    String sql = "SELECT id FROM log WHERE player1=? AND player2=? AND timeplay<? ORDER BY timeplay limit 1";
    List<LogModel> models = query(sql, new LogMapper(), player1, player2, timeplay);
    return models.isEmpty() ? null : models.get(0);
  }

  @Override
  public void updatePoint(Long id, Long point, Long cpoint) {
    String sql = "UPDATE log SET point=?, cpoint=? WHERE id=?";
    save(sql, point, cpoint, id);
    
  }

}
