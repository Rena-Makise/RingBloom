/* BoardService.java
 * Description : 게시판 서비스 인터페이스
 */
package ringbloom.ringbloom.service;

import java.util.List;

import ringbloom.ringbloom.dto.BoardDto;

public interface BoardService {
	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto board) throws Exception;
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	void updateBoard(BoardDto board) throws Exception;
	void deleteBoard(int boardIdx) throws Exception;
}
