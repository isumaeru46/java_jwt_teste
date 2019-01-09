package com.example.demo;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	
	// EXPIRATION_TIME = 10 dias
		static final long EXPIRATION_TIME = 860_000_000;
		static final String SECRET = "MySecret";
		static final String TOKEN_PREFIX = "Bearer";
		static final String HEADER_STRING = "Authorization";
		
		static void addAuthentication(HttpServletResponse response, String username) {
			
			User user = new User();
			user.nome = "Ismael";
			user.email = "teste@teste.com";
			user.id = 1;
			
			Claims claims = Jwts.claims().setSubject("{\n" + 
				"        \"login\": \"ismael\",\n" + 
				"        \"id\": 1,\n" + 
				"        \"regras\": [\n" + 
				"            {\n" + 
				"                \"id\": 1,\n" + 
				"                \"nome\": \"ROLE_USER\"\n" + 
				"            }\n" + 
				"        ],\n" + 
				"        \"dadosPessoais\": {\n" + 
				"            \"nome\": \"Usuário Ismael\",\n" + 
				"            \"cpf\": \"035.954.951-95\",\n" + 
				"            \"dataNascimento\": \"1991-09-22\",\n" + 
				"            \"email\": \"ismael.ramosdeoliveira@gmail.com\",\n" + 
				"            \"nacionalidade\": null,\n" + 
				"            \"naturalidade\": null,\n" + 
				"            \"estadoCivil\": null,\n" + 
				"            \"nomePai\": null,\n" + 
				"            \"nomeMae\": null,\n" + 
				"            \"sexo\": \"Masculino\",\n" + 
				"            \"endereco\": {\n" + 
				"                \"id\": 6,\n" + 
				"                \"logradouro\": \"av flamboyant\",\n" + 
				"                \"numero\": 1905,\n" + 
				"                \"complemento\": null,\n" + 
				"                \"bairro\": \"aguas claras\",\n" + 
				"                \"cep\": \"71917-000\",\n" + 
				"                \"cidade\": null,\n" + 
				"                \"uf\": \"DF\"\n" + 
				"            },\n" + 
				"            \"tituloEleitor\": null,\n" + 
				"            \"rg\": null,\n" + 
				"            \"telefones\": [\n" + 
				"                {\n" + 
				"                    \"id\": 4,\n" + 
				"                    \"tipo\": \"CELULAR\",\n" + 
				"                    \"numero\": \"(61) 9 92332544\",\n" + 
				"                    \"ramal\": null\n" + 
				"                }\n" + 
				"            ]\n" + 
				"        }\n" + 
				"    }");
			
			String JWT = Jwts.builder()
					.setSubject(username)
					.setClaims(claims)
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
					.signWith(SignatureAlgorithm.HS512, SECRET)
					.compact();
			
			response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		}
		
		static Authentication getAuthentication(HttpServletRequest request) {
			String token = request.getHeader(HEADER_STRING);
			
			if (token != null) {
				// faz parse do token
				String user = Jwts.parser()
						.setSigningKey(SECRET)
						.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
						.getBody()
						.getSubject();
				
				if (user != null) {
					return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
				}
			}
			return null;
		}

}
