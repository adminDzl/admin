package com.wolves.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

public class SortUtil {

	/**
	 * 对list进行排序
	 * @param sortList 需要排序的list
	 * @param param1   排序的参数名称
	 * @param orderType 排序类型：正序-asc；倒序-desc  
	 */
	public static List sort(List sortList, String param1, String orderType){
		Comparator mycmp1 = ComparableComparator.getInstance ();
		if("desc".equals(orderType)){
			mycmp1 = ComparatorUtils. reversedComparator(mycmp1);
		}
		ArrayList<Object> sortFields = new ArrayList<Object>();
		sortFields.add( new BeanComparator(param1 , mycmp1));
		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort (sortList , multiSort);
		
		return sortList;
	}
	
}