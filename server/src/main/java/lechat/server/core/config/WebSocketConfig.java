package lechat.server.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override // add a new stomp endpoint to our web socket configuration
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // endpoint to be connected (stomp connect url => /ws)
                .withSockJS();  // Connect with SocketJS
    }

    @Override // add application definition prefixes
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // endpoint used for sending messages
        registry.setApplicationDestinationPrefixes("/app");

        // endpoint used for receiving messsages
        registry.enableSimpleBroker("/topic");
    }
}
