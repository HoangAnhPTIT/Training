package com.hoanganh.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel {
	private Long id;
	private Long point;
	private String fullName;
	private Long loseCount;
	private Long winsCount;
	
	private Long[] ids;
	
	private List<PlayerModel> listModel = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPoint() {
		return point;
	}
	public void setPoint(Long point) {
		this.point = point;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getLoseCount() {
		return loseCount;
	}
	public void setLoseCount(Long loseCount) {
		this.loseCount = loseCount;
	}
	public Long getWinsCount() {
		return winsCount;
	}
	public void setWinsCount(Long winsCount) {
		this.winsCount = winsCount;
	}
	public List<PlayerModel> getListModel() {
		return listModel;
	}
	public void setListModel(List<PlayerModel> listModel) {
		this.listModel = listModel;
	}
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	
}
