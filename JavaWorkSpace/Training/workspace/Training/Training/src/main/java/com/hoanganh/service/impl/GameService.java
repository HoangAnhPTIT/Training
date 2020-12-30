package com.hoanganh.service.impl;

import javax.inject.Inject;

import com.hoanganh.DAO.IGameDAO;
import com.hoanganh.model.GameModel;
import com.hoanganh.service.IGameService;

public class GameService implements IGameService{

    @Inject 
    private IGameDAO gameDAO;
    
    @Override
    public Long save(GameModel model) {
        return gameDAO.save(model);
    }

}
