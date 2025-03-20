package com.techcrunch.bluepay.product;

import com.techcrunch.bluepay.account.AuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class myProductController {
    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/manageProduct")
    public String myProduct(@ModelAttribute("productData") Map<String, Object> productData, Map<String, Object> model) {
        model.put("productData", productData);
        return "/product/manageProduct";
    }

    @PostMapping("/addNewProduct")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addNewProduct(
            HttpServletRequest httpServletRequest,@RequestParam Map<String, Object> data) {
        // Log the incoming data
        System.out.println("The data I'm getting is ===== " + data);

        List<Map<String,Object>> products = (List<Map<String, Object>>)
                httpServletRequest.getSession().getAttribute("productData");
        System.out.println(" products list is ====="+products);
        if(products==null){
            System.out.println(" Initializing the products list");
            products=new ArrayList<>();
        }
        products.add(data);

        products.forEach(product -> {
            System.out.println(" The product I'm getting is ===== " + product);
        });
        // Create a response map
        Map<String, Object> response = new HashMap<>();
        httpServletRequest.getSession().setAttribute("productData",products);
        response.put("status", "success");
        response.put("message", "Product added successfully");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

}
