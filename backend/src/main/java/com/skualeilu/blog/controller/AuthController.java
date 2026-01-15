package com.skualeilu.blog.controller;

import com.skualeilu.blog.dto.auth.LoginRequest;
import com.skualeilu.blog.dto.auth.LoginResponse;
import com.skualeilu.blog.dto.auth.ChangePasswordRequest;
import com.skualeilu.blog.dto.auth.ChangeUsernameRequest;
import com.skualeilu.blog.entity.User;
import com.skualeilu.blog.repository.UserRepository;
import com.skualeilu.blog.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final JwtTokenProvider tokenProvider;
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
                this.authenticationManager = authenticationManager;
                this.tokenProvider = tokenProvider;
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @PostMapping("/login")
        public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginRequest.getUsername(),
                                                loginRequest.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = tokenProvider.generateToken(authentication);
                return ResponseEntity.ok(new LoginResponse(jwt));
        }

        @PostMapping("/change-password")
        public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();

                // Verify old password
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(username, request.getOldPassword()));

                // Update new password
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);

                return ResponseEntity.ok().body("Password changed successfully");
        }

        @PostMapping("/change-username")
        @org.springframework.transaction.annotation.Transactional
        public ResponseEntity<?> changeUsername(@RequestBody ChangeUsernameRequest request) {
                String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

                // 1. Verify password
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(currentUsername, request.getPassword()));

                // 2. Check if new username is taken
                if (userRepository.existsByUsername(request.getNewUsername())) {
                        return ResponseEntity.badRequest().body("Username is already taken!");
                }

                // 3. Update username
                User user = userRepository.findByUsername(currentUsername)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                user.setUsername(request.getNewUsername());
                userRepository.save(user);

                // 4. Update SecurityContext to reflect the change immediately
                // This prevents issues where downstream filters might try to load the user
                // using the old username
                Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
                UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                                request.getNewUsername(),
                                null,
                                currentAuth.getAuthorities());
                newAuth.setDetails(currentAuth.getDetails());
                SecurityContextHolder.getContext().setAuthentication(newAuth);

                // 5. Invalidate old token logic is client-side (client should re-login)
                return ResponseEntity.ok().body("Username changed successfully. Please login again with new username.");
        }
}