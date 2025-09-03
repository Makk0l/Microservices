package mentorship.roadmap.microservices.service_c.messageRepository;

import mentorship.roadmap.microservices.service_c.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {
}
