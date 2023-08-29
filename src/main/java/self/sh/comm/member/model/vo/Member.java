package self.sh.comm.member.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String socialType;
	private String memberAddr;
	private String memberEmail;
	private String emailOptIn;
	private String memberNick;
	private String secessionFl;
}
