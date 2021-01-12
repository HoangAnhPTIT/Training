package com.hoanganh.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.hoanganh.mapper.PlayerToInfo;
import com.hoanganh.model.PlayerModel;
import com.hoanganh.service.IPlayerService;
import com.hoanganh.service.impl.PlayerService;
import com.hoanganh.viewmodel.ListPlayer;
import com.hoanganh.viewmodel.PlayerInfoModel;
import com.hoanganh.viewmodel.ShowPlayerInfo;

@Path("/player-api")
public class PlayerController extends HttpServlet {
  private static final long serialVersionUID = 1L;

//    @Inject
  private IPlayerService playerService = new PlayerService();

  private PlayerToInfo mapperToModel = new PlayerToInfo();

  @GET
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response doGet() {
    PlayerModel model = new PlayerModel();
    model.setListPlayerModel(playerService.findAll());
    List<PlayerInfoModel> list = new ArrayList<>();
    for (PlayerModel player : model.getListPlayerModel()) {
      list.add(mapperToModel.mapper(player));
    }
    ListPlayer listPlayer = new ListPlayer();
    listPlayer.setPlayers(list);
    return Response.ok().entity(listPlayer).build();
  }

  @POST
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response doPost(PlayerModel model) {
    Long id = playerService.save(model);
    model.setPlayer_id(id);
    PlayerInfoModel playerInfo = mapperToModel.mapper(model);
    ShowPlayerInfo showPlayer = new ShowPlayerInfo();
    showPlayer.setPlayerInfo(playerInfo);
    return Response.ok().entity(showPlayer).build();
  }

  @PUT
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response doPut(PlayerModel model) {
    playerService.update(model);
    PlayerInfoModel playerInfo = mapperToModel.mapper(model);
    ShowPlayerInfo showPlayer = new ShowPlayerInfo();
    showPlayer.setPlayerInfo(playerInfo);
    return Response.ok().entity(showPlayer).build();
  }

  @DELETE
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response doDelete(PlayerModel model) {
    playerService.delete(model.getIds());
    return Response.ok().entity("Done !!!").build();
  }

  @GET
  @Path("/{id}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  @Secured
  public Response getOne(@PathParam("id") Long id) {
    PlayerModel model = playerService.findOne(id);
    PlayerInfoModel playerInfo = mapperToModel.mapper(model);
    ShowPlayerInfo showPlayer = new ShowPlayerInfo();
    showPlayer.setPlayerInfo(playerInfo);
    return Response.ok().entity(showPlayer).build();
  }
}
