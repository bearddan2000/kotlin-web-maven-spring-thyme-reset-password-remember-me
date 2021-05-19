package example.service;

import example.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
interface UserService {
  fun save(user :User);
  fun resetPassword(username :String, newPassword :String) :User?;
  fun findByUsername(username :String) :UserDetails;
}
