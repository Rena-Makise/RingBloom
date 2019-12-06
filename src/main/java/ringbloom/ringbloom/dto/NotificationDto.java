/* NotificationDto.java
 * Description : 푸시 알림으로 보낼 메시지 정보 DTO
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.ringbloom.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@ApiModel(value = "NotificationDto : 푸시 알림 메시지 정보", description = "푸시 알림 메시지 정보")
@Data
public class NotificationDto {
	@ApiModelProperty(value = "알림 타이틀")
    private String title;
	@ApiModelProperty(value = "알림 메시지")
    private String message;
	@ApiModelProperty(value = "알림 사운드")
	private String sound;
	@ApiModelProperty(value = "클릭 액션")
	private String click_action;
	@ApiModelProperty(value = "알림 아이콘")
	private String icon;
	@ApiModelProperty(value = "상대 토큰")
    private String token;
	
	@Builder
	public NotificationDto(String title, String message, String icon, String token) {
		this.title = title;
		this.message = message;
		this.icon = icon;
		this.token = token;
	}
}
