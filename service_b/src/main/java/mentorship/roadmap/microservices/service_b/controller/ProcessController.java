package mentorship.roadmap.microservices.service_b.controller;

import lombok.RequiredArgsConstructor;
import mentorship.roadmap.microservices.service_b.dto.MessageRequest;
import mentorship.roadmap.microservices.service_b.dto.MessageResponse;
import mentorship.roadmap.microservices.service_b.service.ProcessService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProcessController {
    private final ProcessService service;

    @PostMapping("/process")
    public MessageResponse response(@RequestBody MessageRequest request){
        return service.process(request);
    }
}
