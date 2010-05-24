package org.milton.provider.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.milton.provider.CatacombCollectionResource;
import org.milton.provider.CatacombFileResource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.bradmcevoy.http.Resource;

public class DaslResourceDao extends NamedParameterJdbcDaoSupport {

    private static final String SELECT_COLLECTION = "SELECT uri, getcontentlength, document_id, getlastmodified, resourcetype, creationdate, depth, win32fileattributes FROM dasl_resource WHERE user_id=:user_id AND uri=:uri";

    private static final String SELECT_COLLECTION_CHILDS = "SELECT uri, getcontentlength, document_id, getlastmodified, resourcetype, creationdate, depth, win32fileattributes FROM dasl_resource WHERE user_id=:user_id AND depth=:depth AND uri LIKE :uri_like AND uri != :uri";

    private static final String DELETE_COLLECTION = "DELETE FROM dasl_resource WHERE user_id=:user_id AND uri LIKE :uri";

    private static final String DELETE_RESOURCE = "DELETE FROM dasl_resource WHERE user_id=:user_id AND uri = :uri";

    public DaslResourceDao(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public Resource getResource(long user_id, String uri, RowMapper resultMapper) {
        Map<String, Object> queryParameter = new HashMap<String, Object>();
        queryParameter.put("user_id", user_id);
        queryParameter.put("uri", uri);

        try {
            return (Resource) getNamedParameterJdbcTemplate().queryForObject(SELECT_COLLECTION, queryParameter, resultMapper);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<? extends Resource> propfind(CatacombCollectionResource catacombCollectionResource) {
        Map<String, Object> queryParameter = new HashMap<String, Object>();
        queryParameter.put("user_id", catacombCollectionResource.getUserId());
        queryParameter.put("depth", catacombCollectionResource.getDepth());
        queryParameter.put("uri_like", catacombCollectionResource.getName() + "%");
        queryParameter.put("uri", catacombCollectionResource.getName());

        return (List<Resource>) getNamedParameterJdbcTemplate().query(SELECT_COLLECTION_CHILDS, queryParameter,
                catacombCollectionResource.getCatacombResourceFactory());
    }

    public void deleteCollection(CatacombCollectionResource catacombCollectionResource) {
        Map<String, Object> queryParameter = new HashMap<String, Object>();
        queryParameter.put("user_id", catacombCollectionResource.getUserId());
        queryParameter.put("uri", catacombCollectionResource.getName() + "%"); // LIKE-Query

        getNamedParameterJdbcTemplate().query(DELETE_COLLECTION, queryParameter, catacombCollectionResource.getCatacombResourceFactory());
    }

    public void deleteFile(CatacombFileResource catacombFileResource) {
        Map<String, Object> queryParameter = new HashMap<String, Object>();
        queryParameter.put("user_id", catacombFileResource.getUserId());
        queryParameter.put("uri", catacombFileResource.getName());

        getNamedParameterJdbcTemplate().query(DELETE_RESOURCE, queryParameter, catacombFileResource.getCatacombResourceFactory());
    }
}
