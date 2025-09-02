package mentorship.roadmap.microservices.service_b.client;

import mentorship.roadmap.microservices.service_b.dto.MessageRequest;
import mentorship.roadmap.microservices.service_b.dto.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "service-c", url = "{service-c.url}")
public interface ServiceCClient {

    @PostMapping("/api/save")
    MessageResponse save(@RequestBody MessageRequest request);
}
