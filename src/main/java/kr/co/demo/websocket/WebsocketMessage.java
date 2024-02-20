package kr.co.demo.websocket;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WebsocketMessage {
    private String sender;      // sender sessionId
    private String receiver;    // receiver sessionId
    private String nickName;
    private String message;
}
