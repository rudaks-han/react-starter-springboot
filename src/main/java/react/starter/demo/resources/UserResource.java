package react.starter.demo.resources;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import react.starter.demo.models.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserResource {
    String filePath = System.getProperty("user.dir") + "/userList.json";
    Gson gson = new Gson();

    private List<User> userList;

    public UserResource() {

        userList = new ArrayList<>();

        readFromFile();
    }

    @GetMapping
    public List<User> listUser(@RequestParam(required = false, defaultValue = "") String condition,
                               @RequestParam(required = false, defaultValue = "") String keyword) {
        if (condition != null && condition.length() > 0 && keyword != null && keyword.length() > 0) {

            List <User> searchUserList = new ArrayList<>();
            for (User user: userList) {;
                if ("userId".equals(condition) && user.getUserId() != null && user.getUserId().indexOf(keyword) > -1)
                    searchUserList.add(user);
                else if ("name".equals(condition) && user.getName() != null && user.getName().indexOf(keyword) > -1)
                    searchUserList.add(user);
                else if ("email".equals(condition) && user.getEmail() != null && user.getEmail().indexOf(keyword) > -1)
                    searchUserList.add(user);
                else if ("tel".equals(condition) && user.getTel() != null && user.getTel().indexOf(keyword) > -1)
                    searchUserList.add(user);
            }

            return searchUserList;
        }

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

    @PostMapping
    public String createUser(@RequestBody User paramUser) {
        User addUser = new User();

        String id = UUID.randomUUID().toString();
        addUser.setId(id);
        addUser.setUserId(paramUser.getUserId());
        addUser.setName(paramUser.getName());
        addUser.setEmail(paramUser.getEmail());
        addUser.setTel(paramUser.getTel());
        userList.add(addUser);

        writeToFile(userList);

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

    private void readFromFile() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            User[] users = new Gson().fromJson(br, User[].class);
            userList = Arrays.asList(users);

            System.out.println("read from path : " + filePath);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(List<User> list) {

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(gson.toJson(list));
            writer.close();

            System.out.println("write to path : " + filePath);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
