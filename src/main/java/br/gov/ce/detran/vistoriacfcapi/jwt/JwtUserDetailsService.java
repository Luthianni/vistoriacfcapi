package br.gov.ce.detran.vistoriacfcapi.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.gov.ce.detran.vistoriacfcapi.entity.Usuario;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return new JwtUserDetails(usuario);
    }

    public JwtToken getTokenAuthenticated(String username) {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        Usuario.Role role = usuarioService.buscarRolePorUsername(username);

        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()), usuario.getId());
    }

    public Long getUserIdByUsername(String username) {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return usuario.getId();
    }

    public String extractUsernameFromToken(String token, String secretKey) {
        // Lógica para extrair o nome de usuário de um token JWT
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    
        return claims.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
    try {
        // Sua lógica de validação do token aqui
        return JwtUtils.isTokenValid(token);
    } catch (JwtException | IllegalArgumentException e) {
        // Capturar exceções relacionadas à validação do token
        return false;
    }
}

    public JwtToken generateToken(UserDetails userDetails, int i) {
        if (userDetails instanceof JwtUserDetails) {
            JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetails;
    
            // Use Keys.secretKeyFor to generate a secure key for HS512 algorithm
            SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    
            String token = Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .claim("authorities", userDetails.getAuthorities())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + i * 1000))  // i seconds
                    .signWith(secretKey, SignatureAlgorithm.HS512)
                    .compact();
    
            return new JwtToken(token, jwtUserDetails.getId());
        } else {
            throw new IllegalArgumentException("O userDetails não é uma instância de JwtUserDetails");
        }
    }

    public Long getUserId(UserDetails userDetails) {
        if (userDetails instanceof JwtUserDetails) {
            JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetails;
            return jwtUserDetails.getId();
        } else {
            throw new IllegalArgumentException("O userDetails não é uma instância de JwtUserDetails");
        }
    }

}
