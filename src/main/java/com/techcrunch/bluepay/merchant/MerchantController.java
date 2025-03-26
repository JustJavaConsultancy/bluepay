package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.compliance.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.text.DecimalFormat;

@Controller
@RequestMapping("/merchant")
public class MerchantController {
    @Autowired
    MerchantService merchantService;

    @Autowired
    ComplianceService complianceService;
    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("/new")
    public String getCompliance(Model model){
        String loginUser= (String) authenticationManager.get("sub");

        Map<String,Object> variables = new HashMap<>();

        variables.put("country","NG");
        variables.put("businessType","Unregistered/Starterbusiness");
        variables.put("businessName",authenticationManager.get("businessName"));

        System.out.println(" ---The Variables i'm redirecting is====="+variables);
        model.addAttribute("merchantDetails",variables);
        return "/compliance/compliance";
    }

    @GetMapping("/dashboard")
    public String getDashboard(){
        String loginUser= (String) authenticationManager.get("sub");

        return "merchant/dashboard";
    }

    @GetMapping("/transactions")
    public String getTransaction(Model model){

        DecimalFormat df = new DecimalFormat("#,##0.00");

        Map<String, Object> transactionOne = Map.ofEntries(
                Map.entry("reference-cell", "T123456789234455"),
                Map.entry("amount-cell", df.format(20000.00)),
                Map.entry("customer-cell", "Adedapo Tiamiyu"),
                Map.entry("payment-tag", "Bank Transfer"),
                Map.entry("timestamp-cell", "Mar 27, 2025, 6 : 50 PM")
        );

        Map<String, Object> transactionTwo = Map.ofEntries(
                Map.entry("reference-cell", "T123456789234567"),
                Map.entry("amount-cell", df.format(40000.00)),
                Map.entry("customer-cell", "Isiah Thomas"),
                Map.entry("payment-tag", "Bank Transfer"),
                Map.entry("timestamp-cell", "Mar 27, 2025, 6 : 50 PM")
        );

        Map<String, Object> transactionThree = Map.ofEntries(
                Map.entry("reference-cell", "T123456789237892"),
                Map.entry("amount-cell", df.format(70000.00)),
                Map.entry("customer-cell", "Jide Adeyanju"),
                Map.entry("payment-tag", "Bank Transfer"),
                Map.entry("timestamp-cell", "Mar 27, 2025, 6 : 50 PM")
        );

        List<Map<String, Object>> transactions = List.of(
                transactionOne,
                transactionTwo,
                transactionThree
        );


        model.addAttribute("transactions", transactions);
        model.addAttribute("transactionsCount", transactions.size());

        return "merchant/transactions";
    }

    @GetMapping("/balance")
    public String getBalance(Model model){
        DecimalFormat df = new DecimalFormat("#,##0.00");

        Map<String, Object> balanceOne = Map.ofEntries(
                Map.entry("timestamp", "Mar 27, 2025, 6 : 00 PM"),
                Map.entry("activity", "Transfer"),
                Map.entry("change", df.format(55000.00)),
                Map.entry("running-balance", df.format(66000.00))
        );
        Map<String, Object> balanceTwo = Map.ofEntries(
                Map.entry("timestamp", "Mar 28, 2025, 6 : 00 PM"),
                Map.entry("activity", "Transaction"),
                Map.entry("change", df.format(13000.00)),
                Map.entry("running-balance", df.format(25000.00))
        );
        Map<String, Object> balanceThree = Map.ofEntries(
                Map.entry("timestamp", "Mar 25, 2025, 6 : 00 PM"),
                Map.entry("activity", "Transfer"),
                Map.entry("change", df.format(40000.00)),
                Map.entry("running-balance", df.format(60000.00))
        );

        List<Map<String, Object>> balanceList = List.of(
                balanceOne,
                balanceTwo,
                balanceThree
        );

        model.addAttribute("balanceList", balanceList);

        return "merchant/balance";
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitDetails(@RequestParam Map<String,Object> formData){
        merchantService.submitMyDetail(formData);

        HttpHeaders headers = new HttpHeaders();
        headers.add("HX-Redirect", "/overview");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }
    @PostMapping("/resubmit")
    public ResponseEntity<Void> reSubmitDetails(@RequestParam Map<String,Object> formData){
        merchantService.completeDocumentResubmittion(formData);

        HttpHeaders headers = new HttpHeaders();
        headers.add("HX-Redirect", "/overview");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }
    @GetMapping("/submitted")
    public String getSubmittedStatus(Model model){

        return "merchant/merchantPending";
    }
    @GetMapping("/approved")
    public String getApprovedStatus(){
        return "merchant/merchantStatus";
    }
    @GetMapping("/successful")
    public String merchantStatus(Model model) {


        Map merchantDetails= new HashMap();
        merchantDetails.put("businessType","Partnership");
        merchantDetails.put("businessName","Just Java");
        model.addAttribute("merchantDetails",merchantDetails);

        return "merchant/merchantStatus";
    }
    @GetMapping("/declined")
    public String merchantFailed(Model model) {
        return "merchant/merchantFailed";
    }

    @GetMapping("/orders")
    public String getOrders(Model model){
        DecimalFormat df = new DecimalFormat("#,##0.00");

        Map<String, Object> orderOne = Map.ofEntries(
                Map.entry("id-col", 1903072),
                Map.entry("customer-col", "Adedapo Tiamiyu"),
                Map.entry("product-col", "High Fashion"),
                Map.entry("quantity-col", 5),
                Map.entry("amount-col", df.format(20000.00)),
                Map.entry("timestamp-col", "Mar 27, 2025, 6 : 50 PM"),
                Map.entry("email-col", "tiamiyuadedapo@gmail.com"),
                Map.entry("phone-col", "08138482251")
        );

        Map<String, Object> orderTwo = Map.ofEntries(
                Map.entry("id-col", 1903502),
                Map.entry("customer-col", "Oluwaseun Tiamiyu"),
                Map.entry("product-col", "High Design"),
                Map.entry("quantity-col", 10),
                Map.entry("amount-col", df.format(50000.00)),
                Map.entry("timestamp-col", "Mar 29, 2025, 6 : 50 PM"),
                Map.entry("email-col", "tiamiyuseun@gmail.com"),
                Map.entry("phone-col", "08138482731")
        );

        Map<String, Object> orderThree = Map.ofEntries(
                Map.entry("id-col", 1903204),
                Map.entry("customer-col", "Hammed Joshua"),
                Map.entry("product-col", "Luxury Wears"),
                Map.entry("quantity-col", 40),
                Map.entry("amount-col", df.format(60000.00)),
                Map.entry("timestamp-col", "Mar 28, 2025, 6 : 50 PM"),
                Map.entry("email-col", "hammedjoshua@gmail.com"),
                Map.entry("phone-col", "0812282731")
        );

        List<Map<String, Object>> orders = List.of(
                orderOne,
                orderTwo,
                orderThree
        );

//        double totalAmount = orders.stream()
//                .mapToDouble(map -> ((Number) map.get("amount-col")).doubleValue())
//                .sum();

        double totalAmount = 1000.00;
        model.addAttribute("totalAmount", df.format(totalAmount));
        model.addAttribute("orderCount", orders.size());
        model.addAttribute("orders", orders);

        return "order/viewOrders";
    }

}
