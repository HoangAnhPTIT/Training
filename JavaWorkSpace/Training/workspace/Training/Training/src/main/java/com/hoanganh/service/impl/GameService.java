package com.hoanganh.service.impl;

import java.util.List;

import javax.inject.Inject;

import com.hoanganh.DAO.IGameDAO;
import com.hoanganh.DAO.impl.GameDAO;
import com.hoanganh.model.GameModel;
import com.hoanganh.service.IGameService;

public class GameService implements IGameService{

//    @Inject 
    private IGameDAO gameDAO = new GameDAO();
    
    @Override
    public Long save(GameModel model) {
        return gameDAO.save(model);
    }

    @Override
    public List<GameModel> findByPlayerId(Long id) {
       return gameDAO.findByPlayerId(id);
    }

    @Override
    public GameModel findOne(Long id) {
      return gameDAO.findOne(id);
    }

}
