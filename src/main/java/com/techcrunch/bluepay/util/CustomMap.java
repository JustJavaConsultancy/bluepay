package com.techcrunch.bluepay.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomMap {
    private String key;
    private Object value;
}
