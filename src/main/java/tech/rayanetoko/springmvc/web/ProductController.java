package tech.rayanetoko.springmvc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/index")
	public String index(Model model) {
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/delete")
	public String delete(Long id) {
		productRepository.deleteById(id);
		return "redirect:/index";
	}
}
