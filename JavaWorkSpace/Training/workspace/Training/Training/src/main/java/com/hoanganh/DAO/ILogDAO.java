package com.hoanganh.DAO;

import java.sql.Timestamp;
import java.util.List;

import com.hoanganh.model.LogModel;

public interface ILogDAO {
  Long save(LogModel model);
  Timestamp getLastPlay(Long player1, Long player2);
  LogModel findByPlayerAndTimePlay(Long player1, Long player2, Timestamp time);
  LogModel findByPlayerAndStatus(Long id, int status);
  LogModel findOne(Long id);
  void updateOutGame(Long id, int status);
  void update(LogModel model);
  LogModel modelNearest(Long player1, Long player2, Timestamp timeplay);
  void updatePoint(Long id, Long point, Long cpoint);
  List<LogModel> findListLast(Long id);
  void delete(Long id);
  LogModel findByGameIdAndStatus(Long gameId, int status);
}
