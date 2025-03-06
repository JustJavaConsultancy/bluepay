package com.techcrunch.bluepay.tasks;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TaskInfo {
    private String schema;
    private String data;
}
