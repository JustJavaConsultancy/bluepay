package com.techcrunch.bluepay.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.cloudinary.Image;
import com.techcrunch.bluepay.cloudinary.ImageService;
import com.techcrunch.bluepay.payment.PaymentDTO;
import com.techcrunch.bluepay.payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class myProductController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/manageProduct")
    public String myProduct( Map<String, Object> model) {
        List<ProductDTO> productDTOS=productService.findAll();
        model.put("productList", productDTOS);
        PaymentDTO paymentDTO=PaymentDTO.builder()
                .amount(new BigDecimal(100))
                .cardCvv("234")
                .channel("card")
                .cardExpirationDate("25-03-2025")
                .cardHolderName("Akinrinde Kazeem")
                .invoiceId(1L)
                .cardNumber("4111111111111111")
                .currency("NIG")
                .payerEmail("akinrinde@justjava.com.ng")
                .payerPhoneNumber("07062023181")
                .build();
        Map<String,Object> variables=objectMapper.convertValue(paymentDTO,Map.class);
        variables.put("productName","Laptop");
        variables.put("merchantId","24424244242424");
        //paymentService.startPaymentProcess(variables,"92002020020");
        return "/product/manageProduct";
    }

    @PostMapping("/addNewProduct")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addNewProduct(
            HttpServletRequest httpServletRequest,
            @RequestParam(value = "productImageNames", required = false) List<MultipartFile> files, // Accept multiple files
            @RequestParam Map<String, Object> data) {


        System.out.println(" The data I'm getting is ====="+data);
        List<String> media = new ArrayList<>();
        Long quantity = Strings.isEmpty(String.valueOf(data.get("productQuantityNum")))?0:Long.parseLong(String.valueOf(data.get("productQuantityNum")));

        Boolean isPhysical = String.valueOf(data.get("physicalProducts")).equalsIgnoreCase("true");

        ProductDTO productDTO=ProductDTO.builder()
                .code(String.valueOf(data.get("productName").hashCode()) )
                .price(new BigDecimal(String.valueOf(data.get("productPrice"))))
                .name((String) data.get("productName"))
                .description((String) data.get("productDescription"))
                .quantityInStock(quantity)
                .containsPhysicalGoods(isPhysical)
                .build();

        System.out.println("The data I'm getting is ===== " + data);

        try {
            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        Image image = new Image();
                        image.setName(file.getOriginalFilename());
                        image.setFile(file);

                        ResponseEntity<Map> response = imageService.uploadImage(image);

                        if (response.getBody() != null) {
                            String imageUrl = response.getBody().get("url").toString();
                            media.add(imageUrl);
                            System.out.println("Uploaded Image URL: " + imageUrl);
                        }
                    }
                }
            } else {
                System.out.println("No images uploaded.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("the media is ====="+media.size());
        media.forEach(med->System.out.println("URL==="+med));
        productDTO.setMedia(media);

        productService.create(productDTO);

        // Retrieve session data
        List<Map<String, Object>> products = (List<Map<String, Object>>)
                httpServletRequest.getSession().getAttribute("productData");

        if (products == null) {
            System.out.println("Initializing the products list");
            products = new ArrayList<>();
        }
        // Add product data along with image URLs
        Map<String, Object> productData = new HashMap<>(data);
        productData.put("imageUrls", media);
        products.add(productData);

        // Update session
        httpServletRequest.getSession().setAttribute("productData", products);

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Product added successfully");
        response.put("data", productData);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/productDetail/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        ProductDTO productDTO=productService.get(id);
//        Map<String, Object> product = new HashMap<>();
//        product.put("productName", "Bootify");
//        product.put("productDescription", "A Bootstrap 4 template for modern businesses and corporate websites");
//        product.put("productImages", Arrays.asList("", "image2.jpg", "image3.jpg"));

        model.addAttribute("product", productDTO);
        return "/product/productDetail";
    }
    @PostMapping("/editProductName/{id}")
    @ResponseBody
    public String editProductName(@PathVariable Long id,@RequestParam String productName) {
        System.out.println(" Submitting Here ------------------id===="+id);
        ProductDTO productDTO = productService.get(id);

        System.out.println(" Submitting Here --------------------");
        if (productDTO != null) {
            productDTO.setName(productName);
            productService.update(id, productDTO);
            //redirectAttributes.addFlashAttribute("successMessage", "Product name updated successfully!");
        } else {
            //redirectAttributes.addFlashAttribute("errorMessage", "Product not found!");
        }
        String response="<input style=\"background-color: #F9F7F7;font-size: 0.85rem;border: " +
                "1px solid #E8E8E8;\" type=\"text\" id=\"productLink\" class=\"form-control\" " +
                "value='https://bluepayment/buy/"+productName.replace(" ","_")+".com' readonly>\n";
        return response;
    }
    @GetMapping("/preview/{id}")
    public String previewProduct(@PathVariable Long id, Model model) {
        ProductDTO productDTO=productService.get(id);
        model.addAttribute("product", productDTO);
        return "/product/productPreview";
    }
    @PostMapping("/pay")
    public String processPayment(@RequestBody Map<String, String> paymentData) {
        // Extract individual values from the Map
        String cardNumber = paymentData.get("card-number");
        String expiryDate = paymentData.get("expiry-date");
        String cvv = paymentData.get("cvv");
        String pin1 = paymentData.get("pin1");
        String pin2 = paymentData.get("pin2");
        String pin3 = paymentData.get("pin3");
        String pin4 = paymentData.get("pin4");

        // Process the payment request here
        System.out.println("Processing payment for card number: " + cardNumber);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("CVV: " + cvv);
        System.out.println("PIN: " + pin1 + pin2 + pin3 + pin4);

        // After processing payment, return a response
        return "Payment processed successfully!";
    }
    @PostMapping("/payerInfo")
    public String payerInfo(@RequestParam Map<String, String> paymentRequest) {
        System.out.println("Received payment details: " + paymentRequest);
        return "Payment user successfully submitted";
    }





}
