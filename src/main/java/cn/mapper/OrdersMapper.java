package cn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.entity.Orders;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nnn
 * @since 2022-10-12
 */
public interface OrdersMapper extends BaseMapper<Orders> {
	@Select("select c.name,sum(o.price) as price from orders o,cinema c where o.cid=c.id and SUBSTRING(o.optime,1,10)>#{time1} and SUBSTRING(o.optime,1,10)<#{time2} group by c.name")
	public List<Map<String,Object>> groupbytype(@Param("time1") String time1,@Param("time2") String time2);

}
