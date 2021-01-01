package com.hoanganh.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import com.hoanganh.mapper.PlayerToInfo;
import com.hoanganh.model.PlayerModel;
import com.hoanganh.service.IPlayerService;
import com.hoanganh.service.impl.PlayerService;
import com.hoanganh.utils.HttpUtil;
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
  
  public void doGet(@Context HttpServletRequest request, @Context HttpServletResponse response, InputStream requestBody)
      throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    PlayerModel model = new PlayerModel();
    model.setListPlayerModel(playerService.findAll());
    mapper.writeValue(response.getOutputStream(), model);
  }
  @POST
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void doPost(@Context HttpServletRequest request, @Context HttpServletResponse response,
      InputStream requestBody) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
    PlayerModel model = HttpUtil.of(reader).toModel(PlayerModel.class);
    Long id = playerService.save(model);
    model.setPlayer_id(id);
    PlayerInfoModel playerInfo = mapperToModel.mapper(model);
    ShowPlayerInfo showPlayer = new ShowPlayerInfo();
    showPlayer.setPlayerInfo(playerInfo);
    mapper.writeValue(response.getOutputStream(), showPlayer);
  }
  @PUT
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void doPut(@Context HttpServletRequest request, @Context HttpServletResponse response, InputStream requestBody)
      throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
    PlayerModel model = HttpUtil.of(reader).toModel(PlayerModel.class);
    playerService.update(model);
    PlayerInfoModel playerInfo = mapperToModel.mapper(model);
    ShowPlayerInfo showPlayer = new ShowPlayerInfo();
    showPlayer.setPlayerInfo(playerInfo);
    mapper.writeValue(response.getOutputStream(), showPlayer);
  }
  @DELETE
  @Path("/")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  public void doDelete(@Context HttpServletRequest request, @Context HttpServletResponse response,
      InputStream requestBody) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
    PlayerModel model = HttpUtil.of(reader).toModel(PlayerModel.class);
    playerService.delete(model.getIds());
    mapper.writeValue(response.getOutputStream(), "Xoa Xong Roi, HIHI!!!");
  }
  
  @GET
  @Path("/{id}")
  @Consumes({ MediaType.APPLICATION_JSON })
  @Produces({ MediaType.APPLICATION_JSON })
  
  public void getOne(@PathParam("id") Long id, @Context HttpServletRequest request, @Context HttpServletResponse response, InputStream requestBody)
      throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    PlayerModel model = playerService.findOne(id);
    PlayerInfoModel playerInfo = mapperToModel.mapper(model);
    ShowPlayerInfo showPlayer = new ShowPlayerInfo();
    showPlayer.setPlayerInfo(playerInfo);
    mapper.writeValue(response.getOutputStream(), showPlayer);
  }
}
