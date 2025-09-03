package mentorship.roadmap.microservices.service_c.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import mentorship.roadmap.microservices.service_c.conroller.SaveController;
import mentorship.roadmap.microservices.service_c.dto.MessageRequest;
import mentorship.roadmap.microservices.service_c.dto.MessageResponse;
import mentorship.roadmap.microservices.service_c.service.SaveService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;


@WebMvcTest(SaveController.class)
public class SaveControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SaveService saveService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void save_returnsResponse() throws Exception {
        MessageRequest req = MessageRequest.builder()
                .id("321")
                .content("Test Controller")
                .type("normal")
                .build();

        MessageResponse res = new MessageResponse("OK", "Saved and published");

        Mockito.when(saveService.save(req)).thenReturn(res);

        mockMvc.perform(post("/api/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Saved and published"));
    }
}
