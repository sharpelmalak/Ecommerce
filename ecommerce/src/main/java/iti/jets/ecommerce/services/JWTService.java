package iti.jets.ecommerce.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private final String jwtSecret = "TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg=rn";
    private final int jwtExpirationMs = 86400000; // 24 hours


    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // Add role as a claim
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String getUsernameFromJwtToken(String token) {
        try {
            // Validate and extract the username from the token
            String username = extractClaim(token, Claims::getSubject);
            System.out.println("token: " + username);
            return username;
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("JWT token is malformed: " + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("Invalid JWT signature: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }
        return null;
    }

    public String getRoleFromJwtToken(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }


    public boolean validateJwtToken(String token, UserDetails userDetails) {
        final String userName = getUsernameFromJwtToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }



    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


}