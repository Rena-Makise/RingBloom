/* BoardDto.java
 * Description : Data Transfer Object
 * ver 0.1 : 초기 구성 - 이 창 재
 * ver 0.2 : 날짜 자료형 String -> LocalDateTime
 */
package ringbloom.ringbloom.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardDto {
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private LocalDateTime createdDatetime;
	private String updaterId;
	private LocalDateTime updatedDatetime;
}
