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
       
	//Logger / Debug Mode ���
	//Logger ��ü ����(�ش� Ŭ������ ���� log�� ����ϴ� ��ü)
	private Logger logger = LoggerFactory.getLogger(InitFilter.class);
	
	//���Ͱ� ������ �� ����
	public void init(FilterConfig fConfig) throws ServletException {
		//logger�� �̿��ؼ� ����ϴ� ���
		//trace - debug - info - warn - error
		//debug, info, error �ַ� ���
		
		//debug : ������ �帧�� �ľ��ϴ� �뵵
		//infor : �޼��� ���� �ľ�
		
		
		logger.info("�ʱ�ȭ ���� ����");
		
	}
	
	//���Ͱ� �ı����� �� ����(������ �� �ִ� ���·� �鿣�� �ڵ尡 �����Ǿ��� ��)
	public void destroy() {
		logger.info("�ʱ�ȭ ���� �ı�");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		 // application ���� ��ü ������
        ServletContext application = request.getServletContext();
        // �ֻ��� �ּ� ������
        String contextPath =  ( (HttpServletRequest)request ).getContextPath();
        // ����
        application.setAttribute("contextPath", contextPath);
        
        chain.doFilter(request, response);
        
        
	}
	
	


}
