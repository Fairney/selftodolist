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
	
	// @Around : ��ó��(@Before)�� ��ó��(@After)�� �� ���� �ۼ� ������ advice
	@Around("CommonPointcut.implPointcut()")
	public Object runningTime(ProceedingJoinPoint jp) throws Throwable {
		//�޼��� �ҿ� �ð��� �˷��ִ� �޼���
		//ProceedingJoinPoint �������̼� : ��/�� ó�� ���, ���� ���� �� �ִ� �޼��� ����
		
		// proceed() �޼ҵ� ȣ�� ��  : @Before advice �ۼ�
		// proceed() �޼ҵ� ȣ�� ��  : @After advice �ۼ�
		// �޼ҵ� �������� proceed()�� ��ȯ���� �����ؾ���.
		
		// System.currentTimeMillis() : 
		//	1970/01/01 ���� 9��(�ѱ� OS ����) ���� 
		//  ���ݱ��� ���� �ð��� ms������ ��Ÿ�� ��
		
		long startMs = System.currentTimeMillis();
		
		Object obj = jp.proceed();// ���� ó���� ������ ����. �̺��� ���� befor, �ڰ� after
		
		long endMs = System.currentTimeMillis();
		
		logger.info("Running Time : " + (endMs-startMs) + "ms");
		
		return obj;
		
		
		
	}
}
