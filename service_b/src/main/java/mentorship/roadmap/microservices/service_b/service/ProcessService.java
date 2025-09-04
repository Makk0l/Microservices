package mentorship.roadmap.microservices.service_b.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mentorship.roadmap.microservices.service_b.client.ServiceCClient;
import mentorship.roadmap.microservices.service_b.dto.MessageRequest;
import mentorship.roadmap.microservices.service_b.dto.MessageResponse;
import mentorship.roadmap.microservices.service_b.repository.RedisRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProcessService {

    private final ServiceCClient serviceCClient;
    private final RedisRepository repository;

    public MessageResponse process(MessageRequest request) {

        log.info("Processing request: {}", request);

        if ("important".equalsIgnoreCase(request.getType())) {
            repository.saveImportantMessage(request);
            log.info("Saved important message to Redis; {}", request.getId());
        } else {
            log.warn("Message not important. Request: {}", request);
        }
        MessageResponse response = serviceCClient.save(request);
        log.info("Response from service C: {}", response);
        return response;
    }
}
