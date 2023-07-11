package cn.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.entity.Ting;
import cn.entity.Message;
import cn.entity.News;
import cn.entity.Reply;
import cn.entity.Users;
import cn.service.CinemaService;
import cn.service.TingService;
import cn.service.MessageService;
import cn.service.ReplyService;
import cn.service.UsersService;
import cn.util.Const;
import cn.util.PageBean;
import cn.util.ServerResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nnn
 */
@RequestMapping("/ting")
@Controller
public class TingController {
	@Autowired
	TingService tingService;
	@Autowired
	CinemaService cinemaService;
	
	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit,String name) {
		QueryWrapper<Ting> wrapper = new QueryWrapper<Ting>();
		if(!StringUtils.isNullOrEmpty(name)){
			wrapper.like("name", name);
		}
		wrapper.eq("isdel", 0);
		wrapper.orderByDesc("cid");
		IPage<Ting> pageList = new Page<Ting>(page, limit);
		pageList = tingService.page(pageList, wrapper);
		List<Ting> list = pageList.getRecords();
		for(Ting t:list){
			t.setCinema(cinemaService.getById(t.getCid()));
		}
		// 会自动查出总条数
		int count = (int) pageList.getTotal();
		return new ServerResponse("0", "", count, list);
	}
	
	@RequestMapping("index")
	public String index(Integer page,String name,Integer kid,HttpSession session,Model model) {
		PageBean pageBean=new PageBean();
		if(page!=null){
			pageBean.setPageNo(page);
		}
		pageBean.setPageSize(12);
		QueryWrapper<Ting> wrapper = new QueryWrapper<Ting>();
		if(!StringUtils.isNullOrEmpty(name)){
			wrapper.like("name", name);
		}
		if(kid!=null){
			wrapper.eq("kid", kid);
		}
		wrapper.eq("isdel", 0).last(" order by id desc");
		IPage<Ting> pageList=new Page<Ting>(pageBean.getPageNo(),pageBean.getPageSize());
		pageList = tingService.page(pageList, wrapper);
		List<Ting> list=pageList.getRecords();
		for(Ting t:list){
			t.setCinema(cinemaService.getById(t.getCid()));
		}
		pageBean.setTotalCount((int)pageList.getTotal());
		// 类型
		QueryWrapper<Cinema> nwrapper = new QueryWrapper<Cinema>();
		nwrapper.eq("isdel", 0);
		List<Cinema> cinemaList = cinemaService.list(nwrapper);
		model.addAttribute("cinemaList", cinemaList);
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("list", list);
		return "tingList";
	}

	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(Ting ting) {
		ting.setIsdel(0);
		boolean flag = tingService.save(ting);
		if (flag) {
			return new ServerResponse("0", "添加成功!");
		} else {
			return new ServerResponse("1", "添加失败!");
		}
	}
	
	@RequestMapping("detail")
	public String detail(Integer id,Model model) {
		Ting ting=tingService.getById(id);
		model.addAttribute("ting", ting);
		return "tingDetail";
	}
	
	
	@RequestMapping("json")
	@ResponseBody
	public Ting json(Integer id) {
		Ting ting = tingService.getById(id);
		return ting;
	}
	
	
	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Ting ting) {
		ting.setIsdel(1);
		boolean flag = tingService.updateById(ting);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(Ting ting) {
		boolean flag = tingService.updateById(ting);
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}
	
	@RequestMapping("jsonlist")
	@ResponseBody
	public List<Ting> jsonlist(Integer cid) {
		QueryWrapper<Ting> wrapper = new QueryWrapper<Ting>();
		wrapper.eq("cid", cid);
		wrapper.eq("isdel", 0);
		return tingService.list(wrapper);
	}
}

