package com.tyx.security.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Create By C  2019-09-08 22:18
 */
@Getter
@Setter
@Table(name = "menu")
@Entity
public class Menu implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String url;

    private String menuName;

    private String menuIcon;

    private Integer parentId;

    private Integer order;  // 菜单排序
    @Transient
    private List<Menu> children;

    @ManyToMany(mappedBy = "menuSet")
    private Set<Role> roleSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return id == menu.id &&
                parentId == menu.parentId &&
                Objects.equals(url, menu.url) &&
                Objects.equals(menuName, menu.menuName) &&
                Objects.equals(menuIcon, menu.menuIcon) &&
                Objects.equals(children, menu.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, menuName, menuIcon, parentId, children);
    }

    @Override
    public int compareTo(Object o) {
        if(((Menu)o).getMenuName().equals(this.getMenuName())){
            return 0;
        }
        return -1;
    }
}
