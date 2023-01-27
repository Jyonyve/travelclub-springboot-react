package io.namoosori.travelclub.web.service.sdo;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Provider;
import io.namoosori.travelclub.web.aggregate.club.vo.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
public class MemberCdo implements Serializable {
    //
    private String email;
    private String name;
    //private String nickName;
    private String phoneNumber;
    private Provider provider;
    private String refreshToken;

    public MemberCdo(CommunityMember member) {
        BeanUtils.copyProperties(member, this);
    }
//    private Address addresses;

    @Builder
    public MemberCdo(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public MemberCdo(String name, String email, String phoneNumber, Provider provider, String refreshToken) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
        this.refreshToken = refreshToken;
    }
}
