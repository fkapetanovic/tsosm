package portal.test.unit.service;

import static org.mockito.Mockito.verify;

import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import portal.config.AppPropKeys;
import portal.domain.impl.WebItemType;
import portal.repository.WebItemTypeDAO;
import portal.service.impl.WebItemTypeServiceImpl;
import portal.util.Helper;

public class WebItemTypeServiceTest {
	@Autowired
	@InjectMocks
	private WebItemTypeServiceImpl webItemTypeService;

	@Mock
	private WebItemTypeDAO webItemTypeDao;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private final Properties APP_PROPERTIES = Helper.getAppProperties();
	private final String OR_DLM = APP_PROPERTIES.getProperty(AppPropKeys.LOGICAL_OR_DELIMITER);

	@Test
	public void getWebItemTypeByIdTest() {
		webItemTypeService.getWebItemTypeById(1L);
		verify(webItemTypeDao).getEntityById(WebItemType.class, 1L);
	}

	@Test
	public void getAllWebItemTypeTest() {
		webItemTypeService.getAllWebItemTypes();
		verify(webItemTypeDao).getAllWebItemTypes();
	}

	@Test
	public void getWebItemTypeByNameTest() {
		webItemTypeService.getWebItemTypeByName("Web item type name");
		verify(webItemTypeDao).getWebItemTypeByName("Web item type name");
	}

	@Test
	public void getWebItemTypesTest() {
		String webItemTypeIds = "1" + OR_DLM + "2" + OR_DLM;
		webItemTypeService.getWebItemTypes(webItemTypeIds, OR_DLM);
		verify(webItemTypeDao).getEntityById(WebItemType.class, 1L);
		verify(webItemTypeDao).getEntityById(WebItemType.class, 2L);
	}
}
