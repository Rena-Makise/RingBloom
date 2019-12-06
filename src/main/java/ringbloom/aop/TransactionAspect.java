/* TransactionAspect.java
 * Description : 트랜잭션 Aspect
 * ver 0.1 : 초기 구성 - 이 창 재
 */
package ringbloom.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionAspect {
	// 트랜잭션 설정 시 사용되는 설정 값을 상수로 선언
	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	private static final String AOP_TRANSACTION_EXPRESSION = "execution(* ringbloom..service.*Impl.*(..))";
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	/**
	 * 트랜잭션의 이름과 트랜잭션에서 롤백을 하는 룰 설정
	 * @return
	 */
	@Bean
	public TransactionInterceptor transactionAdvice() {
		MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
		// 예외가 일어나면 무조건 롤백
		transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		source.setTransactionAttribute(transactionAttribute);
		
		return new TransactionInterceptor(transactionManager, source);
	}
	
	/**
	 * AOP의 포인트컷 설정
	 * @return
	 */
	@Bean
	public Advisor transactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}
}
