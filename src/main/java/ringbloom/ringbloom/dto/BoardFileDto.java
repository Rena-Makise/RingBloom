/* BoardFileDto.java
 * Description : 첨부파일 정보 DTO
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.ringbloom.dto;

import lombok.Data;

@Data
public class BoardFileDto {
	private int idx;
	private int boardIdx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
}
