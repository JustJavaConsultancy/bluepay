package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.accounting.Account;
import com.techcrunch.bluepay.accounting.AccountService;
import com.techcrunch.bluepay.accounting.JournalLine;
import com.techcrunch.bluepay.chat.ChatMessage;
import com.techcrunch.bluepay.processes.CustomProcessService;
import com.techcrunch.bluepay.product.ProductDTO;
import com.techcrunch.bluepay.product.ProductRepository;
import com.techcrunch.bluepay.product.ProductService;
import com.techcrunch.bluepay.tasks.TaskDTO;
import com.techcrunch.bluepay.tasks.TaskRepository;
import com.techcrunch.bluepay.tasks.services.JsonFileReaderService;
import com.techcrunch.bluepay.transaction.TransactionDTO;
import com.techcrunch.bluepay.transaction.TransactionService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("merchantService")
public class MerchantService {
    private final AuthenticationManager authenticationManager;
    private final RuntimeService runtimeService;
    private final CustomProcessService processService;
    private final TaskRepository taskRepository;

    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;
    private final AccountService accountService;
    private final OrderRepository orderRepository;
    private final TransactionService transactionService;
    private final ProductService productService;
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    JsonFileReaderService jsonFileReaderService;


    public MerchantService(AuthenticationManager authenticationManager,
                           RuntimeService runtimeService,
                           CustomProcessService processService,
                           TaskRepository taskRepository,
                           MerchantRepository merchantRepository,
                           MerchantMapper merchantMapper, AccountService accountService,
                           OrderRepository orderRepository, TransactionService transactionService, ProductRepository productRepository, ProductService productService, SimpMessagingTemplate messagingTemplate) {
        this.authenticationManager = authenticationManager;
        this.runtimeService = runtimeService;
        this.processService = processService;
        this.taskRepository = taskRepository;
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
        this.accountService = accountService;
        this.orderRepository = orderRepository;
        this.transactionService = transactionService;
        this.productService = productService;
        this.messagingTemplate = messagingTemplate;
    }
    public Map<String,Object> getMerchantStatus(String merchantId){
        Map<String,Object> result=new HashMap<String,Object>();
        // Check for active process instance

        ProcessInstance activeProcessInstance=processService
                .getProcessInstanceByBusinessKey(merchantId);
        if(activeProcessInstance==null) {
            result.put("status", "NEW");
            result.put("variables",new HashMap<>());
            return result;
        }else {
            Map<String,Object> processVariables=processService
                    .getProcessInstanceVariables(activeProcessInstance.getProcessInstanceId());
/*            System.out.println(" activeProcessInstance.getProcessVariables()=="+
                    processVariables);*/
            result.put("status",processVariables.get("onboardStatus"));
            result.put("variables",processVariables);
        }
        return result;
    }
    public ProcessInstance submitMyDetail(Map<String,Object> data){

        data.put("onboardStatus","SUBMITTED");
        String loginUser= (String) authenticationManager.get("sub");
        return processService
                .startProcess("merchantOnboardingProcess",loginUser,data);
    }

