package tech.rayanetoko.springmvc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

	@GetMapping("/user/index")
	public String index(Model model) {
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/user")
	public String home() {
		return "redirect:/user/index";
	}

	@GetMapping("/admin/delete")
	public String delete(Long id) {
		productRepository.deleteById(id);
		return "redirect:/user/index";
	}

	@GetMapping("/admin/new-product")
	public String newProduct(Model model) {
		model.addAttribute("product", new Product());
		return "new-product";
	}

	@PostMapping("/admin/save-product")
	public String saveProduct(@Valid Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "new-product";
		}

		productRepository.save(product);

		return "redirect:/user/index";
	}
}
