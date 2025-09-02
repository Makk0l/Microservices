package mentorship.roadmap.microservices.service_b.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import mentorship.roadmap.microservices.service_b.controller.ProcessController;
import mentorship.roadmap.microservices.service_b.dto.MessageRequest;
import mentorship.roadmap.microservices.service_b.dto.MessageResponse;
import mentorship.roadmap.microservices.service_b.service.ProcessService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ProcessController.class)
public class ProcessControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProcessService service;

    @Test
    void process_returnsResponse() throws Exception {
        MessageRequest request = MessageRequest.builder()
                .id("1")
                .content("Hi")
                .type("normal")
                .build();
        MessageResponse response = new MessageResponse("Ok", "Saved");

        Mockito.when(service.process(request)).thenReturn(response);

        mockMvc.perform(post("/api/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Ok"))
                .andExpect(jsonPath("$.message").value("Saved"));
    }

}
