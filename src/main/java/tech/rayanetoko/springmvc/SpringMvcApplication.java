package tech.rayanetoko.springmvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import tech.rayanetoko.springmvc.entities.Product;
import tech.rayanetoko.springmvc.repositories.ProductRepository;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
class SpringMvcApplication {

	static public void main(String[] args) {
		SpringApplication.run(SpringMvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner start(ProductRepository productRepository) {
		return args -> {
			productRepository.save(Product.builder()
				.name("Computer")
				.price(12000)
				.quantity(33)
				.build()
			);
			productRepository.save(Product.builder()
				.name("Printer")
				.price(33000)
				.quantity(5)
				.build()
			);
			productRepository.findAll().forEach(p -> {
				System.out.println(p.toString());
			});
		};
	}
}
