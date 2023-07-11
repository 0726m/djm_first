package cn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author nnn
 * @since 2022-10-12
 */
@TableName("cinema")
public class Cinema extends Model<Cinema> {

    private static final long serialVersionUID = 1L;

        /**
     * 编号
     */
         @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        /**
     * 类型名
     */
         @TableField("name")
    private String name;

        /**
     * 类型
     */
         @TableField("type")
    private String type;

        /**
     * 地址
     */
         @TableField("address")
    private String address;

        /**
     * 删除标记
     */
         @TableField("isdel")
    private Integer isdel;
         
         @TableField(exist=false)
         private List<Ting> tings;
         


    public List<Ting> getTings() {
			return tings;
		}

		public void setTings(List<Ting> tings) {
			this.tings = tings;
		}

	public Integer getId() {
        return id;
    }

    public Cinema setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Cinema setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Cinema setType(String type) {
        this.type = type;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Cinema setAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public Cinema setIsdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Cinema{" +
        "id=" + id +
        ", name=" + name +
        ", type=" + type +
        ", address=" + address +
        ", isdel=" + isdel +
        "}";
    }
}
