package com.hoanganh.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hoanganh.viewmodel.GameInfoModel;

public class GameModel {
  private Long id;
  private Map<String, String> players = new HashMap<String, String>();
  private GameInfoModel game;
  private Long player1;
  private Long player2;
  private Long winner;
  
  private List<GameModel> listGameModel = new ArrayList<>();
  
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

  public Long getPlayer1() {
    return player1;
  }

  public void setPlayer1(Long player1) {
    this.player1 = player1;
  }

  public Long getPlayer2() {
    return player2;
  }

  public void setPlayer2(Long player2) {
    this.player2 = player2;
  }

  public Long getWinner() {
    return winner;
  }

  public void setWinner(Long winner) {
    this.winner = winner;
  }

  public List<GameModel> getListGameModel() {
    return listGameModel;
  }

  public void setListGameModel(List<GameModel> listGameModel) {
    this.listGameModel = listGameModel;
  }

}
