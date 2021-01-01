package com.hoanganh.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hoanganh.model.LogModel;

public class SetListPlayer {
  
  public void setListPlayer(LogModel logModel, List<Map<String, String>> listPlayers){
    Map<String, String> player1 = new HashMap<String, String>();
    player1.put("id", logModel.getPlayer1().toString());
    player1.put("points", logModel.getcPoint1().toString());
    listPlayers.add(player1);
    Map<String, String> player2 = new HashMap<String, String>();
    player2.put("id", logModel.getPlayer2().toString());
    player2.put("points", logModel.getcPoint2().toString());
    listPlayers.add(player2);
  }
}
