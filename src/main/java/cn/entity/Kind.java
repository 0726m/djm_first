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
@TableName("kind")
public class Kind extends Model<Kind> {

    private static final long serialVersionUID=1L;

    /**
     * 资讯编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @TableField("name")
    private String name;

    /**
     * 删除标记
     */
    @TableField("isdel")
    private Integer isdel;


    public Integer getId() {
        return id;
    }

    public Kind setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Kind setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public Kind setIsdel(Integer isdel) {
        this.isdel = isdel;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Kind{" +
        "id=" + id +
        ", name=" + name +
        ", isdel=" + isdel +
        "}";
    }
}
