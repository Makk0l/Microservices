package mentorship.roadmap.microservices.service_a.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_a.client.ServiceBClient;
import mentorship.roadmap.microservices.service_a.dto.MessageRequest;
import mentorship.roadmap.microservices.service_a.dto.MessageResponse;
import mentorship.roadmap.microservices.service_a.model.MessageDocument;
import mentorship.roadmap.microservices.service_a.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final ServiceBClient serviceBClient;
    private final MessageRepository repository;

    public void processMessage(MessageRequest request){
        MessageDocument document = MessageDocument.builder()
                .id(request.getId())
                .content(request.getContent())
                .type(request.getType())
                .receivedAt(Instant.now())
                .build();
        repository.save(document);
        log.info("Saved message to MongoDB:{}", document);

        MessageResponse response = serviceBClient.process(request);
        log.info("Response from service B:{}", response);
    }
}
