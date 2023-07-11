package cn.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * <p>
 * 
 * </p>
 *
 * @author nnn
 * @since 2022-10-12
 */
@TableName("message")
public class Message extends Model<Message> {

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
		@TableField(exist=false)
        private List<Reply> replys;
        
       public List<Reply> getReplys() {
			return replys;
		}

		public void setReplys(List<Reply> replys) {
			this.replys = replys;
		}

		/**
     * 评价对象
     */
         @TableField("gid")
    private Integer gid;
         @TableField(exist=false)
         private Goods goods;
         
         
        public Goods getGoods() {
			return goods;
		}

		public void setGoods(Goods goods) {
			this.goods = goods;
		}

    @TableField("score")
    private Integer score;

        /**
     * 内容
     */
         @TableField("content")
    private String content;

        /**
     * 留言时间
     */
         @TableField("optime")
    private String optime;

        /**
     * 删除标记
     */
         @TableField("isdel")
    private Integer isdel;


    public Integer getId() {
        return id;
    }

    public Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUid() {
        return uid;
    }

    public Message setUid(Integer uid) {
        this.uid = uid;
        return this;
    }

    public Integer getGid() {
        return gid;
    }

    public Message setGid(Integer gid) {
        this.gid = gid;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public Message setScore(Integer score) {
        this.score = score;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public String getOptime() {
        return optime;
    }

    public Message setOptime(String optime) {
        this.optime = optime;
        return this;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public Message setIsdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Message{" +
        "id=" + id +
        ", uid=" + uid +
        ", gid=" + gid +
        ", score=" + score +
        ", content=" + content +
        ", optime=" + optime +
        ", isdel=" + isdel +
        "}";
    }
}
