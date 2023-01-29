package io.namoosori.travelclub.web.util.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Provider;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.logic.MemberServiceLogic;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.jpastore.MemberJpaStore;
import io.namoosori.travelclub.web.store.jpastore.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class GoogleAuthentification {

    private MemberService memberService;

    public GoogleAuthentification() {
        this.memberService = MemberServiceLogic.getMemberServiceLogic();
    }

    public String insertUserInDB(Map<String, String> tokens) throws IOException {

        try {
            DecodedJWT decodedJWT = JWT.decode(tokens.get("id_token"));
            String payload = new String(Base64Utils.decode(decodedJWT.getPayload().getBytes(StandardCharsets.UTF_8)));

            Gson gson = new Gson();
            Map<String, Object> map = gson.fromJson(payload, Map.class);
            for(Map.Entry<String, Object> entry : map.entrySet()){
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

            String refreshToken = tokens.get("refresh_token");
            System.out.println("refreshToken : " + refreshToken);
            MemberCdo memberCdo = new MemberCdo(String.valueOf(map.get("name")), String.valueOf(map.get("email")),
                    "010-0000-0000", Provider.GOOGLE, refreshToken);
            return saveOrUpdate(memberCdo);

        } catch (JWTDecodeException decodeEx) {
            System.out.println("Decode Error");
            decodeEx.printStackTrace();
            throw new IOException("Something wrong happens within saveOrUpdate");
        }
    }

    public String saveOrUpdate(MemberCdo memberCdo){
        CommunityMember communityMember = memberService.findMemberByEmail(memberCdo.getEmail());
        if ( communityMember== null){
            return memberService.register(memberCdo);
        }else {
            NameValueList nameValueList = new NameValueList();
            nameValueList.addNameValue("name", memberCdo.getName());
            memberService.modifyMember(communityMember.getId(), nameValueList);
            return communityMember.getId();
        }
    }

    public Map<String, String > responseParser(ResponseEntity<String> response) throws IOException {
        Map<String, String> tokensMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.getBody());
        tokensMap.put("access_token", node.path("access_token").asText());
        tokensMap.put("refresh_token", node.path("refresh_token").asText());
        tokensMap.put("scope", node.path("scope").asText());
        tokensMap.put("id_token", node.path("id_token").asText());
        tokensMap.put("token_type", node.path("token_type").asText());

        return tokensMap;
    }
}