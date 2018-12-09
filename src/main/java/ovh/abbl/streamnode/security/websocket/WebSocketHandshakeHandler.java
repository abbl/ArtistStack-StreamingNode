package ovh.abbl.streamnode.security.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import ovh.abbl.streamnode.security.principal.AnonymousPrincipal;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        Principal principal = request.getPrincipal();

        if(principal == null){
            principal = new AnonymousPrincipal();

            ((AnonymousPrincipal) principal).setName(UUID.randomUUID().toString());
        }
        return principal;
    }
}
