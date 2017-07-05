package com.claymotion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.claymotion.hasoffer.domain.CountryIPList;

public class CountryIPListMapper implements RowMapper<CountryIPList>{

	public CountryIPList mapRow(ResultSet resultSet, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		CountryIPList countryIPList = new CountryIPList();
		
		countryIPList.setId(resultSet.getInt("id"));
		countryIPList.setCountry(resultSet.getString("country"));
		countryIPList.setIpAddress(resultSet.getString("ipaddress"));
		return countryIPList;
	}

}
