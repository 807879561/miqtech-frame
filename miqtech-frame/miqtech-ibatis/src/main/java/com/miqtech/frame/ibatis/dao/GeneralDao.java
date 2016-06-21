package com.miqtech.frame.ibatis.dao;

import org.springframework.stereotype.Repository;

import com.miqtech.frame.ibatis.model.BaseEntity;

@Repository
public class GeneralDao extends BaseDao {
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> T getEntityById(Class<T> clazz, Long pk) {
		try {
			T c = clazz.newInstance();
			c.setId(pk);
			Object o = this.object(c);
			return (T) o;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object save(BaseEntity entity) {
		return this.insert(entity);
	}

	public void update(BaseEntity entity) {
		super.update(entity);
	}

	public void delete(BaseEntity entity) {
		super.delete(entity);
	}
}
