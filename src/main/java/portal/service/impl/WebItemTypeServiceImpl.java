package portal.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.domain.impl.WebItemType;
import portal.repository.WebItemTypeDAO;
import portal.service.WebItemTypeService;
import portal.util.Helper;

@Service
@Transactional(rollbackFor = Exception.class)
public class WebItemTypeServiceImpl implements WebItemTypeService {
	@Autowired
	private WebItemTypeDAO webItemTypeDao;

  @Override
  public WebItemType getWebItemTypeById(long webItemTypeId) {
      return webItemTypeDao.getEntityById(WebItemType.class, webItemTypeId);
  }

  @Override
  public Collection<WebItemType> getWebItemTypes(String webItemTypeIds, String delimiter) {
    Collection<WebItemType> webItemTypes = new ArrayList<WebItemType>();

    Collection<Long> webItemTypeIdSet = Helper.
    		convertDelimitedStringToLongHashSet(webItemTypeIds, delimiter);

    for (Iterator<Long> it = webItemTypeIdSet.iterator(); it.hasNext();) {
        Long id = it.next();
        WebItemType webItemType = this.getWebItemTypeById(id);

        if (webItemType != null){
            webItemTypes.add(webItemType);
        }
    }

    return webItemTypes;
  }

  @Override
  public Collection<WebItemType> getAllWebItemTypes() {
  	return webItemTypeDao.getAllWebItemTypes();
  }

	@Override
	public WebItemType getWebItemTypeByName(String webItemTypeName) {
		return webItemTypeDao.getWebItemTypeByName(webItemTypeName);
	}
}
