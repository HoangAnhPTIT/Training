package com.hoanganh.utils;

import java.util.List;
import java.util.Map;

import com.hoanganh.model.GameModel;
import com.hoanganh.viewmodel.GameInfoModel;
import com.hoanganh.viewmodel.ShowGameInfo;

public class SetGameInfo {
  public void setGameInfo(List<Map<String, String>> listPlayers, GameModel gameModel, Long id, ShowGameInfo gameInfo) {
    GameInfoModel game = new GameInfoModel();
    game.setPlayers(listPlayers);
    game.setId(id);
    game.setWinner(gameModel.getWinner());
    gameInfo.setGame(game);
  }
}
