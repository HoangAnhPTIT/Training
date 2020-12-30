package com.hoanganh.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoanganh.model.PlayerModel;
import com.hoanganh.service.IPlayerService;
import com.hoanganh.utils.HttpUtil;

@WebServlet(urlPatterns = { "/api" })
public class PlayerController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private IPlayerService playerService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PlayerModel model = new PlayerModel();
        model.setListModel(playerService.findAll());
        mapper.writeValue(response.getOutputStream(), model);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PlayerModel model = HttpUtil.of(request.getReader()).toModel(PlayerModel.class);
        Long id = playerService.save(model);
        model.setId(id);
        mapper.writeValue(response.getOutputStream(), model);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PlayerModel model = HttpUtil.of(request.getReader()).toModel(PlayerModel.class);
        playerService.update(model);
        mapper.writeValue(response.getOutputStream(), model);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PlayerModel model = HttpUtil.of(request.getReader()).toModel(PlayerModel.class);
        playerService.delete(model.getIds());
    }
}
