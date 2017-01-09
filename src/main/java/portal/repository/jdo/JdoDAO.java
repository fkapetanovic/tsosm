package portal.repository.jdo;

import portal.config.MessageKeys;
import portal.domain.AuditableOnUpdate;
import java.util.Collection;
import java.util.Date;

import javax.jdo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;

import portal.domain.*;
import portal.domain.impl.DataEntity;
import portal.repository.DAO;
import portal.service.exceptions.DataEntityNotFoundException;

public abstract class JdoDAO implements DAO {
	@Autowired
	MessageSource messageSource;

	@Autowired
	@Qualifier("pmfProxy")
	private PersistenceManagerFactory pmf;

	protected PersistenceManager getPersistenceManager() {
		return pmf.getPersistenceManager();
	}

	@Override
	public <T extends DataEntity> T getEntityById(Class<T> entityClass, long entityId) {
		T entity;
		PersistenceManager pm = getPersistenceManager();

		try {
			entity = pm.getObjectById(entityClass, entityId);
		} catch (JDOObjectNotFoundException e) {

			String msg = messageSource.getMessage(
				MessageKeys.ENTITY_WITH_GIVEN_ID_EXISTS_NOT,
				new Object[] {Long.toString(entityId), entityClass.toString() },
				null
			);

			throw new DataEntityNotFoundException(msg);
		}

		return entity;
	}

	@Override
	public <T extends DataEntity> T getEntityById(Class<T> entityClass,
			long entityId, String fetchGroup) {
		T entity;
		PersistenceManager pm = getPersistenceManager();
		pm.getFetchPlan().addGroup(fetchGroup);

		try {
			entity = pm.getObjectById(entityClass, entityId);
		} catch (JDOObjectNotFoundException e) {
			throw new DataEntityNotFoundException(
					"Non-existing entity lookup. Id:" + entityId + ", "
							+ entityClass.toString());
		}

		return entity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends DataEntity> Collection<T> getEntities(Class<T> entityClass) {
		PersistenceManager pm = getPersistenceManager();
		return (Collection<T>) pm.newQuery(entityClass).execute();
	}

	public <T extends DataEntity> Collection<T> getEntities(
			Class<T> entityClass, String filter, String ordering) {
		return getEntities(entityClass, filter, "", ordering, "", -1, -1, null);
	}

	public <T extends DataEntity> Collection<T> getEntities(
			Class<T> entityClass, String filter, String ordering,
			int rangeStart, int rangeEnd) {

		return getEntities(entityClass, filter, "", ordering, "", rangeStart,
				rangeEnd, null);
	}

	public <T extends DataEntity> Collection<T> getEntities(
			Class<T> entityClass, String filter, String parameters,
			String ordering, Object[] values) {
		return getEntities(entityClass, filter, parameters, ordering, "", -1,
				-1, values);
	}

	public <T extends DataEntity> Collection<T> getEntities(
			Class<T> entityClass, String filter, String parameters,
			String ordering, String imports, Object[] values) {
		return getEntities(entityClass, filter, parameters, ordering, imports,
				-1, -1, values);
	}

	@SuppressWarnings("unchecked")
	public <T extends DataEntity> Collection<T> getEntities(
			Class<T> entityClass, String filter, String parameters,
			String ordering, String imports, long rangeStart, long rangeEnd,
			Object[] values) {
		PersistenceManager pm = getPersistenceManager();

		Query query = pm.newQuery(entityClass);

		if (filter != null && filter.length() > 0) {
			query.setFilter(filter);
		}
		if (parameters != null & parameters.length() > 0) {
			query.declareParameters(parameters);
		}
		if (ordering != null && ordering.length() > 0) {
			query.setOrdering(ordering);
		}
		if (imports != null && imports.length() > 0) {
			query.declareImports(imports);
		}
		if (rangeStart >= 0 && rangeEnd > 0) {
			query.setRange(rangeStart, rangeEnd);
		}

		if (values != null && values.length > 0) {
			return (Collection<T>) query.executeWithArray(values);
		} else {
			return (Collection<T>) query.execute();
		}
	}

	@Override
	public <T extends DataEntity> void deleteEntity(Class<T> entityClass, T entity) {
		if (entity instanceof SoftDeletable) {
			softDeleteEntity(entity);
		} else {
			getPersistenceManager().deletePersistent(entity);
		}
	}

	@Override
	public <T extends DataEntity> T insertEntity(T entity) {
		return makePersistent(entity);
	}

	@Override
	public <T extends DataEntity> T updateEntity(T entity) {
		return makePersistent(entity);
	}

	@SuppressWarnings("unchecked")
	private <T extends DataEntity> T makePersistent(T entity) {
		PersistenceManager pm = getPersistenceManager();

		if (entity.isNew()) {
			if (entity instanceof Auditable) {
				Auditable auditableEntity = (Auditable) entity;
				auditableEntity.setCreateDate(new Date());
				entity = (T) auditableEntity;
			}
		} else {
			if (entity instanceof AuditableOnUpdate) {
				AuditableOnUpdate auditableOnUpdateEntity = (AuditableOnUpdate) entity;
				auditableOnUpdateEntity.setUpdateDate(new Date());
				entity = (T) auditableOnUpdateEntity;
			}
		}

		entity = pm.makePersistent(entity);

		return entity;
	}

	@SuppressWarnings("unchecked")
	private <T extends DataEntity> void softDeleteEntity(T entity) {

		SoftDeletable deletableEntity = (SoftDeletable) entity;
		deletableEntity.setDeleted(true);

		makePersistent((T) deletableEntity);
	}
}
