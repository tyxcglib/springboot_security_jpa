package com.tyx.security.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "role")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Role  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roleName;

    private String name;

    /**
     *  角色和菜单一对多
     */
    @ManyToMany(fetch =FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "role_Id"),
    inverseJoinColumns = @JoinColumn(name = "menu_Id"))
    @JsonIgnoreProperties(value = "roleSet")
    private Set<Menu> menuSet;


    @ManyToMany(mappedBy = "roles")
    private Set<User> userSet;

}
