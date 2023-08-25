package self.sh.comm.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class AfterAspect {

	private Logger logger = LoggerFactory.getLogger(AfterAspect.class);
	

	@After("CommonPointcut.implPointcut()")
	public void serviceEnd(JoinPoint jp) {
		
	
		
		//jp.getTarget() : AOP�� ����� ��ü, ���� serviceImpl
				//.getClass().getSimpleName() : ��Ű������ ������ Ŭ������
				String className = jp.getTarget().getClass().getSimpleName();
				
				//jp.getSignature() : ����Ǵ� �޼��� ����
				//.getName() : �޼��� �̸�
				String methodName = jp.getSignature().getName();
			
				String str = "End : " + className + " - " + methodName + "\n";
				// End : MemberServiceImpl - login
				
				
				 str += "----------------------------------------------\n";
				 
				 logger.info(str);
				
		
	}
}
