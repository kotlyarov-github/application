package ru.kotlyarov.spring.application

import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
class LoginTests {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun contextLoads() {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("Hello guest")))
    }

    @Test
    fun failureLoginTest() {
        mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("http://localhost/login"))
    }

    @Test
    @Sql(value = ["/create-user-before.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun successLoginTest() {
        mockMvc.perform(formLogin().user("admin").password("pwd"))
                .andDo(print())
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("/"))
    }

    @Test
    fun unknownUserTest() {
        mockMvc.perform(post("/login").param("user", "Test"))
                .andDo(print())
                .andExpect(status().isForbidden)
    }


}
