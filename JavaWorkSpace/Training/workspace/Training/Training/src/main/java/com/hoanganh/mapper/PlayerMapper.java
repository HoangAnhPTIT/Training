package com.hoanganh.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.hoanganh.model.PlayerModel;

public class PlayerMapper implements RowMapper<PlayerModel>{

	@Override
	public PlayerModel mapRow(ResultSet result) {
		PlayerModel model = new PlayerModel();
		try {
			model.setPlayer_id(result.getLong("id"));
			model.setFullName(result.getString("fullname"));
			model.setLoseCount(result.getLong("losecount"));
			model.setWinsCount(result.getLong("winscount"));
			model.setPassword(result.getString("password"));
			model.setUserName(result.getString("username"));
			model.setStatus(result.getInt("status"));
			model.setTotalPoint(result.getLong("totalpoint"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public Long mapPoint(ResultSet result) {
	    Long point = null;
	    try {
	        point = result.getLong("points");
	    } catch(SQLException e) {
	        e.printStackTrace();
	    }
	    return point;
	}

  @Override
  public Timestamp mapTime(ResultSet result) {
    // TODO Auto-generated method stub
    return null;
  }

}
