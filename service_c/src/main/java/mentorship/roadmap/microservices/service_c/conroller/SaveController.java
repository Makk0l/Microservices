package mentorship.roadmap.microservices.service_c.conroller;

import lombok.RequiredArgsConstructor;
import mentorship.roadmap.microservices.service_c.dto.MessageRequest;
import mentorship.roadmap.microservices.service_c.dto.MessageResponse;
import mentorship.roadmap.microservices.service_c.service.SaveService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SaveController {
    private final SaveService service;

    @PostMapping("/save")
    public MessageResponse save(@RequestBody MessageRequest request){
        return service.save(request);
    }
}
