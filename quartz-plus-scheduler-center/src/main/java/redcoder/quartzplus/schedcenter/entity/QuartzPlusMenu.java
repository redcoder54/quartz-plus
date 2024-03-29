package redcoder.quartzplus.schedcenter.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "quartz_plus_menu")
@Entity(name = "QuartzPlusMenu")
public class QuartzPlusMenu implements Serializable {

    /**
     * 角色id
     */
    @Id
    @Column(name = "`menu_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuId;

    /**
     * 菜单编码
     */
    @Column(name = "menu_code")
    private String menuCode;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 菜单类型，C: 目录，M：菜单，A：操作
     */
    @Column(name = "menu_type")
    private String menuType;

    /**
     * 父菜单id，0：表示无父菜单，即一级菜单
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 菜单状态，0-隐藏，1-显示
     */
    @Column(name = "menu_status")
    private Integer menuStatus;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;
}
