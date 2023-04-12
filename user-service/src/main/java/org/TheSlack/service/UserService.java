package org.TheSlack.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.TheSlack.domain.ERole;
import org.TheSlack.domain.Role;
import org.TheSlack.domain.User;
import org.TheSlack.dto.UserRequest;
import org.TheSlack.dto.UserResponse;
import org.TheSlack.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder encoder;

    public void addUser(UserRequest userRequest){
        if (userExistByEmail(userRequest.getEmail())){log.info("User already exists");}
        else {
        Role role = Role.builder().eRole(ERole.ROLE_USER).build();

        User user = User.builder()
        .firstName(userRequest.getFirstName())
        .lastName(userRequest.getLastName())
        .email(userRequest.getEmail())
        .phoneNumber(userRequest.getPhoneNumber())
        .password(encoder.encode(userRequest.getPassword()))
        .dob(userRequest.getDob())
        .role(role)
        .build();
        userRepo.save(user);
        log.info("User {} is saved", user.getId());
        }
    }
    public void editUser(Integer id, UserRequest userRequest){
        User user = userRepo.findById(id).get();
        if(encoder.matches(userRequest.getPassword(), user.getPassword())) {
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setDob(userRequest.getDob());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            userRepo.save(user);
            log.info("User {} is saved", user.getId());
        }
        else
            log.info("incorrect password editing {}", user.getId());
    }
public void deleteUser(Integer id){
        userRepo.deleteById(id);
}

//public List<UserResponse> findUser(String query){
//        return
//userRepo.searchUsers(query).stream().map(this::mapToUserResponse).toList();
//}

    public boolean userExistByEmail(String email){
        if(userRepo.findAll().stream().filter(item -> item.getEmail().equals(email)).findFirst().isPresent()){return true;}
        else return false;

    }

    @PostConstruct
    public void initializedb(){
        User admin = new User(1, "Amir", "Nabil", "amirnabiloff@icloud.com", "01207597075", "CAIAawad2", Date.from(Instant.now()), new Role(1, ERole.ROLE_ADMIN), new ArrayList<>());
        admin.setPassword(encoder.encode(admin.getPassword()));
        userRepo.save(admin);
    }

    private UserResponse mapToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .dob(user.getDob())
                .role(user.getRole())
                .permissions(user.getPermissions())
                .build();
    }



}
