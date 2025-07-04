# Complete Spring Boot Cheat Sheet

## Core Concepts

### IoC (Inversion of Control)
Instead of creating objects manually with `new`, Spring creates and manages objects for you. This removes the dependency creation burden from your code.

**Traditional approach:**
```java
public class OrderService {
    private EmailService emailService = new EmailService(); // Manual creation
}
```

**Spring approach:**
```java
@Service
public class OrderService {
    @Autowired
    private EmailService emailService; // Spring injects it
}
```

### DI (Dependency Injection)
Spring automatically provides (injects) the dependencies your classes need. No more manual object creation and wiring.

### Bean
Any object that Spring creates, configures, and manages. Think of it as a "Spring-managed object."

### ApplicationContext
The Spring container that holds all your beans. It's like a factory that creates and manages all your objects.

### Component Scan
Spring automatically scans your packages to find classes annotated with `@Component`, `@Service`, etc., and registers them as beans.

---

## Bean Annotations

### @Component
Marks a class as a Spring-managed bean. Generic annotation for any component.

```java
@Component
public class FileProcessor {
    public void processFile(String filename) {
        // Processing logic
    }
}
```

### @Service
Semantically same as `@Component`, but specifically for business logic layer.

```java
@Service
public class UserService {
    public User findUserById(Long id) {
        // Business logic
        return new User();
    }
}
```

### @Repository
For data access layer. Adds automatic exception translation for database errors.

```java
@Repository
public class UserRepository {
    public User findById(Long id) {
        // Database access logic
        return new User();
    }
}
```

### @Controller
For MVC controllers that return HTML views.

```java
@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Welcome!");
        return "home"; // Returns view name
    }
}
```

### @RestController
Combines `@Controller` + `@ResponseBody`. Returns JSON/XML data instead of views.

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return new User(id, "John Doe"); // Returns JSON
    }
}
```

### @Bean
Manual bean declaration inside a `@Configuration` class.

```java
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource();
    }
}
```

### @Configuration
Indicates that a class declares one or more `@Bean` methods.

```java
@Configuration
public class DatabaseConfig {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

### @ComponentScan
Tells Spring where to look for components.

```java
@ComponentScan(basePackages = {"com.example.service", "com.example.repository"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### @Scope
Controls bean lifecycle and creation strategy.

```java
@Service
@Scope("singleton") // Default - one instance per application
public class SingletonService { }

@Service
@Scope("prototype") // New instance every time it's requested
public class PrototypeService { }
```

---

## Dependency Injection Annotations

### @Autowired
Injects a dependency automatically. Can be used on fields, constructors, or methods.

```java
@Service
public class OrderService {
    @Autowired
    private EmailService emailService; // Field injection
    
    private PaymentService paymentService;
    
    @Autowired
    public OrderService(PaymentService paymentService) { // Constructor injection (recommended)
        this.paymentService = paymentService;
    }
}
```

### @Qualifier
Specifies which bean to inject when multiple implementations exist.

```java
@Service
public class NotificationService {
    @Autowired
    @Qualifier("emailNotifier")
    private Notifier notifier;
}

@Component("emailNotifier")
public class EmailNotifier implements Notifier { }

@Component("smsNotifier")
public class SmsNotifier implements Notifier { }
```

### @Primary
Makes a bean the default choice when multiple implementations exist.

```java
@Service
@Primary
public class PrimaryEmailService implements EmailService { }

@Service
public class SecondaryEmailService implements EmailService { }
```

### @Value
Injects values from application.properties or application.yml.

```java
@Service
public class ConfigService {
    @Value("${app.name}")
    private String appName;
    
    @Value("${server.port:8080}") // Default value 8080
    private int serverPort;
}
```

---

## Spring Boot Startup Annotation

### @SpringBootApplication
Combines three annotations:
- `@Configuration` - Can define beans
- `@ComponentScan` - Scans for components
- `@EnableAutoConfiguration` - Enables Spring Boot's auto-configuration

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

---

## Web Layer (Spring MVC)

### @RequestMapping
Maps HTTP requests to handler methods.

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Product getProduct(@PathVariable Long id) {
        return new Product(id, "Laptop");
    }
}
```

### HTTP Method Specific Mappings
Shorthand annotations for specific HTTP methods.

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping // GET /api/users
    public List<User> getAllUsers() {
        return Arrays.asList(new User("John"), new User("Jane"));
    }
    
    @PostMapping // POST /api/users
    public User createUser(@RequestBody User user) {
        // Save user logic
        return user;
    }
    
