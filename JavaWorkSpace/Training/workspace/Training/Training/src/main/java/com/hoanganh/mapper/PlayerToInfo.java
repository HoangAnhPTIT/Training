package com.hoanganh.mapper;

import com.hoanganh.model.PlayerModel;
import com.hoanganh.viewmodel.PlayerInfoModel;

public class PlayerToInfo {
  public PlayerInfoModel mapper(PlayerModel playerModel) {
    PlayerInfoModel infoModel = new PlayerInfoModel();
    infoModel.setFullName(playerModel.getFullName());
    infoModel.setPlayer_id(infoModel.getPlayer_id());
    infoModel.setLoseCount(playerModel.getLoseCount());
    infoModel.setWinsCount(playerModel.getWinsCount());
    return infoModel;

  }
}
