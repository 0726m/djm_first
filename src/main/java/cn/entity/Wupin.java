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
@TableName("wupin")
public class Wupin extends Model<Wupin> {

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
          @TableField("jifen")
     private Integer jifen;


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
         

	public Integer getId() {
        return id;
    }

    public Wupin setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Wupin setName(String name) {
        this.name = name;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public Wupin setPic(String pic) {
        this.pic = pic;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Wupin setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public Integer getJifen() {
		return jifen;
	}

	public void setJifen(Integer jifen) {
		this.jifen = jifen;
	}

	public Wupin setIsdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }


	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
