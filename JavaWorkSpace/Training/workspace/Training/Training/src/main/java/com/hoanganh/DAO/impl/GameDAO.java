package com.hoanganh.DAO.impl;

import java.util.List;

import com.hoanganh.DAO.IGameDAO;
import com.hoanganh.mapper.GameMapper;
import com.hoanganh.model.GameModel;

public class GameDAO extends AbstractDAO implements IGameDAO {

  @Override
  public Long save(GameModel model) {
    String sql = "INSERT INTO game (winner, player1, player2, status) VALUES(?, ?, ?, ?)";
    return save(sql, model.getWinner() , model.getPlayer1(), model.getPlayer2(), model.getStatus());
  }

  @Override
  public List<GameModel> findByPlayerId(Long id) {
    String sql = "SELECT * FROM game WHERE player1=? OR player2=?";
    return query(sql, new GameMapper(), id, id);
  }

  @Override
  public GameModel findOne(Long id) {
    String sql = "SELECT * FROM game WHERE id=?";
    List<GameModel> list = query(sql, new GameMapper(), id);
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public void update(GameModel model) {
    GameModel modelDB = findOne(model.getId());
    if(modelDB != null) {
      if(model.getPlayer1() == null) {
        model.setPlayer1(modelDB.getPlayer1());
      } else if(model.getPlayer2() == null) {
        model.setPlayer2(modelDB.getPlayer2());
      } else if(model.getWinner() == null) {
        model.setWinner(modelDB.getWinner());
      }
    }
   String sql = "UPDATE game SET player1=?, player2=?, winner=? WHERE id=?";
   update(sql, model.getPlayer1(), model.getPlayer2(), model.getWinner(), model.getId());
  }

  @Override
  public List<GameModel> findAll() {
    String sql = "SELECT * FROM game";
    return query(sql, new GameMapper());
  }

}
