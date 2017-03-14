package com.claymotion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.claymotion.hasoffer.domain.Affiliate;

public class AffiliateMapper implements RowMapper<Affiliate>{

	public Affiliate mapRow(ResultSet resultSet, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		Affiliate affiliate = new Affiliate();
		
		affiliate.setId(resultSet.getInt("affiliates_id"));
		affiliate.setName(resultSet.getString("affiliates_name"));
		affiliate.setStatusId(resultSet.getInt("affiliates_status_id"));
		
		
		return affiliate;
	}

	
}
