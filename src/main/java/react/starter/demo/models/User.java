package react.starter.demo.models;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String id;
    private String userId;
    private String name;
    private String email;
    private String tel;

    public User() {}

    public User(String userId, String name, String email, String tel) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.tel = tel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
