package com.restopos.controllers;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.restopos.models.*;
import com.restopos.payload.request.LoginRequest;
import com.restopos.payload.request.SignupRequest;
import com.restopos.payload.response.JwtResponse;
import com.restopos.payload.response.MessageResponse;
import com.restopos.repository.*;
import com.restopos.security.jwt.JwtUtils;
import com.restopos.security.services.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ForgotpassService forgotpassService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    GuestInfoRepository guestInfoRepository;
    @Autowired
    private TempDatarepository tempDatarepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TempdataService tempdataService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TableService tableService;

    @Autowired
    private GuestInfoService guestInfoService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private FoodService foodService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TableRepository tableRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles, userDetails.getCity(), userDetails.getCountry(), userDetails.getPhone()));
        } catch (Exception ex) {
            HashMap hm = new HashMap();
            hm.put("Message", new String("Username and Password Incorrect"));
            hm.put("Status", new String("false"));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(hm);
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException {


        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),

                encoder.encode(signUpRequest.getPassword()), signUpRequest.getPhone(), signUpRequest.getCountry(),
                signUpRequest.getCity(), signUpRequest.getProfile(), signUpRequest.getShift(), signUpRequest.getToken());


        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role ROLE_USER = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(ROLE_USER);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role ROLE_ADMIN = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(ROLE_ADMIN);

                        break;

                    case "Cashier":
                        Role Cashier = roleRepository.findByName(ERole.Cashier)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(Cashier);

                        break;

                    case "Receptionist":
                        Role Receptionist = roleRepository.findByName(ERole.Receptionist)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(Receptionist);

                        break;

                    case "Waiter":
                        Role Waiter = roleRepository.findByName(ERole.Waiter)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(Waiter);

                        break;

                    case "Carpet_Sweeper":
                        Role Carpet_Sweeper = roleRepository.findByName(ERole.Carpet_Sweeper)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(Carpet_Sweeper);

                        break;

                    case "Table_Sweeper":
                        Role Table_Sweeper = roleRepository.findByName(ERole.Table_Sweeper)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(Table_Sweeper);

                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
//        user.set("Anurag@12344");
        UUID uuid = UUID.randomUUID();
        System.out.println("UUID=" + uuid.toString());
        user.setToken(String.valueOf(uuid));
        userRepository.save(user);

        String subject = "Restro POS Password Generation";
        boolean result = this.emailService.sendEmail(subject, signUpRequest.getEmail(), user.getToken());
        if (result) {
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something is went wrong");
        }

//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }


    //Add tavble API
    @PostMapping("/addTable")

//    @PreAuthorize("hasAuthority('ADMIN')")
    public Tables createTable(@RequestBody Tables payload, @RequestHeader HttpHeaders headers, HttpServletRequest request) {
        final String authorizationHeaderValue = request.getHeader("Authorization");
        String token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
        // String[] parts = token.[1];
        System.out.println("hello" + token);
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload5 = new String(decoder.decode(chunks[1]));
        JSONObject NewJs = new JSONObject(payload5);
        int UID = NewJs.getInt("Id");
        System.out.println("Table Data" + payload);
        Date now = new Date();
        System.out.println(now);
        payload.setCreatedBy(Integer.toString(UID));
        payload.setCreated_date(now);
        payload.setTable_status(false);
        return ResponseEntity.ok(tableService.createTable(payload)).getBody();
    }


    //Show tabls Api;
    @GetMapping("/showtables")
    public List<Tables> fetchTables() {
        List<Tables> tables = tableService.fetchTables();
        System.out.println(tables);
        return tables;
    }


    //       Add Food Api;
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addfood")
    public Food addFood(@RequestBody Food payload, @RequestHeader HttpHeaders headers, HttpServletRequest request) {

        final String authorizationHeaderValue = request.getHeader("Authorization");
        String token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
        // String[] parts = token.[1];
        System.out.println("hello" + token);
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload5 = new String(decoder.decode(chunks[1]));
        JSONObject NewJs = new JSONObject(payload5);
        System.out.println("Food data" + payload);
        Date now = new Date();
        System.out.println(now);
        return ResponseEntity.ok(foodService.addFood(payload)).getBody();
    }


    //    Show tables API fetchFoods
//Show tabls Api;
    @GetMapping("/showfoods")
    public List<Food> fetchFoods() {
        List<Food> foods = foodService.fetchFoods();
        System.out.println(foods);
        return foods;
    }

//    GetRoles API

    @GetMapping("/GetRole")
    public List<Role> getrole() {
        List<Role> roles = roleService.getrole();
        return roles;
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }


    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }


    ////    API for get details using TableId
    @GetMapping("/table/{id}")
    public Optional<Tables> findById(@PathVariable(value = "id") Integer id) {
        Optional<Tables> tables = tableService.findById(id);
        return (tables);
    }


