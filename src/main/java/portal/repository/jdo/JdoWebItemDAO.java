package portal.repository.jdo;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.springframework.stereotype.Repository;

import portal.domain.impl.Tag;
import portal.domain.impl.WebItem;
import portal.domain.impl.WebItemType;
import portal.repository.WebItemDAO;

@Repository
public class JdoWebItemDAO extends JdoDAO implements WebItemDAO {
	@Override
	public Collection<WebItem> getWebItems(int entitiesReturnedLimit) {

		String filter = "deleted == false";
		String ordering = "createDate DESC";

		Collection<WebItem> webItems = new LinkedHashSet<WebItem>();

		int rangeStart = 0;
		int rangeEnd = entitiesReturnedLimit;

		if (entitiesReturnedLimit > 0) {
			webItems = getEntities(WebItem.class, filter, ordering, rangeStart,
					rangeEnd);
		}

		return webItems;
	}

	@Override
	public Collection<WebItem> getFeaturedWebItems(
			Collection<WebItemType> webItemTypes, int rangeStart, int rangeEnd) {
		Object[] paramValues = new Object[webItemTypes.size()];
		String filter;

		filter = createFilterForWebItemTypes(webItemTypes.size());

		if (filter.length() > 0) {
			filter += " && ";
		}

		filter += "featured == true && deleted == false";
		String ordering = "createDate DESC";
		String imports = "import portal.domain.impl.WebItemType;";
		String parameters = addParameters("WebItemType", webItemTypes.size());

		paramValues = addObjectsToParamsArray(new HashSet<Tag>(), webItemTypes,
				paramValues);

		return getEntities(WebItem.class, filter, parameters, ordering,
				imports, rangeStart, rangeEnd, paramValues);
	}

	@Override
	public Collection<WebItem> getWebItems(Collection<Tag> tags,
			Collection<WebItemType> webItemTypes, int rangeStart, int rangeEnd) {
		Object[] paramValues = new Object[tags.size() + webItemTypes.size()];

		String imports = "import portal.domain.impl.Tag; "
				+ "import portal.domain.impl.WebItemType;";

		String ordering = "createDate DESC";
		String filter = buildFilter(tags.size(), webItemTypes.size());
		String parameters = buildParameters(tags.size(), webItemTypes.size());

		paramValues = addObjectsToParamsArray(tags, webItemTypes, paramValues);

		return getEntities(WebItem.class, filter, parameters, ordering,
				imports, rangeStart, rangeEnd, paramValues);
	}

	private String buildFilter(int tagsSize, int webItemTypesSize) {
		String tagFilter = "";
		String webItemTypeFilter = "";
		String connectionString = "";
		String filter = "";

		tagFilter += createFilterForTags(tagsSize);
		webItemTypeFilter += createFilterForWebItemTypes(webItemTypesSize);

		if (tagFilter.length() > 0 && webItemTypeFilter.length() > 0) {
			connectionString = " && ";
		}

		filter += tagFilter + connectionString + webItemTypeFilter;

		if (filter.length() > 0) {
			filter += " && ";
		}

		filter += "deleted == false";

		return filter;
	}

	private String createFilterForTags(int tagsCount) {
		String tagFilter = "";

		if (tagsCount > 0) {
			for (Integer i = 0; i < tagsCount; i++) {
				if (tagFilter.length() > 0) {
					tagFilter += " && ";
				}
				tagFilter += "tags.contains(Tag" + i.toString() + ")";
			}
		}
		return tagFilter;
	}

	private String createFilterForWebItemTypes(int webItemTypesCount) {
		String webItemTypeFilter = "";

		if (webItemTypesCount > 0) {

			webItemTypeFilter += "(";

			for (Integer i = 0; i < webItemTypesCount; i++) {

				if (webItemTypeFilter.length() > 1) {
					webItemTypeFilter += " || ";
				}
				webItemTypeFilter += "webItemType == WebItemType"
						+ i.toString();
			}

			webItemTypeFilter += ")";
		}
		return webItemTypeFilter;
	}

	private String buildParameters(int tagsSize, int webItemTypesSize) {
		String tagParameters = "";
		String webItemParameters = "";
		String connectionString = "";
		String parameters = "";

		tagParameters += addParameters("Tag", tagsSize);
		webItemParameters += addParameters("WebItemType", webItemTypesSize);

		if (tagParameters.length() > 0 && webItemParameters.length() > 0) {
			connectionString = ", ";
		}

		parameters += tagParameters + connectionString + webItemParameters;

		return parameters;
	}

	private String addParameters(String parameterType, int parameterCount) {
		String parameters = "";

		if (parameterCount > 0) {

			for (Integer i = 0; i < parameterCount; i++) {
				if (parameters.length() > 0) {
					parameters += ", ";
				}
				parameters += parameterType + " " + parameterType
						+ i.toString();
			}
		}
		return parameters;
	}

	private Object[] addObjectsToParamsArray(Collection<Tag> tags,
			Collection<WebItemType> webItemTypes, Object[] paramObjectsArray) {
		int index = 0;

		for (Tag tag : tags) {
			paramObjectsArray[index] = tag;
			index++;
		}

		for (WebItemType wit : webItemTypes) {
			paramObjectsArray[index] = wit;
			index++;
		}

		return paramObjectsArray;
	}

	@Override
	public Collection<WebItem> getWebItemsByUserId(long UserId,
			long rangeStart, long rangeEnd, Date dateParamStart,
			Date dateParamEnd) {
		String filter = "createdBy == p1 && (createDate " +
				">= dateParamBegin && createDate < dateParamEnd)";
		String parameters = "java.lang.Long p1, java.util.Date dateParamBegin, " +
				"java.util.Date dateParamEnd";
		String ordering = "";

		Object[] values = new Object[3];

		values[0] = UserId;
		values[1] = dateParamStart;
		values[2] = dateParamEnd;

		Collection<WebItem> webItems = getEntities(WebItem.class, filter,
				parameters, ordering, "", rangeStart, rangeEnd, values);

		return webItems;
	}
}
