package com.hoanganh.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoanganh.model.PlayerModel;
import com.hoanganh.service.IPlayerService;
import com.hoanganh.service.impl.PlayerService;
import com.hoanganh.utils.HttpUtil;

@Path("/player-api")
public class PlayerController extends HttpServlet {
  private static final long serialVersionUID = 1L;

//    @Inject
  private IPlayerService playerService = new PlayerService();
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
    model.setListModel(playerService.findAll());
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
    mapper.writeValue(response.getOutputStream(), model);
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
    mapper.writeValue(response.getOutputStream(), model);
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
  }
}
