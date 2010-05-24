package org.milton.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Map;

import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.CollectionResource;
import com.bradmcevoy.http.CopyableResource;
import com.bradmcevoy.http.DeletableResource;
import com.bradmcevoy.http.GetableResource;
import com.bradmcevoy.http.MoveableResource;
import com.bradmcevoy.http.PropFindableResource;
import com.bradmcevoy.http.PropPatchableResource;
import com.bradmcevoy.http.Range;
import com.bradmcevoy.http.Request;
import com.bradmcevoy.http.exceptions.BadRequestException;
import com.bradmcevoy.http.exceptions.ConflictException;
import com.bradmcevoy.http.exceptions.NotAuthorizedException;
import com.bradmcevoy.http.webdav.PropPatchHandler.Fields;

public class CatacombFileResource extends CatacombResource implements CopyableResource, DeletableResource, GetableResource,
		MoveableResource, PropFindableResource, PropPatchableResource {

	public CatacombFileResource(CatacombResourceFactory catacombResourceFactory, ResultSet rs) {
		super(catacombResourceFactory, rs);
	}

	public void copyTo(CollectionResource toCollection, String name) {
		// TODO Auto-generated method stub
	}

	public String checkRedirect(Request request) {
		return null;
	}

	public void delete() {
		getDaslResourceDao().deleteFile(this);
	}

	public Long getContentLength() {
		return super.getContentLength();
	}

	public String getContentType(String accepts) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getMaxAgeSeconds(Auth auth) {
		return null;
	}

	public void sendContent(OutputStream out, Range range, Map<String, String> params, String contentType) throws IOException,
			NotAuthorizedException, BadRequestException {

	}

	public void moveTo(CollectionResource rDest, String name) throws ConflictException {
		// TODO Auto-generated method stub
	}

	public void setProperties(Fields fields) {
		// TODO Auto-generated method stub
	}
}
