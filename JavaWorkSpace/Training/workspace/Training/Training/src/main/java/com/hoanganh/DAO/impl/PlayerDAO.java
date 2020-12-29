package com.hoanganh.DAO.impl;

import java.util.List;

import com.hoanganh.DAO.IPlayerDAO;
import com.hoanganh.mapper.PlayerMapper;
import com.hoanganh.model.PlayerModel;

public class PlayerDAO extends AbstractDAO implements IPlayerDAO{

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
		String sql = "UPDATE player set fullname=?, points=?, losecount=?, winscount=? WHERE id=?";
		update(sql, model.getFullName(), model.getPoint(), model.getLoseCount(), model.getWinsCount(), model.getId());
		
	}

	@Override
	public void delete(Long[] ids) {
		String sql = "DELETE FROM player WHERE id=?";
		for(Long id:ids) {
			update(sql, id);
		}
		
	}
	
}
