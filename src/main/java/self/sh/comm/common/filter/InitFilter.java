package self.sh.comm.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebFilter(filterName = "initFilter", urlPatterns="/*")
public class InitFilter extends HttpFilter implements Filter {
       
	//Logger / Debug Mode 사용
	//Logger 객체 생성(해당 클래스에 대한 log를 출력하는 객체)
	private Logger logger = LoggerFactory.getLogger(InitFilter.class);
	
	//필터가 생성될 때 실행
	public void init(FilterConfig fConfig) throws ServletException {
		//logger를 이용해서 출력하는 방법
		//trace - debug - info - warn - error
		//debug, info, error 주로 사용
		
		//debug : 개발의 흐름을 파악하는 용도
		//infor : 메서드 실행 파악
		
		
		logger.info("초기화 필터 생성");
		
	}
	
	//필터가 파괴가될 때 실행(서버가 켜 있는 상태로 백엔드 코드가 수정되었을 때)
	public void destroy() {
		logger.info("초기화 필터 파괴");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		 // application 내장 객체 얻어오기
        ServletContext application = request.getServletContext();
        // 최상위 주소 얻어오기
        String contextPath =  ( (HttpServletRequest)request ).getContextPath();
        // 세팅
        application.setAttribute("contextPath", contextPath);
        
        chain.doFilter(request, response);
        
        
	}
	
	


}
