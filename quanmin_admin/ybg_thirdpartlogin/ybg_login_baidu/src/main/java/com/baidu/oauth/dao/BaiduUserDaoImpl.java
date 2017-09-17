package com.baidu.oauth.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.baidu.oauth.domain.BaiduUser;
import com.baidu.oauth.qvo.BaiduUserQvo;
import com.ybg.base.jdbc.BaseDao;
import com.ybg.base.jdbc.BaseMap;

@Repository
public class BaiduUserDaoImpl extends BaseDao implements BaiduUserDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	private static String	QUERY_TABLE_NAME	= "baidu_user user";
	private static String	QUERY_TABLE_COLUMN	= " user.id,user.userid,user.uid ";
	
	@Override
	public void create(BaiduUser bean) throws Exception {
		BaseMap<String, Object> createmap = new BaseMap<String, Object>();
		createmap.put("uid", bean.getUid() + "");
		createmap.put("userid", bean.getUserid());
		baseCreate(createmap, "baidu_user", "id");
	}
	
	@Override
	public void update(BaseMap<String, Object> updatemap, BaseMap<String, Object> wheremap) {
		baseupdate(updatemap, wheremap, "baidu_user");
	}
	
	@Override
	public void remove(BaseMap<String, Object> conditionmap) {
		baseremove(conditionmap, "baidu_user");
	}
	
	@Override
	public List<BaiduUser> query(BaiduUserQvo qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT).append(QUERY_TABLE_COLUMN).append(FROM).append(QUERY_TABLE_NAME);
		sql.append(getcondition(qvo));
		return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper(BaiduUser.class));
	}
	
	private String getcondition(BaiduUserQvo qvo) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(WHERE).append("1=1");
		sqlappen(sql, "user.uid", qvo.getUid());
		sqlappen(sql, "user.userid", qvo.getUserid());
		sqlappen(sql, "user.id", qvo.getId());
		return sql.toString();
	}
	
	@Override
	public Map<String, String> getSetting() {
		StringBuilder sql = new StringBuilder();
		Map<String, String> map = new LinkedHashMap<String, String>();
		sql.append(SELECT).append("`key`,`value`").append(FROM).append("baidu_login_setting_1 t");
		getJdbcTemplate().query(sql.toString(), new RowMapper<Object>() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				map.put(rs.getString("key"), rs.getString("value"));
				return null;
			}
		});
		return map;
	}
	
	@Override
	public void updateSetting(String appid, String value, String url) {
		String tablename = "baidu_login_setting_1";
		StringBuilder sql = new StringBuilder();
		sql.append(UPDATE).append(tablename).append(SET).append(" `value` ='").append(appid).append("'");
		sql.append(WHERE).append("`key`='client_ID'");
		getJdbcTemplate().update(sql.toString());
		sql = new StringBuilder();
		sql.append(UPDATE).append(tablename).append(SET).append(" `value` ='").append(value).append("'");
		sql.append(WHERE).append("`key`='client_SERCRET'");
		getJdbcTemplate().update(sql.toString());
		sql = new StringBuilder();
		sql.append(UPDATE).append(tablename).append(SET).append(" `value` ='").append(url).append("'");
		sql.append(WHERE).append("`key`='redirect_URI'");
		getJdbcTemplate().update(sql.toString());
	}

	@Override
	public long queryBaiduId(String userid) {
		StringBuilder sql= new StringBuilder();
		sql.append(SELECT ).append("uid").append(FROM).append(QUERY_TABLE_NAME);
		sql.append(WHERE ).append("userid='").append(userid).append("'");
		//getJdbcTemplate().queryForList(sql.toString());
		return 0;
	}
}
