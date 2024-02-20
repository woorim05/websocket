package kr.co.demo.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    
    // ConcurrentHashMap: 멀티스레드일때 안전을 보장하는 Map 구현체
    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

    // 소켓 연결
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        CLIENTS.put(session.getId(), session); // 세션 저장
        log.info(session + " 클라이언트 접속 요청");
    }

    // 소켓 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId()); // 세션 제거
        log.info(session + " 클라이언트 접속 해제");
    }

    // 양방향 데이터 통신
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        // 1:1 채팅
        ObjectMapper objectMapper = new ObjectMapper();
        WebsocketMessage chatting = objectMapper.readValue(textMessage.getPayload(), WebsocketMessage.class);
        chatting.setSender(session.getId());
        WebSocketSession receiver = CLIENTS.get(chatting.getReceiver());
        if (receiver != null && receiver.isOpen()) {
            receiver.sendMessage(new TextMessage(chatting.toString()));
        }

         // 1:n 채팅
         String ssId = session.getId();
         CLIENTS.entrySet().forEach(arg -> {
             if (!arg.getKey().equals(ssId)) {
                 try {
                     arg.getValue().sendMessage(new TextMessage(chatting.toString()));
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         });
    }

    // 소켓 통신 에러
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        throw new Exception("소켓 통신 에러");
    }
    
}
