package com.oreilly.demo.controllers;

import com.oreilly.demo.entities.Greeting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloRestControllerTests {

    TestRestTemplate template;

    //The first test uses the getForEntity method of the template, which returns a ResponseEntity<Greeting>.
    // The response entity gives access to the headers, so the two provided asserts check the status code and the media type of the response.
    // The actual response is inside the body.
    // By calling getBody, the response is returned as a de-serialized Greeting instance, which allows you to check its message.
    @Test
    public void greetWithoutName() {
        ResponseEntity<Greeting> entity = template.getForEntity("/rest", Greeting.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, entity.getHeaders().getContentType());
        Greeting response = entity.getBody();
        assertEquals("Hello, World!", response.getGreeting());
    }

    //The second test uses the getForObject method, which returns the de-serialized response directly.
    // This is simpler, but does not allow access to the headers. You can use either approach in your code.
    @Test
    public void greetWithName() {
        Greeting response = template.getForObject("/rest?name=Dolly", Greeting.class);
        assertEquals("Hello, Dolly!", response.getGreeting());
    }
}
