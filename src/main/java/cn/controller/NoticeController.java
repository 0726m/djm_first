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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;

import cn.entity.Notice;
import cn.entity.Notice;
import cn.entity.Reply;
import cn.entity.Users;
import cn.service.NoticeService;
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
@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	NoticeService noticeService;
	
	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit,String title,HttpSession session) {
		QueryWrapper<Notice> wrapper = new QueryWrapper<Notice>();
		if(!StringUtils.isNullOrEmpty(title)){
			wrapper.like("title", title);
		}
		Integer role = (Integer) session.getAttribute("role");
		Users users = (Users) session.getAttribute("users");
		if (role != null && (role == 1 || role==2)) {
			wrapper.eq("sid", users.getId());
		}
		wrapper.eq("isdel", 0);
		wrapper.orderByDesc("id");
		IPage<Notice> pageList = new Page<Notice>(page, limit);
		pageList = noticeService.page(pageList, wrapper);
		int count = (int) pageList.getTotal();
		return new ServerResponse("0", "", count, pageList.getRecords());
	}
	
	@RequestMapping("index")
	public String index(Integer page,String title,HttpSession session,Model model) {
		PageBean pageBean=new PageBean();
		if(page!=null){
			pageBean.setPageNo(page);
		}
		QueryWrapper<Notice> wrapper = new QueryWrapper<Notice>();
		if(!StringUtils.isNullOrEmpty(title)){
			wrapper.like("title", title);
		}
		wrapper.eq("isdel", 0);
		wrapper.orderByDesc("id");
		IPage<Notice> pageList=new Page<Notice>(pageBean.getPageNo(),pageBean.getPageSize());
		pageList = noticeService.page(pageList, wrapper);
		List<Notice> list=pageList.getRecords();
		pageBean.setTotalCount((int)pageList.getTotal());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("list", list);
		return "noticeList";
	}
	
	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(Notice notice) {
		notice.setOptime(Const.getFullTime());
		notice.setIsdel(0);
		boolean flag = noticeService.save(notice);
		if (flag) {
			return new ServerResponse("0", "添加成功!");
		} else {
			return new ServerResponse("1", "添加失败!");
		}
	}
	
	@RequestMapping("detail")
	public String detail(Integer id,HttpServletRequest request) {
		Notice notice=noticeService.getById(id);
		request.setAttribute("notice", notice);
		return "noticeDetail";
	}
	
	@RequestMapping("json")
	@ResponseBody
	public Notice json(Integer id) {
		Notice notice = noticeService.getById(id);
		return notice;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Notice notice) {
		notice.setIsdel(1);
		boolean flag = noticeService.updateById(notice);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(Notice notice) {
		boolean flag = noticeService.updateById(notice);
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}
}

