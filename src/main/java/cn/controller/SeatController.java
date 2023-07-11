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

import cn.entity.Cinema;
import cn.entity.Seat;
import cn.entity.Message;
import cn.entity.News;
import cn.entity.Reply;
import cn.entity.Seat;
import cn.entity.Ting;
import cn.entity.Users;
import cn.service.CinemaService;
import cn.service.GoodsService;
import cn.service.SeatService;
import cn.service.MessageService;
import cn.service.ReplyService;
import cn.service.SeatService;
import cn.service.TingService;
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
@RequestMapping("/seat")
public class SeatController {
	@Autowired
	SeatService seatService;

	@RequestMapping("jsonlist")
	@ResponseBody
	public List<Seat> jsonlist(Integer pid) {
		QueryWrapper<Seat> wrapper = new QueryWrapper<Seat>();
		wrapper.eq("pid", pid);
		return seatService.list(wrapper);
	}
	
	
}

