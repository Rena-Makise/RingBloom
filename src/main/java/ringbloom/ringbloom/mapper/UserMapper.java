/* UserMapper.java
 * Description : 유저 매퍼 - XML 쿼리와 매핑
 * ver 0.1 : 초기 구성 - 이 창 재
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
}
