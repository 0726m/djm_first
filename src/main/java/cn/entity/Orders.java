package cn.entity;

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
@TableName("orders")
public class Orders extends Model<Orders> {

    private static final long serialVersionUID = 1L;

        /**
     * 订单编号
     */
         @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
         @TableField("ordersno")
         private String ordersno;
         @TableField("status")
         private String status;
         
        public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getOrdersno() {
			return ordersno;
		}

		public void setOrdersno(String ordersno) {
			this.ordersno = ordersno;
		}
		 @TableField("seats")
         private String seats;
		 @TableField(exist=false)
		 private Integer sids[];
		 
		 
		public Integer[] getSids() {
			return sids;
		}

		public void setSids(Integer[] sids) {
			this.sids = sids;
		}

		public String getSeats() {
			return seats;
		}

		public void setSeats(String seats) {
			this.seats = seats;
		}
		/**
     * 用户编号
     */
         @TableField("uid")
    private Integer uid;
         @TableField(exist=false)
         private Users users;
         
        /**
     * 影院编号
     */
         @TableField("cid")
    private Integer cid;
         @TableField(exist=false)
         private Cinema cinema;
        /**
     * 厅编号
     */
         @TableField("tid")
    private Integer tid;
         @TableField(exist=false)
         private Ting ting;
        /**
     * 电影编号
     */
         @TableField("gid")
    private Integer gid;
         @TableField(exist=false)
         private Goods goods;
        /**
     * 排片编号
     */
         @TableField("pid")
    private Integer pid;
         @TableField(exist=false)
         private Paipian paipian;
         
        public Users getUsers() {
			return users;
		}

		public void setUsers(Users users) {
			this.users = users;
		}

		public Cinema getCinema() {
			return cinema;
		}

		public void setCinema(Cinema cinema) {
			this.cinema = cinema;
		}

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

		public Paipian getPaipian() {
			return paipian;
		}

		public void setPaipian(Paipian paipian) {
			this.paipian = paipian;
		}

		/**
     * 数量
     */
         @TableField("nums")
    private Integer nums;

        /**
     * 价格
     */
         @TableField("price")
    private Double price;

        /**
     * 预定时间
     */
         @TableField("optime")
    private String optime;


    public Integer getId() {
        return id;
    }

    public Orders setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUid() {
        return uid;
    }

    public Orders setUid(Integer uid) {
        this.uid = uid;
        return this;
    }

    public Integer getCid() {
        return cid;
    }

    public Orders setCid(Integer cid) {
        this.cid = cid;
        return this;
    }

    public Integer getTid() {
        return tid;
    }

    public Orders setTid(Integer tid) {
        this.tid = tid;
        return this;
    }

    public Integer getGid() {
        return gid;
    }

    public Orders setGid(Integer gid) {
        this.gid = gid;
        return this;
    }

    public Integer getPid() {
        return pid;
    }

    public Orders setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public Integer getNums() {
        return nums;
    }

    public Orders setNums(Integer nums) {
        this.nums = nums;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Orders setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getOptime() {
        return optime;
    }

    public Orders setOptime(String optime) {
        this.optime = optime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Orders{" +
        "id=" + id +
        ", uid=" + uid +
        ", cid=" + cid +
        ", tid=" + tid +
        ", gid=" + gid +
        ", pid=" + pid +
        ", nums=" + nums +
        ", price=" + price +
        ", optime=" + optime +
        "}";
    }
}
