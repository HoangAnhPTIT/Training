package com.hoanganh.mapper;

import java.sql.ResultSet;

public interface RowMapper<T> {
	T mapRow(ResultSet result);
	Long mapPoint(ResultSet result);
}
