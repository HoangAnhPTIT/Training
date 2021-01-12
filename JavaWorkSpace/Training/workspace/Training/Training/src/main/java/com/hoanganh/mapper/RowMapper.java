package com.hoanganh.mapper;

import java.sql.ResultSet;
import java.sql.Timestamp;

public interface RowMapper<T> {
	T mapRow(ResultSet result);
	Long mapPoint(ResultSet result);
	Timestamp mapTime(ResultSet result);
}
