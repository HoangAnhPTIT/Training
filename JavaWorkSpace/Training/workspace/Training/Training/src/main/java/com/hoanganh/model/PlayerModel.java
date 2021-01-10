package com.hoanganh.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerModel {
  private Long player_id;
  private String fullName;
  private Long loseCount;
  private Long winsCount;
  private Long[] ids;
  private String userName;
  private String password;
  private Integer status;
  private Long totalPoint;
  
  private Map<String, String> player = new HashMap<String, String>();
  
  private List<PlayerModel> listPlayerModel = new ArrayList<>();

  public Long getPlayer_id() {
    return player_id;
  }

  public void setPlayer_id(Long player_id) {
    this.player_id = player_id;
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

  public List<PlayerModel> getListPlayerModel() {
    return listPlayerModel;
  }

  public void setListPlayerModel(List<PlayerModel> listPlayerModel) {
    this.listPlayerModel = listPlayerModel;
  }

  public Long[] getIds() {
    return ids;
  }

  public void setIds(Long[] ids) {
    this.ids = ids;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Map<String, String> getPlayer() {
    return player;
  }

  public void setPlayer(Map<String, String> player) {
    this.player = player;
  }

  public Long getTotalPoint() {
    return totalPoint;
  }

  public void setTotalPoint(Long totalPoint) {
    this.totalPoint = totalPoint;
  }
}
