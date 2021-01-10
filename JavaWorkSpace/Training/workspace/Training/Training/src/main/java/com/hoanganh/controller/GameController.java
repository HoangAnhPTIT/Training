package com.hoanganh.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hoanganh.filter.Secured;
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
  @Path("/login")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response login(PlayerModel playerModel) {
    Map<String, String> player = new HashMap<String, String>();
    player = playerModel.getPlayer();
    String username = player.get("username");
    String password = player.get("password");
    PlayerModel playerLogin = playerService.findByUsernameAndPassword(username, password);
    if (playerLogin == null) {
      return Response.ok().entity("username or password invalid").build();
    } else if (playerLogin.getStatus() == 1) {
      return Response.ok().entity("Player was in game").build();
    } else {
      playerService.updateStatus(playerLogin.getPlayer_id(), 1);
      return Response.ok().entity("Login success").build();
    }

  }

  @POST
  @Path("/logout")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response logout(PlayerModel playerModel) {
    PlayerModel playerLogout = playerService.findOne(playerModel.getPlayer_id());
    if (playerLogout == null) {
      return Response.ok().entity("player's id invalid").build();
    } else {
      playerService.updateStatus(playerLogout.getPlayer_id(), 0);
      return Response.ok().entity("Logout Success").build();
    }
  }

  @POST
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response startGame(GameModel model) {
    List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();

    List<Long> ids = GetIdFromPlayers.getId(model);

    GameInfoModel game = new GameInfoModel();
    game.setWinner(0L);
    for (int i = 0; i < 2; i++) {
      Map<String, String> player = new HashMap<String, String>();
      Long id = ids.get(i);

      PlayerModel modelLogIn = playerService.findOne(id);
      if (modelLogIn == null || modelLogIn.getStatus() == 0) {
        return Response.ok().entity("Id Invalid Or Not Login, Restart Game ???").build();
      } else {
        player.put("id", id.toString());
        player.put("points", "0");
        listPlayers.add(player);
      }
    }
    model.setPlayer1(ids.get(0));
    model.setPlayer2(ids.get(1));
    model.setStatus(1);
    model.setWinner(0L);
    Long gameId = gameService.save(model);
    Timestamp time = new Timestamp(System.currentTimeMillis());
    LogModel logModel = new LogModel(ids.get(0), ids.get(1), 0L, 0L, time, 1, gameId);
    Long id = logService.save(logModel);
    game.setPlayers(listPlayers);
    model.setGame(game);
    model.setId(id);
    game.setId(id);
    ShowGameInfo gameInfo = new ShowGameInfo();
    gameInfo.setGame(game);
    return Response.ok().entity(gameInfo).build();
  }

  @POST
  @Path("/{id}/{score}/{action}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response scorePoint(@PathParam("id") Long id, @PathParam("score") Long score,
      @PathParam("action") String action, PlayerModel playerModel) {
    Long playerId = playerModel.getPlayer_id();
    LogModel logModel = logService.findOne(id);
    if (logModel != null) {
      boolean isExist = false;
      if (logModel.getPlayer1() == playerId) {
        logModel.setPoint1(logModel.getPoint1() + score);
        logModel.setPoint2(logModel.getPoint2());
        logModel.setGameId(logModel.getGameId());
        logModel.setInGame(1);
        PlayerModel player = playerService.findOne(playerId);
        player.setTotalPoint(player.getTotalPoint() + score);
        playerService.update(player);
        isExist = true;
      } else if (logModel.getPlayer2() == playerId) {
        logModel.setPoint2(logModel.getPoint2() + score);
        logModel.setPoint1(logModel.getPoint1());
        logModel.setGameId(logModel.getGameId());
        logModel.setInGame(1);
        PlayerModel player = playerService.findOne(playerId);
        player.setTotalPoint(player.getTotalPoint() + score);
        playerService.update(player);
        isExist = true;
      }
      if (isExist) {
        Long newID = logService.save(logModel);
        logService.updateOutGame(id, 0);
        List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
        setListPlayer.setListPlayer(logModel, listPlayers);
        GameInfoModel game = new GameInfoModel();
        game.setPlayers(listPlayers);
        game.setId(newID);
        game.setWinner(0L);
        ShowGameInfo gameInfo = new ShowGameInfo();
        gameInfo.setGame(game);
        return Response.ok().entity(gameInfo).build();
      } else {
        return Response.ok().entity("Player's Id invalid").build();
      }

    } else {
      return Response.ok().entity("Log's Id invalid").build();
    }
  }

  @DELETE
  @Path("/{id}/{action}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response resetPoint(@PathParam("id") Long id, @PathParam("action") String action, PlayerModel playerModel) {
    Long playerId = playerModel.getPlayer_id();
    List<LogModel> listLogModel = logService.findListLast(id);

    if (listLogModel.size() == 0) {
      return Response.ok().entity("Game's Id invalid").build();
    } else {
      if (action.equals("reset_point")) {
        LogModel logModel1 = listLogModel.get(0);
        LogModel logModel2 = listLogModel.get(1);
        Long changePoint = null;
        boolean isExist = false;
        if (logModel1.getPlayer1() == playerId) {
          changePoint = logModel1.getPoint1() - logModel2.getPoint1();
          isExist = true;
        } else if (logModel1.getPlayer2() == playerId) {
          changePoint = logModel1.getPoint2() - logModel2.getPoint2();
          isExist = true;
        }
        if (isExist) {
          PlayerModel player = playerService.findOne(playerId);
          if (player.getTotalPoint() == 0) {
            return Response.ok().entity("Point = 0").build();
          } else {
            player.setTotalPoint(player.getTotalPoint() - changePoint);
            playerService.update(player);
            logService.delete(logModel1.getId());
            logService.updateOutGame(logModel2.getId(), 1);
            List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
            setListPlayer.setListPlayer(logModel2, listPlayers);
            GameInfoModel game = new GameInfoModel();
            game.setPlayers(listPlayers);
            game.setId(id);
            game.setWinner(0L);
            ShowGameInfo gameInfo = new ShowGameInfo();
            gameInfo.setGame(game);
            return Response.ok().entity(gameInfo).build();
          }
        } else {
          return Response.ok().entity("Player's Id Not Valid").build();
        }
      }

    }
    return null;
  }

  @PUT
  @Path("/{id}/{action}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response endGame(@PathParam("id") Long id, @PathParam("action") String action) {
    GameModel gameModel = gameService.findOne(id);
    if (gameModel != null) {
      gameModel.setStatus(0);
      LogModel logModel = logService.findByGameIdAndStatus(id, 1);
      logService.updateOutGame(logModel.getId(), 0);
      PlayerModel player1 = playerService.findOne(gameModel.getPlayer1());
      PlayerModel player2 = playerService.findOne(gameModel.getPlayer2());
      GameInfoModel game = new GameInfoModel();
      if (logModel.getPoint1() > logModel.getPoint2()) {
        gameModel.setWinner(gameModel.getPlayer1());
        player1.setWinsCount(player1.getWinsCount() + 1);
        player2.setLoseCount(player2.getLoseCount() + 1);
        game.setWinner(gameModel.getPlayer1());
      } else if (logModel.getPoint1() < logModel.getPoint2()) {
        gameModel.setWinner(gameModel.getPlayer2());
        player2.setWinsCount(player2.getWinsCount() + 1);
        player1.setLoseCount(player1.getLoseCount() + 1);
        game.setWinner(gameModel.getPlayer2());
      } else {
        gameModel.setWinner(0L);
        game.setWinner(0L);
      }
      gameService.update(gameModel);
      playerService.update(player1);
      playerService.update(player2);

      List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
      Map<String, String> playerToMap1 = new HashMap<String, String>();
      playerToMap1.put("id", gameModel.getPlayer1().toString());
      playerToMap1.put("point", player1.getTotalPoint().toString());
      listPlayers.add(playerToMap1);
      Map<String, String> playerToMap2 = new HashMap<String, String>();
      playerToMap2.put("id", gameModel.getPlayer2().toString());
      playerToMap2.put("point", player2.getTotalPoint().toString());
      listPlayers.add(playerToMap2);
      game.setPlayers(listPlayers);
      game.setId(id);
      ShowGameInfo gameInfo = new ShowGameInfo();
      gameInfo.setGame(game);
      return Response.ok().entity(gameInfo).build();
    } else {
      return Response.ok().entity("Game's Id Invalid").build();
    }

  }

  @GET
  @Path("/{id}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response getGameDetail(@PathParam("id") Long id) {
    GameModel gameModel = gameService.findOne(id);
    PlayerModel player1 = playerService.findOne(gameModel.getPlayer1());
    PlayerModel player2 = playerService.findOne(gameModel.getPlayer2());
    GameInfoModel game = new GameInfoModel();
    List<Map<String, String>> listPlayers = new ArrayList<Map<String, String>>();
    Map<String, String> playerToMap1 = new HashMap<String, String>();
    playerToMap1.put("id", gameModel.getPlayer1().toString());
    playerToMap1.put("point", player1.getTotalPoint().toString());
    listPlayers.add(playerToMap1);
    Map<String, String> playerToMap2 = new HashMap<String, String>();
    playerToMap2.put("id", gameModel.getPlayer2().toString());
    playerToMap2.put("point", player2.getTotalPoint().toString());
    listPlayers.add(playerToMap2);
    game.setPlayers(listPlayers);
    game.setId(id);
    game.setWinner(gameModel.getWinner());
    ShowGameInfo gameInfo = new ShowGameInfo();
    gameInfo.setGame(game);
    return Response.ok().entity(gameInfo).build();
  }

  @GET
  @Path("/leaderboard")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response getLeaderboard() {
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
    return Response.ok().entity(leaderboard).build();
  }

}
