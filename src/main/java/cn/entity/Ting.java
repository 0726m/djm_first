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
@TableName("ting")
public class Ting extends Model<Ting> {

    private static final long serialVersionUID = 1L;

        /**
     * 厅编号
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
     * 名称
     */
         @TableField("name")
    private String name;

        /**
     * 总座位数
     */
         @TableField("seatnums")
    private Integer seatnums;

        /**
     * 删除标记
     */
         @TableField("isdel")
    private Integer isdel;


    public Integer getId() {
        return id;
    }

    public Ting setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCid() {
        return cid;
    }

    public Ting setCid(Integer cid) {
        this.cid = cid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ting setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSeatnums() {
        return seatnums;
    }

    public Ting setSeatnums(Integer seatnums) {
        this.seatnums = seatnums;
        return this;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public Ting setIsdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Ting{" +
        "id=" + id +
        ", cid=" + cid +
        ", name=" + name +
        ", seatnums=" + seatnums +
        ", isdel=" + isdel +
        "}";
    }
}
