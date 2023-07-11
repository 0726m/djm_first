package cn.service.impl;

import java.util.List;
import java.util.Map;

import cn.entity.Orders;
import cn.mapper.OrdersMapper;
import cn.service.OrdersService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nnn
 * @since 2022-10-12
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

	@Override
	public List<Map<String, Object>> groupbytype(String time1, String time2) {
		// TODO Auto-generated method stub
		return this.baseMapper.groupbytype(time1, time2);
	}

}
