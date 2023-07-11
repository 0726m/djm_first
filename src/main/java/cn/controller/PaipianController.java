package cn.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;

import cn.entity.Cinema;
import cn.entity.Goods;
import cn.entity.Paipian;
import cn.entity.Message;
import cn.entity.News;
import cn.entity.Reply;
import cn.entity.Seat;
import cn.entity.Ting;
import cn.entity.Users;
import cn.service.CinemaService;
import cn.service.GoodsService;
import cn.service.PaipianService;
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
@RequestMapping("/paipian")
public class PaipianController {
	@Autowired
	PaipianService paipianService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	SeatService seatService;
	@Autowired
	UsersService usersService;
	@Autowired
	CinemaService cinemaService;
	@Autowired
	TingService tingService;
	
	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit,Integer cid,Integer tid,String opday,
			HttpSession session) {
		QueryWrapper<Paipian> wrapper = new QueryWrapper<Paipian>();
		if(cid!=null){
			wrapper.eq("cid", cid);
		}
		if(tid!=null){
			wrapper.eq("tid", tid);
		}
		if(!StringUtils.isNullOrEmpty(opday)){
			wrapper.like("opday", opday);
		}
		wrapper.orderByAsc("cid");
		wrapper.orderByAsc("id");
		IPage<Paipian> pageList = new Page<Paipian>(page, limit);
		pageList = paipianService.page(pageList, wrapper);
		List<Paipian> list = pageList.getRecords();
		for(Paipian p:list){
			p.setCinema(cinemaService.getById(p.getCid()));
			p.setTing(tingService.getById(p.getTid()));
			p.setGoods(goodsService.getById(p.getGid()));
		}
		// 会自动查出总条数
		int count = (int) pageList.getTotal();
		return new ServerResponse("0", "", count, list);
	}

	@RequestMapping("index")
	public String index(Integer page,Integer cid,Integer tid,Model model) {
		PageBean pageBean=new PageBean();
		if(page!=null){
			pageBean.setPageNo(page);
		}
		pageBean.setPageSize(12);
		QueryWrapper<Paipian> wrapper = new QueryWrapper<Paipian>();
		if(cid!=null){
			wrapper.eq("cid", cid);
		}
		if(tid!=null){
			wrapper.eq("tid", tid);
		}
		
		wrapper.orderByAsc("cid");
		wrapper.orderByAsc("id");
		IPage<Paipian> pageList = new Page<Paipian>(pageBean.getPageNo(),pageBean.getPageSize());
		pageList = paipianService.page(pageList, wrapper);
		List<Paipian> list = pageList.getRecords();
		pageBean.setTotalCount((int)pageList.getTotal());
		for(Paipian p:list){
			p.setCinema(cinemaService.getById(p.getCid()));
			p.setTing(tingService.getById(p.getTid()));
			p.setGoods(goodsService.getById(p.getGid()));
		}
		// 会自动查出总条数
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("list", list);
		return "goods";
	}

	

	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(Paipian paipian) {
		boolean flag = paipianService.save(paipian);
		//获取座位数加座位
		Ting ting=tingService.getById(paipian.getTid());
		int seatnums=ting.getSeatnums();
		for(int i=1;i<=seatnums;i++){
			Seat seat=new Seat();
			if(i<10){
				seat.setName("0"+i);
			}else{
				seat.setName(i+"");
			}
			seat.setStatus(0);//未占用
			seat.setPid(paipian.getId());
			seatService.save(seat);
		}
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}
	
	@RequestMapping("json")
	@ResponseBody
	public Paipian json(Integer id) {
		Paipian paipian = paipianService.getById(id);
		return paipian;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Integer id) {
		QueryWrapper<Seat> wrapper = new QueryWrapper<Seat>();
		wrapper.eq("pid", id);
		seatService.remove(wrapper);
		boolean flag = paipianService.removeById(id);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(Paipian paipian) {
		Paipian p=paipianService.getById(paipian.getId());
		paipian.setCid(p.getCid());
		paipian.setTid(p.getTid());
		paipian.setGid(p.getGid());
		paipian.setPrice(p.getPrice());
		paipian.setYhprice(p.getYhprice());
		boolean flag = paipianService.save(paipian);
		//获取座位数加座位
		Ting ting=tingService.getById(paipian.getTid());
		int seatnums=ting.getSeatnums();
		for(int i=1;i<=seatnums;i++){
			Seat seat=new Seat();
			if(i<10){
				seat.setName("0"+i);
			}else{
				seat.setName(i+"");
			}
			seat.setStatus(0);//未占用
			seat.setPid(paipian.getId());
			seatService.save(seat);
		}
		if (flag) {
			return new ServerResponse("0", "操作成功!");
		} else {
			return new ServerResponse("1", "操作失败!");
		}
	}
	
	@RequestMapping("jsonlist")
	@ResponseBody
	public JSONArray jsonlist(Integer tid) {
		JSONArray results=new JSONArray();
		LinkedHashMap<String, List<Paipian>> data=new LinkedHashMap<String, List<Paipian>>();
		QueryWrapper<Paipian> wrapper = new QueryWrapper<Paipian>();
		wrapper.eq("tid", tid);
		String nowday=Const.getTime();
		wrapper.ge("opday",nowday);
		wrapper.orderByAsc("opday");
		wrapper.orderByAsc("starttime");
		List<Paipian> pbs=paipianService.list(wrapper);
		List<String> opdays=new ArrayList<String>();
		for(Paipian p:pbs){
			String opday=p.getOpday();
			if(!opdays.contains(opday)){//不存在放入集合
				opdays.add(opday);
				data.put(opday, new ArrayList<Paipian>());
			}
			data.get(opday).add(p);
		}
		for(String opday:opdays){
			JSONObject obj=new JSONObject();
			obj.put("name", opday);
			obj.put("times", data.get(opday));
			results.add(obj);
		}
		//放入
		return results;
	}
}

