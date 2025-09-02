package mentorship.roadmap.microservices.service_b.repository;

import lombok.RequiredArgsConstructor;
import mentorship.roadmap.microservices.service_b.dto.MessageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final StringRedisTemplate redisTemplate;
    private static final long TTL = 300;

    public void saveImportantMessage(MessageRequest request){
        redisTemplate.opsForValue().set(
                "important:" + request.getId(),
                request.getContent(),
                TTL, TimeUnit.SECONDS
        );
    }

}
