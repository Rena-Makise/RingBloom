/* UserMapper.java
 * Description : 유저 매퍼 - XML 쿼리와 매핑
 * ver 0.1 : 초기 구성
 * ver 0.2 : 토큰 처리 추가
 */
package ringbloom.ringbloom.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import ringbloom.ringbloom.dto.UserDto;

@Mapper
public interface UserMapper {
	void signUp(UserDto member) throws Exception;
	Map<String, Object> countNickname(String nickname) throws Exception;
	UserDto checkLogin(@Param("nickname") String nickname, @Param("password") String password) throws Exception;
	String checkTokenUser(@Param("token") String token) throws Exception;
	void updateToken(@Param("nickname") String nickname, @Param("token") String token) throws Exception;
	UserDto checkUserInfo(@Param("nickname") String nickname) throws Exception;
	void updatePassword(@Param("password") String password, @Param("nickname") String nickname) throws Exception;
	int checkPostNum(@Param("nickname") String nickname) throws Exception;
	int checkCommentNum(@Param("nickname") String nickname) throws Exception;
}
