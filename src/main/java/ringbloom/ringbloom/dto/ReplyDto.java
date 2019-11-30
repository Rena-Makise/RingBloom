/* BoardDto.java
 * Description : 댓글 Data Transfer Object
 * ver 0.1 : 초기 구성
 */
package ringbloom.ringbloom.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "ReplyDto : 댓글 내용", description = "댓글 내용")
@Data
public class ReplyDto {
	@ApiModelProperty(value = "댓글 번호")
	private int replyIdx;
	
	@ApiModelProperty(value = "게시글 번호")
	private int boardIdx;
	
	@ApiModelProperty(value = "작성자")
	private String creatorId;
	
	@ApiModelProperty(value = "댓글 내용")
	private String contents;
	
	@ApiModelProperty(value = "작성시간")
	@JsonFormat(pattern = "yy.MM.dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime createdDatetime;
	
	@ApiModelProperty(value = "수정시간")
	@JsonFormat(pattern = "yy.MM.dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime updatedDatetime;
}
