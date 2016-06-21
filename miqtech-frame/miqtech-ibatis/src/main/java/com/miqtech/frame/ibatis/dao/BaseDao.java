package com.miqtech.frame.ibatis.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.miqtech.frame.ibatis.common.MapAndObject;
import com.miqtech.frame.ibatis.common.Page;

public abstract class BaseDao {

	public static final Logger logger = Logger.getLogger(BaseDao.class);
	public static final String DELETE_BY_PK = "DELETE_BY_PK";
	public static final String UPDATE_BY_PK = "UPDATE_BY_PK";
	public static final String INSERT = "INSERT";
	public static final String SELECT_BY_PK = "SELECT_BY_PK";
	public static final String SELECT_BY_ALL = "SELECT_BY_ALL";
	public static final String SELECT_PAGE = "SELECT_PAGE";

	@Resource(name = "sqlMapClient")
	public SqlMapClient client;

	// 如果是多数据源时，可以更换数据源使用
	public void setClient(SqlMapClient client) {
		this.client = client;
	}

	public String getNamespace(Object o) {
		return o.getClass().getSimpleName().toUpperCase() + ".";
	}

	public int delete(String id) {
		try {
			return client.delete(id);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return 0;
	}

	public int delete(String id, Object o) {
		try {
			return client.delete(id, o);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return 0;
	}

	public int delete(Object o) {
		try {
			return client.delete(getNamespace(o) + DELETE_BY_PK, o);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return 0;
	}

	public int update(String id) {
		try {
			return client.update(id);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return 0;
	}

	public int update(String id, Object o) {
		try {
			return client.update(id, o);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return 0;
	}

	public int update(Object o) {
		try {
			return client.update(getNamespace(o) + UPDATE_BY_PK, o);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return 0;
	}

	public Object insert(String id) {
		try {
			return client.insert(id);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public Object insert(String id, Object o) {
		try {
			Object obj = client.insert(id, o);
			return obj;
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public Object insert(Object o) {
		try {
			Object obj = client.insert(getNamespace(o) + INSERT, o);
			return obj;
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List list(String id) {
		try {
			return client.queryForList(id);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return new ArrayList();
	}

	@SuppressWarnings("rawtypes")
	public List list(String id, Object o) {
		try {
			return client.queryForList(id, o);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return new ArrayList();
	}

	@SuppressWarnings("rawtypes")
	public List list(Object o) {
		try {
			return client.queryForList(getNamespace(o) + SELECT_BY_ALL, o);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return new ArrayList();
	}

	public Map<?, ?> map(String id, Object parameterObject, String keyProp) {
		try {
			return client.queryForMap(id, parameterObject, keyProp);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public Map<?, ?> map(String id, Object parameterObject, String keyProp, String valueProp) {
		try {
			return client.queryForMap(id, parameterObject, keyProp, valueProp);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public Object object(String id) {
		try {
			return client.queryForObject(id);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public Object object(String id, Object o) {
		try {
			return client.queryForObject(id, o);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public Object object(Object o) {
		try {
			return client.queryForObject(getNamespace(o) + SELECT_BY_PK, o);
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public void startBatch() {
		try {
			client.startBatch();
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return;
	}

	public int executeBatch() {
		try {
			return client.executeBatch();
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return 0;
	}

	public void startTransaction() {
		try {
			client.startTransaction();
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return;
	}

	public void commitTransaction() {
		try {
			client.commitTransaction();
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public void endTransaction() {
		try {
			client.endTransaction();
		} catch (SQLException e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param sqlID
	 *            Ibatis中的SQLID 如果是select count(1)的话，必须以——count结尾
	 * @param map
	 *            存放相关的查询条件
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page page(String id, Map<String, Object> map, Class clazz) {
		try {
			String namespace = clazz.getSimpleName().toUpperCase() + "." + id;
			Page page = (Page) map.get("page");
			if (page != null) {
				Integer totalCount = (Integer) client.queryForObject(namespace + "_COUNT", map);
				page.setTotal(totalCount);
			}

			page.setResult(client.queryForList(id, map));
			return page;
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public Page page(String id, Page page, Object obj) {
		try {
			String namespace = getNamespace(obj) + id;
			Map<String, Object> map = new HashMap<String, Object>();
			if (page != null) {
				Integer totalCount = (Integer) client.queryForObject(namespace + "_COUNT", obj);
				page.setTotal(totalCount);
				map = MapAndObject.objectToMap(obj);
				map.put("start", page.getStart());
				map.put("pageSize", page.getPageSize());
				page.setResult(client.queryForList(namespace, map));
			}
			return page;
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	public Page page(Page page, Object obj) {
		try {
			String namespace = getNamespace(obj) + SELECT_PAGE;
			Map<String, Object> map = new HashMap<String, Object>();
			if (page != null) {
				Integer totalCount = (Integer) client.queryForObject(namespace + "_COUNT", obj);
				page.setTotal(totalCount);
				map = MapAndObject.objectToMap(obj);
				map.put("start", page.getStart());
				map.put("pageSize", page.getPageSize());
				page.setResult(client.queryForList(namespace, map));
			}
			return page;
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

}
