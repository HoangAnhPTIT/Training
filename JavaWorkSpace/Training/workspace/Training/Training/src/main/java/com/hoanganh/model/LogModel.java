package com.hoanganh.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LogModel {
  private Long id;
  private Long player1;
  private Long player2;
  private Long point1;
  private Long point2;
  private Timestamp timePlay;
  private int inGame;
  private Long gameId;
  
  private List<LogModel> listModel = new ArrayList<>();
  
  public LogModel(Long player1, Long player2, Long point1, Long point2, Timestamp timePlay,
      int inGame, Long gameId) {
    this.player1 = player1;
    this.player2 = player2;
    this.point1 = point1;
    this.point2 = point2;
    this.timePlay = timePlay;
    this.inGame = inGame;
    this.gameId = gameId;
  }

  public LogModel() {
    // TODO Auto-generated constructor stub
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getPoint1() {
    return point1;
  }

  public void setPoint1(Long point1) {
    this.point1 = point1;
  }

  public Long getPoint2() {
    return point2;
  }

  public void setPoint2(Long point2) {
    this.point2 = point2;
  }

  public Timestamp getTimePlay() {
    return timePlay;
  }

  public void setTimePlay(Timestamp timePlay) {
    this.timePlay = timePlay;
  }

  public int getInGame() {
    return inGame;
  }

  public void setInGame(int inGame) {
    this.inGame = inGame;
  }

  public Long getGameId() {
    return gameId;
  }

  public void setGameId(Long gameId) {
    this.gameId = gameId;
  }

  public List<LogModel> getListModel() {
    return listModel;
  }

  public void setListModel(List<LogModel> listModel) {
    this.listModel = listModel;
  }

}
