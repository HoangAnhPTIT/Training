package com.hoanganh.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hoanganh.model.GameModel;
import com.hoanganh.model.LogModel;
import com.hoanganh.model.PlayerModel;
import com.hoanganh.service.IGameService;
import com.hoanganh.service.ILogService;
import com.hoanganh.service.IPlayerService;
import com.hoanganh.service.impl.GameService;
import com.hoanganh.service.impl.LogService;
import com.hoanganh.service.impl.PlayerService;
import com.hoanganh.utils.GetIdFromPlayers;
import com.hoanganh.utils.HttpUtil;
import com.hoanganh.utils.SetGameInfo;
import com.hoanganh.utils.SetListPlayer;
import com.hoanganh.viewmodel.GameInfoModel;
import com.hoanganh.viewmodel.Leaderboard;
import com.hoanganh.viewmodel.ShowGameInfo;

@Path("/games")
public class GameController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private IGameService gameService = new GameService();
  private IPlayerService playerService = new PlayerService();
  private ILogService logService = new LogService();
  private SetListPlayer setListPlayer = new SetListPlayer();

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
    List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();

    List<Long> ids = GetIdFromPlayers.getId(model);

    GameInfoModel game = new GameInfoModel();
    game.setWinner(0L);
    for (int i = 0; i < 2; i++) {
      Map<String, String> player = new HashMap<String, String>();
      Long id = ids.get(i);
      PlayerModel modelLogIn = playerService.findOne(id);
      if (modelLogIn == null) {
        mapper.writeValue(response.getOutputStream(), "Id invalid, Restart Game ???");
        return;
      } else {
        player.put("id", id.toString());
        player.put("points", "0");
        listPlayers.add(player);
      }
    }
    Timestamp time = new Timestamp(System.currentTimeMillis());
    Timestamp timePlay = logService.getLastPlay(ids.get(0), ids.get(1));
    Long id;
    if (timePlay != null) {
      LogModel lastModel = logService.findByPlayerAndTimePlay(ids.get(0), ids.get(1), timePlay);
      logService.updateOutGame(lastModel.getId(), 0);
      LogModel logModel = new LogModel(ids.get(0), ids.get(1), lastModel.getPoint1(), lastModel.getPoint2(), 0L, 0L,
          time, 1);
      id = logService.save(logModel);
    } else {
      LogModel logModel = new LogModel(ids.get(0), ids.get(1), 0L, 0L, 0L, 0L, time, 1);
      id = logService.save(logModel);
    }
    game.setPlayers(listPlayers);
    model.setGame(game);
    model.setId(id);
    game.setId(id);
    ShowGameInfo gameInfo = new ShowGameInfo();
    gameInfo.setGame(game);
    mapper.writeValue(response.getOutputStream(), gameInfo);
  }

  @POST
  @Path("/{id}/{score}/{action}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void scorePoint(@PathParam("id") Long id, @PathParam("score") Long score, @PathParam("action") String action,
      @Context HttpServletRequest request, @Context HttpServletResponse response, InputStream requestBody)
      throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
    PlayerModel playerModel = HttpUtil.of(reader).toModel(PlayerModel.class);
    Long playerId = playerModel.getPlayer_id();
    LogModel logModel = logService.findByPlayerAndStatus(playerId, 1);
    if (logModel != null) {

      if (logModel.getPlayer1() == playerId) {
        logModel.setcPoint1(logModel.getcPoint1() + score);
        logModel.setPoint1(logModel.getPoint1() + score);
      } else if (logModel.getPlayer2() == playerId) {
        logModel.setcPoint2(logModel.getcPoint1() + score);
        logModel.setPoint2(logModel.getPoint1() + score);
      }
      logService.update(logModel);
      List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
      setListPlayer.setListPlayer(logModel, listPlayers);
      GameInfoModel game = new GameInfoModel();
      game.setPlayers(listPlayers);
      game.setId(id);
      if (logModel.getcPoint1() > logModel.getcPoint2()) {
        game.setWinner(logModel.getPlayer1());
      } else if (logModel.getcPoint1() < logModel.getcPoint2()) {
        game.setWinner(logModel.getPlayer2());
      } else {
        game.setWinner(0L);
      }
      ShowGameInfo gameInfo = new ShowGameInfo();
      gameInfo.setGame(game);
      mapper.writeValue(response.getOutputStream(), gameInfo);
    } else {
      mapper.writeValue(response.getOutputStream(), "Player's Id invalid");
      return;
    }
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
    LogModel logModel = logService.findOne(id);
    if (logModel == null) {
      mapper.writeValue(response.getOutputStream(), "Game's Id invalid");
      return;
    } else {
      if (action.equals("reset_point")) {
        if (logModel.getPlayer1() == playerModel.getPlayer_id()
            || logModel.getPlayer2() == playerModel.getPlayer_id()) {
          logModel.setPoint1(logModel.getPoint1() - logModel.getcPoint1());
          logModel.setPoint2(logModel.getPoint2() - logModel.getcPoint2());
          logModel.setcPoint1(0L);
          logModel.setcPoint2(0L);
          logService.update(logModel);
        } else {
          mapper.writeValue(response.getOutputStream(), "Game's Id invalid");
          return;
        }
      }
      List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
      setListPlayer.setListPlayer(logModel, listPlayers);
      GameInfoModel game = new GameInfoModel();
      game.setPlayers(listPlayers);
      game.setId(id);
      game.setWinner(0L);
      ShowGameInfo gameInfo = new ShowGameInfo();
      gameInfo.setGame(game);
      mapper.writeValue(response.getOutputStream(), gameInfo);
    }
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
    GameModel gameModel = new GameModel();
    LogModel logModel = logService.findOne(id);
    if (logModel == null) {
      mapper.writeValue(response.getOutputStream(), "Game's Id invalid");
      return;
    } else {
      if (action.equals("end-game")) {
        Long gameId = null;
        if (logModel.getPoint1() > logModel.getPoint2()) {
          gameModel.setWinner(logModel.getPlayer1());
          gameModel.setPlayer1(logModel.getPlayer1());
          gameModel.setPlayer2(logModel.getPlayer2());
          PlayerModel winner = playerService.findOne(logModel.getPlayer1());
          PlayerModel loser = playerService.findOne(logModel.getPlayer2());
          winner.setWinsCount(winner.getWinsCount() + 1);
          loser.setLoseCount(loser.getLoseCount() + 1);
          playerService.update(winner);
          playerService.update(loser);
          gameId = gameService.save(gameModel);
        } else if (logModel.getPoint1() < logModel.getPoint2()) {
          gameModel.setWinner(logModel.getPlayer2());
          gameModel.setPlayer1(logModel.getPlayer1());
          gameModel.setPlayer2(logModel.getPlayer2());
          PlayerModel winner = playerService.findOne(logModel.getPlayer2());
          PlayerModel loser = playerService.findOne(logModel.getPlayer1());
          winner.setWinsCount(winner.getWinsCount() + 1);
          loser.setLoseCount(loser.getLoseCount() + 1);
          playerService.update(winner);
          playerService.update(loser);
          gameId = gameService.save(gameModel);
        } else {
          gameModel.setWinner(0L);
          gameModel.setPlayer1(logModel.getPlayer1());
          gameModel.setPlayer2(logModel.getPlayer2());
          gameId = gameService.save(gameModel);
        }
        logService.updateOutGame(id, 0);
        List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
        Map<String, String> player1 = new HashMap<String, String>();
        player1.put("id", logModel.getPlayer1().toString());
        player1.put("points", logModel.getPoint1().toString());
        listPlayers.add(player1);
        Map<String, String> player2 = new HashMap<String, String>();
        player2.put("id", logModel.getPlayer2().toString());
        player2.put("points", logModel.getPoint2().toString());
        listPlayers.add(player2);
        SetGameInfo setGameIndo = new SetGameInfo();
        ShowGameInfo gameInfo = new ShowGameInfo();
        setGameIndo.setGameInfo(listPlayers, gameModel, gameId, gameInfo);
        mapper.writeValue(response.getOutputStream(), gameInfo);
      } else if (action.equals("end")) {
        List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
        setListPlayer.setListPlayer(logModel, listPlayers);
        GameInfoModel game = new GameInfoModel();
        game.setPlayers(listPlayers);
        game.setId(id);
        if (logModel.getcPoint1() > logModel.getcPoint2()) {
          game.setWinner(logModel.getPlayer1());
        } else if (logModel.getcPoint1() < logModel.getcPoint2()) {
          game.setWinner(logModel.getPlayer2());
        } else {
          game.setWinner(0L);
        }
        ShowGameInfo gameInfo = new ShowGameInfo();
        gameInfo.setGame(game);
        logService.updateOutGame(id, 0);
        mapper.writeValue(response.getOutputStream(), gameInfo);
      } else {
        mapper.writeValue(response.getOutputStream(), "Action invalid");
        return;
      }
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
    LogModel logModel = logService.findOne(id);
    if (logModel != null) {
      List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
      setListPlayer.setListPlayer(logModel, listPlayers);
      GameInfoModel game = new GameInfoModel();
      game.setPlayers(listPlayers);
      game.setId(id);
      if (logModel.getcPoint1() > logModel.getcPoint2()) {
        game.setWinner(logModel.getPlayer1());
      } else if (logModel.getcPoint1() < logModel.getcPoint2()) {
        game.setWinner(logModel.getPlayer2());
      } else {
        game.setWinner(0L);
      }
      ShowGameInfo gameInfo = new ShowGameInfo();
      gameInfo.setGame(game);
      mapper.writeValue(response.getOutputStream(), gameInfo);
    } else {
      mapper.writeValue(response.getOutputStream(), "Game's Id invalid");
      return;
    }
  }

  @GET
  @Path("/leaderboard")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void getLeaderboard(@Context HttpServletRequest request, @Context HttpServletResponse response,
      InputStream requestBody) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    Leaderboard leaderboard = new Leaderboard();
    PlayerModel playerModel = new PlayerModel();
    playerModel.setListPlayerModel(playerService.findAll());
    List<Map<String, String>> players = new ArrayList<Map<String, String>>();
    for (PlayerModel model : playerModel.getListPlayerModel()) {
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
