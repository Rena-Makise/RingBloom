/* RestBoardApiController.java
 * Description : REST API 컨트롤러
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.ringbloom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ringbloom.ringbloom.dto.BoardDto;
import ringbloom.ringbloom.service.BoardService;

@Api(description = "게시판 REST API")
@RestController
public class RestBoardApiController {
	@Autowired
	private BoardService boardService;
	
	@ApiOperation(value = "게시판 리스트 조회")
	@RequestMapping(value = "/api/board", method = RequestMethod.GET)
	public List<BoardDto> openBoardList() throws Exception {
		return boardService.selectBoardList();
	}
	
	@ApiOperation(value = "게시글 등록")
	@RequestMapping(value = "/api/board/write", method = RequestMethod.POST)
	public void insertBoard(@RequestBody BoardDto board) throws Exception {
		boardService.insertBoard(board, null);
	}
	
	@ApiOperation(value = "게시글 상세 화면 조회")
	@RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.GET)
	public BoardDto openBoardDetail(@PathVariable("boardIdx") @ApiParam(value = "게시글 번호") int boardIdx) throws Exception {
		return boardService.selectBoardDetail(boardIdx);
	}
	
	@ApiOperation(value = "게시글 수정")
	@RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.PUT)
	public String updateBoard(@RequestBody BoardDto board) throws Exception {
		boardService.updateBoard(board);
		return "redirect:/board";
	}
	
	@ApiOperation(value = "게시글 삭제")
	@RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") @ApiParam(value = "게시글 번호") int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
	}
}
