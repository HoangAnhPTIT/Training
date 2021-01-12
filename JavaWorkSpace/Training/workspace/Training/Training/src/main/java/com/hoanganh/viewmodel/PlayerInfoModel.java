package com.hoanganh.viewmodel;

public class PlayerInfoModel {
  private Long player_id;
  private Long point;
  private String fullName;
  private Long loseCount;
  private Long winsCount;
  private Integer status;
  
  public Long getPlayer_id() {
    return player_id;
  }
  public void setPlayer_id(Long player_id) {
    this.player_id = player_id;
  }
  public Long getPoint() {
    return point;
  }
  public void setPoint(Long point) {
    this.point = point;
  }
  public String getFullName() {
    return fullName;
  }
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  public Long getLoseCount() {
    return loseCount;
  }
  public void setLoseCount(Long loseCount) {
    this.loseCount = loseCount;
  }
  public Long getWinsCount() {
    return winsCount;
  }
  public void setWinsCount(Long winsCount) {
    this.winsCount = winsCount;
  }
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  
}
