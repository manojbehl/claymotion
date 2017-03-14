package com.claymotion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.claymotion.hasoffer.domain.HasOfferData;

public class OfferDataMapper implements RowMapper<HasOfferData>{

	public HasOfferData mapRow(ResultSet resultSet, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		HasOfferData hasOfferData = new HasOfferData();
		
		hasOfferData.setOfferId(resultSet.getInt("offer_id"));
		hasOfferData.setName(resultSet.getString("name"));
		
		return hasOfferData;
	}

}
