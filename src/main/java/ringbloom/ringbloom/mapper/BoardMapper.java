/* BoardMapper.java
 * Description : 게시판 매퍼 - XML 쿼리와 매핑
 * ver 0.1 : 초기 구성 - 이 창 재
 * ver 0.3 : 게시판 CRUD 구현
 */
package ringbloom.ringbloom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ringbloom.ringbloom.dto.BoardDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto board) throws Exception;
	void updateHitCount(int boardIdx) throws Exception;
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	void updateBoard(BoardDto board) throws Exception;
	void deleteBoard(int boardIdx) throws Exception;
}
