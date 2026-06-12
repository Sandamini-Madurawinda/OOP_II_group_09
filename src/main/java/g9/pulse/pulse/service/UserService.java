package g9.pulse.pulse.service;

import g9.pulse.pulse.dto.RegistrationDto;
import  g9.pulse.pulse.model.User;
import g9.pulse.pulse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

    @Service
    public class UserService {

        @Autowired
        private UserRepository repository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        public void register(RegistrationDto dto){

            if(repository.existsByEmail(dto.getEmail()))
            {
                throw new RuntimeException("Email already exists");
            }


            User user = new User();


            user.setFirstName(dto.getFirstName());

            user.setLastName(dto.getLastName());

            user.setEmail(dto.getEmail());


            user.setPassword(
                    passwordEncoder.encode(dto.getPassword())
            );


            repository.save(user);

        }

    }

