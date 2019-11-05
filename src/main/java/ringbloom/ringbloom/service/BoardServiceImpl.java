/* BoardServiceImpl.java
 * Description : 게시판 서비스 구현
 * ver 0.1 : 초기 구성 - 이 창 재
 * ver 0.3 : 게시판 CRUD 구현
 */
package ringbloom.ringbloom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ringbloom.ringbloom.dto.BoardDto;
import ringbloom.ringbloom.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	// 게시글 목록 조회
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}
	
	// 게시글 등록
	@Override
	public void insertBoard(BoardDto board) throws Exception {
		boardMapper.insertBoard(board);
	}

	// 게시글 상세 화면 출력
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		boardMapper.updateHitCount(boardIdx);  // 게시글 조회수 증가
		
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);  // 게시글 내용 조회
		
		return board;
	}

	// 게시글 수정
	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
	}

	// 게시글 삭제
	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardMapper.deleteBoard(boardIdx);		
	}
}
