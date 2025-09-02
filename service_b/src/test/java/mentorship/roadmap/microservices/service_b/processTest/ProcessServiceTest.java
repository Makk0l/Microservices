package mentorship.roadmap.microservices.service_b.processTest;

import mentorship.roadmap.microservices.service_b.client.ServiceCClient;
import mentorship.roadmap.microservices.service_b.dto.MessageRequest;
import mentorship.roadmap.microservices.service_b.dto.MessageResponse;
import mentorship.roadmap.microservices.service_b.repository.RedisRepository;
import mentorship.roadmap.microservices.service_b.service.ProcessService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProcessServiceTest {
    @Mock
    private ServiceCClient client;

    @Mock
    private RedisRepository repository;

    @InjectMocks
    private ProcessService service;

    @Test
    void process_normalMessage_callsServiceC_only(){
        MessageRequest request = MessageRequest.builder()
                .id("1")
                .content("Hi")
                .type("normal")
                .build();
        Mockito.when(client.save(request))
                .thenReturn(new MessageResponse("Ok", "Saved"));

        MessageResponse response = service.process(request);

        Mockito.verify(repository, Mockito.never()).saveImportantMessage(Mockito.any());
        Mockito.verify(client, Mockito.times(1)).save(request);
        Assertions.assertThat(response.getStatus()).isEqualTo("Ok");
    }

    @Test
    void process_importantMessage_savesToRedis_andCallsServiceC(){

        MessageRequest request = MessageRequest.builder()
                .id("1")
                .content("Hi")
                .type("important")
                .build();
        Mockito.when(client.save(request))
                .thenReturn(new MessageResponse("Ok", "Saved"));

        service.process(request);

        Mockito.verify(repository, Mockito.times(1)).saveImportantMessage(request);
        Mockito.verify(client, Mockito.times(1)).save(request);
    }
}
