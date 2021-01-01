package com.hoanganh.service;

import java.sql.Timestamp;

import com.hoanganh.model.LogModel;

public interface ILogService {
  Long save(LogModel model);
  Timestamp getLastPlay(Long player1, Long player2);
  LogModel findByPlayerAndTimePlay(Long player1, Long player2, Timestamp time);
  void updateOutGame(Long id, int status);
  LogModel findByPlayerAndStatus(Long id, int status);
  void update(LogModel model);
  LogModel findOne(Long id);
  LogModel modelNearest(Long player1, Long player2, Timestamp timeplay);
  void updatePoint(Long id, Long point, Long cpoint);
}
