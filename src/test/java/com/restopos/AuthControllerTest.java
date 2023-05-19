package com.restopos;

import com.restopos.controllers.AuthController;
import com.restopos.models.ERole;
import com.restopos.models.Role;
import com.restopos.models.Tables;
import com.restopos.models.User;
import com.restopos.payload.request.SignupRequest;
import com.restopos.payload.response.MessageResponse;
import com.restopos.repository.RoleRepository;
import com.restopos.repository.TableRepository;
import com.restopos.repository.UserRepository;
import com.restopos.security.services.EmailService;
import com.restopos.security.services.TableService;
import org.assertj.core.api.AbstractBigIntegerAssert;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;

import javax.mail.MessagingException;
import java.util.*;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {


    @Mock
    private TableRepository productRepository;
    private Tables product;





    @InjectMocks
    private UnitTestExample unitTestExample;

    @InjectMocks
    private AuthController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private EmailService emailService;



    @Test
    public void testLogin() throws MessagingException {
        // Setup
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser");
        signupRequest.setEmail("testuser@example.com");
        signupRequest.setPassword("testpassword");
        signupRequest.setPhone("1234567890");
        signupRequest.setCountry("India");
        signupRequest.setCity("Delhi");
        signupRequest.setProfile("testprofile");
        signupRequest.setShift("day");
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        signupRequest.setRole(roles);

        User user = new User();
        user.setId(1L);
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword("encoded_password");
        user.setPhone(signupRequest.getPhone());
        user.setCountry(signupRequest.getCountry());
        user.setCity(signupRequest.getCity());
        user.setProfile(signupRequest.getProfile());
        user.setShift(signupRequest.getShift());

        Role role = new Role();
        role.setId(1);
        role.setName(ERole.ROLE_ADMIN);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRoles(userRoles);

        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(encoder.encode(signupRequest.getPassword())).thenReturn("encoded_password");
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(emailService.sendEmail(anyString(), anyString(), anyString())).thenReturn(true);

        // Execute
        ResponseEntity<?> response = userController.registerUser(signupRequest);

        // Verify
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat((HttpStatus) ((MessageResponse) response.getBody()).isEqualTo("User registered successfully!");
        verify(userRepository).existsByUsername(signupRequest.getUsername());
        verify(userRepository).existsByEmail(signupRequest.getEmail());
        verify(encoder).encode(signupRequest.getPassword());
        verify(roleRepository).findByName(ERole.ROLE_ADMIN);
        verify(userRepository).save(any(User.class));
        verify(emailService).sendEmail(anyString(), anyString(), anyString());

    }

    private <SELF extends AbstractBigIntegerAssert<SELF>> AbstractBigIntegerAssert assertThat(HttpStatus statusCode) {
        return null;
    }

    @Test
    public void testAdd() {
        int resut = unitTestExample.doSum(2, 2);
        assertEquals(4, resut);
    }


    @Test
    public void doProductTest() {
        int product = unitTestExample.doProduct(4, 4);
        assertEquals(8,product,"Success");

    }


//    @Test
//    public void testGetAllEmployees() throws Exception {
//        Tables employee =new Tables();
//        List<Tables> employees = new ArrayList<>();
//        employees.add(employee);
//        given(tableService.fetchTables()).willReturn(employees);
//        List<Tables> result = tableService.fetchTables();
//        assertEquals(result.size(), 1);
//    }

    @Test
    public void GivenGetAllProductShouldReturnListOfAllProducts() {
        Tables product1 = new Tables(344, 343, "Lawn", "5", true, "Watson", null, "sssds");
        productRepository.save(product1);
        List<Tables> productList = (List<Tables>) productRepository.findAll();
        assertEquals(1, productList.size());
        assertEquals("Lawn", productList.get(0).getTable_status());
    }
}

