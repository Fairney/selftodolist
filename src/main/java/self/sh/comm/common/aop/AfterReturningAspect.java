package self.sh.comm.common.aop;
import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(5) // �겙 �닽�옄 �닚�쑝濡� 癒쇱� �떎�뻾
public class AfterReturningAspect {
	
private Logger logger = LoggerFactory.getLogger(AfterReturningAspect.class);
	
	//@AfterReturning : 기존 @After + 반환값 얻어오기 기능
	// returning="returnObj" : 반환 값을 저장할 매개변수를 지정

	@AfterReturning(pointcut = "CommonPointcut.implPointcut()", returning="returnObj")
	public void serviceReutrnValue(Object returnObj ) {
		
		
		logger.info("return Value: " + returnObj);
	}
}
