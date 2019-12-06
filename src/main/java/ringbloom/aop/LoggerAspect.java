/* LoggerAspect.java
 * Description : 로그 출력 Aspect
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggerAspect {
	
	// Around 어노테이션으로 해당 기능이 실행될 시점, 즉 어드바이스를 정의
	// Around 어드바이스는 대상 메서드의 호출 전후, 예외 발생 등 모든 시점에 적용가능
	@Around("execution(* ringbloom..controller.*Controller.*(..)) or execution(* ringbloom..service.*Impl.*(..)) or execution(* ringbloom..mapper.*Mapper.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		if(name.indexOf("Controller") > -1) {
			type = "Controller  \t:  ";
		} else if(name.indexOf("Service") > -1) {
			type = "ServiceImpl  \t:  ";
		} else if(name.indexOf("Mapper") > -1) {
			type = "Mapper  \t\t:  ";
		}
		
		log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}
