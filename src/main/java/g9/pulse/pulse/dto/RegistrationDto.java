package g9.pulse.pulse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

    public class RegistrationDto {

        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        @NotBlank
        private String confirmPassword;


        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }


        public String getEmail() {

            return email;
        }

        public void setEmail(String email) {

            this.email = email;
        }


        public String getPassword() {

            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
    }

