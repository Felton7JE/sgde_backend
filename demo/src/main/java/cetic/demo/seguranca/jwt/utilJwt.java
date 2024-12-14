package cetic.demo.seguranca.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cetic.demo.seguranca.wrapper.UsuDetImp;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class utilJwt {

    @Value("${seguranca.jwtSecret}")
    private String jwtSecret;

    @Value("${seguranca.jwtExpirou}")
    private int jwtExpirou;

    // Gerar Token Temporário (token)
    public String gerarToken(UsuDetImp userDetaisImplementation) {
        return Jwts.builder()
                .setSubject(userDetaisImplementation.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 900_000)) // 5 minutos
                .claim("tokenType", "token") // Claim para diferenciar o token temporário
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // Gerar Token Completo 2FA (token2FA)
    public String gerarTokenCompleto(UsuDetImp userDetaisImplementation) {
        return Jwts.builder()
                .setSubject(userDetaisImplementation.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirou))
                .claim("tokenType", "token2FA") // Claim para diferenciar o token 2FA
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // Obter o nome de usuário do token
    public String getUsernameToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Obter o tipo de token (token ou token2FA)
    public String getTokenType(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("tokenType", String.class);
    }

    // Validar Token
    public boolean validarToken(String autToken) {
        try {
            Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(autToken);

            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Token malformado: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Token ilegal: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Token não suportado: " + e.getMessage());
        }
        return false;
    }

    // Obter a chave de assinatura
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
