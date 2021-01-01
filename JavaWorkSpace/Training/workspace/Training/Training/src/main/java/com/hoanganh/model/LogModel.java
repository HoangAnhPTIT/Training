package com.hoanganh.model;

import java.sql.Timestamp;

public class LogModel {
  private Long id;
  private Long player1;
  private Long player2;
  private Long point1;
  private Long point2;
  private Long cPoint1;
  private Long cPoint2;
  private Timestamp timePlay;
  private int inGame;

  
  
  public LogModel(Long player1, Long player2, Long point1, Long point2, Long cPoint1, Long cPoint2, Timestamp timePlay,
      int inGame) {
    this.player1 = player1;
    this.player2 = player2;
    this.point1 = point1;
    this.point2 = point2;
    this.cPoint1 = cPoint1;
    this.cPoint2 = cPoint2;
    this.timePlay = timePlay;
    this.inGame = inGame;
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

  public Long getcPoint1() {
    return cPoint1;
  }

  public void setcPoint1(Long cPoint1) {
    this.cPoint1 = cPoint1;
  }

  public Long getcPoint2() {
    return cPoint2;
  }

  public void setcPoint2(Long cPoint2) {
    this.cPoint2 = cPoint2;
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

}
