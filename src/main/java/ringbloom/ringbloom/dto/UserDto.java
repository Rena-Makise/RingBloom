/* UserDto.java
 * Description : 유저 관련 정보 DTO
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.ringbloom.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "UserDto : 유저 정보", description = "유저 정보")
@Data
public class UserDto {
	@ApiModelProperty(value = "사용자 고유번호")
	private int pid;
	@ApiModelProperty(value = "닉네임")
	private String nickname;
	@ApiModelProperty(value = "이메일")
	private String email;
	@ApiModelProperty(value = "패스워드")
	private String password;
	@ApiModelProperty(value = "패스워드 질문1")
	private String op1;
	@ApiModelProperty(value = "패스워드 질문2")
	private String op2;
	@ApiModelProperty(value = "패스워드 답변1")
	private String answer1;
	@ApiModelProperty(value = "패스워드 답변2")
	private String answer2;
	@ApiModelProperty(value = "토큰")
	private String token;
}

