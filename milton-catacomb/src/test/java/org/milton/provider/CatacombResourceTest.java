package org.milton.provider;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.milton.provider.dao.DaslResourceDao;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;

import com.bradmcevoy.http.Auth;
import com.bradmcevoy.http.Request;
import com.bradmcevoy.http.Resource;
import com.bradmcevoy.http.SecurityManager;
import com.bradmcevoy.http.Request.Method;
import com.bradmcevoy.http.http11.auth.DigestResponse;
import com.bradmcevoy.http.http11.auth.PreAuthenticationFilter;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class CatacombResourceTest {
	@TestDataSource
	private DataSource dataSource;

	private SecurityManager securityManager = new SecurityManager() {

		@Override
		public Object authenticate(DigestResponse digestRequest) {
			return 11005;
		}

		@Override
		public Object authenticate(String user, String password) {
			return 11005;
		}

		@Override
		public boolean authorise(Request request, Method method, Auth auth, Resource resource) {
			return true;
		}

		@Override
		public String getRealm(String host) {
			return null;
		}
	};

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		PreAuthenticationFilter filter = new PreAuthenticationFilter(null, securityManager);
		Request requestMock = createMock(Request.class);
		Auth auth = new Auth("11005", "foo");
		auth.setTag(11005L);
		expect(requestMock.getAuthorization()).andStubReturn(auth);
		replay(requestMock);

		Field tlRequestField = PreAuthenticationFilter.class.getDeclaredField("tlRequest");
		tlRequestField.setAccessible(true);
		ThreadLocal<Request> tlRequest = (ThreadLocal<Request>) tlRequestField.get(filter);
		tlRequest.set(requestMock);
	}

	@Test
	@DataSet("CatacombResourceTest.propfind.xml")
	@SuppressWarnings("unchecked")
	public void basicPropfindTest() {
		CatacombResourceFactory factory = new CatacombResourceFactory(securityManager, new DaslResourceDao(dataSource));
		Resource testResource = factory.getResource("localhost", "/");
		assertNotNull(testResource);
		assertEquals("/", testResource.getName());
		assertEquals(1267438336000000L, testResource.getModifiedDate().getTime());
		assertTrue(testResource instanceof CatacombCollectionResource);

		CatacombCollectionResource testCollection = (CatacombCollectionResource) testResource;

		List<Resource> children = (List<Resource>) testCollection.getChildren();
		assertEquals(1, children.size());
		assertEquals("/foo", children.get(0).getName());
	}
}
