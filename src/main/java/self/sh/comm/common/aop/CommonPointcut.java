package self.sh.comm.common.aop;
import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {

	// �쉶�썝 �꽌鍮꾩뒪�슜 Pointcut
	@Pointcut("execution(* self.sh.comm.member..*Impl.*(..))")
	public void memberPointcut() {} 
		
	// 紐⑤뱺 ServiceImpl �겢�옒�뒪�슜 Pointcut
	@Pointcut("execution(* self.sh.comm.*Impl.*(..))")
	public void implPointcut() {}
	 {}
	
}
