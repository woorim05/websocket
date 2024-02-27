package kr.co.demo.stomp;

import kr.co.demo.jwt.JwtToken;
import kr.co.demo.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            log.info("CONNECTED CLIENT :: " + headerAccessor);

            JwtToken jwtToken = jwtTokenProvider.convertAuthToken(headerAccessor.getFirstNativeHeader("Authorization"));
            if(!jwtToken.validate()){
                throw new MessageDeliveryException("INVALID_JWT_TOKEN");
            }
        }

        if (StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
            log.info("DISCONNECTED CLIENT :: " + headerAccessor);
        }

        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            log.info("SUBSCRIBE :: " + headerAccessor);
        }

        if (StompCommand.SEND.equals(headerAccessor.getCommand())) {
            log.info("SEND :: " + headerAccessor);
        }

        return message;
    }
}
