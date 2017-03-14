package com.claymotion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.claymotion.hasoffer.domain.AdvertiserCreative;

public class AdvertiserCreativeMapper implements RowMapper<AdvertiserCreative>{

	public AdvertiserCreative mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		// TODO Auto-generated method stub
		AdvertiserCreative advertiserCreative = new AdvertiserCreative();
		advertiserCreative.setId(rs.getInt("id"));
		advertiserCreative.setOfferId(rs.getInt("offer_id"));
		advertiserCreative.setKey(rs.getString("creative_key"));
		advertiserCreative.setValue(rs.getString("creative_value"));
		advertiserCreative.setCountryCode(rs.getString("country_code"));
		
		return advertiserCreative;
	}

}
