package com.claymotion.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.claymotion.api.response.Offer;
import com.claymotion.api.response.OfferPrioirty;
import com.claymotion.hasoffer.HasOfferConstant;
import com.claymotion.hasoffer.domain.Advertiser;
import com.claymotion.hasoffer.domain.AdvertiserCreative;
import com.claymotion.hasoffer.domain.AdvertiserRawData;
import com.claymotion.hasoffer.domain.Affiliate;
import com.claymotion.hasoffer.domain.AffiliateOffer;
import com.claymotion.hasoffer.domain.CountryIPList;
import com.claymotion.hasoffer.domain.CreativeDomain;
import com.claymotion.hasoffer.domain.HasOfferData;
import com.claymotion.mapper.AdvertiserCreativeMapper;
import com.claymotion.mapper.AdvertiserMapper;
import com.claymotion.mapper.AffiliateMapper;
import com.claymotion.mapper.CountryIPListMapper;
import com.claymotion.mapper.HasOfferDataMapper;
import com.claymotion.mapper.OfferDataMapper;
import com.claymotion.mapper.RawDataMapper;
import com.claymotion.response.OfferRequest;

@Component
public class ClayMotionDAO implements IClayMotionDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<CountryIPList> getCountryIPList() {
		String sqlQuery = " Select * from country_ip_list";

