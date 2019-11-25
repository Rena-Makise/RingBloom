/* NotificationRequest.java
 * Description : 푸시 알림으로 보낼 메시지 정보 DTO
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.ringbloom.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "NotificationRequst : 푸시 알림 메시지 정보", description = "푸시 알림 메시지 정보")
@Data
public class NotificationRequest {
	@ApiModelProperty(value = "알림 타이틀")
    private String title;
	@ApiModelProperty(value = "알림 메시지")
    private String message;
	@ApiModelProperty(value = "상대 토큰")
    private String token;
}
