package com.hoanganh.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hoanganh.model.GameModel;
import com.hoanganh.service.IPlayerService;
import com.hoanganh.service.impl.PlayerService;

public class SetListPlayer {
  private IPlayerService playerService = new PlayerService();
  
  public void setListPlayer(GameModel gameModel, List<Map<String, String>> listPlayers){
    Map<String, String> player1 = new HashMap<String, String>();
    player1.put("id", gameModel.getPlayer1().toString());
    player1.put("points", playerService.findPointById(gameModel.getPlayer1()).toString());
    listPlayers.add(player1);
    Map<String, String> player2 = new HashMap<String, String>();
    player2.put("id", gameModel.getPlayer2().toString());
    player2.put("points", playerService.findPointById(gameModel.getPlayer2()).toString());
    listPlayers.add(player2);
  }
}
