package com.ttt.InsightAI.controller;

import com.ttt.InsightAI.domain.Analysis;
import com.ttt.InsightAI.domain.Diary;
import com.ttt.InsightAI.domain.User;
import com.ttt.InsightAI.repository.UserRepository;
import com.ttt.InsightAI.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입과 로그인
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Name is required"));        }
        if (userRepository.findByName(user.getName()) != null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "User with this name already exists"));
        }
        userRepository.save(user);
        return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        User existingUser = userRepository.findByName(user.getName());
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid name or password"));
        }
        LOGGER.info("User logged in: {}", user.getName());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User logged in");
        response.put("userId", existingUser.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}/diaries")
    public ResponseEntity<List<Diary>> getUserDiaries(@PathVariable String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Set<Diary> diariesSet = user.getDiaries();
        List<Diary> diariesList = diariesSet.stream().collect(Collectors.toList());

        return ResponseEntity.ok(diariesList);
    }

    @GetMapping("/{name}/analyses")
    public ResponseEntity<List<Analysis>> getUserAnalyses(@PathVariable String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Set<Analysis> analysisSet = user.getAnalyses();
        List<Analysis> analysisList = analysisSet.stream().collect(Collectors.toList());

        return ResponseEntity.ok(analysisList);
    }
}
