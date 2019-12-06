/* FCMInitializer.java
 * Description : FCM 초기화 처리
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.common.firebase;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


@Service
public class FCMInitializer {
	private static final Logger logger = LoggerFactory.getLogger(FCMInitializer.class);
	private static final String FIREBASE_CONFIG_PATH = "ringbloom-firebase-adminsdk.json";
	
	@PostConstruct
	public void initialize() {
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())).build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				logger.info("Firebase application has been initialized");
			}
		} catch (IOException e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
	}
}
