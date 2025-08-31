package mentorship.roadmap.microservices.service_a.repository;

import mentorship.roadmap.microservices.service_a.model.MessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<MessageDocument, String> {
}
