package com.hoanganh.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoanganh.model.GameInfoModel;
import com.hoanganh.model.GameModel;
import com.hoanganh.service.IGameService;
import com.hoanganh.service.IPlayerService;
import com.hoanganh.utils.HttpUtil;

@WebServlet(urlPatterns = { "/games" })
public class GameController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Inject
  private IGameService gameService;

  @Inject
  private IPlayerService playerService;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    GameModel model = HttpUtil.of(request.getReader()).toModel(GameModel.class);
    Map<String, String> players = model.getPlayers();
    List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
    Set<String> set = players.keySet();
    List<Long> ids = new ArrayList<>();
    for (String key : set) {
      ids.add(Long.parseLong(players.get(key)));
    }
    GameInfoModel game = new GameInfoModel();
    game.setWinner(0L);
    for (int i = 0; i < 2; i++) {
      Map<String, String> player = new HashMap<String, String>();
      player.put("id", ids.get(i).toString());
      player.put("points", playerService.findPointById(ids.get(i)).toString());
      listPlayers.add(player);
    }
    game.setPlayers(listPlayers);
    model.setGame(game);
    Long id = gameService.save(model);
    model.setId(id);
    game.setId(id);
    mapper.writeValue(response.getOutputStream(), model);
  }
}
