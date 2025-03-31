package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.compliance.ComplianceService;
import jakarta.servlet.http.HttpServletRequest;
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
    public String getCompliance(HttpServletRequest request, Model model){
        String loginUser= (String) authenticationManager.get("sub");

        Map<String,Object> variables = new HashMap<>();

        variables.put("country","NG");
        variables.put("businessType","Unregistered/Starterbusiness");
        variables.put("businessName",authenticationManager.get("businessName"));

        System.out.println(" ---The Variables i'm redirecting is====="+variables);
        model.addAttribute("merchantDetails",variables);
        model.addAttribute("currentUrl", request.getRequestURI());

        return "/compliance/compliance";
    }

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest request, Model model){
        String loginUser= (String) authenticationManager.get("sub");
        DecimalFormat df = new DecimalFormat("#,##0.00");

        Map<String, Object> coordinate1 = Map.ofEntries(
                Map.entry("class_", "bar-march3"),
                Map.entry("x", "March 3"),
                Map.entry("y", 7000)
        );
        Map<String, Object> coordinate2 = Map.ofEntries(
                Map.entry("class_", "bar-march4"),
                Map.entry("x", "March 4"),
                Map.entry("y", 32000)
        );
        Map<String, Object> coordinate3 = Map.ofEntries(
                Map.entry("class_", "bar-march10"),
                Map.entry("x", "March 10"),
                Map.entry("y", 2500)
        );
        Map<String, Object> coordinate4 = Map.ofEntries(
                Map.entry("class_", "bar-march15"),
                Map.entry("x", "March 15"),
                Map.entry("y", 10000)
        );
        Map<String, Object> coordinate5 = Map.ofEntries(
                Map.entry("class_", "bar-march16"),
                Map.entry("x", "March 16"),
                Map.entry("y", 3500)
        );
        Map<String, Object> coordinate6 = Map.ofEntries(
                Map.entry("class_", "bar-march17"),
                Map.entry("x", "March 17"),
                Map.entry("y", 4500)
        );
        Map<String, Object> coordinate7 = Map.ofEntries(
                Map.entry("class_", "bar-march18"),
                Map.entry("x", "March 18"),
                Map.entry("y", 1000)
        );

        List<Map<String, Object>> revenueList = List.of(
                coordinate1,
                coordinate2,
                coordinate3,
                coordinate4,
                coordinate5,
                coordinate6,
                coordinate7
        );

        String totalRevenue = df.format(0);

        Map<String, Object> inflowTransactions = Map.ofEntries(
                Map.entry("chartPercentage", 0),
                Map.entry("successTransactions", 0),
                Map.entry("errorTransactions", 0)
        );

        Map<String, Object> outflowTransactions = Map.ofEntries(
                Map.entry("chartPercentage", 0),
                Map.entry("successTransactions", 0),
                Map.entry("errorTransactions", 0)
        );

        Map<String, Object> paymentIssues = Map.ofEntries(
                Map.entry("customerError", 0),
                Map.entry("bankError", 0),
                Map.entry("fraudBlock", 0),
                Map.entry("systemError", 0)
        );
        Map<String, Object> nextSettlement = Map.ofEntries(
                Map.entry("amount",df.format(0)),
                Map.entry("date", "Due Monday, March 2025")
        );
        Map<String, Object> balance = Map.ofEntries(
                Map.entry("amount", df.format(0)),
                Map.entry("status", "Available")
        );

        model.addAttribute("revenueList", revenueList);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("inflowTransactions", inflowTransactions);
        model.addAttribute("outflowTransactions", outflowTransactions);
        model.addAttribute("paymentIssues", paymentIssues);
        model.addAttribute("nextSettlement", nextSettlement);
        model.addAttribute("balance", balance);
        model.addAttribute("currentUrl", request.getRequestURI());

        return "merchant/dashboard";
    }

    @GetMapping("/transactions")
    public String getTransaction(HttpServletRequest request,Model model){

        DecimalFormat df = new DecimalFormat("#,##0.00");

        Map<String, Object> transactionOne = Map.ofEntries(
                Map.entry("reference-cell", "T123456789234455"),
                Map.entry("amount-cell", df.format(0)),
                Map.entry("customer-cell", "Adedapo Tiamiyu"),
                Map.entry("payment-tag", "Bank Transfer"),
                Map.entry("timestamp-cell", "Mar 27, 2025, 6 : 50 PM")
        );


        List<Map<String, Object>> transactions = List.of(
                transactionOne
        );

        model.addAttribute("transactions", transactions);
        model.addAttribute("transactionsCount", transactions.size());
        model.addAttribute("currentUrl", request.getRequestURI());

        return "merchant/transactions";
    }

    @GetMapping("/transactions/{id}")
    public String getTransactionDetails(Model model){
        System.out.println("\n\n\n transactions detail controller is working");
        DecimalFormat df = new DecimalFormat("#,##0.00");
        Map<String, Object> transactionItem = Map.ofEntries(
                Map.entry("status", "Successful"),
                Map.entry("amount", df.format(50000.00)),
                Map.entry("reference", "T123456789234567"),
                Map.entry("channel", "card"),
                Map.entry("fees", df.format(500.00)),
                Map.entry("timestamp", "Mar 27, 2025, 6 : 50 PM"),
                Map.entry("products", df.format(40000.00)),
                Map.entry("customer-name", "Adeyanju Timothy"),
                Map.entry("customer-email", "tiamiyuadedapo@gmail.com"),
                Map.entry("customer-phone-code", "+234"),
                Map.entry("customer-phone-number", "8138482251")
        );

        model.addAttribute("transactionItem", transactionItem);
        return "merchant/transaction-details";
    }

    @GetMapping("/balance")
    public String getBalance(HttpServletRequest request, Model model){
        DecimalFormat df = new DecimalFormat("#,##0.00");

        Map<String, Object> balanceOne = Map.ofEntries(
                Map.entry("transactionId", "T2343393939386"),
                Map.entry("timestamp", "Mar 27, 2025, 6 : 00 PM"),
                Map.entry("activity", "Transfer"),
                Map.entry("change", df.format(0)),
                Map.entry("running-balance", df.format(0))
        );


        List<Map<String, Object>> balanceList = List.of(
                balanceOne
        );

        Map<String, Object> balanceTotal = Map.of("total", df.format(0));

        model.addAttribute("balanceList", balanceList);
        model.addAttribute("balanceTotal", balanceTotal);
        model.addAttribute("currentUrl", request.getRequestURI());

        return "merchant/balance";
    }

    @GetMapping("/settlements")
    public String getSettlements(HttpServletRequest request, Model model){
        DecimalFormat df = new DecimalFormat("#,##0.00");
        Map<String, Object> settlement1 = Map.ofEntries(
               Map.entry("status", "Success"),
                Map.entry("settledAmount", df.format(0)),
                Map.entry("date", "Mar 24, 2025")
        );


        List<Map<String, Object>> settlementList = List.of(
                settlement1
        );

        Map<String, Object> totalSettlements = Map.of("total", settlementList.size());
        Map<String, Object> nextSettlement = Map.of("amount", df.format(0), "date", "Mar 29, 2025");

        model.addAttribute("settlementList", settlementList);
        model.addAttribute("totalSettlements", totalSettlements);
        model.addAttribute("nextSettlement", nextSettlement);
        model.addAttribute("currentUrl", request.getRequestURI());

        return "merchant/settlements";
    }

    @GetMapping("/transfers")
    public String getTransfers(HttpServletRequest request, Model model){
        DecimalFormat df = new DecimalFormat("#,##0.00");

        Map<String, Object> transfer1 = Map.ofEntries(
                Map.entry("status", "Success"),
                Map.entry("reference", "T1234567890"),
                Map.entry("amount", df.format(0)),
                Map.entry("beneficiary", "John Smith"),
                Map.entry("timestamp", "23 Mar, 2025")
        );

        List<Map<String, Object>> transferList = List.of(
                transfer1
        );

        Map<String, Object> transferTotal = Map.of("total", transferList.size());
        Map<String, Object> transferBalance = Map.of("balance", df.format(0));

        model.addAttribute("transferList", transferList);
        model.addAttribute("transferTotal", transferTotal);
        model.addAttribute("transferBalance", transferBalance);
        model.addAttribute("currentUrl", request.getRequestURI());

        return "merchant/transfers";
    }

    @GetMapping("/transfers/{id}")
    public String getTransferDetails(Model model){
        DecimalFormat df = new DecimalFormat("#,##0.00");
        Map<String, Object> transferItem = Map.ofEntries(
                Map.entry("amount", df.format(50000)),
                Map.entry("status", "Successful"),
                Map.entry("accountNumber", "8138482251"),
                Map.entry("accountBank", "GTBank Services Ltd"),
                Map.entry("accountName", "Adeyanju Salem"),
                Map.entry("timestamp", "Mar 27, 2025, 6 : 50 PM"),
                Map.entry("reference", "T123457890"),
                Map.entry("fees", df.format(500)),
                Map.entry("narration", "Feeding Allowance")
        );

        model.addAttribute("transferItem", transferItem);
        return "merchant/transfers-detail";
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitDetails(HttpServletRequest request){
        Map<String, Object> formData = (Map<String, Object>)request.getSession(true).getAttribute("merchantDetails");

        System.out.println("The Submitted Data === " + formData);
        merchantService.submitMyDetail(formData);

        HttpHeaders headers = new HttpHeaders();
        headers.add("HX-Redirect", "/merchant/dashboard");
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
    public String getOrders(HttpServletRequest request, Model model){
        DecimalFormat df = new DecimalFormat("#,##0.00");

        Map<String, Object> orderOne = Map.ofEntries(
                Map.entry("id-col", 1903072),
                Map.entry("customer-col", "Adedapo Tiamiyu"),
                Map.entry("product-col", "High Fashion"),
                Map.entry("quantity-col", 1),
                Map.entry("amount-col", df.format(0)),
                Map.entry("timestamp-col", "Mar 27, 2025, 6 : 50 PM"),
                Map.entry("email-col", "tiamiyuadedapo@gmail.com"),
                Map.entry("phone-col", "08138482251")
        );

        List<Map<String, Object>> orders = List.of(
                orderOne
        );

//        double totalAmount = orders.stream()
//                .mapToDouble(map -> ((Number) map.get("amount-col")).doubleValue())
//                .sum();

        double totalAmount = 0;
        model.addAttribute("totalAmount", df.format(totalAmount));
        model.addAttribute("orderCount", orders.size());
        model.addAttribute("orders", orders);
        model.addAttribute("currentUrl", request.getRequestURI());

        return "order/viewOrders";
    }

    @PostMapping("/newMerchant/{step}")
    public String saveNewMerchant(@PathVariable Integer step, HttpServletRequest request,
                                  @RequestParam Map<String, Object> formData, Model model) {
        Map<String, Object> merchantDetails = (Map<String, Object>)
                request.getSession(true).getAttribute("merchantDetails");
        if (merchantDetails == null) {
            merchantDetails = new HashMap<>();
        }

        merchantDetails.putAll(formData);
        request.getSession(true).setAttribute("merchantDetails", merchantDetails);

        String nextFragment = null;
        switch (step) {
            case 1:
                nextFragment = "compliance/contact :: form2(data=${test}, complianceButton=${test})";
                break;
            case 2:
                nextFragment="compliance/businessOwner :: form3(data=${test},complianceButton=${test})";
                break;
            case 3:
                nextFragment = "compliance/bankAccount :: form4(data=${test}, complianceButton=${test})";
                break;
            case 4:
                nextFragment = "compliance/serviceAgreement :: form5(data=${test})";
                break;
            case 5:
                model.addAttribute("merchantDetails", merchantDetails); // Pass collected data
                nextFragment = "compliance/summary :: form6(showclass='', backButton='null', hideButton='null', data=${merchantDetails},someCondition= true)";
                break;
        }
        merchantDetails.putAll(formData);
        request.getSession(true).setAttribute("merchantDetails",merchantDetails);
        System.out.println(" The data sent now inside saveNewMerchant ==="+formData);

        System.out.println("Data saved so far: " + merchantDetails);

        return nextFragment;
    }

}
