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
        public User getByEmail(String email) {

            return repository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        public void updateProfile(String email,
                                  String firstName,
                                  String lastName) {

            User user = getByEmail(email);

            user.setFirstName(firstName);
            user.setLastName(lastName);

            repository.save(user);
        }

    }

