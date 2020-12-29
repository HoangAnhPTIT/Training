package com.hoanganh.DAO;

import java.util.List;

import com.hoanganh.model.PlayerModel;

public interface IPlayerDAO {
	List<PlayerModel> findAll();
	Long save(PlayerModel model); 
	void update(PlayerModel model);
	void delete(Long[] ids);
}
