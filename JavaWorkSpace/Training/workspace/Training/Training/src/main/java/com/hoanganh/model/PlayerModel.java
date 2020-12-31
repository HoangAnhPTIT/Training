package com.hoanganh.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel {
  private Long player_id;
  private Long point;
  private String fullName;
  private Long loseCount;
  private Long winsCount;

  private Long[] ids;

  private List<PlayerModel> listPlayerModel = new ArrayList<>();

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

}
