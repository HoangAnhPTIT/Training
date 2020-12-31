package com.hoanganh.DAO.impl;

import java.util.List;

import javax.inject.Inject;

import com.hoanganh.DAO.IPlayerDAO;
import com.hoanganh.mapper.PlayerMapper;
import com.hoanganh.model.PlayerModel;
import com.hoanganh.service.IPlayerService;

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
        String sql = "INSERT INTO player (fullname, points, winscount, losecount) VALUES(?, ?, ?, ?)";
        return save(sql, model.getFullName(), model.getPoint(), model.getWinsCount(), model.getLoseCount());
    }

    @Override
    public void update(PlayerModel model) {
        PlayerModel modelDB = findOne(model.getPlayer_id());
        if (modelDB != null) {
            if (model.getFullName() == null) {
                model.setFullName(modelDB.getFullName());
            } else if (model.getPoint() == null) {
                model.setPoint(modelDB.getPoint());
            } else if (model.getLoseCount() == null) {
                model.setLoseCount(modelDB.getLoseCount());
            } else if (model.getWinsCount() == null) {
                model.setWinsCount(modelDB.getWinsCount());
            }
        }
        String sql = "UPDATE player set fullname=?, points=?, losecount=?, winscount=? WHERE id=?";
        update(sql, model.getFullName(), model.getPoint(), model.getLoseCount(), model.getWinsCount(), model.getPlayer_id());

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

}
