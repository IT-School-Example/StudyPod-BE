//package com.itschool.study_pod.global.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    // 그룹채팅별로 연결된 웹소켓 세션(사용자)들을 저장하는 맵
//    private static final Map<String, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();
//
//    // 성공적으로 사용자가 접속했을 때
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // 연결만 해두고, 메시지를 보낼 때 roomId로 어떤 방에 보낼지 결정
//        System.out.println("새로운 사용자 연결됨: " + session.getId());
//    }
//
//    private final  ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    // 클라이언트가 텍스트 메시지를 보낼 때 호출되어 해당 메시지를 처리하는 메서드
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        try {
//            Map<String, String> payload = mapper.readValue(message.getPayload(), Map.class);
//            String roomId = payload.get("roomId");
//            String chatMessage = payload.get("message");
//            if (roomId == null || chatMessage == null) return;
//            chatRooms.computeIfAbsent(roomId, keyRoom -> ConcurrentHashMap.newKeySet()).add(session);
//            for (WebSocketSession s : chatRooms.get(roomId)) {
//                if (s.isOpen()) {
//                    s.sendMessage(new TextMessage(chatMessage));
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("메시지 처리 중 오류 발생 : " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        // 연결 종료 시 모든 방에서 해당 세션을 제거
//        /*chatRooms.values().forEach(sessions -> sessions.remove(session));*/
//        chatRooms.entrySet().removeIf(entry -> {
//            Set<WebSocketSession> sessions = entry.getValue();
//            sessions.remove(session);
//            return sessions.isEmpty();
//        });
//    }
//}