package cn.entity;

import cn.util.Const;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author nnn
 * @since 2022-10-12
 */
@TableName("paipian")
public class Paipian extends Model<Paipian> {

    private static final long serialVersionUID = 1L;

        /**
     * 场次编号
     */
         @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        /**
     * 影院编号
     */
         @TableField("cid")
    private Integer cid;
         @TableField(exist=false)
         private Cinema cinema;
         public Cinema getCinema() {
 			return cinema;
 		}

 		public void setCinema(Cinema cinema) {
 			this.cinema = cinema;
 		}
        /**
     * 厅
     */
         @TableField("tid")
    private Integer tid;
         @TableField(exist=false)
         private Ting ting;
         
        /**
     * 电影
     */
         @TableField("gid")
    private Integer gid;
         @TableField(exist=false)
         private Goods goods;
         
        public Ting getTing() {
			return ting;
		}

		public void setTing(Ting ting) {
			this.ting = ting;
		}

		public Goods getGoods() {
			return goods;
		}

		public void setGoods(Goods goods) {
			this.goods = goods;
		}
		/**
     * 普通价格
     */
         @TableField("price")
    private Double price;

        /**
     * 会员价格
     */
         @TableField("yhprice")
    private Double yhprice;

        /**
     * 余票
     */
         @TableField("piaonums")
    private Integer piaonums;


        /**
     * 场次日期
     */
         @TableField("opday")
    private String opday;
         @TableField(exist=false)
         private boolean comptime;
         
        public boolean isComptime() {
        	String now=Const.getTime();
        	if(now.compareTo(getOpday())<0){
        		return true;
        	}
			return false;
		}
		/**
     * 开始时间
     */
         @TableField("starttime")
    private String starttime;

        /**
     * 结束时间
     */
         @TableField("endtime")
    private String endtime;


    public Integer getId() {
        return id;
    }

    public Paipian setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCid() {
        return cid;
    }

    public Paipian setCid(Integer cid) {
        this.cid = cid;
        return this;
    }

    public Integer getTid() {
        return tid;
    }

    public Paipian setTid(Integer tid) {
        this.tid = tid;
        return this;
    }

    public Integer getGid() {
        return gid;
    }

    public Paipian setGid(Integer gid) {
        this.gid = gid;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Paipian setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Double getYhprice() {
        return yhprice;
    }

    public Paipian setYhprice(Double yhprice) {
        this.yhprice = yhprice;
        return this;
    }

    public Integer getPiaonums() {
        return piaonums;
    }

    public Paipian setPiaonums(Integer piaonums) {
        this.piaonums = piaonums;
        return this;
    }

    public String getOpday() {
        return opday;
    }

    public Paipian setOpday(String opday) {
        this.opday = opday;
        return this;
    }

    public String getStarttime() {
        return starttime;
    }

    public Paipian setStarttime(String starttime) {
        this.starttime = starttime;
        return this;
    }

    public String getEndtime() {
        return endtime;
    }

    public Paipian setEndtime(String endtime) {
        this.endtime = endtime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Paipian{" +
        "id=" + id +
        ", cid=" + cid +
        ", tid=" + tid +
        ", gid=" + gid +
        ", price=" + price +
        ", yhprice=" + yhprice +
        ", piaonums=" + piaonums +
        ", opday=" + opday +
        ", starttime=" + starttime +
        ", endtime=" + endtime +
        "}";
    }
}
