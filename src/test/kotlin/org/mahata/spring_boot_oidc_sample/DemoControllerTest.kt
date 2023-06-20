package org.mahata.spring_boot_oidc_sample

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Client
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DemoControllerTest {
    @Autowired lateinit var mockMvc: MockMvc

    @Test
    fun `When a user is not authorized, it redirects the user to a Authorization Server`() {
        mockMvc.perform(get("/demo"))
            .andExpect(status().is3xxRedirection)
    }

    @Test
    fun `When a user is authorized, it returns HTTP 200 status code`() {
        mockMvc.perform(
            get("/demo")
                .with(oauth2Login())
                .with(oauth2Client("github"))
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `When a user is authorized, it outputs user information in JSON`() {
        val user = DefaultOAuth2User(
            emptyList(),
            mapOf(
                "name" to "SOMEONE",
                "email" to "someone@example.com",
            ),
            "name"
        )

        mockMvc.perform(
            get("/demo")
                .with(oauth2Login().oauth2User(user))
                .with(oauth2Client("github"))
        )
            .andExpect(jsonPath("$.name").value("SOMEONE"))
            .andExpect(jsonPath("$.email").value("someone@example.com"))
    }
}
