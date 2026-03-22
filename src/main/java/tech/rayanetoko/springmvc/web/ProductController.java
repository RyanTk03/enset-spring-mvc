package tech.rayanetoko.springmvc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;

import tech.rayanetoko.springmvc.repositories.ProductRepository;
import tech.rayanetoko.springmvc.entities.Product;

/**
 * ProductController
 */
@Controller
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/")
	public String getRoot() {
		return "redirect:/user/index";
	}

	@GetMapping("/user/index")
	@PreAuthorize("hasRole('USER')")
	public String index(Model model) {
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/user")
	public String home() {
		return "redirect:/user/index";
	}

	@PostMapping("/admin/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public String delete(Long id) {
		productRepository.deleteById(id);
		return "redirect:/user/index";
	}

	@GetMapping("/admin/new-product")
	@PreAuthorize("hasRole('ADMIN')")
	public String newProduct(Model model) {
		model.addAttribute("product", new Product());
		return "new-product";
	}

	@PostMapping("/admin/save-product")
	@PreAuthorize("hasRole('ADMIN')")
	public String saveProduct(@Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "new-product";
		}

		productRepository.save(product);

		return "redirect:/user/index";
	}

	@GetMapping("/not-authorized")
	public String notAuthorized() {
		return "not-authorized";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
}
