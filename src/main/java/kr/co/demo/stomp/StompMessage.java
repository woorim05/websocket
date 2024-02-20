package kr.co.demo.stomp;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StompMessage {
    private String channelId;
    private String nickName;
    private String greeting;
}
