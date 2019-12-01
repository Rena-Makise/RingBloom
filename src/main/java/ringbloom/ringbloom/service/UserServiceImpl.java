/* UserServiceImpl.java
 * Description : 사용자 처리 서비스 구현
 * ver 0.1 : 초기 구성
 * ver 0.2 : 토큰 처리 추가
 */
package ringbloom.ringbloom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ringbloom.ringbloom.dto.UserDto;
import ringbloom.ringbloom.mapper.UserMapper;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public void signUp(UserDto user) throws Exception {
		userMapper.signUp(user);
	}

	@Override
	public int countNickname(String nickname) throws Exception {
		int result = Integer.valueOf(String.valueOf(userMapper.countNickname(nickname).get("RESULT")));
		log.debug("닉네임 카운트 결과 : " + result);
		return result;
	}

	@Override
	public UserDto checkLogin(String nickname, String password) throws Exception {
		return userMapper.checkLogin(nickname, password);
	}
	
	@Override
	public String checkTokenUser(String token) throws Exception {
		return userMapper.checkTokenUser(token);
	}

	@Override
	public void updateToken(String nickname, String token) throws Exception {
		userMapper.updateToken(nickname, token);		
	}	
}