    public List<TaskDTO> getMyTasks(){
        List<Task> tasks = taskRepository.getTaskByAssignee((String) authenticationManager.get("sub"));
        List<TaskDTO> taskDTOS = new ArrayList<>();
        tasks.forEach(task -> {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setTaskName(task.getName());
            taskDTO.setTaskId(task.getId());
            taskDTO.setFormKey(task.getFormKey());
            taskDTO.setCreatedDate(task.getCreateTime());
            taskDTO.setVariables(processService.getProcessInstanceVariables(task.getProcessInstanceId()));
            taskDTOS.add(taskDTO);
        });
        return taskDTOS;
    }
    public void completeDocumentResubmittion(Map<String,Object> variables){
        String loginId= (String) authenticationManager.get("sub");
        variables.put("onboardStatus","SUBMITTED");
        List<Task> tasks =taskRepository
                .getTaskByAssigneeAndProcessDefinitionKey(loginId,"merchantOnboardingProcess");
        Task task = tasks.size()>0?tasks.get(0):null;
        Map<String,Object> processVariables= processService.getProcessInstanceVariables(task.getProcessInstanceId());
        processVariables.putAll(variables);
        if(task!=null){
            taskRepository.completeTask(task.getId(),processVariables);
        }
    }
    public MerchantDto get(Long id) {
        return merchantMapper.toDto(merchantRepository.findById(id).orElseThrow());
    }
    public MerchantDto create(MerchantDto merchantDto) {
        return  merchantMapper.toDto(merchantRepository.save(merchantMapper.toEntity(merchantDto)));
    }
    public MerchantDto createMerchant(DelegateExecution execution) {
        Map<String,Object> variables = execution.getVariables();

        MerchantDto  merchantDto = MerchantDto.builder()
                .businessIdentity((String) variables.get("initiator"))
                .businessName((String) variables.get("businessName"))
                .build();

        merchantDto=create(merchantDto);
        accountService.createMerchantRelevantAccounts(merchantDto,variables);
/*        System.out.println(" The execution at this stage=="+execution.getVariables()
        + " The merchantDto created =="+merchantDto);*/
        return merchantDto;
    }
    public void createMerchantTest(Map<String,Object> variables) {
        MerchantDto  merchantDto = MerchantDto.builder()
                .businessIdentity((String) variables.get("initiator"))
                .businessName((String) variables.get("businessName"))
                .build();

        merchantDto=create(merchantDto);
        accountService.createMerchantRelevantAccounts(merchantDto,variables);
/*        System.out.println(" The execution at this stage=="+variables
                + " The merchantDto created =="+merchantDto);*/
    }
    public MerchantDto update(Long id, MerchantDto merchantDto) {
        Merchant merchant = merchantRepository.findById(id).orElseThrow();
        merchantMapper.partialUpdate(merchantDto, merchant);
        return merchantMapper.toDto(merchantRepository.save(merchant));

    }
    public List<Order> myOrders(){
        String loginUser= (String) authenticationManager.get("sub");
        return orderRepository.findByMerchantIdOrderByDateCreatedAsc(loginUser);
    }
    public List<ProductDTO> myProducts(){
        String loginUser= (String) authenticationManager.get("sub");
        return productService.findAllByMerchantId(loginUser);
    }
    public List<JournalLine> myBalances(){
        String loginUser= (String) authenticationManager.get("sub");
        return accountService.getMerchantJournalLinesByCode(loginUser,"payable");
    }
    public List<JournalLine> myAllAccountEntries(){
        String loginUser= (String) authenticationManager.get("sub");
        return accountService.getAllMerchantJournalLines(loginUser);
    }
    public Account myBankAccount(){
        String loginUser= (String) authenticationManager.get("sub");
        return accountService.getMerchantBankAccount(loginUser);
    }
    public Account myPayableAccount(){
        String loginUser= (String) authenticationManager.get("sub");
        return accountService.getMerchantPayableAccount(loginUser);
    }

    public List<TransactionDTO> myTransactions(){
        String loginUser = (String) authenticationManager.get("sub");
//        System.out.println(loginUser);
        return transactionService.getMerchantTransactions(loginUser);
    }

    public TransactionDTO singleTransaction(Long id){
        return transactionService.get(id);
    }
    public void monitorReceived(DelegateExecution execution){
        System.out.println(" The Delegate Task Here " +
                "in monitoring ===");
    }
    public void monitorFire(DelegateExecution execution){
        System.out.println(" The Variables Here monitorFire==" +execution.getVariables());
        ChatMessage chatMessage=ChatMessage.builder()
                .content(execution.getCurrentActivityName())
                .groupId((String) execution.getVariables().get("merchantId"))
                .sender("Monitoring Service")
                .build();
        String destination = "/topic/monitor/" + chatMessage.getGroupId();
        messagingTemplate.convertAndSend(destination, chatMessage);
        System.out.println(" monitoring is fired has it been caught? ==="+execution.getCurrentActivityName());
    }
}
