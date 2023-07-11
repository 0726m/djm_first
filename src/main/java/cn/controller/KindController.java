package cn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.entity.Cinema;
import cn.entity.Goods;
import cn.entity.Kind;
import cn.entity.Message;
import cn.entity.Notice;
import cn.entity.Users;
import cn.service.KindService;
import cn.service.UsersService;
import cn.util.Const;
import cn.util.PageBean;
import cn.util.ServerResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nnn
 * @since 
 */
@Controller
@RequestMapping("/kind")
public class KindController {
	@Autowired
	KindService kindService;
	@Autowired
	UsersService usersService;
	
	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(Kind kind) {
		kind.setIsdel(0);
		boolean flag=kindService.save(kind);
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}
	
	@RequestMapping("detail")
	public String detail(Integer id,HttpServletRequest request) {
		Kind kind=kindService.getById(id);
		request.setAttribute("kind", kind);
		return "kindDetail";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(Kind kind) {
		boolean flag = kindService.updateById(kind);
		if (flag) {
			return new ServerResponse("0", "修改成功!");
		} else {
			return new ServerResponse("1", "修改失败!");
		}
	}
	
	@RequestMapping("json")
	@ResponseBody
	public Kind json(Integer id) {
		Kind kind = kindService.getById(id);
		return kind;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Kind kind) {
		kind.setIsdel(1);
		boolean flag = kindService.updateById(kind);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}

	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit,String name,HttpSession session) {
		QueryWrapper<Kind> wrapper = new QueryWrapper<Kind>();
		if (!StringUtils.isEmpty(name)) {
			wrapper.like("name", name);
		}
		wrapper.eq("isdel", 0);
		IPage<Kind> page_kind = new Page<Kind>(page, limit);
		page_kind = kindService.page(page_kind, wrapper);
		// 会自动查出总条数
		int count = (int) page_kind.getTotal();
		List<Kind> list=page_kind.getRecords();
		
		return new ServerResponse("0", "", count,list);
	}
	@RequestMapping("index")
	public String index(Integer page,HttpSession session,Model model) {
		PageBean pageBean=new PageBean();
		if(page!=null){
			pageBean.setPageNo(page);
		}
		pageBean.setPageSize(12);
		QueryWrapper<Kind> wrapper = new QueryWrapper<Kind>();
		wrapper.eq("isdel", 0);
		IPage<Kind> page_kind = new Page<Kind>(pageBean.getPageNo(),pageBean.getPageSize());
		page_kind = kindService.page(page_kind, wrapper);
		List<Kind> list=page_kind.getRecords();
		pageBean.setTotalCount((int)page_kind.getTotal());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("list", list);
		return "kindList";
	}
	
	@RequestMapping("jsonlist")
	@ResponseBody
	public List<Kind> jsonlist() {
		QueryWrapper<Kind> wrapper = new QueryWrapper<Kind>();
		wrapper.eq("isdel", 0);
		return kindService.list(wrapper);
	}

}
