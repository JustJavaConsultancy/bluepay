package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.account.AuthenticationManager;
import com.techcrunch.bluepay.processes.CustomProcessService;
import com.techcrunch.bluepay.tasks.TaskDTO;
import com.techcrunch.bluepay.tasks.TaskRepository;
import com.techcrunch.bluepay.tasks.services.JsonFileReaderService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerchantService {
    private final AuthenticationManager authenticationManager;
    private final RuntimeService runtimeService;
    private final CustomProcessService processService;
    private final TaskRepository taskRepository;

    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;
    @Autowired
    JsonFileReaderService jsonFileReaderService;


    public MerchantService(AuthenticationManager authenticationManager,
                           RuntimeService runtimeService,
                           CustomProcessService processService,
                           TaskRepository taskRepository,
                           MerchantRepository merchantRepository,
                           MerchantMapper merchantMapper) {
        this.authenticationManager = authenticationManager;
        this.runtimeService = runtimeService;
        this.processService = processService;
        this.taskRepository = taskRepository;
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
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
    public void createMerchant(DelegateExecution execution) {
        System.out.println(" The execution at this stage=="+execution.getVariables());
    }
    public MerchantDto update(Long id, MerchantDto merchantDto) {
        Merchant merchant = merchantRepository.findById(id).orElseThrow();
        merchantMapper.partialUpdate(merchantDto, merchant);
        return merchantMapper.toDto(merchantRepository.save(merchant));

    }

}
