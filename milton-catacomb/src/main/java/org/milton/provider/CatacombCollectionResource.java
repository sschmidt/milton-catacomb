package org.milton.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.CollectionResource;
import com.bradmcevoy.http.CopyableResource;
import com.bradmcevoy.http.DeletableResource;
import com.bradmcevoy.http.GetableResource;
import com.bradmcevoy.http.MakeCollectionableResource;
import com.bradmcevoy.http.MoveableResource;
import com.bradmcevoy.http.PropFindableResource;
import com.bradmcevoy.http.PutableResource;
import com.bradmcevoy.http.Range;
import com.bradmcevoy.http.Resource;
import com.bradmcevoy.http.XmlWriter;
import com.bradmcevoy.http.exceptions.BadRequestException;
import com.bradmcevoy.http.exceptions.ConflictException;
import com.bradmcevoy.http.exceptions.NotAuthorizedException;

public class CatacombCollectionResource extends CatacombResource implements MakeCollectionableResource, PutableResource, CopyableResource,
        DeletableResource, MoveableResource, GetableResource, PropFindableResource {

    public CatacombCollectionResource(CatacombResourceFactory catacombResourceFactory, ResultSet rs) {
        super(catacombResourceFactory, rs);
    }

    public CollectionResource createCollection(String newName) throws NotAuthorizedException, ConflictException {
        return null;
    }

    public Resource child(String childName) {
        return null;
    }

    public List<? extends Resource> getChildren() {
        return getDaslResourceDao().propfind(this);
    }

    public Resource createNew(String newName, InputStream inputStream, Long length, String contentType) throws IOException, ConflictException {
        return null;
    }

    public void delete() {
        getDaslResourceDao().deleteCollection(this);
    }

    public Long getContentLength() {
        return null;
    }

    public String getContentType(String accepts) {
        return "text/html";
    }

    public void sendContent(OutputStream out, Range range, Map<String, String> params, String contentType) throws IOException,
            NotAuthorizedException, BadRequestException {
        XmlWriter w = new XmlWriter(out);

        w.writeXMLHeader();
        w.open("html");
        w.open("body");
        w.begin("h1").open().writeText(this.getName()).close();
        w.open("table");
        for (Resource resource : getChildren()) {
            w.open("tr");

            w.open("td");
            w.begin("a").writeAtt("href", "/" + resource.getName()).open().writeText(resource.getName()).close();
            w.close("td");

            w.begin("td").open().writeText(resource.getModifiedDate() + "").close();
            w.close("tr");
        }

        w.close("table");
        w.close("body");
        w.close("html");
        w.flush();
    }

    public Long getMaxAgeSeconds(Auth arg0) {
        return null;
    }

    public void copyTo(CollectionResource arg0, String arg1) {
        // TODO Auto-generated method stub
    }

    public void moveTo(CollectionResource arg0, String arg1) throws ConflictException {
        // TODO Auto-generated method stub
    }
}
