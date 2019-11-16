/* BoardService.java
 * Description : 게시판 서비스 인터페이스
 */
package ringbloom.ringbloom.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import ringbloom.ringbloom.dto.BoardDto;
import ringbloom.ringbloom.dto.BoardFileDto;

public interface BoardService {
	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	void updateBoard(BoardDto board) throws Exception;
	void deleteBoard(int boardIdx) throws Exception;
	BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;
}
