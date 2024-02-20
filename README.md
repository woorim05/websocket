# WebSocket
spring boot 웹소켓 학습 및 채팅 구현

---

### 1. websocket
#### 양방향 통신으로 데이터를 처리할 수 있는 프로토콜     
- sessionId를 메모리(Map 형태로)에 저장하여 관리
- 메모리에 저장하기 때문에 웹소켓 서버가 2대 이상이면 sessionId를 공유할 수 없음  
-> STOMP, 외부 저장소에 저장(Kafka) 방법으로 해결

### 2. STOMP
#### STOMP(Simple/Streaming Text Oriented Messaging Protocol) 텍스트 기반의 메세지 프로토콜
- pub/sub 구조로 채널(토픽)별로 관리하여 sessionId 관리할 필요가 없음
- Kafka를 활용하여 다수의 서버 환경에서 브로드 캐스트 구현가능