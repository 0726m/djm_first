package cn.service;

import java.util.List;
import java.util.Map;

import cn.entity.Orders;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nnn
 * @since 2022-10-12
 */
public interface OrdersService extends IService<Orders> {
	public List<Map<String,Object>> groupbytype(String time1,String time2);
}
