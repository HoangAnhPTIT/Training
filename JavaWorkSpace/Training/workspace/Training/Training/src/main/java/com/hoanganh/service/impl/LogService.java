package com.hoanganh.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.hoanganh.DAO.ILogDAO;
import com.hoanganh.DAO.impl.LogDAO;
import com.hoanganh.model.LogModel;
import com.hoanganh.service.ILogService;

public class LogService implements ILogService {

  private ILogDAO logDAO = new LogDAO();

  @Override
  public Long save(LogModel model) {
    return logDAO.save(model);
  }

  @Override
  public Timestamp getLastPlay(Long player1, Long player2) {
    return logDAO.getLastPlay(player1, player2);
  }

  @Override
  public LogModel findByPlayerAndTimePlay(Long player1, Long player2, Timestamp time) {
    return logDAO.findByPlayerAndTimePlay(player1, player2, time);
  }

  @Override
  public void updateOutGame(Long id, int status) {
    logDAO.updateOutGame(id, status);
  }

  @Override
  public LogModel findByPlayerAndStatus(Long id, int status) {
    return logDAO.findByPlayerAndStatus(id, status);
  }

  @Override
  public void update(LogModel model) {
    logDAO.update(model);
  }

  @Override
  public LogModel findOne(Long id) {
    return logDAO.findOne(id);
  }

  @Override
  public LogModel modelNearest(Long player1, Long player2, Timestamp timeplay) {
    return logDAO.modelNearest(player1, player2, timeplay);
  }

  @Override
  public void updatePoint(Long id, Long point, Long cpoint) {
    logDAO.updatePoint(id, point, cpoint);
    
  }

  @Override
  public List<LogModel> findListLast(Long id) {
    return logDAO.findListLast(id);
  }

  @Override
  public void delete(Long id) {
    logDAO.delete(id);
    
  }

  @Override
  public LogModel findByGameIdAndStatus(Long gameId, int status) {
    return logDAO.findByGameIdAndStatus(gameId, status);
  }

}
