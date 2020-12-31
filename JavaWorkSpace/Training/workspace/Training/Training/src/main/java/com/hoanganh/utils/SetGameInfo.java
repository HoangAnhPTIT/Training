package com.hoanganh.utils;

import java.util.List;
import java.util.Map;

import com.hoanganh.model.GameInfoModel;
import com.hoanganh.model.GameModel;

public class SetGameInfo {
  public void setGameInfo(List<Map<String, String>> listPlayers, GameModel gameModel, Long id) {
    GameInfoModel game = new GameInfoModel();
    game.setPlayers(listPlayers);
    game.setId(id);
    game.setWinner(gameModel.getWinner());
    gameModel.setGame(game);
  }
}
