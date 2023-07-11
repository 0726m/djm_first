package cn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author nnn
 */
@TableName("users")
public class Users extends Model<Users> {
	//记录我的ID
	 	@TableField(exist = false)
	 	private List<Integer> list = new ArrayList<Integer>();
	 	@TableField(exist = false)
	 	private double seem;

	 	public List<Integer> getList() {
	 		return list;
	 	}

	 	public void setList(List<Integer> list) {
	 		this.list = list;
	 	}
	 	
	        public double getSeem() {
			return seem;
		}

		public void setSeem(double seem) {
			this.seem = seem;
		}

    private static final long serialVersionUID = 1L;

        /**
     * 编号
     */
         @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        /**
     * 用户名
     */
         @TableField("username")
    private String username;

        /**
     * 密码
     */
         @TableField("password")
    private String password;

        /**
     * 姓名
     */
         @TableField("name")
    private String name;

        /**
     * 电话
     */
         @TableField("phone")
    private String phone;


        /**
     * 等级
     */
         @TableField("grade")
    private String grade;

        /**
     * 积分
     */
         @TableField("score")
    private Integer score;
         
         @TableField("jifen")
         private Integer jifen;
         
         @TableField("zhekou")
         private Integer zhekou;

        public Integer getZhekou() {
			return zhekou;
		}

		public void setZhekou(Integer zhekou) {
			this.zhekou = zhekou;
		}

		/**
     * 身份
     */
         @TableField("role")
    private Integer role;

        /**
     * 删除标记
     */
         @TableField("isdel")
    private Integer isdel;


    public Integer getId() {
        return id;
    }

    public Users setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Users setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Integer getJifen() {
		return jifen;
	}

	public void setJifen(Integer jifen) {
		this.jifen = jifen;
	}

	public Users setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public Users setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Users setPhone(String phone) {
        this.phone = phone;
        return this;
    }


    public String getGrade() {
        return grade;
    }

    public Users setGrade(String grade) {
        this.grade = grade;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public Users setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Integer getRole() {
        return role;
    }

    public Users setRole(Integer role) {
        this.role = role;
        return this;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public Users setIsdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Users{" +
        "id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", name=" + name +
        ", phone=" + phone +
        ", grade=" + grade +
        ", score=" + score +
        ", role=" + role +
        ", isdel=" + isdel +
        "}";
    }
}
