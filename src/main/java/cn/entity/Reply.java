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
@TableName("reply")
public class Reply extends Model<Reply> {

    private static final long serialVersionUID = 1L;

        /**
     * 编号
     */
         @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        /**
     * 用户
     */
         @TableField("uid")
    private Integer uid;
         @TableField(exist=false)
         private Users users;
         
        public Users getUsers() {
			return users;
		}

		public void setUsers(Users users) {
			this.users = users;
		}

		/**
     * 1评价2留言
     */
         @TableField("type")
    private Integer type;

        /**
     * 父id
     */
         @TableField("pid")
    private Integer pid;

        /**
     * 内容
     */
         @TableField("content")
    private String content;

        /**
     * 回复时间
     */
         @TableField("optime")
    private String optime;


    public Integer getId() {
        return id;
    }

    public Reply setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUid() {
        return uid;
    }

    public Reply setUid(Integer uid) {
        this.uid = uid;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public Reply setType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getPid() {
        return pid;
    }

    public Reply setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Reply setContent(String content) {
        this.content = content;
        return this;
    }

    public String getOptime() {
        return optime;
    }

    public Reply setOptime(String optime) {
        this.optime = optime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Reply{" +
        "id=" + id +
        ", uid=" + uid +
        ", type=" + type +
        ", pid=" + pid +
        ", content=" + content +
        ", optime=" + optime +
        "}";
    }
}
