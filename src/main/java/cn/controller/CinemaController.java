package cn.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;

import cn.entity.Cinema;
import cn.service.CinemaService;
import cn.util.ServerResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * 
 */
@Controller
@RequestMapping("/cinema")
public class CinemaController {
	@Autowired
	CinemaService cinemaService;
	
	@RequestMapping("add")
	@ResponseBody
	public ServerResponse add(Cinema cinema) {
		cinema.setIsdel(0);
		cinemaService.save(cinema);
		return new ServerResponse("0", "操作成功!");
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(Cinema cinema) {
		cinemaService.updateById(cinema);
		return new ServerResponse("0", "操作成功!");
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(Cinema cinema) {
		cinema.setIsdel(1);
		boolean flag = cinemaService.updateById(cinema);
		if (flag) {
			return new ServerResponse("0", "删除成功!");
		} else {
			return new ServerResponse("1", "删除失败!");
		}
	}

	@RequestMapping("list")
	@ResponseBody
	public ServerResponse list(Integer page, Integer limit,String name) {
		QueryWrapper<Cinema> wrapper = new QueryWrapper<Cinema>();
		if(!StringUtils.isNullOrEmpty(name)){
			wrapper.like("name", name);
		}
		wrapper.eq("isdel", 0);
		IPage<Cinema> page_cinema = new Page<Cinema>(page, limit);
		page_cinema = cinemaService.page(page_cinema, wrapper);
		// 会自动查出总条数
		int count = (int) page_cinema.getTotal();
		// 关联对象
		List<Cinema> list = page_cinema.getRecords();
		return new ServerResponse("0", "", count,list);
	}
	
	@RequestMapping("jsonlist")
	@ResponseBody
	public List<Cinema> jsonlist() {
		QueryWrapper<Cinema> wrapper = new QueryWrapper<Cinema>();
		wrapper.eq("isdel", 0);
		return cinemaService.list(wrapper);
	}

}
