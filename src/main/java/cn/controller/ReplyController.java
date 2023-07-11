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

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.entity.Message;
import cn.entity.Reply;
import cn.entity.Users;
import cn.service.MessageService;
import cn.service.ReplyService;
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
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	ReplyService replyService;
	@Autowired
	UsersService usersService;
	
	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit,Integer pid,Integer type,Model model) {	
		QueryWrapper<Reply> wrapper = new QueryWrapper<Reply>();
		wrapper.eq("pid", pid);
		wrapper.eq("type", type);
		wrapper.orderByDesc("id");
		IPage<Reply> pageList=new Page<Reply>(page,limit);
		pageList = replyService.page(pageList, wrapper);
		List<Reply> list=pageList.getRecords();
		//关联对象
		for(Reply m:list){
			Users u=usersService.getById(m.getUid());
			m.setUsers(u);
		}
		// 会自动查出总条数
		int count = (int) pageList.getTotal();
		return new ServerResponse("0", "", count, list);
	}
	
	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(Reply reply,HttpSession session) {
		Users users=(Users)session.getAttribute("users");
		reply.setUid(users.getId());
		reply.setOptime(Const.getFullTime());
		replyService.save(reply);
		return new ServerResponse("0","回复成功!");
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Integer id) {
		replyService.removeById(id);
		return new ServerResponse("0","操作成功!");
	}
}

