package kr.co.demo.stomp;

import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;
import java.nio.charset.StandardCharsets;

@Log4j2
@Component
public class StompErrorHandler extends StompSubProtocolErrorHandler {
    /**
     * 클라이언트 메시지 처리 중에 발생한 오류를 처리
     *
     * @param message 클라이언트 메시지
     * @param exception 발생한 예외
     * @return 오류 메시지를 포함한 Message 객체
     */
    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> message, Throwable exception) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setLeaveMutable(true); // StompHeaderAccessor 객체가 변경 가능한 상태로 유지됨.

        String exceptionMessage = exception.getMessage();

        return MessageBuilder.createMessage(exceptionMessage.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}
