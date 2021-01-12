package com.hoanganh.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.hoanganh.model.LogModel;

public class LogMapper implements RowMapper<LogModel>{

  @Override
  public LogModel mapRow(ResultSet result) {
     LogModel model = new LogModel();
     try {
       model.setId(result.getLong("id"));
       model.setPlayer1(result.getLong("player1"));
       model.setPlayer2(result.getLong("player2"));
       model.setPoint1(result.getLong("point1"));
       model.setPoint2(result.getLong("point2"));
       model.setInGame(result.getInt("ingame"));
       model.setTimePlay(result.getTimestamp("timeplay"));
       model.setGameId(result.getLong("gameid"));
     } catch(SQLException e) {
       e.printStackTrace();
     }
     return model;
  }

  @Override
  public Long mapPoint(ResultSet result) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Timestamp mapTime(ResultSet result) {
    try {
     Timestamp time = result.getTimestamp("timeplay");
     return time;
    } catch(SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

}
