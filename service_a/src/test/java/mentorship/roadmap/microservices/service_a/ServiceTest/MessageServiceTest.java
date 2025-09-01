package mentorship.roadmap.microservices.service_a.ServiceTest;

import mentorship.roadmap.microservices.service_a.client.ServiceBClient;
import mentorship.roadmap.microservices.service_a.dto.MessageRequest;
import mentorship.roadmap.microservices.service_a.dto.MessageResponse;
import mentorship.roadmap.microservices.service_a.model.MessageDocument;
import mentorship.roadmap.microservices.service_a.repository.MessageRepository;
import mentorship.roadmap.microservices.service_a.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.assertj.core.api.Assertions;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageService service;

    @Mock
    private MessageRepository repository;

    @Mock
    private ServiceBClient client;

    @Test
    void processMessage_savesToMongo_andCallsServiceB() {
        MessageRequest request = MessageRequest.builder()
                .id("1")
                .content("Hi")
                .type("middle")
                .build();
        when(client.process(any(MessageRequest.class)))
                .thenReturn(new MessageResponse("Ok", "Processed"));

        service.processMessage(request);
        ArgumentCaptor<MessageDocument> docCaptor = ArgumentCaptor.forClass(MessageDocument.class);
        verify(repository, times(1)).save(docCaptor.capture());
        MessageDocument saved = docCaptor.getValue();
        Assertions.assertThat(saved.getId()).isEqualTo("1");
        Assertions.assertThat(saved.getContent()).isEqualTo("Hi");
        Assertions.assertThat(saved.getReceivedAt()).isBeforeOrEqualTo(Instant.now());

        verify(client, times(1)).process(request);
    }
}
