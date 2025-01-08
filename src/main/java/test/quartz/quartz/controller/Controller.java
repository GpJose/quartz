package test.quartz.quartz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import test.quartz.quartz.model.QuartzEntity;
import test.quartz.quartz.model.TestDTO;
import test.quartz.quartz.repository.QuartzRepository;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class Controller {

    private final QuartzRepository quartzRepository;
    private final ObjectMapper objectMapper;

    @PutMapping("add")
    public void add(@RequestBody QuartzEntity quartz) {
        quartzRepository.save(quartz);
    }

    @PostMapping(path = "test")
    public void test(@RequestBody(required = false) String body,
    @RequestHeader Map<String, String> headers) throws JsonProcessingException {
        TestDTO testDTO = objectMapper.readValue(body,TestDTO.class);
        log.info("Request Body: {}", testDTO.toString());
        log.info("Request Headers: {}", headers);
    }
}
