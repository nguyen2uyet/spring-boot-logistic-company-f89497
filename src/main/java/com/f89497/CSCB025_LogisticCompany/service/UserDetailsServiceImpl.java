package com.f89497.CSCB025_LogisticCompany.service;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.f89497.CSCB025_LogisticCompany.dto.UserDTO;
import com.f89497.CSCB025_LogisticCompany.entity.Customer;
import com.f89497.CSCB025_LogisticCompany.entity.Role;
import com.f89497.CSCB025_LogisticCompany.entity.User;
import com.f89497.CSCB025_LogisticCompany.exception.UserAlreadyExistException;
import com.f89497.CSCB025_LogisticCompany.repository.CustomerRepository;
import com.f89497.CSCB025_LogisticCompany.repository.RoleRepository;
import com.f89497.CSCB025_LogisticCompany.repository.UserRepository;
import com.f89497.CSCB025_LogisticCompany.security.MyUserDetails;
import com.f89497.CSCB025_LogisticCompany.unity.Unity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
         
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new MyUserDetails(user);
    }

    public void register(UserDTO userDTO) throws UserAlreadyExistException {

        //Let's check if user already registered with us
        if(checkIfUserExist(userDTO.getUsername())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        User user = new User();
        ModelMapper mapper  = new ModelMapper();
        user = mapper.map(userDTO,User.class);
        encodePassword(user, userDTO);
        user.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findOneByName("CUSTOMER"));
        user.setRoles(roles);
        customerRepository.save(Unity.mapUserDtoToCustomer(userDTO));
        userRepository.save(user);
    }

    
    public boolean checkIfUserExist(String username) {
        return userRepository.findByUsername(username) !=null ? true : false;
    }

    private void encodePassword( User userEntity, UserDTO user){
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    }

 
}
