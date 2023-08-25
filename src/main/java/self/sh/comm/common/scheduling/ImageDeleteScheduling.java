//package fp.art.stroke.common.scheduling;
//
//import java.io.File;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.servlet.ServletContext;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import edu.kh.comm.board.model.service.BoardService;
//
//@Component
//public class ImageDeleteScheduling {
//	
//	@Autowired
//	private BoardService service;
//	
//	@Autowired
//	private ServletContext application; //application scop객체 -> 서버 폴더 경로 얻어오기에 사용
//
//	private Logger logger = LoggerFactory.getLogger(ImageDeleteScheduling.class);
//	
//
//	
//	
//	//BOARD_IMG테이블에서는 삭제되었으나 
//	//서버/resources/images/board 폴더에 존재하는 
//	//이미지 파일을 정시마다 삭제
//	
//	
//	
//	
//	//@Scheduled(cron = "0 0 * * * *")  정시 마다
//	@Scheduled(cron = "0 0 * * * *") // 매 분  마다
//	public void serverImageDelete() {
//		//코딩순서
//		//1) BOARD_IMG에 존재하는 모든 이미지 목록 조회
//			List<String> dbList = service.selectDBList();
//		
//
//		//2) /resources/imgaes/board 폴더에 존재하는 모든 이미지 파일 목록 조회
//			
//			String folderPath = application.getRealPath("/resources/images/board");
//			File path = new File(folderPath); // "/resources/imgaes/board" 폴더를 참조하는 객체
//			
//			File[] arr = path.listFiles(); //path가 참조하는 폴더에 있는 모든 파일을 얻어와 File 배열 반환
//			
//			List<File> serverList = Arrays.asList(arr); //arr을 List로 변환
//		//3) 두 목록을 비교해서 일치하지 않는 이미지 파일 삭제(DB에는 없는데 서버폴더에 있으면 삭제)
//			if(!serverList.isEmpty()) { // 서버에 이미지 파일이 있을 때 비교/삭제 진행
//				
//				//server : \resources\images\board\sample1.png
//				// DB    : /resources/images/board/sample1.png
//				
//				for(File serverImage : serverList) {
//					String name = "/resources/images/board/" + serverImage.getName();//파일명만 얻어오기
//					
//					if(dbList.indexOf(name) == -1) {
//						//dbList에는 있는데 serverList에만 파일이 존재하는 경우
//						logger.info(serverImage.getName() + "삭제");
//						serverImage.delete(); //파일 삭제
//					}
//				}
//				logger.info("--------------서버 이미지 삭제 완료 ---------------");
//			}
//		
//		
//	}
//}
