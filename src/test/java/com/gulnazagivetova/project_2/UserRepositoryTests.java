package com.gulnazagivetova.project_2;

import static org.assertj.core.api.Assertions.assertThat;

import com.gulnazagivetova.project_2.dao.RoleRepository;
import com.gulnazagivetova.project_2.dao.UserRepository;
import com.gulnazagivetova.project_2.entity.Role;
import com.gulnazagivetova.project_2.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("alex2@gmail.com");
        user.setPassword("alex22021");
        user.setName("Alexander");
        user.setSurname("Hebb");

        User saveUser = userRepository.save(user);

        User existUser = entityManager.find(User.class, saveUser.getId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindUserByEmail() {
        String email = "alex@gmail.com";

        User user = userRepository.findByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testAddRoleToNewUser() {
        User user = new User();
        user.setEmail("agivetovag@gmail.com");
        user.setPassword("agivetova");
        user.setName("Zaur");
        user.setSurname("Tregulov");

        Role roleUser = roleRepository.findByName("USER");
        user.addRole(roleUser);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(1);

    }

    @Test
    public void testAddRolesToExistingUser() {
        User user = userRepository.findById(2L).get();

        Role roleUser = roleRepository.findByName("USER");
        user.addRole(roleUser);

        Role roleAdmin = new Role(2L);
        user.addRole(roleAdmin);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getRoles().size()).isEqualTo(2);
    }

}
