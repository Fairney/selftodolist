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
	
	//@AfterThrowing : ���� @After + Throwing�� ���� ������
	// throwing="exceptionObj" : ������ ���ܸ� ������ �Ű������� ����

	@AfterThrowing(pointcut = "CommonPointcut.implPointcut()", throwing="exceptionObj")
	public void serviceReutrnValue(Exception exceptionObj ) {
		
		String str = "<<exception>> : " + exceptionObj.getStackTrace()[0];
		
		str += " - " + exceptionObj.getMessage();
		
		logger.error(str);
		
	}
}
