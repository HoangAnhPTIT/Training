package com.hoanganh.service.impl;

import java.util.List;

import javax.inject.Inject;

import com.hoanganh.DAO.IPlayerDAO;
import com.hoanganh.model.PlayerModel;
import com.hoanganh.service.IPlayerService;

public class PlayerService implements IPlayerService{

	@Inject
	private IPlayerDAO playerDAO;
		
	@Override
	public List<PlayerModel> findAll() {
		return playerDAO.findAll();
	}

	@Override
	public Long save(PlayerModel model) {
		return playerDAO.save(model);
	}

	@Override
	public void update(PlayerModel model) {
		playerDAO.update(model);
	}

	@Override
	public void delete(Long[] ids) {
		playerDAO.delete(ids);
		
	}
	
}
