# Lab Report: Product Management Application
**Frameworks: Spring Boot, Data JPA, Thymeleaf & Security**

---

## 1. Project Objectives
The objective is to design and implement a modular JEE Web application to:
* Manage a product inventory (CRUD operations).
* Validate data entered via web forms.
* Secure access to sensitive features based on user roles.

---

## 2. Technical Architecture
The project is based on a layered (N-Tier) architecture:
1. **Presentation Layer**: Thymeleaf + Bootstrap.
2. **Controller Layer**: Spring MVC.
3. **Data Access Layer (DAO)**: Spring Data JPA.
4. **Security Layer**: Spring Security.



---

## 3. Model Layer Implementation
The `Product` entity represents the database table. We use **Lombok** for the automatic generation of boilerplate code.

```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty @Size(min = 4, max = 50)
    private String name;
    
    @Min(100)
    private double price;
    
    @Min(0)
    private int quantity;
}
```

---

## 4. Data Management (DAO & Service)
Thanks to Spring Data JPA, the ProductRepository interface automatically handles CRUD operations.

```Java
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContains(String keyword, Pageable pageable);
}
```

---

## 5. User Interface & Controller
The controller processes requests and returns Thymeleaf views. A Bootstrap Layout is used to ensure visual consistency across all pages.

Display: Utilization of model.addAttribute.

Validation: @Valid to check constraints before saving.

Deletion: Secured by ID and csrf token.

---

## 6. Application Security
The application is protected by a login form. Authorizations are based on two roles:

USER: Product consultation only.

ADMIN: Full rights (Add, Edit, Delete).

```Java
@Configuration
@EnableWebSecurity
class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		PasswordEncoder bcrypt = passwordEncoder();
		return new InMemoryUserDetailsManager(
			User.withUsername("user1").password(bcrypt.encode("1234")).roles("USER").build(),
			User.withUsername("user2").password(bcrypt.encode("1234")).roles("USER").build(),
			User.withUsername("admin").password(bcrypt.encode("1234")).roles("USER", "ADMIN").build()
		);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
		.formLogin(fl -> fl.loginPage("/login").permitAll())
		.authorizeHttpRequests(ar -> ar.requestMatchers("/user/**").hasRole("USER"))
		.authorizeHttpRequests(ar -> ar.requestMatchers("/admin/**").hasRole("ADMIN"))
		.authorizeHttpRequests(ar -> ar.requestMatchers("/public/**", "/webjars/**").permitAll())
		.authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
		.exceptionHandling(eh -> eh.accessDeniedPage("/not-authorized"))
		.build();
	}
}
```
7. Conclusion
This lab allowed for the integration of essential components of the Spring ecosystem. The use of Spring Boot considerably simplifies deployment, while Spring Security ensures robust and granular access control.