//    API for get details of table by table Area Terrace

    @GetMapping("/terracedtls")
    public String terraceDtls() throws Exception {
        try {
            return tableService.terraceDtls();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No data Found");
        }
    }

    //    API for get details of table by table Area  LAwn

    @GetMapping("/lawndtls")
    public String lawnDtls() throws Exception {
        try {
            return tableService.lawnDtls();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No data Found");
        }
    }


    //    API for get details of table by table Area  Garden

    @GetMapping("/gardendtls")
    public String gardenDtls() throws Exception {
        try {
            return tableService.gardenDtls();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No data Found");
        }
    }

    //    API for get details of table by table occupied  EmptyDTLS

    @GetMapping("/occupied")
    public String occupieDTLS() throws Exception {
        try {
            return tableService.occupieDTLS();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No data Found");
        }
    }

    //    API for get details of table by table empty  EmptyDTLS

    @GetMapping("/empty")
    public String EmptyDTLS() throws Exception {
        try {
            return tableService.EmptyDTLS();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No data Found");
        }
    }

    @GetMapping("/all")
    public String alltables() throws Exception {
        try {
            return tableService.alltables();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No data Found");
        }
    }


//    APi For update password


    @PutMapping("/pass/{token}")
    public ResponseEntity<User> updateTutorial(@PathVariable("token") String token, @RequestBody User user) {
        Optional<User> userdata = userRepository.findByToken(token);
        if (userdata.isPresent()) {
            User user1 = userdata.get();
            user1.setPassword(encoder.encode(user.getPassword()));
            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cheaktoken/{token}")
    public ResponseEntity<?> CheakToken(@PathVariable("token") String token) {

//        Boolean user = this.userRepository.existsBytoken(token);
        boolean result = this.userRepository.existsBytoken(token);
        if (result) {
            HashMap hm = new HashMap();
            hm.put("Status", new String("true"));
            hm.put("message", new String("Token is verified"));
            hm.put("Status Code", new String("200"));
            return ResponseEntity.ok(hm);
        } else {
            HashMap hm = new HashMap();
            hm.put("Status", new String("false"));
            hm.put("message", new String("Token unauthorized"));
            hm.put("Status Code", new String("401"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(hm);
        }
    }


    //    Create API For Get All Users
    @GetMapping("/getuser")
    public List<User> getallUser(User user) {
        List<User> user1 = this.userRepository.findAll();
        if (user1.isEmpty()) {
            return (List<User>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something is went wrong");
        } else {
            HashMap hm = new HashMap();
            hm.put("Status", new String("true"));
            hm.put("message", new String("Token is verified"));
            return ResponseEntity.ok(user1).getBody();
        }
    }

    //Api To get details of food using Food ID
    @GetMapping("/food/{food_id}")
    public Optional<Food> findByfId(@PathVariable(value = "food_id") Integer food_id) {
        Optional<Food> foods = foodService.findByfId(food_id);
        return (foods);
    }

    //    APi to Save Guest_Info
    @PostMapping("/Saveguest")
    public ResponseEntity<Map<String, Integer>> saveGuest(@RequestBody GuestInfo guestInfo, OrderTable
            orderTable) {
        GuestInfo guestInfo1 = this.guestInfoService.saveGuest(guestInfo);
        Optional<Tables> tabledata = tableRepository.findById(guestInfo.getTable_id());

        if (tabledata.isPresent()) {
            Tables tables = tabledata.get();
            tables.setTable_status(true);
            tables.setWaiter_name(guestInfo.getWaiter_name());
            /*Set data in orderTable*/
            OrderTable orderTable1 = this.orderService.createOrder(orderTable);
            orderTable1.setTable_id(guestInfo.getTable_id());
            orderTable1.setWiater_name(guestInfo.getWaiter_name());
            orderTable1.setTable_no(guestInfo.getTable_num());
            int orderid = orderTable1.getOrder_id();
            System.out.println(orderid);
            Date now = new Date();
            orderTable1.setCreated_date(now);
            HashMap<String, Integer> map = new HashMap<>();
            map.put("OrderId", orderid);
            map.put("id", tables.getId());
            tableRepository.save(tables);
            return ResponseEntity.ok(map);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

////Update Waiter name in table booking
//@PutMapping("/waitername/{id}")
//public ResponseEntity<Tables> updateTutorial(@PathVariable("id") Integer id, @RequestBody Tables tables1) {
//    Optional<Tables> tabledata = tableRepository.findById(id);
//
//    if (tabledata.isPresent()) {
//        Tables tables = tabledata.get();
//        tables.setWaiter_name(tables1.getWaiter_name());
//        return new ResponseEntity<>(tableRepository.save(tables), HttpStatus.OK);
//    } else {
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//}


////    APi To send Mail for Forgot Password
//    @PostMapping("/forgotpass")
//    public ResponseEntity<?> Forgotpass(@RequestBody User user) throws MessagingException {
//        String subject = "Restro POS Password Generation";
//        boolean result = this.forgotpassService.Forgotpass(subject,user.getEmail(),user.getToken());
//        if (result) {
//            return ResponseEntity.ok("Email is sent SuccessFully");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email Not Send");
//        }
//    }

    //Api to cheak email
    @PostMapping("/cheakmail/{email}")
    public ResponseEntity<?> Cheakemail(@PathVariable("email") String email) throws MessagingException {

//        Boolean user = this.userRepository.existsBytoken(token);
        boolean result = this.userRepository.existsByEmail(email);
        if (result) {
            HashMap hm = new HashMap();
            hm.put("Status", new String("true"));
            hm.put("message", new String("Password Reset Link sent SuccessFully"));
            hm.put("Status Code", new String("200"));
//
            String subject = "Restro POS Password Generation";
            boolean resultt = this.forgotpassService.Forgotpass(subject, email);
            if (result) {
                return ResponseEntity.ok(hm);

            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email Not Send");
            }
        } else {
            HashMap hm = new HashMap();
            hm.put("Status", new String("false"));
            hm.put("message", new String("Email address not found"));
            hm.put("Status Code", new String("401"));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(hm);
        }
    }

    @PutMapping("/forgot/{email}")
    public ResponseEntity<User> forgotPass(@PathVariable("email") String email, @RequestBody User user) {
        Optional<User> userdata = userRepository.findByEmail(email);
        if (userdata.isPresent()) {
            User user1 = userdata.get();
            user1.setPassword(encoder.encode(user.getPassword()));
            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //    Create API For Get All Users using ID
    @GetMapping("/getuser/{id}")
    public Optional<User> finfuser(@PathVariable(value = "id") Long id, User user) {
        Optional<User> user1 = userRepository.findById(id);
        return ResponseEntity.ok(user1).getBody();
    }


    //    Update User using Id
    @PutMapping("/updateuser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        Optional<User> userdata = userRepository.findById(id);

        if (userdata.isPresent()) {
            User user1 = userdata.get();
            user1.setShift(user.getShift());
            user1.setCountry(user.getCountry());
            user1.setCity(user.getCity());
            user1.setPhone(user.getPhone());
            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //Add Tempdata
    @PostMapping("/adddata")
    public Temptable savetempData(@RequestBody Temptable payload) {
        System.out.println("Table Data" + payload);
        Date now = new Date();
        System.out.println(now);
        tempdataService.savetempData(payload);
        Temptable temptable = this.tempDatarepository.lastrow();

        return ResponseEntity.ok(temptable).getBody();
    }


    //    APi To get Details OF orderTable
    @GetMapping("/getfdata/{t_teble_id}")
    public List<Temptable> getbyTid(@PathVariable("t_teble_id") Integer t_teble_id) {
        return this.tempDatarepository.findAllByT_teble_id(t_teble_id);
    }


    //API For Update Food
    @PutMapping("/updatef/{temp_id}")
    public ResponseEntity<Temptable> updateTutorial(@PathVariable("temp_id") Integer temp_id, @RequestBody Temptable temptable) {
        Optional<Temptable> tutorialData = tempDatarepository.findById(temp_id);

        if (tutorialData.isPresent()) {
            Temptable _tutorial = tutorialData.get();
            _tutorial.setT_qty(temptable.getT_qty());
            return new ResponseEntity<>(tempDatarepository.save(_tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//set null guest info

    @PutMapping("/utable/{id}")
    public ResponseEntity<Tables> updatetable(@PathVariable("id") Integer id) {
        Optional<Tables> table = tableRepository.findById(id);

        if (table.isPresent()) {
            Tables settble = table.get();
            settble.setWaiter_name(null);
            settble.setTable_status(false);
            return new ResponseEntity<>(tableRepository.save(settble), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //    Update Payment in food
    @PutMapping("/updatepayment/{order_id}")
    public ResponseEntity<OrderTable> updatepayment(@PathVariable("order_id") Integer id, @RequestBody OrderTable orderTable) {
        Optional<OrderTable> orderdata = orderRepository.findById(id);

        if (orderdata.isPresent()) {
            OrderTable _tutorial = orderdata.get();
            _tutorial.setTotal_payment(orderTable.getTotal_payment());
            return new ResponseEntity<>(orderRepository.save(_tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/history")
    public List<OrderTable> gethistory(OrderTable orderTable) {
        List<OrderTable> orderTables = this.orderRepository.findAll();
        if (orderTables.isEmpty()) {
            return (List<OrderTable>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something is went wrong");
        } else {
            HashMap hm = new HashMap();
            hm.put("Status", new String("true"));
            hm.put("message", new String("Token is verified"));
            return ResponseEntity.ok(orderTables).getBody();
        }
    }


    @GetMapping("/bytableno/{table_no}")
    public List<OrderTable> gethistory(OrderTable orderTable, @PathVariable("table_no") Integer id) {
        List<OrderTable> orderTables = this.orderRepository.getbytableno(id);
        if (orderTables.isEmpty()) {
            return (List<OrderTable>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something is went wrong");
        } else {
            HashMap hm = new HashMap();
            hm.put("Status", new String("true"));
            hm.put("message", new String("Token is verified"));
            return ResponseEntity.ok(orderTables).getBody();
        }
    }


    @DeleteMapping("Deletetdata/{order_id}")
    public Integer deletetdata(@PathVariable("order_id") Integer order_id) {
        return tempDatarepository.deletedata(order_id);
    }
}








