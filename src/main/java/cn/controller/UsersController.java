package cn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.entity.Users;
import cn.service.UsersService;
import cn.util.ServerResponse;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nnn
 */
@Controller
@RequestMapping("/users")
public class UsersController {
	@Autowired
	UsersService usersService;

	@RequestMapping("login")
	@ResponseBody
	public ServerResponse login(String username, String password, Integer role,
			HttpServletRequest request) {
		QueryWrapper<Users> wrapper = new QueryWrapper<Users>();
		wrapper.eq("username", username);
		wrapper.eq("password", password);
		wrapper.eq("role", 0);
		wrapper.eq("isdel", 0);
		Object obj = usersService.getOne(wrapper);
		if (obj != null) {
			request.getSession().setAttribute("users", obj);
			request.getSession().setAttribute("role", 0);
			return new ServerResponse("0", "登录成功!");
		} else {
			return new ServerResponse("1", "用户名密码错误!");
		}
	}

	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit,String name, Integer role) {
		QueryWrapper<Users> wapper = new QueryWrapper<Users>();
		if (!StringUtils.isEmpty(name)) {
			wapper.like("name", name);
		}
		wapper.eq("role", role);
		wapper.eq("isdel", 0);
		IPage<Users> page_users = new Page<Users>(page, limit);
		page_users = usersService.page(page_users, wapper);
		// 会自动查出总条数
		int count = (int) page_users.getTotal();
		return new ServerResponse("0", "", count,
				page_users.getRecords());
	}
	
	@RequestMapping("index")
	public String index(Model model) {
		QueryWrapper<Users> wapper = new QueryWrapper<Users>();
		wapper.eq("role", 1);
		wapper.eq("isdel", 0);
		wapper.orderByDesc("score");
		List<Users> usersList=usersService.list(wapper);
		if(usersList.size()>3){
			usersList=usersList.subList(0, 3);
		}
		model.addAttribute("usersList", usersList);
		return "usersList";
	}


	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "admin/login";
	}
	//后台注册
	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(Users users) {
		QueryWrapper<Users> wapper = new QueryWrapper<Users>();
		wapper.eq("username", users.getUsername());
		wapper.eq("isdel", 0);
		Users u = usersService.getOne(wapper);
		if (u != null) {
			return new ServerResponse("1", "该用户名已经存在!");
		} else {
			users.setRole(1);
			users.setGrade("普通用户");
			users.setZhekou(100);//无折扣
			users.setScore(0);
			users.setJifen(0);
			users.setIsdel(0);
			usersService.save(users);
			return new ServerResponse("0", "注册成功!");
		}
	}

	@RequestMapping("updateindex")
	@ResponseBody
	public ServerResponse updateindex(Users users, HttpSession session,Model model) {
		boolean flag = usersService.updateById(users);
		session.invalidate();
		if(flag){
			return new ServerResponse("0", "修改成功，请重新登录!");
		}else{
			return new ServerResponse("1", "修改失败!");
		}
	}

	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(Users users) {
		boolean flag = usersService.updateById(users);
		if (flag) {
			return new ServerResponse("0", "修改成功!");
		} else {
			return new ServerResponse("1", "修改失败!");
		}
	}
	
//	@RequestMapping("chongzhi")
//	@ResponseBody
//	public ServerResponse chongzhi(Integer id,Integer zhekou,HttpSession session) {
//		Users users = usersService.getById(id);
//		users.setRole(2);
//		users.setGrade("GRADES");
//		users.setZhekou(zhekou);
//		boolean flag = usersService.updateById(users);
//		if (flag) {
//			session.setAttribute("role", 2);
//			session.setAttribute("users", users);
//			return new ServerResponse("0", "修改成功!");
//		} else {
//			return new ServerResponse("1", "修改失败!");
//		}
//	}

	@RequestMapping("isexists")
	@ResponseBody
	public String isexists(String username) {
		QueryWrapper<Users> wrapper = new QueryWrapper<Users>();
		wrapper.eq("username", username);
		Object obj = usersService.getOne(wrapper);
		if (obj == null) {
			return "0";
		} else {
			return "1";
		}
	}

	@RequestMapping("json")
	@ResponseBody
	public Users json(Integer id) {
		Users users = usersService.getById(id);
		return users;
	}

	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Users users) {
		users.setIsdel(1);
		boolean flag = usersService.updateById(users);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}
}
