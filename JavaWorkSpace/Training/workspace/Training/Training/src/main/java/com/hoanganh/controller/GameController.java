package com.hoanganh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoanganh.model.GameModel;
import com.hoanganh.utils.HttpUtil;

@WebServlet(urlPatterns = {"/games"})
public class GameController extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		GameModel model = HttpUtil.of(request.getReader()).toModel(GameModel.class);
		System.out.println(model.getPlayers());
		mapper.writeValue(response.getOutputStream(), model);
	}
}
