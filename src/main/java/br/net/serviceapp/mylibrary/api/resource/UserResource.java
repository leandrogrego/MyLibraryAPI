package br.net.serviceapp.mylibrary.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.net.serviceapp.mylibrary.api.Service.UserService;
import br.net.serviceapp.mylibrary.api.entity.User;
import br.net.serviceapp.mylibrary.tools.WebTools;

@RestController
@RequestMapping("users")
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping
    public void save(@RequestBody User user){
        userService.save(user);
    }

    @PutMapping
    public void update(@RequestBody User user){
        userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        userService.delete(id);
    }

    @GetMapping("one/{id}")
    public User getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.listAll();
    }

    @GetMapping
    public User getByToken(@RequestHeader(value="Authorization") String token){
        return userService.getByToken(token);
    }

    @GetMapping("/login/google")
	public User loginGoogle(
			@RequestHeader(value="Authorization") String token
			) {
        User user = null;        
		System.out.println(token);
		if(!token.equals(null) && !token.equals("")) {
            try {
                Object profile = WebTools.get("https://www.googleapis.com/oauth2/v3/userinfo?access_token="+token, null);
                if(profile != null) {
                    String socialId = profile.getClass().getField("sub").toString();
                    String name = profile.getClass().getField("name").toString();
                    String email = profile.getClass().getField("meail").toString();
                    String avatar_url = profile.getClass().getField("picture").toString();
                    user = userService.socialLogin(socialId, "google", token, name, email, avatar_url);
                } 
            }catch (NoSuchFieldException | SecurityException e) {
                    e.printStackTrace();
			}
		}
		return user;
	}

}
