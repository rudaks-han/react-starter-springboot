package react.starter.demo.resources;

import org.springframework.web.bind.annotation.*;
import react.starter.demo.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserResource {

    private List<User> userList;

    public UserResource() {
        userList = new ArrayList<>();
    }

    @GetMapping
    public List<User> listUser() {
        return userList;
    }

    @GetMapping("{id}")
    public User retrieveUser(@PathVariable(name = "id") String id) {

        boolean found = false;

        for (User user : userList) {
            if (id.equals(user.getId())) {
                return user;
            }
        }

        if (!found) {
            throw new NoSuchElementException("userId not found [" + id + "]");
        }

        return null;
    }

    @PostMapping("")
    public String createUser(@RequestBody User paramUser) {
        User addUser = new User();

        String id = UUID.randomUUID().toString();
        addUser.setId(id);
        addUser.setUserId(paramUser.getUserId());
        addUser.setName(paramUser.getName());
        addUser.setEmail(paramUser.getEmail());
        addUser.setTel(paramUser.getTel());
        userList.add(addUser);

        return id;
    }

    @PutMapping("{id}")
    public String updateUser(@PathVariable(name = "id") String id, @RequestBody User paramUser) {

        boolean found = false;

        for (User user : userList) {
            if (id.equals(user.getId())) {
                user.setUserId(paramUser.getUserId());
                user.setName(paramUser.getName());
                user.setEmail(paramUser.getEmail());
                user.setTel(paramUser.getTel());

                found = true;
                break;
            }
        }

        if (!found) {
            throw new NoSuchElementException("userId not found [" + id + "]");
        }

        return id;
    }

    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable(name = "id") String id) {
        int i=0;
        boolean found = false;
        for (User user : userList) {
            if (id.equals(user.getId())) {
                userList.remove(i);
                found = true;
                break;
            }
            i++;
        }

        if (!found) {
            throw new NoSuchElementException("userId not found [" + id + "]");
        }

        return id;
    }
}
