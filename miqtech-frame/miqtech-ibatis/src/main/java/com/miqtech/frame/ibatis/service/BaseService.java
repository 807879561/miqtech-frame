package com.miqtech.frame.ibatis.service;

import com.miqtech.frame.ibatis.model.BaseEntity;

public interface BaseService {

	<T extends BaseEntity> T getEntityById(Class<T> clazz, Long pk);

	Long save(BaseEntity entity);

	void delete(BaseEntity entity);

	void update(BaseEntity entity);

}
