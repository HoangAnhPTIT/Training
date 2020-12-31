package com.hoanganh.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoanganh.model.GameInfoModel;
import com.hoanganh.model.GameModel;
import com.hoanganh.model.Leaderboard;
import com.hoanganh.model.PlayerModel;
import com.hoanganh.model.ShowGameInfo;
import com.hoanganh.service.IGameService;
import com.hoanganh.service.IPlayerService;
import com.hoanganh.service.impl.GameService;
import com.hoanganh.service.impl.PlayerService;
import com.hoanganh.utils.HttpUtil;
import com.hoanganh.utils.SetGameInfo;
import com.hoanganh.utils.SetListPlayer;

@Path("/games")
public class GameController extends HttpServlet {
  private static final long serialVersionUID = 1L;

//  @Inject
//  private IGameService gameService;
//
//  @Inject
//  private IPlayerService playerService;

  private IGameService gameService = new GameService();
  private IPlayerService playerService = new PlayerService();

  @POST
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void startGame(@Context HttpServletRequest request, @Context HttpServletResponse response,
      InputStream requestBody) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
    GameModel model = HttpUtil.of(reader).toModel(GameModel.class);
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
    ShowGameInfo gameInfo = new ShowGameInfo();
    gameInfo.setGame(game);
    mapper.writeValue(response.getOutputStream(), gameInfo);
  }

  @POST
  @Path("/{id}/{action}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void scorePoint(@PathParam("id") Long id, @PathParam("action") String action,
      @Context HttpServletRequest request, @Context HttpServletResponse response, InputStream requestBody)
      throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
    PlayerModel playerModel = HttpUtil.of(reader).toModel(PlayerModel.class);
    GameModel gameModel = gameService.findOne(id);
    playerModel = playerService.findOne(playerModel.getPlayer_id());
    if (action.equals("score")) {
      if (gameModel.getPlayer1() == playerModel.getPlayer_id()
          || gameModel.getPlayer2() == playerModel.getPlayer_id()) {
        playerModel.setPoint(playerModel.getPoint() + 1);
      } else {
        mapper.writeValue(response.getOutputStream(), "Player's id invalid");
        return;
      }
    }
    playerService.update(playerModel);
    List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
    SetListPlayer setListPlayer = new SetListPlayer();
    setListPlayer.setListPlayer(gameModel, listPlayers);
    GameInfoModel game = new GameInfoModel();
    game.setPlayers(listPlayers);
    game.setId(id);
    game.setWinner(gameModel.getWinner());
    ShowGameInfo gameInfo = new ShowGameInfo();
    gameInfo.setGame(game);
    mapper.writeValue(response.getOutputStream(), gameInfo);
  }

  @DELETE
  @Path("/{id}/{action}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void resetPoint(@PathParam("id") Long id, @PathParam("action") String action,
      @Context HttpServletRequest request, @Context HttpServletResponse response, InputStream requestBody)
      throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
    PlayerModel playerModel = HttpUtil.of(reader).toModel(PlayerModel.class);
    GameModel gameModel = gameService.findOne(id);
    playerModel = playerService.findOne(playerModel.getPlayer_id());
    if (action.equals("reset_point")) {
      if (gameModel.getPlayer1() == playerModel.getPlayer_id()
          || gameModel.getPlayer2() == playerModel.getPlayer_id()) {
        playerModel.setPoint(0L);
      } else {
        mapper.writeValue(response.getOutputStream(), "Player's id invalid");
        return;
      }
    }
    playerService.update(playerModel);
    List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
    SetListPlayer setListPlayer = new SetListPlayer();
    setListPlayer.setListPlayer(gameModel, listPlayers);
    GameInfoModel game = new GameInfoModel();
    game.setPlayers(listPlayers);
    game.setId(id);
    game.setWinner(gameModel.getWinner());
    ShowGameInfo gameInfo = new ShowGameInfo();
    gameInfo.setGame(game);
    mapper.writeValue(response.getOutputStream(), gameInfo);
  }

  @PUT
  @Path("/{id}/{action}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void endGame(@PathParam("id") Long id, @PathParam("action") String action, @Context HttpServletRequest request,
      @Context HttpServletResponse response, InputStream requestBody) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    if (action.equals("end")) {
      GameModel gameModel = gameService.findOne(id);
      PlayerModel player1 = playerService.findOne(gameModel.getPlayer1());
      PlayerModel player2 = playerService.findOne(gameModel.getPlayer2());
      if (player1.getPoint() > player2.getPoint()) {
        gameModel.setWinner(player1.getPlayer_id());
        player1.setWinsCount(player1.getWinsCount()+1);
        player2.setLoseCount(player2.getLoseCount()+1);
        playerService.update(player1);
        playerService.update(player2);
      } else if (player1.getPoint() < player2.getPoint()) {
        gameModel.setWinner(player2.getPlayer_id());
        player2.setWinsCount(player2.getWinsCount()+1);
        player1.setLoseCount(player1.getLoseCount()+1);
        playerService.update(player1);
        playerService.update(player2);
      } else {
        mapper.writeValue(response.getOutputStream(), "Unknown Winner !!!");
        return;
      }
      gameService.update(gameModel);
      List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
      SetListPlayer setListPlayer = new SetListPlayer();
      setListPlayer.setListPlayer(gameModel, listPlayers);
      SetGameInfo setGameIndo = new SetGameInfo();
      ShowGameInfo gameInfo = new ShowGameInfo();
      setGameIndo.setGameInfo(listPlayers, gameModel, id, gameInfo);
      mapper.writeValue(response.getOutputStream(), gameInfo);
    } else {
      mapper.writeValue(response.getOutputStream(), "Action invalid");
      return;
    }

  }

  @GET
  @Path("/{id}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void getGameDetail(@PathParam("id") Long id, @Context HttpServletRequest request,
      @Context HttpServletResponse response, InputStream requestBody) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    GameModel gameModel = gameService.findOne(id);
    List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
    SetListPlayer setListPlayer = new SetListPlayer();
    setListPlayer.setListPlayer(gameModel, listPlayers);
    SetGameInfo setGameIndo = new SetGameInfo();
    ShowGameInfo gameInfo = new ShowGameInfo();
    setGameIndo.setGameInfo(listPlayers, gameModel, id, gameInfo);
    
    mapper.writeValue(response.getOutputStream(), gameInfo);
  }

  @GET
  @Path("/leaderboard")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void getLeaderboard(@Context HttpServletRequest request,
      @Context HttpServletResponse response, InputStream requestBody) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    Leaderboard leaderboard = new Leaderboard();
    PlayerModel playerModel = new PlayerModel();
    playerModel.setListPlayerModel(playerService.findAll());
    List<Map<String, String>> players = new ArrayList<Map<String, String>>();
    for(PlayerModel model:playerModel.getListPlayerModel()) {
      Map<String, String> player = new HashMap<String, String>();
      player.put("id", model.getPlayer_id().toString());
      player.put("name", model.getFullName());
      player.put("wins_count", model.getWinsCount().toString());
      player.put("loses_count", model.getLoseCount().toString());
      players.add(player);
    }
    leaderboard.setPlayers(players);
    
    mapper.writeValue(response.getOutputStream(), leaderboard);
  }
  
}
