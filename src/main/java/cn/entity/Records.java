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
@TableName("records")
public class Records extends Model<Records> {

    private static final long serialVersionUID=1L;

    /**
     * 兑换记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 物品编号
     */
    @TableField("wid")
    private Integer wid;
    @TableField(exist=false)
    private Wupin wupin;
    @TableField(exist=false)
    private Users users;
    
    public Wupin getWupin() {
		return wupin;
	}

	public void setWupin(Wupin wupin) {
		this.wupin = wupin;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	/**
     * 用户编号
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 时间
     */
    @TableField("optime")
    private String optime;


    public Integer getId() {
        return id;
    }

    public Records setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getWid() {
        return wid;
    }

    public Records setWid(Integer wid) {
        this.wid = wid;
        return this;
    }

    public Integer getUid() {
        return uid;
    }

    public Records setUid(Integer uid) {
        this.uid = uid;
        return this;
    }

    public String getOptime() {
        return optime;
    }

    public Records setOptime(String optime) {
        this.optime = optime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Records{" +
        "id=" + id +
        ", wid=" + wid +
        ", uid=" + uid +
        ", optime=" + optime +
        "}";
    }
}
