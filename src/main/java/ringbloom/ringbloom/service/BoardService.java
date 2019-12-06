/* BoardService.java
 * Description : 게시판 서비스 인터페이스
 */
package ringbloom.ringbloom.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import ringbloom.ringbloom.dto.BoardDto;
import ringbloom.ringbloom.dto.BoardFileDto;
import ringbloom.ringbloom.dto.ReplyDto;

public interface BoardService {
	int boardListGetCount(String searchType, String searchWord) throws Exception;
	List<BoardDto> selectBoardList(int start, int end, String searchType, String searchWord) throws Exception;
	void insertBoard(BoardDto board, String nickname, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	void addHitCount(int boardIdx) throws Exception;
	void updateBoard(BoardDto board) throws Exception;
	void deleteBoard(int boardIdx) throws Exception;
	BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;
	public void fileUpload(BoardDto board, String nickname, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
	List<ReplyDto> selectCommentList(int boardIdx, int start, int end) throws Exception;
	void insertComment(ReplyDto reply) throws Exception;
	int commentListGetCount(int boardIdx) throws Exception;
	void deleteComment(int replyIdx) throws Exception;
	String checkToken(int boardIdx) throws Exception;
}
