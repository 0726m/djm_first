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
 * @since 2022-03-13
 */
@TableName("seat")
public class Seat extends Model<Seat> {

    private static final long serialVersionUID = 1L;

        /**
     * 座位编号
     */
         @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        /**
     * 排班编号
     */
         @TableField("pid")
    private Integer pid;
         @TableField(exist=false)
    private Paipian paiban;
         
        public Paipian getPaiban() {
			return paiban;
		}

		public void setPaiban(Paipian paiban) {
			this.paiban = paiban;
		}

		/**
     * 座位号
     */
         @TableField("name")
    private String name;

        /**
     * 占用状态
     */
         @TableField("status")
    private Integer status;


    public Integer getId() {
        return id;
    }

    public Seat setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPid() {
        return pid;
    }

    public Seat setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Seat setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Seat setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Seat{" +
        "id=" + id +
        ", pid=" + pid +
        ", name=" + name +
        ", status=" + status +
        "}";
    }
}
