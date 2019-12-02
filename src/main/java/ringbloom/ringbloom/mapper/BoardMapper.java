/* BoardMapper.java
 * Description : 게시판 매퍼 - XML 쿼리와 매핑
 * ver 0.1 : 초기 구성
 * ver 0.3 : 게시판 CRUD 구현
 * ver 0.4 : 페이징 처리
 */
package ringbloom.ringbloom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import ringbloom.ringbloom.dto.BoardDto;
import ringbloom.ringbloom.dto.BoardFileDto;
import ringbloom.ringbloom.dto.ReplyDto;

@Mapper
public interface BoardMapper {
	int boardListGetCount(@Param("searchType") String searchType, @Param("searchWord") String searchWord) throws Exception;
	List<BoardDto> selectBoardList(@Param("start") int start, @Param("end") int end, @Param("searchType") String searchType, @Param("searchWord") String searchWord) throws Exception;
	void insertBoard(BoardDto board) throws Exception;
	void updateHitCount(int boardIdx) throws Exception;
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	void updateBoard(BoardDto board) throws Exception;
	void deleteBoard(int boardIdx) throws Exception;
	void insertBoardFileList(List<BoardFileDto> list) throws Exception;
	List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;
	BoardFileDto selectBoardFileInformation(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
	List<ReplyDto> selectCommentList(@Param("boardIdx") int boardIdx, @Param("start") int start, @Param("end") int end) throws Exception;
	void insertComment(ReplyDto reply) throws Exception;
	int commentListGetCount(@Param("boardIdx") int boardIdx) throws Exception;
	void deleteComment(@Param("replyIdx") int replyIdx) throws Exception;
	String checkToken(@Param("boardIdx") int boardIdx) throws Exception;
}
