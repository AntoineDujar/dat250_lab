package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class DemoApplication {
    private final List<User> users = new ArrayList<>();
    private List<Poll> polls = new ArrayList<>();

    private void updatePolls() {
        List<Poll> updatedPolls = new ArrayList<>();
        for (User tempUser : users) {
            List<Poll> tempUserPolls = tempUser.getCreatedPolls();
            updatedPolls.addAll(tempUserPolls);
        }
        polls = updatedPolls;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return users;
    }

    @PostMapping("/createUser")
    public ResponseEntity<Response> createUser(@RequestBody User user) {
        users.add(user);
        Response response = new Response();
        response.setMessage(String.format("Name: %s, Email: %s", user.getUsername(), user.getEmail()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateUser/{username}")
    public ResponseEntity<Response> updateUser(@PathVariable String username, @RequestBody User user) {
        for (User tempUser : users) {
            if (tempUser.getUsername().equalsIgnoreCase(username)) {
                tempUser.setEmail(user.getEmail());
            }
        }
        Response response = new Response();
        response.setMessage(String.format("Name: %s, Email: %s", user.getUsername(), user.getEmail()));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Response> deleteUser(@RequestBody User user) {
        Response response = new Response();
        boolean removed = users.removeIf(tempUser -> tempUser.getUsername().equalsIgnoreCase(user.getUsername()));
        if (removed) {
            users.remove(user);
            response.setMessage(String.format("Name: %s, Email: %s Deleted", user.getUsername(), user.getEmail()));
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("User cannot be found");
            return ResponseEntity.ok(response);
        }
    }

//    POLLS

    public record IncomingPollDetails(Integer id, String question, List<VoteOption> options) {}

    @GetMapping("/getPolls")
    public List<Poll> getPolls() {
        return polls;
    }

    @PostMapping("/createPoll/{username}")
    public ResponseEntity<Response> createPoll(@PathVariable String username, @RequestBody IncomingPollDetails IncPoll) {
        Response response = new Response();
        for (User tempUser : users) {
            if (tempUser.getUsername().equalsIgnoreCase(username)) {
                Poll tempPoll = new Poll(IncPoll.id(), IncPoll.question(), IncPoll.options(), username);
                List<Poll> tempUserPolls = tempUser.getCreatedPolls();
                tempUserPolls.add(tempPoll);
                tempUser.setCreatedPolls(tempUserPolls);
                updatePolls();
                response.setMessage("Created Poll");
                return ResponseEntity.ok(response);
            }
        }
        response.setMessage("User not found");
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/deletePoll/{username}/{id}")
    public ResponseEntity<Response> deletePoll(@PathVariable String username, @PathVariable String id) {
        Response response = new Response();
        for (User tempUser : users) {
            if (tempUser.getUsername().equalsIgnoreCase(username)) {
                List<Poll> tempUserPolls = tempUser.getCreatedPolls();
                for (Poll tempPoll : tempUserPolls) {
                    System.out.println(tempPoll.getQuestion());
                    if (tempPoll.getId().equals(Integer.parseInt(id))) {
                        tempUserPolls.remove(tempPoll);
                        tempUser.setCreatedPolls(tempUserPolls);
                        polls.remove(tempPoll);
                        updatePolls();
                        response.setMessage("Deleted Poll");
                        return ResponseEntity.ok(response);
                    }
                }
                response.setMessage("Poll question not found");
                return ResponseEntity.badRequest().body(response);
            }
        }
        response.setMessage("User not found");
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/updatePoll/{pollId}")
    public ResponseEntity<Response> updatePoll(@PathVariable String pollId, @RequestBody IncomingPollDetails IncPoll) {
        Response response = new Response();
        for (Poll tempPoll : polls) {
            if (tempPoll.getId().equals(Integer.parseInt(pollId))) {
                String username  = tempPoll.getUsername();
                Poll updatePoll = new Poll(IncPoll.id(), IncPoll.question(), IncPoll.options(), username);
                tempPoll.setQuestion(updatePoll.getQuestion());
                tempPoll.setOptions(updatePoll.getOptions());
                updatePolls();
                response.setMessage("Poll updated");
                return ResponseEntity.ok(response);
            }
        }
        response.setMessage("Poll not found");
        return ResponseEntity.badRequest().body(response);
    }

//    VOTE OPTIONS
    public record IncomingVoteOptionDetails(String caption, Integer presentationOrder) {}

    @GetMapping("/getVoteOption/{pollId}/{presentationOrder}")
    public VoteOption getVoteOption(@PathVariable String pollId, @PathVariable String presentationOrder) {
        for (Poll tempPoll : polls) {
            if (tempPoll.getId().equals(Integer.parseInt(pollId))) {
                for (VoteOption tempVoteOption : tempPoll.getOptions()) {
                    if (tempVoteOption.getPresentationOrder().equals(Integer.parseInt(presentationOrder))) {
                        return tempVoteOption;
                    }
                }
            }
        }
        return null;
    }

    @PostMapping("/createVoteOption/{pollId}")
    public ResponseEntity<Response> createVoteOption(@PathVariable String pollId, @RequestBody IncomingVoteOptionDetails IncVoteOption) {
        Response response = new Response();
        for (Poll tempPoll : polls) {
            if (tempPoll.getId().equals(Integer.parseInt(pollId))) {
                List<VoteOption> tempVoteOptions =  tempPoll.getOptions();
                VoteOption newVoteOption = new VoteOption(IncVoteOption.caption(), IncVoteOption.presentationOrder());
                tempVoteOptions.add(newVoteOption);
                response.setMessage("Vote option added");
                return ResponseEntity.ok(response);
            }
        }
        response.setMessage("Poll not found, vote option not created");
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/deleteVoteOption/{pollId}/{presentationOrder}")
    public ResponseEntity<Response> deleteVoteOption(@PathVariable String pollId, @PathVariable String presentationOrder) {
        Response response = new Response();
        for (Poll tempPoll : polls) {
            if (tempPoll.getId().equals(Integer.parseInt(pollId))) {
                for (VoteOption tempVoteOption : tempPoll.getOptions()) {
                    if (tempVoteOption.getPresentationOrder().equals(Integer.parseInt(presentationOrder))) {
                        tempPoll.getOptions().remove(tempVoteOption);
                        response.setMessage("Vote option removed");
                        return ResponseEntity.ok(response);
                    }
                }
            }
        }
        response.setMessage("Vote option not found");
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/updateVoteOption/{pollId}/{presentationOrder}")
    public ResponseEntity<Response> updateVoteOption(@PathVariable String pollId, @PathVariable String presentationOrder, @RequestBody IncomingVoteOptionDetails IncVoteOption) {
        Response response = new Response();
        for (Poll tempPoll : polls) {
            if (tempPoll.getId().equals(Integer.parseInt(pollId))) {
                for (VoteOption tempVoteOption : tempPoll.getOptions()) {
                    if (tempVoteOption.getPresentationOrder().equals(Integer.parseInt(presentationOrder))) {
                        tempVoteOption.setCaption(IncVoteOption.caption());
                        tempVoteOption.setPresentationOrder(IncVoteOption.presentationOrder());
                        response.setMessage("Vote option updated");
                        return ResponseEntity.ok(response);
                    }
                }
            }
        }
        response.setMessage("Poll not found");
        return ResponseEntity.badRequest().body(response);
    }

//    VOTES

    private String findVoteUsernames(Integer presentationOrder) {
        String usernames = "";
        for (User tempUser : users) {
            for (Vote tempVote : tempUser.getVotedPolls()) {
                if (tempVote.getVoteOption().getPresentationOrder().equals(presentationOrder)) {
                    usernames = usernames.concat(' ' +tempUser.getUsername());
                }
            }
        }
        return usernames;
    }

    @GetMapping("/getVotes/{pollId}")
    public String getVoteOption(@PathVariable String pollId) {
        String tempResponse;
        for (Poll tempPoll : polls) {
            if (tempPoll.getId().equals(Integer.parseInt(pollId))) {
                tempResponse = tempPoll.getQuestion();
                for (VoteOption tempVoteOption : tempPoll.getOptions()) {
                    tempResponse = tempResponse.concat('\n' + tempVoteOption.getCaption());
//                    get usernames
                    tempResponse = tempResponse.concat('\n' + findVoteUsernames(tempVoteOption.getPresentationOrder())) + '\n';
                }
                return tempResponse;
            }
        }
        return null;
    }

    @PostMapping("/createVote/{pollId}/{presentationOrder}/{username}")
    public ResponseEntity<Response> createVote(@PathVariable String pollId, @PathVariable String presentationOrder, @PathVariable String username) {
        Response response = new Response();
        for (Poll tempPoll : polls) {
            if (tempPoll.getId().equals(Integer.parseInt(pollId))) {
                for (VoteOption tempVoteOption : tempPoll.getOptions()) {
                    if (tempVoteOption.getPresentationOrder().equals(Integer.parseInt(presentationOrder))) {
                        Vote tempVote = new Vote(tempVoteOption, username, Integer.parseInt(pollId));
                        for (User tempUser : users) {
                            if (tempUser.getUsername().equals(username)) {
                                tempUser.getVotedPolls().add(tempVote);
                                response.setMessage("Vote added");
                                return ResponseEntity.ok(response);
                            }
                        }
                    }
                }
            }
        }
        response.setMessage("Poll not found, vote not created");
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/deleteVote/{pollId}/{presentationOrder}/{username}")
    public ResponseEntity<Response> deleteVote(@PathVariable String pollId, @PathVariable String presentationOrder, @PathVariable String username) {
        Response response = new Response();
        for (User tempUser : users) {
            if (tempUser.getUsername().equals(username)) {
                for (Vote tempVote : tempUser.getVotedPolls()) {
                    if (tempVote.getPollId().equals(Integer.parseInt(pollId))) {
                        if (tempVote.getVoteOption().getPresentationOrder().equals(Integer.parseInt(presentationOrder))) {
                            tempUser.getVotedPolls().remove(tempVote);
                            response.setMessage("Vote removed");
                            return ResponseEntity.ok(response);
                        }
                    }
                }
            }
        }
        response.setMessage("Vote not found");
        return ResponseEntity.badRequest().body(response);
    }
}