    @PutMapping("/{id}") // PUT /api/users/1
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        // Update logic
        return user;
    }
    
    @DeleteMapping("/{id}") // DELETE /api/users/1
    public void deleteUser(@PathVariable Long id) {
        // Delete logic
    }
}
```

### @RequestParam
Binds query parameters to method parameters.

```java
@GetMapping("/search")
public List<User> searchUsers(
    @RequestParam String name,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(required = false) String email
) {
    // GET /search?name=John&page=1&email=john@example.com
    return userService.search(name, page, email);
}
```

### @PathVariable
Binds URL path parameters to method parameters.

```java
@GetMapping("/users/{id}/orders/{orderId}")
public Order getUserOrder(
    @PathVariable Long id,
    @PathVariable Long orderId
) {
    // GET /users/123/orders/456
    return orderService.findByUserAndOrder(id, orderId);
}
```

### @RequestBody
Converts JSON request body to Java object.

```java
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    // JSON in request body is converted to User object
    return userService.save(user);
}
```

### @ResponseBody
Converts Java object to JSON response. Not needed with `@RestController`.

```java
@Controller
public class UserController {
    @GetMapping("/api/users/{id}")
    @ResponseBody
    public User getUser(@PathVariable Long id) {
        return userService.findById(id); // Returns JSON
    }
}
```

---

## HTTP Status Codes & ResponseEntity

### ResponseEntity
Provides full control over HTTP response including status codes, headers, and body.

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); // 201 Created
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!userService.exists(id)) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok(updatedUser); // 200 OK
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.exists(id)) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        userService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
```

### Common HTTP Status Codes

#### Success (2xx)
```java
// 200 OK - Request successful
return ResponseEntity.ok(data);

// 201 Created - Resource created successfully
return ResponseEntity.status(HttpStatus.CREATED).body(newResource);

// 204 No Content - Request successful, no content to return
return ResponseEntity.noContent().build();
```

#### Client Error (4xx)
```java
// 400 Bad Request - Invalid request
return ResponseEntity.badRequest().body("Invalid data provided");

// 401 Unauthorized - Authentication required
return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

// 403 Forbidden - Access denied
return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

// 404 Not Found - Resource not found
return ResponseEntity.notFound().build();

// 409 Conflict - Resource conflict
return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
```

#### Server Error (5xx)
```java
// 500 Internal Server Error
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred");
```

### Error Handling with ResponseEntity

```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            // Validate order
            if (order.getAmount() <= 0) {
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Amount must be positive"));
            }
            
            Order savedOrder = orderService.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
            
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                .body(new ErrorResponse("Insufficient funds"));
                
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("An error occurred"));
        }
    }
}

// Error response DTO
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
    
    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and setters
}
```

### Adding Custom Headers

```java
@GetMapping("/download/{fileId}")
public ResponseEntity<byte[]> downloadFile(@PathVariable String fileId) {
    byte[] fileContent = fileService.getFileContent(fileId);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=document.pdf");
    headers.add("Content-Type", "application/pdf");
    
    return ResponseEntity.ok()
        .headers(headers)
        .body(fileContent);
}
```

---

## Lifecycle Hooks

### @PostConstruct
Runs after the bean is created and dependencies are injected.

```java
@Service
public class DatabaseService {
    @PostConstruct
    public void initDatabase() {
        System.out.println("Database connection initialized");
        // Initialize database connections, cache, etc.
    }
}
```

### @PreDestroy
Runs before the bean is destroyed (application shutdown).

```java
@Service
public class CleanupService {
    @PreDestroy
    public void cleanup() {
        System.out.println("Performing cleanup before shutdown");
        // Close connections, save state, etc.
    }
}
```

---

## Advanced Annotations

### @PropertySource
Loads custom properties files.

```java
@Configuration
@PropertySource("classpath:custom.properties")
public class CustomConfig {
    @Value("${custom.setting}")
    private String customSetting;
}
```

### @Profile
Activates beans for specific profiles (dev, prod, test).

```java
@Service
@Profile("dev")
public class DevEmailService implements EmailService {
    // Development implementation
}

@Service
@Profile("prod")
public class ProdEmailService implements EmailService {
    // Production implementation
}
```

### @ConditionalOnProperty
Conditional bean loading based on property values.

```java
@Service
@ConditionalOnProperty(name = "feature.email.enabled", havingValue = "true")
public class EmailService {
    // Only created if feature.email.enabled=true
}
```

---

## Quick Reference Flow

```
@SpringBootApplication 
    ↓
@ComponentScan (finds components)
    ↓
@Component/@Service/@Repository (registers as beans)
    ↓
@Autowired (injects dependencies)
    ↓
IoC & DI (Spring manages everything)
    ↓
@RestController (handles HTTP requests)
    ↓
ResponseEntity (controls HTTP response)
    ↓
JSON Response (sent to client)
```

## Key Takeaways

1. **Focus on business logic** - Spring handles object creation and management
2. **Use constructor injection** over field injection for better testability
3. **ResponseEntity gives full control** over HTTP responses
4. **Choose appropriate HTTP status codes** for different scenarios
5. **Use profiles** to separate configuration for different environments
6. **Leverage Spring Boot's auto-configuration** to reduce boilerplate code

**Remember:** You write the business logic, Spring handles the plumbing! 🚀