package com.anykeysolutions.controller.voice;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling Twilio voice calls.
 * This controller provides webhooks for Twilio to handle incoming voice calls.
 */
@RestController
@RequestMapping("/api/voice")
@Tag(name = "Voice", description = "Twilio voice call handling operations")
public class VoiceController {

    /**
     * Webhook endpoint for handling incoming Twilio voice calls.
     * This endpoint is called by Twilio when a call is received on the configured phone number.
     * 
     * @return TwiML response with voice instructions in Bulgarian
     */
    @PostMapping(value = "/incoming", produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Handle incoming voice call", 
               description = "Twilio webhook endpoint for handling incoming voice calls. Returns TwiML response with Bulgarian voice message.")
    public ResponseEntity<String> handleIncomingCall() {
        try {
            // Create a Say verb with Bulgarian language and voice
            Say say = new Say.Builder("Здравейте! Свързахте се със сервиза. За да запишете час, кажете проблема с автомобила след сигнала.")
                    .language(Say.Language.BG_BG)
                    .voice(Say.Voice.POLLY_MAJA)
                    .build();

            // Create the TwiML voice response
            VoiceResponse twiml = new VoiceResponse.Builder()
                    .say(say)
                    .build();

            // Return the TwiML response as XML
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(twiml.toXml());

        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error creating TwiML with Polly voice: " + e.getMessage());

            // Fallback response in case of any errors
            try {
                Say fallbackSay = new Say.Builder("Здравейте! Свързахте се със сервиза. За да запишете час, кажете проблема с автомобила след сигнала.")
                        .language(Say.Language.BG_BG)
                        .build();

                VoiceResponse fallbackTwiml = new VoiceResponse.Builder()
                        .say(fallbackSay)
                        .build();

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_XML)
                        .body(fallbackTwiml.toXml());

            } catch (Exception fallbackException) {
                // Log the fallback error
                System.err.println("Error creating fallback TwiML: " + fallbackException.getMessage());

                // Ultimate fallback - simple TwiML without specific voice settings
                String simpleTwiml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<Response>" +
                        "<Say language=\"bg-BG\">Здравейте! Свързахте се със сервиза. За да запишете час, кажете проблема с автомобила след сигнала.</Say>" +
                        "</Response>";

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_XML)
                        .body(simpleTwiml);
            }
        }
    }

    /**
     * Health check endpoint for the voice controller.
     * Can be used to verify that the voice controller is working properly.
     * 
     * @return Simple status message
     */
    @GetMapping("/health")
    @Operation(summary = "Voice controller health check", 
               description = "Simple health check endpoint to verify voice controller is operational")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Voice controller is operational");
    }
}
