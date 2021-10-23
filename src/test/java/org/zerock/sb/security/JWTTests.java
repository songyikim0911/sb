package org.zerock.sb.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb.security.util.JWTUtil;

@SpringBootTest
@Log4j2
public class JWTTests {

    @Autowired
    JWTUtil jwtUtil;

    @Test
    public void testGenerate(){

        String jwtStr = jwtUtil.generateToken("user11");
        log.info(jwtStr);
    }

    @Test
    public void testValidate(){
        String str= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTEiLCJpYXQiOjE2MzQ4NzAzODYsImV4cCI6MTYzNDg3MDQ0Nn0.7rqAPhG8yf5BplcASU48a686hwf40iBXWOWqPdKiaGo";

    try {
        jwtUtil.validateToken(str);
    }catch(ExpiredJwtException ex){
        log.error("expired..........");
        log.error(ex.getMessage());
    }
    catch(JwtException ex){
        log.info("jwtException........");
        log.error(ex.getMessage());
    }

    }

}
