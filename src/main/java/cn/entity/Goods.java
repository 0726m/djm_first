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
@TableName("goods")
public class Goods extends Model<Goods> {

    private static final long serialVersionUID = 1L;

        /**
     * 电影编号
     */
         @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        /**
     * 名称
     */
         @TableField("name")
    private String name;

        /**
     * 封面
     */
         @TableField("pic")
    private String pic;

         /**
      * 开始日期
      */
          @TableField("startday")
     private String startday;
          @TableField("mp4")
          private String mp4;
          @TableField("bofang")
          private Integer bofang;
         /**
      * 结束日期
      */
          @TableField("endday")
     private String endday;
          
        public String getStartday() {
			return startday;
		}

		public void setStartday(String startday) {
			this.startday = startday;
		}

		public String getEndday() {
			return endday;
		}

		public void setEndday(String endday) {
			this.endday = endday;
		}

		/**
     * 时长
     */
         @TableField("times")
    private Integer times;

        /**
     * 介绍
     */
         @TableField("content")
    private String content;

        /**
     * 删除标记
     */
         @TableField("isdel")
    private Integer isdel;

         @TableField("kid")
         private Integer kid;
         @TableField(exist=false)
         private Kind kind;
         
         
    public Integer getKid() {
			return kid;
		}

		public void setKid(Integer kid) {
			this.kid = kid;
		}

		public Kind getKind() {
			return kind;
		}

		public void setKind(Kind kind) {
			this.kind = kind;
		}

	public Integer getId() {
        return id;
    }

    public Goods setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Goods setName(String name) {
        this.name = name;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public Goods setPic(String pic) {
        this.pic = pic;
        return this;
    }

    public Integer getTimes() {
        return times;
    }

    public Goods setTimes(Integer times) {
        this.times = times;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Goods setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public Goods setIsdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    public String getMp4() {
		return mp4;
	}

	public void setMp4(String mp4) {
		this.mp4 = mp4;
	}

	public Integer getBofang() {
		return bofang;
	}

	public void setBofang(Integer bofang) {
		this.bofang = bofang;
	}

	@Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Goods{" +
        "id=" + id +
        ", name=" + name +
        ", pic=" + pic +
        ", times=" + times +
        ", content=" + content +
        ", isdel=" + isdel +
        "}";
    }
}
