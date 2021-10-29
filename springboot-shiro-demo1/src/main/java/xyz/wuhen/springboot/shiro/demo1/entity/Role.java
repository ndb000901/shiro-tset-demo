package xyz.wuhen.springboot.shiro.demo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Role {
    private String id;
    private String name;
    //定义权限集合
    private List<Perms> perms;
}
