package cn.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.entity.Goods;
import cn.entity.Orders;
import cn.entity.Paipian;
import cn.entity.Seat;
import cn.entity.Users;
import cn.service.CinemaService;
import cn.service.GoodsService;
import cn.service.OrdersService;
import cn.service.PaipianService;
import cn.service.SeatService;
import cn.service.TingService;
import cn.service.UsersService;
import cn.util.Const;
import cn.util.PageBean;
import cn.util.ServerResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;

@Controller
@RequestMapping("/orders")
public class OrdersController {
	@Autowired
	OrdersService ordersService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	PaipianService paipianService;
	@Autowired
	TingService tingService;
	@Autowired
	CinemaService cinemaService;
	@Autowired
	UsersService usersService;
	@Autowired
	SeatService seatService;
	// 后台list
	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit,String ordersno) {
		QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>();
		if (!StringUtils.isNullOrEmpty(ordersno)) {
			wrapper.like("ordersno", ordersno);
		}
		wrapper.orderByDesc("id");
		IPage<Orders> pageList = new Page<Orders>(page, limit);
		pageList = ordersService.page(pageList, wrapper);
		List<Orders> list = pageList.getRecords();
		// 关联对象
		for (Orders m : list) {
			Goods g = goodsService.getById(m.getGid());
			m.setGoods(g);
			m.setUsers(usersService.getById(m.getUid()));
			m.setCinema(cinemaService.getById(m.getCid()));
			m.setTing(tingService.getById(m.getTid()));
			m.setPaipian(paipianService.getById(m.getPid()));
			m.setGoods(goodsService.getById(m.getGid()));
		}
		// 会自动查出总条数
		int count = (int) pageList.getTotal();
		return new ServerResponse("0", "", count,list);
	}

	@RequestMapping("index")
	public String index(HttpSession session, Model model) {
		QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>();
		Integer role = (Integer) session.getAttribute("role");
		Users users = (Users) session.getAttribute("users");
		if (role != null && (role == 1 || role==2)) {
			wrapper.eq("uid", users.getId());// 客户自己订单
		}
		wrapper.orderByDesc("id");

		List<Orders> list = ordersService.list(wrapper);
		for (Orders m : list) {
			Goods g = goodsService.getById(m.getGid());
			m.setGoods(g);
			m.setCinema(cinemaService.getById(m.getCid()));
			m.setTing(tingService.getById(m.getTid()));
			m.setPaipian(paipianService.getById(m.getPid()));
			m.setGoods(goodsService.getById(m.getGid()));
		}
		model.addAttribute("list", list);
		return "ordersList";
	}
	
	@RequestMapping("pay")
	public String pay(Integer pid,Integer[] sids,Model model,HttpSession session) {
		Users users = (Users) session.getAttribute("users");
		Users u=usersService.getById(users.getId());
		Paipian paipian = paipianService.getById(pid);
		//判断周日
		Calendar c=Calendar.getInstance();
		int week=c.get(Calendar.DAY_OF_WEEK);
		//票数
		Orders orders=new Orders();
		orders.setOrdersno(Const.getIds());
		orders.setUid(users.getId());
		orders.setCid(paipian.getCid());
		orders.setTid(paipian.getTid());
		orders.setGid(paipian.getGid());
		orders.setPid(pid);
		int nums=sids.length;
		orders.setNums(nums);
		double price=0;
		double total=0;
		int zhekou=u.getZhekou();
		if(week==1){//周日
			price=paipian.getYhprice();
		}else{
			price=paipian.getPrice();
		}
		total=Const.format(price*nums*zhekou/100.0);
		orders.setPrice(total);
		orders.setOptime(Const.getFullTime());
		orders.setSids(sids);
		session.setAttribute("orders", orders);
		return "pay";
	}
	
	
	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(HttpSession session) {
		Orders orders=(Orders)session.getAttribute("orders");
		String seats="";
		for(int i=0;i<orders.getSids().length;i++){
			Seat s=seatService.getById(orders.getSids()[i]);
			seats+="["+s.getName()+"]&nbsp;&nbsp;";
			//更改座位状态
			s.setStatus(1);
			s.updateById();
		}
		orders.setSeats(seats);
		orders.setStatus("未确认");
		boolean flag=ordersService.save(orders);
		//更新用户积分和等级 
		Users u=usersService.getById(orders.getUid());
		u.setScore(u.getScore()+orders.getPrice().intValue());
		//总积分,算等级的
		u.setJifen(u.getJifen()+orders.getPrice().intValue());
		String grade=Const.getGrade(u.getJifen());
		Integer zhekou=Const.getZhekou(u.getJifen());
		u.setGrade(grade);
		if(!u.getGrade().equals("普通用户")){
			u.setRole(2);//升级为会员
		}
		u.setZhekou(zhekou);
		u.updateById();
		session.removeAttribute("orders");
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}

	@RequestMapping("detail")
	public String detail(Integer id, HttpServletRequest request) {
		Orders orders = ordersService.getById(id);
		request.setAttribute("orders", orders);
		return "redirect:/ordersdetail/index?oid=" + id;
	}

	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(Orders orders) {
		Orders ord = ordersService.getById(orders.getId());
		boolean flag = ordersService.updateById(orders);
		
		//修改座位
		Paipian paipian = paipianService.getById(orders.getPid());
		paipian.setPiaonums(paipian.getPiaonums()+orders.getNums());
		paipianService.updateById(paipian);
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}

	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Orders orders) {
		boolean flag = ordersService.removeById(orders);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}
	@RequestMapping("changestatus")
	@ResponseBody
	public ServerResponse changestatus(Integer id,String status) {
		Orders o=ordersService.getById(id);
		o.setStatus(status);
		boolean flag = ordersService.updateById(o);
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}
	@RequestMapping("exists")
	@ResponseBody
	public ServerResponse exists(Integer gid, HttpSession session) {
		QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>();
		Users users = (Users) session.getAttribute("users");
		if (users != null) {
			wrapper.eq("uid", users.getId());
		}
		wrapper.eq("gid", gid);
		List<Orders> ordersList = ordersService.list(wrapper);
		if (ordersList != null && ordersList.size() > 0) {
			return new ServerResponse("1", "存在");
		} else {
			return new ServerResponse("0", "不存在!");
		}
	}
	
	@RequestMapping("tongji")
	@ResponseBody
	public Map<String, Object> tongji(String time1, String time2) {
		if (StringUtils.isNullOrEmpty(time1)) {
			time1 = "0000-00-00";
		}
		if (StringUtils.isNullOrEmpty(time2)) {
			time2 = "9999-99-99";
		}
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> result = ordersService.groupbytype(time1,
				time2);
		data.put("result", result);
		return data;
	}
}
