package cn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.entity.Goods;
import cn.entity.Kind;
import cn.entity.Notice;
import cn.entity.Users;
import cn.service.GoodsService;
import cn.service.KindService;
import cn.service.NoticeService;
import cn.service.UsersService;
import cn.util.Const;
import cn.util.PageBean;
import cn.util.ServerResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * 
 */
@Controller
public class IndexController {
	@Autowired
	UsersService usersService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	NoticeService noticeService;

	@Autowired
	KindService kindService;

	// 前台登录
	@RequestMapping("/login")
	@ResponseBody
	public ServerResponse login(String username, String password,
			HttpServletRequest request) {
		QueryWrapper<Users> wrapper = new QueryWrapper<Users>();
		wrapper.eq("username", username);
		wrapper.eq("password", password);
		wrapper.ne("role", 0);
		wrapper.eq("isdel", 0);
		Users users = usersService.getOne(wrapper);
		if (users != null) {
			request.getSession().setAttribute("users", users);
			request.getSession().setAttribute("role", users.getRole());
			return new ServerResponse("0", "登录成功");
		} else {
			return new ServerResponse("1", "用户名密码错误!");
		}
	}

	@RequestMapping("/index")
	public String index(Integer page, String name, HttpServletRequest request) {
		PageBean pageBean = new PageBean();
		if (page != null) {
			pageBean.setPageNo(page);
		}
		pageBean.setPageSize(8);
		QueryWrapper<Goods> wrapper = new QueryWrapper<Goods>();
		if (!StringUtils.isNullOrEmpty(name)) {
			wrapper.like("name", name);
			request.setAttribute("name", name);
		}
		String now = Const.getTime();
		wrapper.eq("isdel", 0);
		wrapper.le("startday", now);// 上映时间
		wrapper.ge("endday", now);
		wrapper.orderByAsc("id");
		IPage<Goods> pageList = new Page<Goods>(pageBean.getPageNo(),
				pageBean.getPageSize());
		pageList = goodsService.page(pageList, wrapper);
		List<Goods> list = pageList.getRecords();
		for(Goods g:list){
			Kind k=kindService.getById(g.getKid());
			g.setKind(k);
		}
		pageBean.setTotalCount((int) pageList.getTotal());
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("goodsList", list);
		// 查看通知
		QueryWrapper<Notice> wrapper2 = new QueryWrapper<Notice>();
		wrapper2.eq("isdel", 0);
		wrapper2.orderByDesc("optime");
		List<Notice> noticeList = noticeService.list(wrapper2);
		request.setAttribute("noticeList", noticeList);
		
		
		return "index";
	}

	// 前台注册
	@RequestMapping("/reg")
	@ResponseBody
	public ServerResponse reg(Users users, HttpServletRequest request) {
		QueryWrapper<Users> wapper = new QueryWrapper<Users>();
		wapper.eq("username", users.getUsername());
		wapper.eq("isdel", 0);
		Users u = usersService.getOne(wapper);
		if (u != null) {
			return new ServerResponse("1", "该用户名已经存在!");
		} else {
			users.setRole(1);
			users.setGrade(Const.GRADES[0]);
			users.setScore(0);
			users.setZhekou(100);
			users.setIsdel(0);
			boolean flag = usersService.save(users);
			if (flag) {
				return new ServerResponse("0", "注册成功！");
			} else {
				return new ServerResponse("1", "注册失败！");
			}
		}
	}

	@RequestMapping("/updatepwd")
	@ResponseBody
	public ServerResponse updatepwd(String password,
			HttpServletRequest request) {
		Users users = (Users) request.getSession().getAttribute("users");
		if (users != null) {
			users.setPassword(password);
			boolean flag = usersService.updateById(users);
			if (flag) {
				return new ServerResponse("0", "操作成功");
			}
		}
		return new ServerResponse("1", "操作失败");
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/index";
	}

}