		return jdbcTemplate.query(sqlQuery, new CountryIPListMapper());
	}

	public List<AdvertiserRawData> getListOfApp(int advertiserId, String country) {
		// TODO Auto-generated method stub
		String sqlQuery = "Select * from advertiser_raw_data where Advertiser = ? and country=?";

		List<AdvertiserRawData> listOfData = jdbcTemplate.query(sqlQuery, new Object[] { advertiserId, country },
				new RawDataMapper());

		return listOfData;
	}

	public void insertAdvertiserData(final List<Advertiser> listOfAdvertiser) {
		String sqlQuery = " INSERT INTO advertisers (id,advertiser_name,advertiser_status_id) VALUES (?,?,?) ";

		jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				Advertiser advertiser = listOfAdvertiser.get(i);
				ps.setString(1, advertiser.getId());
				ps.setString(2, advertiser.getName());
				ps.setInt(3, 2);
				

			}

			public int getBatchSize() {
				// TODO Auto-generated method stub
				return listOfAdvertiser.size();
			}
		});
	}

	public void addRawData(Offer advertiserRawData) {

		/*
		 * String sqlQuery =
		 * " INSERT INTO advertiser_raw_data (Name,Description,category,Advertiser,package,revenue_type,revenue_rate,campaign_id,"
		 * +
		 * " store_rating,country,currency,app_url,offer_id, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
		 * ;
		 * 
		 * jdbcTemplate.update(sqlQuery, new
		 * Object[]{advertiserRawData.getTitle(),
		 * advertiserRawData.getDescription(),
		 * advertiserRawData.getCategories().get(0),
		 * advertiserRawData.getAdvertiser(),
		 * advertiserRawData.getPackageName(),
		 * advertiserRawData.getRevenueType(),
		 * advertiserRawData.getRevenueRate(), advertiserRawData.getC(),
		 * advertiserRawData.getStoreRating(),
		 * advertiserRawData.getCountries().get(0),
		 * advertiserRawData.getCurrency(), advertiserRawData.getUrl(),
		 * advertiserRawData.getOffer_id()(), advertiserRawData.getStatus()
		 * 
		 * 
		 * });
		 */

		String sqlQuery = " INSERT INTO advertiser_raw_data (Name,Description,category,Advertiser,package,"
				+ " app_url,offer_id, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

		jdbcTemplate.update(sqlQuery,
				new Object[] { advertiserRawData.getTitle(), advertiserRawData.getDescription(),
						advertiserRawData.getCategories().get(0), advertiserRawData.getAdvertiser(),
						advertiserRawData.getPackageName(), advertiserRawData.getUrl(), advertiserRawData.getOffer_id(),
						advertiserRawData.getStatus()

				});

	}

	public void updateStatus(AdvertiserRawData advertiserRawData) {
		String sqlQuery = " update advertiser_raw_data set status ='" + advertiserRawData.getStatus() + "' where id = "
				+ advertiserRawData.getId();

		jdbcTemplate.update(sqlQuery);
	}

	public void updateOfferData(Offer offer) {
		String sqlQuery = " update hasoffer_data set status='" + offer.getStatus() + "' where  offer_id = "
				+ offer.getId();

		jdbcTemplate.update(sqlQuery);
	}

	public void addOfferData(Offer offer) {
		// TODO Auto-generated method stub

		String sqlQuery = " INSERT INTO hasoffer_data (title,Name,Description,Advertiser,"
				+ " country,app_url,offer_id,status,android_package,category,id,currency,revenue_rate,revenue_type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		String name = "";
		try {
			name = offer.getTitle().substring(0, offer.getTitle().indexOf("_"));
		} catch (Exception ex) {
			name = offer.getTitle();
		}
		jdbcTemplate.update(sqlQuery,
				new Object[] { offer.getTitle(), name, offer.getDescription(), offer.getAdvertiser(),
						// offer.getRevenue_type(), of(),offer.getRating(),
						offer.getCountries().size() > 0 ? offer.getCountries().get(0) : null, offer.getUrl(),
						offer.getOffer_id(), offer.getStatus(), offer.getPackageName(),
						offer.getCategories().size() > 0 ? offer.getCategories().get(0) : null, offer.getId(),
						offer.getCurrency(), offer.getRevenueRate(), "Fixed"

				});
	}

	public void addGenerateLinkData(AffiliateOffer affiliateOffer) {
		// TODO Auto-generated method stub
		String sqlQuery = " INSERT INTO offer_affiliate (offer_id,affiliate_id,impression_pixel,click_url) VALUES (?,?,?,?) ";

		jdbcTemplate.update(sqlQuery, new Object[] { Integer.parseInt(affiliateOffer.getOffer_id()),
				affiliateOffer.getAffiliate_id(), affiliateOffer.getImpression_pixel(), affiliateOffer.getClick_url()

		});

	}

	/*
	 * public void addGenerateLinkDataList(final List<AffiliateOffer>
	 * listOfAffiliateOffer) { // TODO Auto-generated method stub StringBuffer
	 * sb = new StringBuffer(); sb.append(
	 * "INSERT INTO offer_affiliate (offer_id,affiliate_id,impression_pixel,click_url) VALUES (?,?,?,?) "
	 * );
	 * 
	 * jdbcTemplate.batchUpdate(sb.toString(), new
	 * BatchPreparedStatementSetter() {
	 * 
	 * public void setValues(PreparedStatement ps, int i) throws SQLException {
	 * // TODO Auto-generated method stub AffiliateOffer affiliateOffer =
	 * listOfAffiliateOffer.get(i); ps.setInt(1, affiliateOffer.getOffer_id());
	 * ps.setInt(2, Integer.parseInt(affiliateOffer.getAffiliate_id()));
	 * ps.setString(3, affiliateOffer.getImpression_pixel()); ps.setString(4,
	 * affiliateOffer.getClick_url()); }
	 * 
	 * public int getBatchSize() { // TODO Auto-generated method stub return
	 * listOfAffiliateOffer.size(); } });
	 * 
	 * 
	 * 
	 * }
	 */

	public String getOfferData() {
		String strQuery = " select name from hasoffer_data limit 1";

		return jdbcTemplate.queryForObject(strQuery, String.class);
	}

	public void addHasOfferData(final List<HasOfferData> listOfHasOfferData) {
		// TODO Auto-generated method stub

		String sqlQuery = " INSERT INTO hasoffer_data (Name,Description,Advertiser,revenue_type,revenue_rate,"
				+ " store_rating,currency,app_url,offer_id,status,android_package,developer,icon,version,country,title,category,short_description) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

		jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				HasOfferData hasOfferData = listOfHasOfferData.get(i);
				ps.setString(1, hasOfferData.getName());
				ps.setString(2, hasOfferData.getDescription());
				ps.setString(3, hasOfferData.getAdvertiser());
				ps.setString(4, hasOfferData.getRevenueType());
				ps.setDouble(5, hasOfferData.getRevenueRate());
				ps.setDouble(6, hasOfferData.getStoreRating());
				ps.setString(7, hasOfferData.getCurrency());
				ps.setString(8, hasOfferData.getAppUrl());
				ps.setString(9, hasOfferData.getOfferId());
				ps.setString(10, hasOfferData.getStatus());
				ps.setString(11, hasOfferData.getAndroidPackage().trim());
				ps.setString(12, hasOfferData.getDeveloper());
				ps.setString(13, hasOfferData.getIcon());
				ps.setString(14, hasOfferData.getVersion());
				ps.setString(15, hasOfferData.getCountry());
				ps.setString(16, hasOfferData.getTitle());
				ps.setString(17, hasOfferData.getCategory());
				ps.setString(18, hasOfferData.getSubDescription());

			}

			public int getBatchSize() {
				// TODO Auto-generated method stub
				return listOfHasOfferData.size();
			}
		});

	}

	public void addCreativeData(final List<CreativeDomain> listOfCreativeDomains) {
		// TODO Auto-generated method stub

		String sqlQuery = " INSERT INTO creative (offer_id,width,height,mime_type,size,file_name,width_height) VALUES (?,?,?,?,?,?,?) ";

		jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				CreativeDomain creativeDomain = listOfCreativeDomains.get(i);

				String widthHeight = creativeDomain.getWidth() + "x" + creativeDomain.getHeight();

				ps.setInt(1, creativeDomain.getOfferId());
				ps.setInt(2, creativeDomain.getWidth());
				ps.setInt(3, creativeDomain.getHeight());
				ps.setString(4, creativeDomain.getMimetype());
				ps.setString(5, creativeDomain.getSize());
				ps.setString(6, creativeDomain.getFilename());
				ps.setString(7, widthHeight);

			}

			public int getBatchSize() {
				// TODO Auto-generated method stub
				return listOfCreativeDomains.size();
			}
		});

	}

	public void updateGenerateLink(int offerId) {
		// TODO Auto-generated method stub
		String sqlQuery = " update hasoffer_data  set does_link_generate =1  where offer_id = " + offerId;

		jdbcTemplate.update(sqlQuery);

	}

	public List<HasOfferData> getUnGeneratedOffer() {
		// TODO Auto-generated method stub
		String sqlQuery = "SELECT id,offer_id,name,advertiser from hasoffer_data hod inner join advertisers adv on adv.advertiser_id = hod.Advertiser "
				+ " where hod.does_link_generate = 0 and adv.advertiser_priority =1";

		return (List<HasOfferData>) jdbcTemplate.query(sqlQuery, new OfferDataMapper());

	}

	public List<Affiliate> getAffiliate() {

		String sqlQuery = " Select * from affiliates where affiliates_status_id = 1";

		// TODO Auto-generated method stub
		return (List<Affiliate>) jdbcTemplate.query(sqlQuery, new AffiliateMapper());
	}

	public List<Advertiser> getAdvertiser() {

		String sqlQuery = " Select * from advertisers where advertiser_status_id = 1";

		// TODO Auto-generated method stub
		return (List<Advertiser>) jdbcTemplate.query(sqlQuery, new AdvertiserMapper());
	}
	
	public List<Advertiser> getAllExistingAdvertiser() {

		String sqlQuery = " Select * from advertisers";

		// TODO Auto-generated method stub
		return (List<Advertiser>) jdbcTemplate.query(sqlQuery, new AdvertiserMapper());
	}

	public void deleteAlreadyCreatedPriority() {
		String sqlQuery = " delete from offer_priority";

		jdbcTemplate.update(sqlQuery);

	}

	public void deleteAlreadyGeneratedLinks() {
		// TODO Auto-generated method stub
		String sqlQuery = " delete from offer_affiliate";

		jdbcTemplate.update(sqlQuery);
	}

	public void deleteAlreadyCreatedOffers() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String sqlQuery = " delete from hasoffer_data";

		jdbcTemplate.update(sqlQuery);
	}

	public void deleteAlreadyCreatedCreatives() {
		String sqlQuery = " delete from creative";

		jdbcTemplate.update(sqlQuery);
	}

	public String getHasOfferLink(int offerId) {

		String sqlQuery = " select click_url from offer_affiliate where offer_id = ? limit 1";

		return jdbcTemplate.queryForObject(sqlQuery, String.class, new Object[] { offerId });

	}

	public List<AdvertiserCreative> getAdvertiserCreative(int offerId) {
		String sqlQuery = " select * from advertiser_creative where offer_id = ?";

		return jdbcTemplate.query(sqlQuery, new Object[] { offerId }, new AdvertiserCreativeMapper());
	}

	public List<HasOfferData> getHasOfferData(OfferRequest offerRequest) {

		StringBuffer sb = new StringBuffer();

		sb.append(
				" SELECT hod.title,hod.short_description, hod.android_package,hod.id, hod.offer_id,hod.Advertiser,hod.app_url,hod.country,hod.currency,hod.Description,hod.Name,hod.developer, ");
		sb.append(
				" hod.icon,hod.store_rating, ofa.click_url, hod.version, ofa.affiliate_id,  cr.id as creative_id, cr.description as creative_description, cr.file_name , cr.height , ");
		sb.append(
				" cr.is_active,cr.mime_type,hod.revenue_rate,hod.country as cr_country, cr.width, cr.height, cr.file_name, cr.mime_type,cr.offer_id, cr.width_height, op.priority, hod.category ");
		sb.append(
				" FROM hasoffer_data hod  inner join offer_affiliate ofa on  hod.id = ofa.offer_id   inner join affiliates aff on aff.affiliates_id = ofa.affiliate_id  inner join offer_priority op on op.offer_id = hod.id  ");
		// sb.append(" left outer join advertiser_raw_data ard on ard.offer_id =
		// hod.offer_id ");
		sb.append(" left outer join creative cr on hod.id = cr.offer_id ");
		sb.append("  and  ( cr.partner_id is null || lower(cr.partner_id) =  lower(aff.affiliates_name))");
		sb.append("  and  ( cr.country is null || lower(cr.country) =  lower(hod.country))");

		boolean whereAdded = false;
		if (offerRequest != null) {

			sb.append(" where op.priority >= " + offerRequest.getStart());

			if (offerRequest.getCategory() != null && offerRequest.getCategory().trim().length() > 0) {
				sb.append(" and hod.category like '%" + offerRequest.getCategory() + "%'");
				whereAdded = true;
			}
			if (offerRequest.getCountry() != null && offerRequest.getCountry().trim().length() > 0) {
				/*
				 * if(whereAdded){ sb.append("  and "); }else{ sb.append(
				 * " where "); }
				 */ whereAdded = true;
				sb.append(" and hod.country = '" + offerRequest.getCountry() + "'");
			}
			if (offerRequest.getPartnerName() != null && offerRequest.getPartnerName().trim().length() > 0) {
				/*
				 * if(whereAdded){ sb.append("  and "); }else{ sb.append(
				 * " where "); }
				 */ whereAdded = true;
				sb.append(" and ofa.affiliate_id = " + offerRequest.getPartnerName());
			}

			sb.append(" order by op.priority");
			if (offerRequest.getLimit() > 0) {
				sb.append(" limit " + offerRequest.getLimit());
			}

		}

		System.err.println(sb.toString());

		return jdbcTemplate.query(sb.toString(), new HasOfferDataMapper());
	}

	public void addPriorityData(final List<OfferPrioirty> listOfOfferPriority) {
		// TODO Auto-generated method stub

		String sqlQuery = " INSERT INTO offer_priority (offer_id, priority) VALUES (?,?) ";

		jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				OfferPrioirty offerPrioirty = listOfOfferPriority.get(i);
				ps.setInt(1, offerPrioirty.getOfferId());
				ps.setInt(2, offerPrioirty.getPriority());

			}

			public int getBatchSize() {
				// TODO Auto-generated method stub
				return listOfOfferPriority.size();
			}
		});

	}

	public boolean doesCreativeExists(int offerId) {

		String sqlQuery = " select count(*) from advertiser_creative where offer_id = ?";

		Integer intValue = jdbcTemplate.queryForObject(sqlQuery, new Object[] { offerId }, Integer.class);

		if (intValue > 0)
			return true;
		else
			return false;
	}

	public void updateApplicationStatus(String status) {

		String sqlQuery = " update application_status set status =?";

		jdbcTemplate.update(sqlQuery, new Object[] { status });
	}

	public String getApplicationStatus() {
		String sqlQuery = " select status from application_status";
		return jdbcTemplate.queryForObject(sqlQuery, String.class);
	}

	public void addAdvertiserCreative(final List<AdvertiserCreative> listOfAdvertiserCreatives) {
		// TODO Auto-generated method stub

		String sqlQuery = " INSERT INTO advertiser_creative (offer_id, creative_key, creative_value, country_code) VALUES (?,?,?,?) ";

		jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				AdvertiserCreative advertiserCreative = listOfAdvertiserCreatives.get(i);
				ps.setInt(1, advertiserCreative.getOfferId());
				ps.setString(2, advertiserCreative.getKey());
				ps.setString(3, advertiserCreative.getValue());
				ps.setString(4, advertiserCreative.getCountryCode());

			}

			public int getBatchSize() {
				// TODO Auto-generated method stub
				return listOfAdvertiserCreatives.size();
			}
		});

	}

}
