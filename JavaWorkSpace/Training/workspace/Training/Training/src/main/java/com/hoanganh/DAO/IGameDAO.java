package com.hoanganh.DAO;

import java.util.List;

import com.hoanganh.model.GameModel;

public interface IGameDAO {
    Long save(GameModel model);
    List<GameModel> findByPlayerId(Long id);
    List<GameModel> findAll();
    GameModel findOne(Long id);
    void update(GameModel model);
}
