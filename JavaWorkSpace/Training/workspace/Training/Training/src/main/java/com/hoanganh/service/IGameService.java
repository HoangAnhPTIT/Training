package com.hoanganh.service;

import java.util.List;

import com.hoanganh.model.GameModel;

public interface IGameService {
    Long save(GameModel model);
    List<GameModel> findByPlayerId(Long id);
    GameModel findOne(Long id);
    void update(GameModel model);
}
