package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
public class DemoApplicationTests {
    RestClient restClient = RestClient.create();
    ResponseEntity<String> result;

    @Test
    void fullTest() {
        System.out.println("===== create users =====");

        record UserTemplate(String username, String email) {}

        UserTemplate tom = new UserTemplate("Tom", "tom@gmail.com");
        result = restClient.post()
                .uri("http://localhost:8080/createUser")
                .contentType(APPLICATION_JSON)
                .body(tom)
                .retrieve()
                .toEntity(String.class);

        result = restClient.get()
                .uri("http://localhost:8080/getUsers")
                .retrieve()
                .toEntity(String.class);
        System.out.println(result);

        UserTemplate chloe = new UserTemplate("Chloe", "chloe@yahoo.com");
        result = restClient.post()
                .uri("http://localhost:8080/createUser")
                .contentType(APPLICATION_JSON)
                .body(chloe)
                .retrieve()
                .toEntity(String.class);

        result = restClient.get()
                .uri("http://localhost:8080/getUsers")
                .retrieve()
                .toEntity(String.class);
        System.out.println(result);

        record PollTemplate(Integer id, String question, List<VoteOption> options ) {}
        VoteOption voteOption1 = new VoteOption("one", 1);
        VoteOption voteOption2 = new VoteOption("two", 2);
        List<VoteOption> voteOptions = new ArrayList<>();
        voteOptions.add(voteOption1);
        voteOptions.add(voteOption2);
        PollTemplate tomPoll = new PollTemplate(1, "What is 1 + 1?", voteOptions);

        System.out.println("===== create poll =====");

        result = restClient.post()
                .uri("http://localhost:8080/createPoll/Tom")
                .contentType(APPLICATION_JSON)
                .body(tomPoll)
                .retrieve()
                .toEntity(String.class);

        result = restClient.get()
                .uri("http://localhost:8080/getPolls")
                .retrieve()
                .toEntity(String.class);
        System.out.println(result);

        System.out.println("===== vote poll =====");

        result = restClient.post()
                .uri("http://localhost:8080/createVote/1/1/Chloe")
                .retrieve()
                .toEntity(String.class);

        result = restClient.get()
                .uri("http://localhost:8080/getVotes/1")
                .retrieve()
                .toEntity(String.class);
        System.out.println(result);

        result = restClient.delete()
                .uri("http://localhost:8080/deleteVote/1/1/Chloe")
                .retrieve()
                .toEntity(String.class);

        result = restClient.post()
                .uri("http://localhost:8080/createVote/1/2/Chloe")
                .retrieve()
                .toEntity(String.class);

        result = restClient.get()
                .uri("http://localhost:8080/getVotes/1")
                .retrieve()
                .toEntity(String.class);
        System.out.println(result);

        System.out.println("===== delete poll =====");

        result = restClient.delete()
                .uri("http://localhost:8080/deletePoll/Tom/1")
                .retrieve()
                .toEntity(String.class);

        result = restClient.get()
                .uri("http://localhost:8080/getPolls")
                .retrieve()
                .toEntity(String.class);
        System.out.println(result);
    }
}