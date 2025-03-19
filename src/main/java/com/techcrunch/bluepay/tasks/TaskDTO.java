package com.techcrunch.bluepay.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private String taskName;
    private String taskId;
    private Date createdDate;
    private String formKey;
    private Map<String,Object> variables;
}
