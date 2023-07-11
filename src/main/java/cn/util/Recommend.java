package cn.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.entity.Users;

/*
 * 算法采用是协同过滤算法
 *  杰卡德相似系数(Jaccard similarity coefficient)
 * */
public class Recommend {
	public static double compare(Users users1, Users users2) {
		// 并集
		List<Integer> union = new ArrayList<>();
		// 交集
		List<Integer> intersection = new ArrayList<>();
		// 自己的订单
		List<Integer> listg1 = users1.getList();
		// 别人的订单
		List<Integer> listg2 = users2.getList();

		for (int i = 0; i < listg1.size(); i++) {
			union.add(listg1.get(i));
		}
		for (int i = 0; i < listg2.size(); i++) {
			if (union.contains(listg2.get(i))) {
				// 如果并集中存在，加入到交集中
				intersection.add(listg2.get(i));
			} else {
				// 并集中不存在，加到并集中
				union.add(listg2.get(i));
			}
		}
		// 相似度
		double likes = (double) intersection.size() / union.size();
		return likes;
	}

	public static Map<Integer, Double> recommend(Users users, List<Users> list) {
		List<Integer> recommends = new ArrayList<>();
		Map<Integer, Double> map = new HashMap<>();
		//如果没有相关联的
		if(list.size()==0){
			return map;
		}
		
		for (int i = 0; i < list.get(0).getList().size(); i++) {
			recommends.add(list.get(0).getList().get(i));
			map.put(list.get(0).getList().get(i), list.get(0).getSeem());
		}

		for (int j = 1; j < list.size(); j++) {
			for (int i = 0; i < list.get(j).getList().size(); i++) {
				// 如果recommends里面存在，即有交集
				if (recommends.contains(list.get(j).getList().get(i))) {
					// 如果有，修改值
					map.replace(list.get(j).getList().get(i),
							map.get(list.get(j).getList().get(i))
									+ list.get(j).getSeem());
				} else {
					// recommends中不存在
					map.put(list.get(j).getList().get(i), list.get(j).getSeem());
				}
			}
		}

		return map;
	}

	public static List<Users> searchUserM(Users users, List<Users> list) {
		List<Integer> like = new ArrayList<>();// 存放当前登录用户喜欢的类型
		List<Users> listU = new ArrayList<>();// 存放与当前登录用户相关的用户

		for (int i = 0; i < users.getList().size(); i++) {
			like.add(users.getList().get(i));
		}

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).getList().size(); j++) {
				if (like.contains(list.get(i).getList().get(j))) {
					listU.add(list.get(i));
					break;
				} else {
					continue;
				}
			}
		}

		return listU;
	}

}
