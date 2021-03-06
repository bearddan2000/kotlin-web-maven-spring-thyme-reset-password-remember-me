package example.repository;

import example.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
class UserRepositoryImpl() : UserRepository {

    @Autowired
    private lateinit var inMemoryUserDetailsManager :InMemoryUserDetailsManager;

    @Autowired
    private lateinit var passwordEncoder :PasswordEncoder;

    override fun findUserByName(name :String) :UserDetails {
        return inMemoryUserDetailsManager.loadUserByUsername(name);
    }

    override fun save(user :User) {
      val name :String = user.username;
      val password :String = user.password;

      var grantedAuthoritiesList = listOf<GrantedAuthority>();
      grantedAuthoritiesList += SimpleGrantedAuthority("ROLE_USER");

      inMemoryUserDetailsManager
      .createUser(org.springframework.security.core.userdetails.User(name, passwordEncoder.encode(password), grantedAuthoritiesList));

    }

    override fun resetPassword(username :String, newPassword :String) :User?
    {
      var user :User? = null;
      val userExists :Boolean = inMemoryUserDetailsManager.userExists(username);
      if (userExists){
        user = User()
        user.username = username;
        user.password = newPassword;
        inMemoryUserDetailsManager.deleteUser(username);
      }
      return user;
    }
}
