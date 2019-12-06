/* SecurityUtil.java
 * Description : SHA-256 암호화
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {
    public String encryptSHA256(String str){

        String sha = "";

        try{
           MessageDigest sh = MessageDigest.getInstance("SHA-256");
           sh.update(str.getBytes());
           byte byteData[] = sh.digest();
           StringBuffer sb = new StringBuffer();
           for(int i = 0 ; i < byteData.length ; i++){
               sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
           }

           sha = sb.toString();

       } catch(NoSuchAlgorithmException e) {
           //e.printStackTrace();
    	   log.error("Encrypt Error - NoSuchAlgorithmException");
           sha = null;
       }
       return sha;
     }
}

