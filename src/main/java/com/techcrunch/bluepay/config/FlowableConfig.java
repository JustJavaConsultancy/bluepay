package com.techcrunch.bluepay.config;

import com.techcrunch.bluepay.JustJavaFlowableListener;
import jakarta.annotation.PostConstruct;
import org.flowable.common.engine.impl.event.FlowableEventDispatcherImpl;
import org.flowable.engine.ProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class FlowableConfig {
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @PostConstruct
    public void registerEventListener() {
        if (processEngineConfiguration.getEventDispatcher() == null) {
            processEngineConfiguration.setEventDispatcher(new FlowableEventDispatcherImpl());
        }
        processEngineConfiguration.getEventDispatcher().addEventListener(new JustJavaFlowableListener(messagingTemplate));
    }
}
