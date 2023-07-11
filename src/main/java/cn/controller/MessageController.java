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

import cn.entity.Goods;
import cn.entity.Message;
import cn.entity.Reply;
import cn.entity.Users;
import cn.service.GoodsService;
import cn.service.MessageService;
import cn.service.ReplyService;
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
 */
@Controller
@RequestMapping("/message")
public class MessageController {
	@Autowired
	MessageService messageService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	UsersService usersService;
	
	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit,
			HttpSession session,String content) {
		QueryWrapper<Message> wrapper = new QueryWrapper<Message>();
		Integer role = (Integer) session.getAttribute("role");
		Users users = (Users) session.getAttribute("users");
		if (role != null && (role == 1 || role==2)) {
			wrapper.eq("uid", users.getId());
		}
		wrapper.eq("isdel", 0);
		
		if(content!=null && !"".equals(content)){
			wrapper.like("content",content);
		}
		IPage<Message> pageList = new Page<Message>(page, limit);
		pageList = messageService.page(pageList, wrapper);
		List<Message> list = pageList.getRecords();
		// 关联对象
		for (Message m : list) {
			Users u = usersService.getById(m.getUid());
			m.setUsers(u);
			Goods g = goodsService.getById(m.getGid());
			m.setGoods(g);
		}
		// 会自动查出总条数
		int count = (int) pageList.getTotal();
		return new ServerResponse("0", "", count, list);
	}

	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(Message message) {
			message.setIsdel(0);
			message.setOptime(Const.getFullTime());
			boolean flag=messageService.save(message);
			if(flag){
				return new ServerResponse("0", "操作成功!");
			}else{
				return new ServerResponse("1", "操作失败!");
			}
	}

	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(Message message) {
		boolean flag = messageService.updateById(message);
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}
	
	@RequestMapping("zan")
	@ResponseBody
	public ServerResponse zan(Integer id) {
		Message message=messageService.getById(id);
		message.setScore(message.getScore()+1);
		boolean flag = messageService.updateById(message);
		if (flag) {
			return new ServerResponse("0", message.getScore()+"");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Message message) {
		message.setIsdel(1);
		boolean flag = messageService.updateById(message);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}

	
}
