package io.namoosori.travelclub.web.service.sdo;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCdo implements Serializable {
    //
    private String email;
    private String name;
    //private String nickName;
    private String phoneNumber;
//    private String birthDay;
//    private Address addresses;

    public MemberCdo(CommunityMember member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
    }
}
