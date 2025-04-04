package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.accounting.Account;
import com.techcrunch.bluepay.accounting.JournalLine;
import com.techcrunch.bluepay.compliance.ComplianceService;
import com.techcrunch.bluepay.transaction.Transaction;
import com.techcrunch.bluepay.transaction.TransactionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

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
    public String getCompliance(HttpServletRequest request,Model model){
        String loginUser= (String) authenticationManager.get("sub");

        Map<String,Object> variables = new HashMap<>();

        variables.put("country","NG");
        variables.put("businessType","Unregistered/Starterbusiness");
        variables.put("businessName",authenticationManager.get("businessName"));

        request.getSession(true).setAttribute("submitAction","submit");
        System.out.println(" ---The Variables i'm redirecting is====="+variables);
        model.addAttribute("merchantDetails",variables);
        return "/compliance/compliance";
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model){
        String loginUser= (String) authenticationManager.get("sub");
        DecimalFormat df = new DecimalFormat("#,##0.00");

        String totalRevenue = df.format(50000.00);

        Map<String, Object> paymentIssues = Map.ofEntries(
                Map.entry("customerError", 0),
                Map.entry("bankError", 0),
                Map.entry("fraudBlock", 0),
                Map.entry("systemError", 0)
        );

        Map<String, Object> nextSettlement = Map.of("amount", df.format(90000), "date", "April 29, 2025");
        List<TransactionDTO> myTransactions = merchantService.myTransactions();

/*
        Account payable=merchantService.myPayableAccount();
        Account bankAccount=merchantService.myBankAccount();
*/

        List<JournalLine> bankBalances=merchantService.myBalances();
        List<BigDecimal> bankCurrentAmount = bankBalances.stream()
                .map(balance -> balance.getTransaction().getAmount())
                        .toList();
        List<Integer> bankCurrentDate = bankBalances.stream()
                .map(balance -> balance.getTransaction().getDateCreated().getDayOfMonth())
                        .toList();
//                .collect(Collectors.groupingBy(balance -> balance.getTransaction().getDateCreated().getDayOfMonth().toString()))
//                .toList();

//        System.out.println("This is the bank balance month"+ bankCurrentDate);
//        System.out.println("This is the bank balance month"+ bankCurrentAmount);

        int allInflowTransactions = myTransactions.stream().filter(transaction -> transaction.getPaymentType().toString().equals("INFLOW"))
                .toList().size();

        int paidInflowTransactions = myTransactions.stream().filter(transaction -> transaction.getPaymentType().toString().equals("INFLOW"))
                        .filter(transaction -> transaction.getStatus().toString().equals("PAID"))
                        .toList().size();

        int errorInflowTransactions = myTransactions.stream().filter(transaction -> transaction.getPaymentType().toString().equals("INFLOW"))
                .filter(transaction -> transaction.getStatus().toString().equals("CANCELLED"))
                .toList().size();

        int allOutflowTransactions = myTransactions.stream().filter(transaction -> transaction.getPaymentType().toString().equals("OUTFLOW"))
                .toList().size();

        int paidOutflowTransactions = myTransactions.stream().filter(transaction -> transaction.getPaymentType().toString().equals("OUTFLOW"))
                .filter(transaction -> transaction.getStatus().toString().equals("PAID"))
                .toList().size();

        int errorOutflowTransactions = myTransactions.stream().filter(transaction -> transaction.getPaymentType().toString().equals("OUTFLOW"))
                .filter(transaction -> transaction.getStatus().toString().equals("CANCELLED"))
                .toList().size();

        int successInflowRate = (int) allInflowTransactions != 0 ? (paidInflowTransactions / allInflowTransactions) * 100 : 0;
        int successOutflowRate = (int) allOutflowTransactions != 0 ? (paidOutflowTransactions / allOutflowTransactions) * 100 : 0;


        System.out.println(" I'm sending this username here=="+loginUser);
        model.addAttribute("username",loginUser);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("paidInflowTransactions", paidInflowTransactions);
        model.addAttribute("errorInflowTransactions", errorInflowTransactions);
        model.addAttribute("successInflowRate", successInflowRate);

        model.addAttribute("paidOutflowTransactions", paidOutflowTransactions);
        model.addAttribute("errorOutflowTransactions", errorOutflowTransactions);
        model.addAttribute("successOutflowRate", successOutflowRate);

        model.addAttribute("paymentIssues", paymentIssues);
        model.addAttribute("nextSettlement", nextSettlement);
        model.addAttribute("balanceTotal", 0.00);

//        new entries
        model.addAttribute("bankCurrentAmount", bankCurrentAmount);
        model.addAttribute("bankCurrentDate", bankCurrentDate);


        return "merchant/dashboard";
    }

    @GetMapping("/transactions")
    public String getTransaction(Model model){
        List<TransactionDTO> myTransactions = merchantService.myTransactions();

        BigDecimal transactionTotal = myTransactions.stream()
                        .map(transaction -> transaction.getAmount())
                                .reduce(BigDecimal.ZERO,BigDecimal::add);

        model.addAttribute("myTransactions", myTransactions);
        model.addAttribute("transactionsCount", myTransactions.size());

        return "merchant/transactions";
    }

    @GetMapping("/transactions/{id}")
    public String getTransactionDetails(@PathVariable("id") Long id,Model model){
        TransactionDTO singleTransaction = merchantService.singleTransaction(id);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        Map<String, Object> transactionItem = Map.ofEntries(

                Map.entry("fees", df.format(500.00)),
                Map.entry("customer-phone-code", "+234")
        );

        model.addAttribute("singleTransaction", singleTransaction);
        model.addAttribute("transactionItem", transactionItem);
        return "merchant/transaction-details";
    }

    @GetMapping("/balance")
    public String getBalance(Model model){

        List<JournalLine> bankBalances=merchantService.myBalances();
//        bankBalances.forEach(journalLine -> {
//            System.out.println(" The lines are==== "+journalLine.toString());
//            }
//        );
        BigDecimal total =new BigDecimal(0.00);
        try {
            Account payable=merchantService.myPayableAccount();
            Account bankAccount=merchantService.myBankAccount();
            total=payable.getBalance().add(bankAccount.getBalance());

        }catch (Exception exception){

        }
        model.addAttribute("balanceTotal",total);

        model.addAttribute("bankBalances", bankBalances);
        return "merchant/balance";
    }

    @GetMapping("/settlements")
    public String getSettlements(Model model){
        DecimalFormat df = new DecimalFormat("#,##0.00");

        List<JournalLine> bankSettlements=merchantService.myBalances();
        BigDecimal nextSettlementAmount = new BigDecimal(0.00);
        try {
            Account payable = merchantService.myPayableAccount();
            Account bankAccount = merchantService.myBankAccount();
            nextSettlementAmount = payable.getBalance().add(bankAccount.getBalance());
        }catch (Exception exception){

        }
        Map<String, Object> nextSettlement = Map.of("amount", df.format(90000), "date", "April 29, 2025");

        model.addAttribute("bankSettlements", bankSettlements);
        model.addAttribute("totalSettlements", bankSettlements.size());
        model.addAttribute("nextSettlementAmount", nextSettlementAmount);
        model.addAttribute("nextSettlement", nextSettlement);

        return "merchant/settlements";
    }
    @GetMapping("/settlementsDetail")
    public String getSettlementsDetail(Model model){
        return "merchant/settlements-details";
    }

    @GetMapping("/transfers")
    public String getTransfers(Model model){
        DecimalFormat df = new DecimalFormat("#,##0.00");

        List<TransactionDTO> myTransfers = merchantService.myTransactions();
        BigDecimal transferTotal = new BigDecimal(0.00);
        try {
            Account payable=merchantService.myPayableAccount();
            Account bankAccount=merchantService.myBankAccount();
            transferTotal=payable.getBalance().add(bankAccount.getBalance());
        }catch (Exception exception){

        }

        System.out.println("This is my transfer" + myTransfers);
        model.addAttribute("myTransfers", myTransfers);
        model.addAttribute("transferTotal", myTransfers.size());
        model.addAttribute("transferBalance",transferTotal);
        return "merchant/transfers";
    }

    @GetMapping("/transfers/{id}")
    public String getTransferDetails(@PathVariable("id") Long id, Model model){
        TransactionDTO singleTransfer = merchantService.singleTransaction(id);

        System.out.println("This is a single transaction" + singleTransfer);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        Map<String, Object> transferItem = Map.ofEntries(
                Map.entry("accountNumber", "8138482251"),
                Map.entry("accountBank", "GTBank Services Ltd"),
                Map.entry("fees", df.format(500)),
                Map.entry("narration", "Feeding Allowance")
        );

        model.addAttribute("singleTransfer", singleTransfer);
        model.addAttribute("transferItem", transferItem);
        return "merchant/transfers-detail";
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitDetails(HttpServletRequest request){
        Map<String, Object> formData = (Map<String, Object>)request.getSession(true).getAttribute("merchantDetails");

        System.out.println("The Submitted Data === " + formData);
        request.getSession(true).setAttribute("status","submitted");
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

        String loginUser= (String) authenticationManager.get("sub");

        Map<String,Object> variables = (Map<String, Object>)
                merchantService.getMerchantStatus(loginUser).get("variables");
        model.addAttribute("merchantDetails",variables);
        return "merchant/merchantPending";
    }
    @GetMapping("/approved")
    public String getApprovedStatus(Model model){

        String loginUser= (String) authenticationManager.get("sub");


        Map<String,Object> variables = (Map<String, Object>)
                merchantService.getMerchantStatus(loginUser).get("variables");
        model.addAttribute("merchantDetails",variables);
        return "merchant/merchantStatus";
    }
    @GetMapping("/successful")
    public String merchantStatus(Model model) {


        String loginUser= (String) authenticationManager.get("sub");


        Map<String,Object> variables = (Map<String, Object>)
                merchantService.getMerchantStatus(loginUser).get("variables");
        model.addAttribute("merchantDetails",variables);
        return "merchant/merchantStatus";
    }
    @GetMapping("/declined")
    public String merchantFailed(HttpServletRequest request,Model model) {
        String loginUser= (String) authenticationManager.get("sub");

        request.getSession(true).setAttribute("submitAction","resubmit");

        Map<String,Object> variables = (Map<String, Object>)
                merchantService.getMerchantStatus(loginUser).get("variables");
        request.getSession(true).setAttribute("merchantDetails",variables);
        //System.out.println(" Inside merchantFailed variables=="+variables);
        model.addAttribute("merchantDetails",variables);

        return "merchant/merchantFailed";
    }

    @GetMapping("/orders")
    public String getOrders(Model model){
        List<Order> myOrders = merchantService.myOrders();

        BigDecimal total = myOrders.stream()
                .map(order -> order.getInvoice())
                .map(invoice -> invoice.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(" Orders lenght=="+myOrders.size());
        myOrders.forEach(order-> {
            System.out.println(" The Order here =="+order);
        });

        DecimalFormat df = new DecimalFormat("#,##0.00");

        model.addAttribute("totalAmount", df.format(total));
        model.addAttribute("orderCount", myOrders.size());
        model.addAttribute("myOrders", myOrders);

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

        //System.out.println(" Inside the case 1 merchantDetails=="+merchantDetails);
        model.addAttribute("merchantDetails",merchantDetails);
        String test = "'saveCompliance'";
        String nextFragment = null;
        switch (step) {
            case 1:

                nextFragment = "compliance/contact :: form2(data=${merchantDetails}, complianceButton="+test+")";
                break;
            case 2:
                nextFragment="compliance/businessOwner :: form3(data=${merchantDetails},complianceButton=${test})";
                break;
            case 3:
                nextFragment = "compliance/bankAccount :: form4(data=${merchantDetails}, complianceButton=${test})";
                break;
            case 4:
                nextFragment = "compliance/serviceAgreement :: form5(data=${merchantDetails})";
                break;
            case 5:
                //model.addAttribute("merchantDetails", merchantDetails); // Pass collected data
                nextFragment = "compliance/summary :: form6(showclass='', backButton='null', hideButton='null', data=${merchantDetails},someCondition= true)";
                break;
        }
        merchantDetails.putAll(formData);
        request.getSession(true).setAttribute("merchantDetails",merchantDetails);
        System.out.println(" The data sent now inside saveNewMerchant ==="+formData);
        //System.out.println("Data saved so far: " + merchantDetails);

        return nextFragment;
    }

     @PostMapping("/addNewTransfer")
    public String addTransfer(@RequestParam Map<String, String> requestParams) {

        requestParams.forEach((key, value) -> System.out.println(key + ": " + value));

        return "product/sucessfulPayment";
    }

}
