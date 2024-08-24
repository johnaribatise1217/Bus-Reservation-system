package com.reservation.bus_reservation.service.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reservation.bus_reservation.Config.JwtService;
import com.reservation.bus_reservation.Exception.ResourceNotFound;
import com.reservation.bus_reservation.models.Auth.AuthenticationRequest;
import com.reservation.bus_reservation.models.Auth.AuthenticationResponse;
import com.reservation.bus_reservation.models.Auth.RegisterRequest;
import com.reservation.bus_reservation.models.Role;
import com.reservation.bus_reservation.models.User;
import com.reservation.bus_reservation.models.wrapper.ModelMapper;
import com.reservation.bus_reservation.models.wrapper.UserResponse;
import com.reservation.bus_reservation.repository.UserRepository;

@Service
public class UserServiceImplementation implements IUserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private PasswordEncoder passwordEncoder;

  //register user method
  @Override
  public String createUser(RegisterRequest registerRequest) {
    Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());

    if (existingUser.isPresent()) {
      return "User email already in use";
    }

    var user = User.builder()
      .firstname(registerRequest.getFirstname())
      .lastname(registerRequest.getLastname())
      .email(registerRequest.getEmail())
      .password(passwordEncoder.encode(registerRequest.getPassword()))
      .role(registerRequest.getRole())
      .build();
    //save user object to database
    userRepository.save(user);
    return "User created successfully";
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()
      )
    );

    //find the user by email after authentication
    var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
      () -> new UsernameNotFoundException("user does not exist")
    );
 
    var jwtToken = jwtService.generateToken(user);

    return AuthenticationResponse.builder()
      .token(jwtToken)
      .user(ModelMapper.mapUserResponse(user))
      .build();
  }

  @Override
  public List<UserResponse> getAllUsers() {
    List<User> allUsers = userRepository.findAll();

    return allUsers.stream().map((user) -> ModelMapper.mapUserResponse(user))
    .collect(Collectors.toList());
  }

  @Override
  public UserResponse getUserDetails(Integer id) {
    User user = userRepository.findById(id).
    orElseThrow(
     () ->  new ResourceNotFound("User", "userId" ,id)
    );

    return ModelMapper.mapUserResponse(user);
  }

  @Override
  public List<UserResponse> getAllUsersByRole(Role role) {
    List<User> allUsersByRole = userRepository.findByRole(role);

    return allUsersByRole.stream().map((user) -> ModelMapper.mapUserResponse(user))
    .collect(Collectors.toList());
  }

  @Override
  public ResponseEntity<String> updatePassword(Integer userId, String oldPassword, String newPassword) {
    Optional<User> existingUser = userRepository.findById(userId);
    if(existingUser.isEmpty()){
      return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
    }

    boolean passwordIsMatch = passwordEncoder.matches(oldPassword, existingUser.get().getPassword());

    if(!passwordIsMatch){
      return new ResponseEntity<>("Old password is incorrect", HttpStatus.BAD_REQUEST);
    }

    if(passwordIsMatch){
      existingUser.get().setPassword(newPassword);
    }

    userRepository.save(existingUser.get());
    return new ResponseEntity<>("user password successfully updated", HttpStatus.OK);
  }
}
