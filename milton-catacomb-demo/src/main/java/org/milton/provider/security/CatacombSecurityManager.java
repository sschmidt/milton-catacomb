package org.milton.provider.security;

import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.Request;
import com.bradmcevoy.http.Resource;
import com.bradmcevoy.http.SecurityManager;
import com.bradmcevoy.http.Request.Method;
import com.bradmcevoy.http.http11.auth.DigestResponse;

public class CatacombSecurityManager implements SecurityManager {

	private String user = "11005";
	
	public Object authenticate(DigestResponse arg0) {
		return user;
	}

	public Object authenticate(String arg0, String arg1) {
		return user;
	}

	public boolean authorise(Request arg0, Method arg1, Auth arg2, Resource arg3) {
		return true;
	}

	public String getRealm(String arg0) {
		return "SmartDrive";
	}
}
