package com.oflix.OFlix_back.login.service;

import com.oflix.OFlix_back.login.dto.JoinDTO;
import com.oflix.OFlix_back.login.entity.UserEntity;
import com.oflix.OFlix_back.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean signUpProcess(JoinDTO joinDTO) {


        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String nickname = joinDTO.getNickname();
        String phone_Number = joinDTO.getPhoneNumber();
        LocalDate birthDate = joinDTO.getBirthDate();


        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            return false;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setNickname(nickname);
        data.setPhoneNumber(phone_Number);
        data.setRole("ROLE_ADMIN");
        data.setBirthDate(birthDate);

        userRepository.save(data);
        return true;
    }

    public void updateLocalUser () {

    }

    public void deleteUser() {

    }
}
