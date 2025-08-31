package mentorship.roadmap.microservices.service_a.client;

import mentorship.roadmap.microservices.service_a.dto.MessageRequest;
import mentorship.roadmap.microservices.service_a.dto.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "service-b", url = "${service-b.url}")
public interface ServiceBClient {
    @PostMapping("/api/process")
    MessageResponse process(@RequestBody MessageRequest request);
}
