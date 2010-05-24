package org.milton.provider;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.milton.provider.dao.DaslResourceDao;
import org.springframework.jdbc.core.RowMapper;

import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.Request;
import com.bradmcevoy.http.Resource;
import com.bradmcevoy.http.ResourceFactory;
import com.bradmcevoy.http.SecurityManager;
import com.bradmcevoy.http.http11.auth.PreAuthenticationFilter;

public class CatacombResourceFactory implements ResourceFactory, RowMapper {

	private final SecurityManager securityManager;

	private final DaslResourceDao daslResourceDao;

	public CatacombResourceFactory(SecurityManager securityManager, DaslResourceDao daslResourceDao) {
		this.securityManager = securityManager;
		this.daslResourceDao = daslResourceDao;
	}

	public Resource getResource(String host, String path) {
		Request request = PreAuthenticationFilter.getCurrentRequest();
		if (request == null) {
			return null;
		}

		Auth auth = request.getAuthorization();
		if (auth == null) {
			return null;
		}

		return daslResourceDao.getResource((Long) auth.getTag(), path, this);
	}

	public SecurityManager getSecurityManager() {
		return securityManager;
	}

	public DaslResourceDao getDaslResourceDao() {
		return daslResourceDao;
	}

	public Resource mapRow(ResultSet sqlResultSet, int rowNum) throws SQLException {
		int resourceType = sqlResultSet.getInt("resourcetype");

		if (resourceType == 1) {
			return new CatacombCollectionResource(this, sqlResultSet);
		} else {
			return new CatacombFileResource(this, sqlResultSet);
		}
	}
}
