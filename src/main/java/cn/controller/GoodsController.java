package cn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Indexed;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;

import cn.entity.Cinema;
import cn.entity.Orders;
import cn.entity.Goods;
import cn.entity.Kind;
import cn.entity.Message;
import cn.entity.News;
import cn.entity.Orders;
import cn.entity.Paipian;
import cn.entity.Reply;
import cn.entity.Ting;
import cn.entity.Users;
import cn.service.CinemaService;
import cn.service.GoodsService;
import cn.service.KindService;
import cn.service.MessageService;
import cn.service.OrdersService;
import cn.service.PaipianService;
import cn.service.ReplyService;
import cn.service.TingService;
import cn.service.UsersService;
import cn.util.Const;
import cn.util.PageBean;
import cn.util.Recommend;
import cn.util.ServerResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * 
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
	@Autowired
	GoodsService goodsService;
	@Autowired
	MessageService messageService;
	@Autowired
	ReplyService replyService;
	@Autowired
	UsersService usersService;
	@Autowired
	CinemaService cinemaService;
	@Autowired
	TingService tingService;
	@Autowired
	PaipianService paipianService;
	@Autowired
	KindService kindService;
	@Autowired
	OrdersService ordersService;

	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit, String name,
			HttpSession session) {
		QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>();
		Integer role = (Integer) session.getAttribute("role");
		Users users = (Users) session.getAttribute("users");
		if (role != null && (role == 1 || role==2)) {
			wrapper.eq("uid", users.getId());
		}
		if (!StringUtils.isNullOrEmpty(name)) {
			wrapper.like("name", name);
		}

		wrapper.eq("isdel", 0);
		wrapper.orderByDesc("id");
		IPage<Goods> pageList = new Page<Goods>(page, limit);
		pageList = goodsService.page(pageList, wrapper);
		List<Goods> list = pageList.getRecords();
		for (Goods g : list) {
			if (g.getKid() != null) {
				g.setKind(kindService.getById(g.getKid()));
			}
		}
		// 会自动查出总条数
		int count = (int) pageList.getTotal();
		return new ServerResponse("0", "", count, list);
	}

	// 主要是看这个方法
	@RequestMapping("index")
	public String index(Integer page, String name, Integer kid,
			HttpSession session, Model model) {
		// 分页Bean
		PageBean pageBean = new PageBean();
		if (page != null) {
			pageBean.setPageNo(page);
		}
		// 这个可以不管，默认是10
		pageBean.setPageSize(12);
		QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>();
		if (!StringUtils.isNullOrEmpty(name)) {
			wrapper.like("name", name);
		}
		if (kid != null) {
			wrapper.eq("kid", kid);
		}
		wrapper.eq("isdel", 0);
		wrapper.orderByDesc("id");// ID降序
		// 分页查询
		IPage<Goods> pageList = new Page<Goods>(pageBean.getPageNo(),
				pageBean.getPageSize());
		pageList = goodsService.page(pageList, wrapper);
		List<Goods> list = pageList.getRecords();
		for (Goods g : list) {
			Kind k = kindService.getById(g.getKid());
			g.setKind(k);
		}
		// 这里是放总记录数
		pageBean.setTotalCount((int) pageList.getTotal());
		// 类型
		QueryWrapper<Kind> nwrapper = new QueryWrapper<Kind>();
		nwrapper.eq("isdel", 0);
		List<Kind> kindList = kindService.list(nwrapper);
		model.addAttribute("kindList", kindList);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("list", list);
		// 跳转到页面
		return "goodsList";
	}

	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(Goods goods) {
		goods.setIsdel(0);
		boolean flag = goodsService.save(goods);
		if (flag) {
			return new ServerResponse("0", "添加成功!");
		} else {
			return new ServerResponse("1", "添加失败!");
		}
	}

	@RequestMapping("detail")
	public String detail(Integer id, Model model) {
		Goods goods = goodsService.getById(id);
		Kind k = kindService.getById(goods.getKid());
		goods.setKind(k);
		model.addAttribute("goods", goods);
		// 获取评价
		QueryWrapper<Message> wrapper = new QueryWrapper<Message>();
		wrapper.eq("isdel", 0);
		wrapper.eq("gid", id);
		List<Message> list = messageService.list(wrapper);
		// 关联对象
		for (Message m : list) {
			Users u = usersService.getById(m.getUid());
			m.setUsers(u);
			// 回复
			QueryWrapper<Reply> qwrapper = new QueryWrapper<Reply>();
			qwrapper.eq("pid", m.getId());
			qwrapper.eq("type", 1);
			List<Reply> replys = replyService.list(qwrapper);
			for (Reply r : replys) {
				Users ru = usersService.getById(r.getUid());
				r.setUsers(ru);
			}
			m.setReplys(replys);
		}
		model.addAttribute("messageList", list);

		// 该电影的排班信息
		QueryWrapper<Paipian> pwrapper = new QueryWrapper<Paipian>();
		// 日期要大于当前日期，时间要大于当前时间
		String nowday = Const.getTime();
		String nowtime = Const.getNowTime();
		pwrapper.ge("opday", nowday);
		pwrapper.eq("gid", id);
		pwrapper.orderByAsc("opday");
		pwrapper.orderByAsc("starttime");
		List<Paipian> paipians = paipianService.list(pwrapper);
		// 影院
		Set<Integer> cids = new HashSet<Integer>();
		cids.add(0);
		for (Paipian p : paipians) {
			if (p.getOpday().equals(nowday)) {// 今日
				if (p.getStarttime().compareTo(nowtime) > 0) {
					cids.add(p.getCid());
				}
			} else {
				cids.add(p.getCid());
			}
		}
		QueryWrapper<Cinema> cwrapper = new QueryWrapper<Cinema>();
		cwrapper.in("id", cids);
		cwrapper.eq("isdel", 0);
		List<Cinema> cinemaList = cinemaService.list(cwrapper);
		for (Cinema c : cinemaList) {
			QueryWrapper<Ting> twrapper = new QueryWrapper<Ting>();
			twrapper.eq("cid", c.getId());
			List<Ting> tings = tingService.list(twrapper);
			c.setTings(tings);
		}
		model.addAttribute("cinemaList", cinemaList);

		return "goodsDetail";
	}

	@RequestMapping("json")
	@ResponseBody
	public Goods json(Integer id) {
		Goods goods = goodsService.getById(id);
		return goods;
	}

	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Goods goods) {
		goods.setIsdel(1);
		boolean flag = goodsService.updateById(goods);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}

	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(Goods goods) {
		boolean flag = goodsService.updateById(goods);
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}

	@RequestMapping("jsonlist")
	@ResponseBody
	public List<Goods> jsonlist() {
		QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>();
		String now = Const.getTime();
//		wrapper.lt("startday", now);
//		wrapper.gt("endday", now);
		wrapper.eq("isdel", 0);
		return goodsService.list(wrapper);
	}

	// 自动推荐
	@RequestMapping("tuijian")
	public String tuijian(Integer page, HttpSession session, Model model) {
		Users users = (Users) session.getAttribute("users");
		// 最后的推荐结果
		List<Goods> list = new ArrayList<Goods>();
		if (users != null) {
			// 获取全部用户
			QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>();
			wrapper.select("uid");
			wrapper.groupBy("uid");
			List<Orders> orderslist = ordersService.list(wrapper);
			// 全部用户集合
			List<Users> allUsers = new ArrayList<Users>();
			for (Orders o : orderslist) {
				Users u = usersService.getById(o.getUid());
				allUsers.add(u);
			}
			// 全部用户的购买集合
			for (Users u : allUsers) {
				setOrders(u);
			}
			// 记录其他用户的集合
			List<Users> otherUsers = new ArrayList<Users>();
			for (Users u : allUsers) {
				System.out.println(u.getId()+"\t"+users.getId());
				if (u.getId().intValue() != users.getId().intValue()) {
					otherUsers.add(u);// 得到其他用户及集合
				} else {
					users.setList(u.getList());// 我自己的集合
				}
			}
			// 返回与我有关联
			List<Users> userMS = Recommend.searchUserM(users, otherUsers);
			for (int i = 0; i < userMS.size(); i++) {
				// 查询相似度
				userMS.get(i).setSeem(Recommend.compare(users, userMS.get(i)));
				System.out.println(userMS.get(i).toString());
			}
			// 计算关联度
			Map<Integer, Double> recommend = Recommend.recommend(users, userMS);
			System.out.println(recommend);
			// 所有关联的的
			Set<Integer> allids = recommend.keySet();
			// 我的
			List<Integer> myids = users.getList();
			// 排除我的
			Set<Integer> ids = new HashSet<Integer>();

			for (Integer tid : allids) {
				if (!myids.contains(tid)) {
					ids.add(tid);
				}
			}

			for (Integer id : ids) {
				list.add(goodsService.getById(id));
			}
		}
		model.addAttribute("list", list);
		return "tuijian";
	}

	// 获取某用户的订单
	public void setOrders(Users users) {
		List<Integer> ids = new ArrayList<Integer>();
		QueryWrapper<Orders> wrapper = new QueryWrapper<Orders>();
		wrapper.eq("uid", users.getId());
		List<Orders> list = ordersService.list(wrapper);
		for (Orders orders : list) {
			ids.add(orders.getGid());
		}
		users.setList(ids);
	}

}
