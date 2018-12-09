package ovh.abbl.streamnode.security.principal;

import lombok.Data;

import java.security.Principal;

@Data
public class AnonymousPrincipal implements Principal {
    private String name;
}
