package com.wolves.dao;

public interface DAO {
	
	/**
	 * 保存对象
	 */
	public Object save(String str, Object obj) throws Exception;
	
	/**
	 * 修改对象
	 */
	public Object update(String str, Object obj) throws Exception;
	
	/**
	 * 删除对象 
	 */
	public Object delete(String str, Object obj) throws Exception;

	/**
	 * 查找对象
	 */
	public Object findForObject(String str, Object obj) throws Exception;

	/**
	 * 查找对象
	 */
	public Object findForList(String str, Object obj) throws Exception;
	
	/**
	 * 查找对象封装成Map
	 */
	public Object findForMap(String sql, Object obj, String key , String value) throws Exception;
	
}
