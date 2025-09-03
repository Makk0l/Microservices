package mentorship.roadmap.microservices.service_c.serviceTest;

import mentorship.roadmap.microservices.service_c.dto.MessageRequest;
import mentorship.roadmap.microservices.service_c.dto.MessageResponse;
import mentorship.roadmap.microservices.service_c.entity.MessageEntity;
import mentorship.roadmap.microservices.service_c.messageRepository.MessageRepository;
import mentorship.roadmap.microservices.service_c.service.SaveService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;

@ExtendWith(MockitoExtension.class)
public class SaveServiceTest {
    @Mock
    private MessageRepository repository;

    @Mock
    private KafkaTemplate<String, MessageRequest> kafkaTemplate;

    @InjectMocks
    private SaveService saveService;

    @Test
    public void save_persistsEntity_andPublishesToKafka(){
        MessageRequest request = MessageRequest.builder()
                .id("123")
                .content("Hello")
                .type("normal")
                .build();

        MessageResponse response = saveService.save(request);

        ArgumentCaptor<MessageEntity> captor = ArgumentCaptor.forClass(MessageEntity.class);
        Mockito.verify(repository, Mockito.times(1)).save(captor.capture());
        Assertions.assertThat(captor.getValue().getId()).isEqualTo("123");
        Assertions.assertThat(captor.getValue().getSavedAt()).isBeforeOrEqualTo(Instant.now());

        Mockito.verify(kafkaTemplate, Mockito.times(1)).send("topic-out", request);
        Assertions.assertThat(response.getStatus()).isEqualTo("Ok");
    }
}
