package mentorship.roadmap.microservices.service_a.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_a.dto.MessageRequest;
import mentorship.roadmap.microservices.service_a.service.MessageService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {
    private final MessageService service;

    @KafkaListener(topics = "topic in", groupId = "service-a")
    public void listen(MessageRequest request){
        log.info("Received message from kafka:", request);
        service.processMessage(request);
    }
}
