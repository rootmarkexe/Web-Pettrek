package com.pettrek.backend.passport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.pettrek.backend.auth.dto.ApiResponses;
import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.auth.repos.UserRepo;
import com.pettrek.backend.auth.security.AuthUserDetailsService;
import com.pettrek.backend.auth.security.JwtTokenProvider;
import com.pettrek.backend.auth.security.UserPrincipal;
import com.pettrek.backend.passport.dto.PassportResponse;
import com.pettrek.backend.passport.dto.PetDto;
import com.pettrek.backend.passport.mapper.PetMapper;
import com.pettrek.backend.passport.models.Pet;
import com.pettrek.backend.passport.services.PetPassportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PassportController.class)
public class PassportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetPassportServiceImpl passportService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthUserDetailsService authUserDetailsService;
    private final Long TEST_USER_ID = 1L;
    private User testUser;
    private UserPrincipal testUserPrincipal;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @TestConfiguration
    static class TestConfig {
        @Bean
        public PetPassportServiceImpl passportService() {
            return Mockito.mock(PetPassportServiceImpl.class);
        }

        @Bean
        public UserRepo userRepo() {
            return Mockito.mock(UserRepo.class);
        }
        @Bean
        public JwtTokenProvider jwtTokenProvider() {
            return Mockito.mock(JwtTokenProvider.class);
        }
        @Bean AuthUserDetailsService authUserDetailsService(){
            return Mockito.mock(AuthUserDetailsService.class);
        }
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(TEST_USER_ID);
        testUser.setEmail("user@example.com");
        testUser.setName("Иван");
        testUser.setSecondName("Иванович");
        testUser.setSurname("Иванов");
        testUser.setDateOfBirth(LocalDate.of(1990, 1, 1));

        testUserPrincipal = new UserPrincipal(
                testUser.getId(),
                testUser.getEmail(),
                "password",
                true
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testUserPrincipal,
                null,
                testUserPrincipal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @WithMockUser(username = "mark@gmail.com")
    void getPassport_shouldReturnPassportResponse_whenUserIsAuthenticated() throws Exception {
        PetDto pet1 = new PetDto(
                "Барсик", "Кот", "Мужской", LocalDate.of(2020, 5, 15),
                "Британец", "Короткошёрстный", 4.5, "Очень пушистый", testUser
        );

        PetDto pet2 = new PetDto(
                "Шарик", "Собака", "Мужской", LocalDate.of(2018, 8, 10),
                "Лабрадор", "Гладкошёрстный", 20.0, "Добрый", testUser
        );
        PassportResponse expectedResponse = new PassportResponse(
                "Иван", "Иванович", "Иванов", LocalDate.of(1990, 1, 1),
                "user@example.com", List.of(pet1,pet2)
        );

        given(passportService.getUserWithPets()).willReturn(expectedResponse);

        mockMvc.perform(get("/api/passport/GetPassport")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Иван"))
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.secondName").value("Иванович"))
                .andExpect(jsonPath("$.surname").value("Иванов"))
                .andExpect(jsonPath("$.pets").isArray())
                .andExpect(jsonPath("$.pets[0].name").value("Барсик"))
                .andExpect(jsonPath("$.pets[0].specie").value("Кот"))
                .andExpect(jsonPath("$.pets[0].gender").value("Мужской"))
                .andExpect(jsonPath("$.pets[0].dateOfBirth").value("2020-05-15"))
                .andExpect(jsonPath("$.pets[0].breed").value("Британец"))
                .andExpect(jsonPath("$.pets[0].hair").value("Короткошёрстный"))
                .andExpect(jsonPath("$.pets[0].weight").value(4.5))
                .andExpect(jsonPath("$.pets[0].feature").value("Очень пушистый"))
                .andExpect(jsonPath("$.pets[1].name").value("Шарик"))
                .andExpect(jsonPath("$.pets[1].specie").value("Собака"))
                .andExpect(jsonPath("$.pets[1].gender").value("Мужской"))
                .andExpect(jsonPath("$.pets[1].dateOfBirth").value("2018-08-10"))
                .andExpect(jsonPath("$.pets[1].breed").value("Лабрадор"))
                .andExpect(jsonPath("$.pets[1].hair").value("Гладкошёрстный"))
                .andExpect(jsonPath("$.pets[1].weight").value(20.0))
                .andExpect(jsonPath("$.pets[1].feature").value("Добрый"));
    }

    @Test
    @WithMockUser(username = "mark@gmail.com")
    void createUser_shouldReturnResponseEntity() throws Exception{
        PetDto pet1 = new PetDto(
                "Барсик", "Кот", "Мужской", LocalDate.of(2020, 5, 15),
                "Британец", "Короткошёрстный", 4.5, "Очень пушистый", testUser
        );

        Pet expectedResponse = PetMapper.toEntity(pet1);

        given(passportService.createPetPassport(pet1)).willReturn(expectedResponse);

        mockMvc.perform(post("/api/passport/create-passport")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("Питомец добавлен"));
    }
}
