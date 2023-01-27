package io.namoosori.travelclub.web.aggregate.club;

import io.namoosori.travelclub.web.aggregate.Entity;
import io.namoosori.travelclub.web.aggregate.club.vo.Provider;
import io.namoosori.travelclub.web.aggregate.club.vo.Role;
import io.namoosori.travelclub.web.aggregate.club.vo.RoleInClub;
import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import io.namoosori.travelclub.web.util.exception.InvalidEmailException;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
public class CommunityMember extends Entity{
	//
	//private String id;
	private String email;
	private String name;
	private String phoneNumber;
	private String nickName;
	private String birthDay;
	@Enumerated(EnumType.STRING)
	private RoleInClub roleInClub;
	private Role role;
	private Provider provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
	private String refreshToken;

	public CommunityMember(MemberJpo memberJpo){
		BeanUtils.copyProperties(memberJpo, this);
	}

	public CommunityMember (String email, String name, String phoneNumber, Provider provider, String refreshToken){
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.provider = provider;
		this.refreshToken = refreshToken;
	}

    public CommunityMember(String email, String name, String phoneNumber) {
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
    }

	public String getRoleKey(){
		return this.role.getKey();
	}

//    @Override
//	public String toString() {
//		//
//		StringBuilder builder = new StringBuilder();
//
//		builder.append("Name:").append(name);
//		builder.append(", email:").append(email);
//		builder.append(", nickname:").append(nickName);
//		builder.append(", phone number:").append(phoneNumber);
//		builder.append(", birthDay:").append(birthDay);
//		builder.append(", Address: ").append(Address.class.toString());
//
//		return builder.toString();
//	}

	public void checkValidation() {
		//
		checkEmailValidation(email);
	}

    private void checkEmailValidation(String email) {
		//
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		boolean valid = m.matches();

		if (!valid) {
			throw new InvalidEmailException("Email is invalid.");
		}
	}

	public void modifyValues(NameValueList nameValues) {
		//
		for (NameValue nameValue : nameValues.getNameValues()) {
			String value = nameValue.getValue().toString();
			//Address addresses = (Address)nameValue.getValue();
			switch (nameValue.getName()) {
				case "name":
					this.name = value;
					break;
				case "phoneNumber":
					this.phoneNumber = value;
					break;
				case "nickName":
					this.nickName = value;
					break;
				case "birthDay":
					this.birthDay = value;
					break;

			}
		}
	}

//	public static CommunityMember sample() {
//		//
//		CommunityMember member = new CommunityMember("mymy@nextree.co.kr", "Minsoo Lee", "010-3321-1001", "123", "asdf1234");
//		member.setBirthDay("2001.09.23");
//		return member;
//	}

//	public static void main(String[] args) {
//		//
//		System.out.println(new Gson().toJson(sample()));
//	}

}
