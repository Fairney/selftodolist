package self.sh.comm.common.aop;
import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(3)
public class AroundAspect {

	private Logger logger = LoggerFactory.getLogger(AroundAspect.class);
	
	// @Around : 전처리(@Before)와 후처리(@After)를 한 번에 작성 가능한 advice
	@Around("CommonPointcut.implPointcut()")
	public Object runningTime(ProceedingJoinPoint jp) throws Throwable {
		//메서드 소요 시간을 알려주는 메서드
		//ProceedingJoinPoint 인터페이서 : 전/후 처리 기능, 값을 얻어올 수 있는 메서드 제공
		
		// proceed() 메소드 호출 전  : @Before advice 작성
		// proceed() 메소드 호출 후  : @After advice 작성
		// 메소드 마지막에 proceed()의 반환값을 리턴해야함.
		
		// System.currentTimeMillis() : 
		//	1970/01/01 오전 9시(한국 OS 기준) 부터 
		//  지금까지 지난 시간을 ms단위로 나타낸 값
		
		long startMs = System.currentTimeMillis();
		
		Object obj = jp.proceed();// 전후 처리를 나누는 기준. 이보다 위가 befor, 뒤가 after
		
		long endMs = System.currentTimeMillis();
		
		logger.info("Running Time : " + (endMs-startMs) + "ms");
		
		return obj;
		
		
		
	}
}
