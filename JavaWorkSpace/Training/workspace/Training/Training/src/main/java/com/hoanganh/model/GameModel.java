package com.hoanganh.model;

public class GameModel {
	private Long id;
	private Long winner;
	private Long playerId;
	
	private Object players;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWinner() {
		return winner;
	}

	public void setWinner(Long winner) {
		this.winner = winner;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public Object getPlayers() {
		return players;
	}

	public void setPlayers(Object players) {
		this.players = players;
	}
	
	
	
}
