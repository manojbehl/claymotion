package com.claymotion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.claymotion.advertiser.Creative;
import com.claymotion.hasoffer.domain.CreativeDomain;
import com.claymotion.hasoffer.domain.HasOfferData;

public class HasOfferDataMapper implements RowMapper<HasOfferData>{

	public HasOfferData mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		HasOfferData hasOfferData = new HasOfferData();
		
		hasOfferData.setOfferId(rs.getInt("offer_id"));
		hasOfferData.setAdvertiser(rs.getInt("advertiser"));
		hasOfferData.setAndroidPackage(rs.getString("android_package"));
		hasOfferData.setAppUrl(rs.getString("app_url"));
		hasOfferData.setCurrency(rs.getString("currency"));
		hasOfferData.setCountry(rs.getString("country"));
		hasOfferData.setDescription(rs.getString("Description"));
		hasOfferData.setName(rs.getString("name"));
		hasOfferData.setDeveloper(rs.getString("developer"));
		hasOfferData.setIcon(rs.getString("icon"));
		hasOfferData.setStoreRating(rs.getDouble("store_rating"));
		hasOfferData.setVersion(rs.getString("version"));
		hasOfferData.setClickUrl(rs.getString("click_url"));
		hasOfferData.setAffiliateId(rs.getInt("affiliate_id"));
		hasOfferData.setTitle(rs.getString("title"));
		hasOfferData.setSubDescription(rs.getString("short_description"));
		hasOfferData.setCategory(rs.getString("category"));
		hasOfferData.setRevenueRate(rs.getDouble("revenue_rate"));
		hasOfferData.setPriority(rs.getInt("priority"));
		hasOfferData.setCategory(rs.getString("category"));
		
		
		CreativeDomain creativeDomain = new CreativeDomain();
		
		creativeDomain.setAndroidPackage(rs.getString("package"));
		creativeDomain.setId(rs.getString("creative_id"));
		creativeDomain.setDescription(rs.getString("creative_description"));
		creativeDomain.setFilename(rs.getString("file_name"));
		creativeDomain.setHeight(rs.getInt("height"));
		creativeDomain.setIs_active(rs.getInt("is_active") ==1 ? true :false);
		creativeDomain.setMimetype(rs.getString("mime_type"));
		creativeDomain.setPartner_id(rs.getString("partner_id"));
		creativeDomain.setTitle(rs.getString("title"));
		creativeDomain.setUploadPath(rs.getString("upload_path"));
		creativeDomain.setVersion(rs.getInt("version"));
		creativeDomain.setWidthHeight(rs.getString("width_height"));
		creativeDomain.setCountryCode(rs.getString("cr_country"));
		
		
		hasOfferData.getListOfCreativeDomains().add(creativeDomain);
		
		return hasOfferData;
	}

}
