package portal.repository;

import java.util.Collection;

import portal.domain.impl.DataEntity;

public interface DAO {
	/**
	 * Retrieves an entity by its ID.
	 *
	 * @param <T>
	 *            the type of entity to be retrieved.
	 * @param entityClass
	 *            the class of entity to be retrieved.
	 * @param entityId
	 *            entityId.
	 * @return an entity with the given ID or throws an exception if no such entity.
	 */
	public <T extends DataEntity> T getEntityById(Class<T> entityClass,
			long entityId);

	/**
	 * Retrieves all entities of the given type.
	 *
	 * @param <T>
	 *            the type of entities to be retrieved.
	 * @param entityClass
	 *            the class of entities to be retrieved.
	 * @return an entities collection or an empty collection if no such entities.
	 */
	public <T extends DataEntity> Collection<T> getEntities(Class<T> entityClass);

	/**
	 * Inserts a new entity into the persistent store.
	 *
	 * @param <T>
	 *            the type of entity to be inserted.
	 * @param entity
	 *            the entity to insert.
	 * @return the inserted entity.
	 */
	public <T extends DataEntity> T insertEntity(T entity);

	/**
	 * Saves a modified entity into the persistent store.
	 *
	 * @param <T>
	 *            the type of entity to be updated.
	 * @param entity
	 *            the entity to update.
	 * @return the updated entity.
	 */
	public <T extends DataEntity> T updateEntity(T entity);

	/**
	 * Deletes an entity from the persistent store.
	 *
	 * @param <T>
	 *            the type of entity to be deleted.
	 * @param entityClass
	 *            the class of entity to be deleted.
	 * @param entity
	 *            the entity to delete.
	 */
	public <T extends DataEntity> void deleteEntity(Class<T> entityClass,
			T entity);

	/**
	 * Retrieves an entity by its ID.
	 *
	 * @param <T>
	 *            the type of entity to be retrieved.
	 * @param entityClass
	 *            the class of entity to be retrieved.
	 * @param entityId
	 *            entityId.
	 * @param fetchGroup
	 * 						the fetch group name.
	 * @return an entity with the given ID or throws an exception if no such entity.
	 */
	public <T extends DataEntity> T getEntityById(Class<T> entityClass,
			long entityId, String fetchGroup);
}
