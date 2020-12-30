package com.hoanganh.service;

import java.util.List;

import com.hoanganh.model.PlayerModel;

public interface IPlayerService {
	List<PlayerModel> findAll();
	PlayerModel findOne(Long id);
	Long save(PlayerModel model); 
	void update(PlayerModel model);
	void delete(Long[] ids);
	Long findPointById(Long id);
}
