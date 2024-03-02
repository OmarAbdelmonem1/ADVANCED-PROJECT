package com.adv.adv.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.RedirectView;
import com.adv.adv.model.Product;
import com.adv.adv.repository.CategoryRepository;
import com.adv.adv.repository.MetalRepository;
import com.adv.adv.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MetalRepository metalRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public ModelAndView getAll() {
        ModelAndView mav =new ModelAndView("ProductsList.html");
        List<Product>products=this.productRepository.findAll();
        mav.addObject("products", products);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView addProduct() {
        ModelAndView mav = new ModelAndView("create-product.html");
        mav.addObject("product", new Product());
        mav.addObject("metals", metalRepository.findAll());
        mav.addObject("categories", categoryRepository.findAll());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult result,
                                    @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("create-product.html");
            mav.addObject("metals", metalRepository.findAll());
            mav.addObject("categories", categoryRepository.findAll());
            mav.addObject("bindingResult", result);
            return mav; // Return ModelAndView directly
        }
    
        String fileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
        product.setPhotos(fileName);
        Product savedProduct = this.productRepository.save(product);
        String uploadDir = "uploads/" + savedProduct.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return new ModelAndView("redirect:/products");
    }
    


    
@GetMapping("/edit/{Id}")
public ModelAndView showEditForm(@PathVariable("Id") int Id) {
    Product product = this.productRepository.findById(Id);
    
           ModelAndView mav = new ModelAndView("editProduct.html");
    mav.addObject("product", product);
    mav.addObject("metals", metalRepository.findAll());
    mav.addObject("categories", categoryRepository.findAll());
    return mav; 
}
@PostMapping("/edit/{Id}")
public RedirectView editProduct(@ModelAttribute("product") Product product,
                                @RequestParam(value = "image", required = false) MultipartFile multipartFile) throws IOException {
    if (multipartFile != null && !multipartFile.isEmpty()) {
        handleFileUpload(product, multipartFile);
    } else {
        keepExistingPhoto(product);
    }
    return new RedirectView("/products");
}

private void handleFileUpload(Product product, MultipartFile multipartFile) throws IOException {
    String fileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
    product.setPhotos(fileName);
    Product savedProduct = this.productRepository.save(product);
    String uploadDir = "uploads/" + savedProduct.getId();
    FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
}

private void keepExistingPhoto(Product product) {
    Product existingProduct = this.productRepository.findById(product.getId());
    if (existingProduct != null) {
        product.setPhotos(existingProduct.getPhotos());
        this.productRepository.save(product);
    }
}





//delete
    @GetMapping("/delete/{Id}")
    @Transactional
public RedirectView deleteProduct(@PathVariable("Id") int Id) {
    this.productRepository.deleteById(Id);
    return new RedirectView("/products"); // Redirect to the user list page after deleting
}

   
}
