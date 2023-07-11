package cn.util;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Const {
	public static String ROOT = "/films/";
	public static String ROOT_ADMIN = "/films/admin";
	public static Integer PAGESIZE = 6;
	public static String[] GRADES={"普通用户","青铜会员","白银会员","黄金会员","铂金会员","钻石会员","至尊会员"};
	public static Integer[] SCORES={0,500,2000,5000,10000,20000,50000};
	
	public static String getFullTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	public static String getNowTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date());
	}
	public static String getIds() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	public static Double format(Double nums){
		int n=(int)(Math.round(nums*100));//四舍五入
		return n/100.0;
	}
	//返回等级
	public static String getGrade(Integer score){
		int index=6;
		for(int i=0;i<SCORES.length;i++){
			if(score<SCORES[i]){
				index=i-1;
				break;
			}
		}
		return GRADES[index];
	}
	//返回折扣
	public static int getZhekou(Integer score){
		int index=6;
		for(int i=0;i<SCORES.length;i++){
			if(score<SCORES[i]){
				index=i-1;
				break;
			}
		}
		return 100-(index)*5;
	}
	
	public static void main(String[] args) {
		System.out.println(getGrade(15000));
		System.out.println(getZhekou(200));
	}
	
}
