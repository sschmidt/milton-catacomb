package org.milton.provider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.milton.provider.dao.DaslResourceDao;

import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.Request;
import com.bradmcevoy.http.Resource;
import com.bradmcevoy.http.SecurityManager;
import com.bradmcevoy.http.Request.Method;
import com.bradmcevoy.http.http11.auth.PreAuthenticationFilter;

public class CatacombResource implements Resource {
	private Date modifiedDate;
	private String name;
	private int depth;
	private final SecurityManager securityManager;
	private final DaslResourceDao daslResourceDao;
	private final CatacombResourceFactory catacombResourceFactory;
	private Date createDate;
	private long contentLength;
	private String documentId;

	public CatacombResource(CatacombResourceFactory catacombResourceFactory, ResultSet rs) {
		this.securityManager = catacombResourceFactory.getSecurityManager();
		this.daslResourceDao = catacombResourceFactory.getDaslResourceDao();
		this.catacombResourceFactory = catacombResourceFactory;

		try {
			this.name = rs.getString("uri");
			this.depth = rs.getInt("depth");
			this.modifiedDate = new Date(rs.getLong("getlastmodified"));
			this.createDate = new Date(rs.getLong("creationdate"));
			this.contentLength = rs.getLong("getcontentlength");
			this.documentId = rs.getString("document_id");
		} catch (SQLException e) {
			throw new RuntimeException(e); // FIXME
		}
	}

	public Object authenticate(String user, String password) {
		return securityManager.authenticate(user, password);
	}

	public boolean authorise(Request request, Method method, Auth auth) {
		return true; // no method-specific behaviour yet
	}

	public String checkRedirect(Request request) {
		return null;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getName() {
		return name.substring(name.lastIndexOf("/"));
	}

	public String getUniqueId() {
		return documentId;
	}

	public DaslResourceDao getDaslResourceDao() {
		return daslResourceDao;
	}

	public long getUserId() {
		return (Long) PreAuthenticationFilter.getCurrentRequest().getAuthorization().getTag();
	}

	public int getDepth() {
		return depth;
	}

	public CatacombResourceFactory getCatacombResourceFactory() {
		return catacombResourceFactory;
	}

	public String getRealm() {
		return null;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Long getContentLength() {
		return contentLength;
	}
}
