/* UserService.java
 * Description : 사용자 처리 서비스 인터페이스
 */
package ringbloom.ringbloom.service;

import ringbloom.ringbloom.dto.UserDto;

public interface UserService {
	void signUp(UserDto user) throws Exception;
	int countNickname(String nickname) throws Exception;
	UserDto checkLogin(String nickname, String password) throws Exception;
	String checkTokenUser(String token) throws Exception;
	void updateToken(String nickname, String token) throws Exception;
	UserDto checkUserInfo(String nickname) throws Exception;
	void updatePassword(String password, String nickname) throws Exception;
	int checkPostNum(String nickname) throws Exception;
	int checkCommentNum(String nickname) throws Exception;
}
