/* BoardFileDto.java
 * Description : 첨부파일 정보 DTO
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.ringbloom.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "BoardFileDto : 첨부파일", description = "첨부파일")
@Data
public class BoardFileDto {
	@ApiModelProperty(value = "첨부파일 번호")
	private int idx;
	
	@ApiModelProperty(value = "첨부파일 게시판 번호")
	private int boardIdx;
	
	@ApiModelProperty(value = "원 파일 이름")
	private String originalFileName;
	
	@ApiModelProperty(value = "저장될 파일 경로")
	private String storedFilePath;
	
	@ApiModelProperty(value = "첨부파일 사이즈")
	private long fileSize;
	
	@ApiModelProperty(value = "업로더")
	private String creatorId;
}
