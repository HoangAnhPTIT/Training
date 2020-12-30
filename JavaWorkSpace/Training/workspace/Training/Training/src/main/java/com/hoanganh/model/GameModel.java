package com.hoanganh.model;

import java.util.HashMap;
import java.util.Map;

public class GameModel {
  private Long id;
  private Map<String, String> players = new HashMap<String, String>();
  private GameInfoModel game;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Map<String, String> getPlayers() {
    return players;
  }

  public void setPlayers(Map<String, String> players) {
    this.players = players;
  }

  public GameInfoModel getGame() {
    return game;
  }

  public void setGame(GameInfoModel game) {
    this.game = game;
  }

}
