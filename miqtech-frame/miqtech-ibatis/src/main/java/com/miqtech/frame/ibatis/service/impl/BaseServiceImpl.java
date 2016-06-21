package com.miqtech.frame.ibatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.miqtech.frame.ibatis.dao.GeneralDao;
import com.miqtech.frame.ibatis.model.BaseEntity;
import com.miqtech.frame.ibatis.service.BaseService;

public class BaseServiceImpl implements BaseService {
	@Autowired
	private GeneralDao generalDao;

	@Override
	public <T extends BaseEntity> T getEntityById(Class<T> clazz, Long pk) {
		return generalDao.getEntityById(clazz, pk);
	}

	@Override
	public Long save(BaseEntity entity) {
		return (Long) generalDao.save(entity);
	}

	@Override
	public void delete(BaseEntity entity) {
		generalDao.delete(entity);
	}

	@Override
	public void update(BaseEntity entity) {
		generalDao.update(entity);
	}
}
