package mentorship.roadmap.microservices.service_c.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_c.dto.MessageRequest;
import mentorship.roadmap.microservices.service_c.dto.MessageResponse;
import mentorship.roadmap.microservices.service_c.entity.MessageEntity;
import mentorship.roadmap.microservices.service_c.messageRepository.MessageRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Slf4j
@Service
@RequiredArgsConstructor
public class SaveService {
    private final MessageRepository repository;
    private final KafkaTemplate<String, MessageRequest> kafkaTemplate;

    public MessageResponse save (MessageRequest request){

        MessageEntity entity = MessageEntity.builder()
                .id(request.getId())
                .content(request.getContent())
                .type(request.getType())
                .savedAt(Instant.now())
                .build();
        repository.save(entity);
        log.info("Saved to Posgres: {}", entity);

        kafkaTemplate.send("topic out", request);
        log.info("Publisher to kafka {}",request);
        return new MessageResponse("Ok", "Saved and Published");
    }

}
