package com.anykeysolutions.controller.voice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for VoiceController.
 * Tests the Twilio voice webhook endpoints.
 */
@WebMvcTest(VoiceController.class)
class VoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHandleIncomingCall_ReturnsValidTwiML() throws Exception {
        mockMvc.perform(post("/api/voice/incoming"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<Response>")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("<Say")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("language=\"bg-BG\"")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("</Say>")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("</Response>")));
    }

    @Test
    void testHandleIncomingCall_ContainsBulgarianLanguage() throws Exception {
        mockMvc.perform(post("/api/voice/incoming"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("bg-BG")));
    }

    @Test
    void testHealthCheck_ReturnsOk() throws Exception {
        mockMvc.perform(get("/api/voice/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Voice controller is operational"));
    }

    @Test
    void testIncomingCallEndpoint_AcceptsPostOnly() throws Exception {
        // POST should work
        mockMvc.perform(post("/api/voice/incoming"))
                .andExpect(status().isOk());

        // GET should not be allowed (405 Method Not Allowed)
        mockMvc.perform(get("/api/voice/incoming"))
                .andExpect(status().isMethodNotAllowed());
    }
}
