package edu.pucmm.usersmicroservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean existsById(int id){return userRepository.existsById(id);}

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public String[] findAllEmailsByRole(String role){return userRepository.findAllEmailsByRole(role);}
}
