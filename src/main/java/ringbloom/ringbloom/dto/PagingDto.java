/* PagingDto.java
 * Description : 페이징 처리 DTO
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.ringbloom.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PagingDto : 페이징 정보", description = "페이징 정보")
public class PagingDto {
	@ApiModelProperty(value = "페이지당 게시물 수")
	public static final int PAGE_SCALE = 10;
	@ApiModelProperty(value = "화면당 게시물 수")
	public static final int BLOCK_SCALE = 5;
	@ApiModelProperty(value = "현재 페이지")
	private int curPage;
	@ApiModelProperty(value = "이전 페이지")
	private int prevPage;
	@ApiModelProperty(value = "다음 페이지")
	private int nextPage;
	@ApiModelProperty(value = "전체 페이지 갯수")
	private int totalPage;
	@ApiModelProperty(value = "전체 페이지 블록 갯수")
	private int totalBlock;
	@ApiModelProperty(value = "현재 페이지 블록")
	private int curBlock;
	@ApiModelProperty(value = "이전 페이지 블록")
	private int prevBlock;
	@ApiModelProperty(value = "다음 페이지 블록")
	private int nextBlock;
	@ApiModelProperty(value = "#{START}")
	private int pageBegin;
	@ApiModelProperty(value = "#{END}")
	private int pageEnd;
	@ApiModelProperty(value = "현재 페이지 블록의 시작번호")
	private int blockBegin;
	@ApiModelProperty(value = "현재 페이지 블록의 끝번호")
	private int blockEnd;
	
	// 생성자 (레코드 갯수, 현재 페이지 번호)
	public PagingDto(int count, int curPage) {
		curBlock = 1;  // 현재 페이지 블록 번호
		this.curPage = curPage;  // 현재 페이지 설정
		setTotalPage(count);  // 전체 페이지 갯수 계산
		setPageRange();
		setTotalBlock();  // 전체 페이지 블록
		setBlockRange();  // 페이지 블록의 시작
	}
	
	public void setBlockRange() {
		// 현재 페이지가 몇 번째 페이지 블록에 속하는지 계산
		curBlock = (int)Math.floor((curPage - 1) / BLOCK_SCALE) + 1;
		// 현재 페이지 블록의 시작, 끝 번호를 계산
		blockBegin = (curBlock - 1) * BLOCK_SCALE + 1;
		// 페이지 블록의 끝 번호
		blockEnd = blockBegin + BLOCK_SCALE - 1;
		// 마지막 블록이 범위를 초과하지 않도록 계산
		if(blockEnd > totalPage) {
			blockEnd = totalPage;
		}
		// 이전을 눌렀을 때 이동할 페이지 번호
		prevPage = ((curPage - 5) <= 5) ? 1 : (int)Math.floor((curPage - 5) / 5) * 5 + 1;
		// 다음을 눌렀을 때 이동할 페이지 번호
		nextPage = ((curPage + 5) > totalPage) ? (int)Math.floor(totalPage / 5) * 5 + 1 :  (int)Math.floor((curPage + 5) / 5) * 5 + 1;
		// 마지막 페이지가 범위를 초과하지 않도록 처리
		if(nextPage >= totalPage) {
			nextPage = totalPage;
		}
	}
	
	public void setPageRange() {
		// 시작번호
		pageBegin = (int)Math.floor(curPage / 5) * 5 + 1;
		// 끝번호
		pageEnd = pageBegin + 4 < totalPage ? pageBegin + 4 : totalPage;
	}
	
	public int getCurPage() {
		return curPage;
	}
	
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	
	public int getPrevPage() {
		return prevPage;
	}
	
	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}
	
	public int getNextPage() {
		return nextPage;
	}
	
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(int count) {
		// Math.ceil(실수) 반올림 처리
		totalPage = (int)Math.ceil(count * 1.0 / PAGE_SCALE);
	}
	
	public int getTotalBlock() {
		return totalBlock;
	}
	
	// 페이지 블록의 갯수 계산 (총 100페이지라면 10개의 블록)
	public void setTotalBlock() {
		// 전체 페이지 갯수 / 10
		// 91 / 10 => 9.1 => 10개
		totalBlock = (int)Math.floor(totalPage / BLOCK_SCALE) + 1;
	}
	
	public int getCurBlock() {
		return curBlock;
	}
	
	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}
	
	public void setPrevBlock(int prevBlock) {
		this.prevBlock = prevBlock;
	}
	
	public int getNextBlock() {
		return nextBlock;
	}
	
	public void setNextBlock(int nextBlock) {
		this.nextBlock = nextBlock;
	}
	
	public int getPageBegin() {
		return pageBegin;
	}
	
	public void setPageBegin(int pageBegin) {
		this.pageBegin = pageBegin;
	}
	
	public int getPageEnd() {
		return pageEnd;
	}
	
	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}
	
	public int getPageScale() {
		return PAGE_SCALE;
	}
	
	public int getBlockScale() {
		return BLOCK_SCALE;
	}
}
