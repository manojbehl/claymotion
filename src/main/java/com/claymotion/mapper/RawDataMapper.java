package com.claymotion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.claymotion.hasoffer.domain.AdvertiserRawData;

public class RawDataMapper implements RowMapper<AdvertiserRawData>{

	public AdvertiserRawData mapRow(ResultSet resultSet, int arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		AdvertiserRawData advertiserRawData = new AdvertiserRawData();
		
		advertiserRawData.setAdvertiser(resultSet.getInt("Advertiser"));
		advertiserRawData.setAndroidPackage(resultSet.getString("package"));
		advertiserRawData.setAppUrl(resultSet.getString("app_url"));
		advertiserRawData.setCampaignId(resultSet.getInt("campaign_id"));
		advertiserRawData.setCategory(resultSet.getString("category"));
		advertiserRawData.setCountry(resultSet.getString("country"));
		advertiserRawData.setCurrency(resultSet.getString("currency"));
		advertiserRawData.setDescription(resultSet.getString("description"));
		advertiserRawData.setId(resultSet.getInt("id"));
		advertiserRawData.setName(resultSet.getString("name"));
		advertiserRawData.setOfferId(resultSet.getInt("offer_id"));
		advertiserRawData.setRevenueRate(resultSet.getDouble("revenue_rate"));
		advertiserRawData.setRevenueType(resultSet.getString("revenue_type"));
		advertiserRawData.setStoreRating(resultSet.getDouble("store_rating"));
		
		return advertiserRawData;
	}

}
