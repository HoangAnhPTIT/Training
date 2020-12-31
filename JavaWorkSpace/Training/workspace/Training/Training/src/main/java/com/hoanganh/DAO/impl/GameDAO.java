package com.hoanganh.DAO.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hoanganh.DAO.IGameDAO;
import com.hoanganh.mapper.GameMapper;
import com.hoanganh.model.GameModel;

public class GameDAO extends AbstractDAO implements IGameDAO {

  @Override
  public Long save(GameModel model) {
    String sql = "INSERT INTO game (winner, player1, player2) VALUES(?, ?, ?)";
    Map<String, String> players = model.getPlayers();
    Set<String> set = players.keySet();
    List<Long> ids = new ArrayList<>();
    for (String key : set) {
      ids.add(Long.parseLong(players.get(key)));
    }
    Long id1 = ids.get(0);
    Long id2 = ids.get(1);
    Long winner = model.getGame().getWinner();
    return save(sql, winner , id1, id2);
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

}
