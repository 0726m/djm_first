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

import cn.entity.Wupin;
import cn.entity.Records;
import cn.entity.Paipian;
import cn.entity.Seat;
import cn.entity.Users;
import cn.service.CinemaService;
import cn.service.WupinService;
import cn.service.RecordsService;
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
@RequestMapping("/records")
public class RecordsController {
	@Autowired
	RecordsService recordsService;
	@Autowired
	WupinService wupinService;
	@Autowired
	UsersService usersService;

	@RequestMapping("index")
	public String index(HttpSession session, Model model) {
		QueryWrapper<Records> wrapper = new QueryWrapper<Records>();
		Integer role = (Integer) session.getAttribute("role");
		Users users = (Users) session.getAttribute("users");
		if (role != null && (role == 1 || role==2)) {
			wrapper.eq("uid", users.getId());// 客户自己的
		}
		wrapper.orderByDesc("id");

		List<Records> list = recordsService.list(wrapper);
		for (Records m : list) {
			Wupin g = wupinService.getById(m.getWid());
			m.setWupin(g);
			m.setUsers(usersService.getById(m.getUid()));
		}
		model.addAttribute("list", list);
		return "recordsList";
	}
	
	

	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Records records) {
		boolean flag = recordsService.removeById(records);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}
	
	
}
