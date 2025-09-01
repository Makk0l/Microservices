package mentorship.roadmap.microservices.service_a.ListenerTest;

import mentorship.roadmap.microservices.service_a.dto.MessageRequest;
import mentorship.roadmap.microservices.service_a.kafka.MessageListener;
import mentorship.roadmap.microservices.service_a.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MessageListenerTest {

    @InjectMocks
    private MessageListener listener;

    @Mock
    private MessageService service;

    @Test
    void listen_callMessageService(){

        MessageRequest request = MessageRequest.builder()
                .id("1")
                .content("Hi")
                .type("middle")
                .build();

        listener.listen(request);

        verify(service, times(1)).processMessage(request);
    }
}
