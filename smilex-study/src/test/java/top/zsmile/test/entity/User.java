package top.zsmile.test.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String name;
    private Integer age;
    private List<String> roles;

    public static User of(String name, Integer age, List<String> roles) {
        User user = new User();
        user.setAge(age);
        user.setName(name);
        user.setRoles(roles);
        return user;
    }
}
