package com.hoanganh.DAO.impl;

import java.util.List;

import com.hoanganh.DAO.IPlayerDAO;
import com.hoanganh.mapper.PlayerMapper;
import com.hoanganh.model.PlayerModel;

public class PlayerDAO extends AbstractDAO implements IPlayerDAO {

//    @Inject
//    private IPlayerService playerService;

  @Override
  public List<PlayerModel> findAll() {
    String sql = "SELECT * FROM player";
    return query(sql, new PlayerMapper());
  }

  @Override
  public Long save(PlayerModel model) {
    String sql = "INSERT INTO player (fullname, winscount, losecount, username, password, status, totalpoint) VALUES(?, ?, ?, ?, ?, ?, ?)";
    return save(sql, model.getFullName(), model.getWinsCount(), model.getLoseCount(), model.getUserName(),
        model.getPassword(), model.getStatus(), model.getTotalPoint());
  }

  @Override
  public void update(PlayerModel model) {
    PlayerModel modelDB = findOne(model.getPlayer_id());
    if (modelDB != null) {
      if (model.getFullName() == null) {
        model.setFullName(modelDB.getFullName());
      } else if (model.getLoseCount() == null) {
        model.setLoseCount(modelDB.getLoseCount());
      } else if (model.getWinsCount() == null) {
        model.setWinsCount(modelDB.getWinsCount());
      } else if (model.getTotalPoint() == null) {
        model.setTotalPoint(modelDB.getTotalPoint());
      }
    }
    String sql = "UPDATE player set fullname=?, losecount=?, winscount=?, totalpoint=? WHERE id=?";
    update(sql, model.getFullName(), model.getLoseCount(), model.getWinsCount(), model.getTotalPoint(),
        model.getPlayer_id());

  }

  @Override
  public void delete(Long[] ids) {
    String sql = "DELETE FROM player WHERE id=?";
    for (Long id : ids) {
      update(sql, id);
    }

  }

  @Override
  public Long findPointById(Long id) {
    String sql = "SELECT points FROM player WHERE id=?";
    Long point = queryOne(sql, new PlayerMapper(), id);
    return point;
  }

  @Override
  public PlayerModel findOne(Long id) {
    String sql = "SELECT * FROM player WHERE id=?";
    List<PlayerModel> list = query(sql, new PlayerMapper(), id);
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public PlayerModel findByUsernameAndPassword(String username, String password) {
    String sql = "SELECT * FROM player WHERE username=? AND password=?";
    List<PlayerModel> models = query(sql, new PlayerMapper(), username, password);
    return models.isEmpty() ? null : models.get(0);
  }

  @Override
  public void updateStatus(Long id, int status) {
    String sql = "UPDATE player SET status=? WHERE id=?";
    save(sql, status, id);

  }

}
