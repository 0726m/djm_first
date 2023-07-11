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
import cn.entity.Records;
import cn.entity.Wupin;
import cn.entity.Kind;
import cn.entity.Message;
import cn.entity.News;
import cn.entity.Orders;
import cn.entity.Paipian;
import cn.entity.Reply;
import cn.entity.Ting;
import cn.entity.Users;
import cn.service.CinemaService;
import cn.service.RecordsService;
import cn.service.WupinService;
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
 * @author nnn
 */
@Controller
@RequestMapping("/wupin")
public class WupinController {
	@Autowired
	WupinService wupinService;
	@Autowired
	UsersService usersService;
	@Autowired
	RecordsService recordsService;

	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit, String name,
			HttpSession session) {
		QueryWrapper<Wupin> wrapper = new QueryWrapper<Wupin>();
		if (!StringUtils.isNullOrEmpty(name)) {
			wrapper.like("name", name);
		}
		wrapper.eq("isdel", 0);
		wrapper.orderByDesc("id");
		IPage<Wupin> pageList = new Page<Wupin>(page, limit);
		pageList = wupinService.page(pageList, wrapper);
		List<Wupin> list = pageList.getRecords();
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
		QueryWrapper<Wupin> wrapper = new QueryWrapper<Wupin>();
		if (!StringUtils.isNullOrEmpty(name)) {
			wrapper.like("name", name);
		}
		if (kid != null) {
			wrapper.eq("kid", kid);
		}
		wrapper.eq("isdel", 0);
		wrapper.orderByDesc("id");// ID降序
		// 分页查询
		IPage<Wupin> pageList = new Page<Wupin>(pageBean.getPageNo(),
				pageBean.getPageSize());
		pageList = wupinService.page(pageList, wrapper);
		List<Wupin> list = pageList.getRecords();
		// 这里是放总记录数
		pageBean.setTotalCount((int) pageList.getTotal());

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("list", list);
		// 跳转到页面
		return "wupinList";
	}

	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(Wupin wupin) {
		wupin.setIsdel(0);
		boolean flag = wupinService.save(wupin);
		if (flag) {
			return new ServerResponse("0", "添加成功!");
		} else {
			return new ServerResponse("1", "添加失败!");
		}
	}

	@RequestMapping("detail")
	public String detail(Integer id, Model model) {
		Wupin wupin = wupinService.getById(id);
		model.addAttribute("wupin", wupin);

		return "wupinDetail";
	}

	@RequestMapping("json")
	@ResponseBody
	public Wupin json(Integer id) {
		Wupin wupin = wupinService.getById(id);
		return wupin;
	}

	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Wupin wupin) {
		wupin.setIsdel(1);
		boolean flag = wupinService.updateById(wupin);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}

	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(Wupin wupin) {
		boolean flag = wupinService.updateById(wupin);
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}
	@RequestMapping("dui")
	public String dui(Integer id,HttpSession session) {
		Wupin wp=wupinService.getById(id);
		Users users = (Users) session.getAttribute("users");
		users.setScore(users.getScore()-wp.getJifen());
		boolean flag = usersService.updateById(users);
		Records r=new Records();
		r.setUid(users.getId());
		r.setWid(id);
		r.setOptime(Const.getFullTime());
		recordsService.save(r);
		return "redirect:/records/index";
	}

}
