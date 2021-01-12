package com.hoanganh.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hoanganh.model.Credentials;
import com.hoanganh.model.PlayerModel;
import com.hoanganh.service.IPlayerService;
import com.hoanganh.service.impl.PlayerService;

@Path("/authentication")
public class AuthenticationEndpoint {

//  @Context
  private IPlayerService playerService = new PlayerService();
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response authenticateUser(Credentials credentials) {

    try {
      String username = credentials.getUsername();
      String password = credentials.getPassword();
      // Authenticate the user using the credentials provided
      authenticate(username, password);

      // Issue a token for the user
      String token = issueToken(credentials.getUsername());

      // Return the token on the response
      return Response.ok(token).build();

    } catch (Exception e) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  private void authenticate(String username, String password) throws Exception {
    // Authenticate against a database, LDAP, file or whatever
    // Throw an Exception if the credentials are invalid
    PlayerModel playerModel = playerService.findByUsernameAndPassword(username, password);
    if(playerModel == null) {
      Response.ok("Username Or Password Invalid").build();
      return;
    }
  }

  private String issueToken(String username) {
    // Issue a token (can be a random String persisted to a database or a JWT token)
    // The issued token must be associated to a user
    // Return the issued token
    Random random = new SecureRandom();
    String token = new BigInteger(130, random).toString(32);
    return token;
  }
}