//package self.sh.comm.common.aop;
//
//import java.util.Arrays;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import fp.art.stroke.member.model.vo.Member;
//
//@Component
//@Aspect
//public class BeforeAspect {
//	
//	private Logger logger = LoggerFactory.getLogger(BeforeAspect.class);
//	
//	// JoinPoint 인터페이스 : 
//		//		advice가 적용되는 Target Object (ServiceImpl)의
//		//		정보, 전달되는 매개변수, 메서드, 반환값, 예외 등을 얻을 수 있는 메서드를 제공
//		
//		// (주의사항) JoinPoint 인터페이스는 항상 첫 번째 매개변수로 작성 되어야 한다!
//	
//	
//	@Before("CommonPointcut.implPointcut()")
//	public void serviceStart(JoinPoint jp) { //메서드 시그니처
//		
//		String str = "----------------------------------------------\n";
//		
//		//jp.getTarget() : AOP가 적용된 객체, 각종 serviceImpl
//		//.getClass().getSimpleName() : 패키지명을 제외한 클래스명
//		String className = jp.getTarget().getClass().getSimpleName();
//		
//		//jp.getSignature() : 수행되는 메서드 정보
//		//.getName() : 메서드 이름
//		String methodName = jp.getSignature().getName();
//		
//		//jp.getArgs() : 메서드 호출 시 전달된 매개변수
//		String param = Arrays.toString( jp.getArgs() );
//		
//		str += "Start : " + className + " - " + methodName + "\n";
//		// Start : MemberServiceImpl - login
//		
//		str += "Parameter : " + param + "\n";
//		
//		try {
//			//HttpServletRequest를 이용해서 ip 얻어오기
//			//단, 스프링 스케줄러 동작 시 예외 방식
//			//스케줄러는 요청 객체가 없다
//			
//			HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//			
//			Member loginMember = (Member) req.getSession().getAttribute("loginMember");
//			
//			//ip.xxx.xxx.xxx.xxx(email : test01@naver.com)
//			str += "ip : " + getRemoteAddr(req);
//			
//			if(loginMember != null) {// 로그인 상태인 경우
//				str += "(email : " + loginMember.getMemberEmail() + ")";
//				
//			}
//			
//		}catch(Exception e) {
//			str+="[스케줄러 동작]";
//		}
//		logger.info(str);
//		
//		
//	}
//	
//	//pc  / 브라우저 / os가 각자 달라도
//	//ip 주소를 얻어오는 메서드
//	
//	public static String getRemoteAddr(HttpServletRequest request) {
//
//        String ip = null;
//
//        ip = request.getHeader("X-Forwarded-For");
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
//            ip = request.getHeader("Proxy-Client-IP"); 
//        } 
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
//            ip = request.getHeader("WL-Proxy-Client-IP"); 
//        } 
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
//            ip = request.getHeader("HTTP_CLIENT_IP"); 
//        } 
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
//        }
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
//            ip = request.getHeader("X-Real-IP"); 
//        }
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
//            ip = request.getHeader("X-RealIP"); 
//        }
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
//            ip = request.getHeader("REMOTE_ADDR");
//        }
//
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
//            ip = request.getRemoteAddr(); 
//        }
//
//		return ip;
//	}
//
//}
