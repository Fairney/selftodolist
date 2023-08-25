package self.sh.comm.common.aop;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AfterThrowingAspect {

	private Logger logger = LoggerFactory.getLogger(AfterThrowingAspect.class);
	
	//@AfterThrowing : 기존 @After + Throwing된 예외 얻어오기
	// throwing="exceptionObj" : 던저진 예외를 저장할 매개변수를 지정

	@AfterThrowing(pointcut = "CommonPointcut.implPointcut()", throwing="exceptionObj")
	public void serviceReutrnValue(Exception exceptionObj ) {
		
		String str = "<<exception>> : " + exceptionObj.getStackTrace()[0];
		
		str += " - " + exceptionObj.getMessage();
		
		logger.error(str);
		
	}
}
