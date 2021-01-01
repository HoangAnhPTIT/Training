package com.hoanganh.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.hoanganh.model.GameModel;

public class GameMapper implements RowMapper<GameModel> {

  @Override
  public GameModel mapRow(ResultSet result) {
    GameModel model = new GameModel();
    try {
      model.setId(result.getLong("id"));
      model.setPlayer1(result.getLong("player1"));
      model.setPlayer2(result.getLong("player2"));
      model.setWinner(result.getLong("winner"));
    } catch (SQLException e) {
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
    // TODO Auto-generated method stub
    return null;
  }

}
