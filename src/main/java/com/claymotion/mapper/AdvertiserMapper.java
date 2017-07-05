package com.claymotion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.claymotion.hasoffer.domain.Advertiser;

public class AdvertiserMapper implements RowMapper<Advertiser>{

	public Advertiser mapRow(ResultSet resultSet, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		Advertiser advertiser = new Advertiser();
		
		advertiser.setId(resultSet.getString("id"));
		advertiser.setName(resultSet.getString("advertiser_name"));
		advertiser.setPriority(resultSet.getInt("advertiser_priority"));
		
		
		return advertiser;
	}

}
