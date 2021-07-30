package kr.smhrd.web;


import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.smhrd.domain.AttachFileVO;

@Controller
public class UploadController {

	@GetMapping("/uploadForm.do")
	public void uploadForm() {
		// uploadForm.jsp
	}
	@PostMapping("/uploadFormAction.do")
	public void uploadFormAction(MultipartFile[] uploadFile , Model model){ //model은 객체바인딩하기위해서 자바에서 만든 클래스객체라고 생각하면됨
		
		List<AttachFileVO> list = new ArrayList<AttachFileVO>();
		
		
		String uploadFolder="C:\\upload"; // 실제 업로드 장소
		String uploadFolderPath =getFolder(); //2021\07\27 이런식으로 들가겠지 ?  그래서 경로를 만들어줌
		File uploadPath= new File(uploadFolder,uploadFolderPath);
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs(); // 디렉토리 생성
		}
		for(MultipartFile MultipartFile: uploadFile) { //MultipartFile로 받는 이유 파일의 종류가 너무 다양하기때문
			
			 // System.out.print(MultipartFile.getOriginalFilename()+":");
			 // System.out.println(MultipartFile.getSize());
			 
			AttachFileVO vo =new AttachFileVO();
			
			String uploadFileName= MultipartFile.getOriginalFilename();
			vo.setFileName(uploadFileName); // vo에 file이름 set 해준거임
			UUID uuid =UUID.randomUUID();//자바에서 랜덤수 제공 해주는 객체 이것을 사용하는 이유 중복을 방지하기 위해서
			uploadFileName=uuid.toString()+"_"+uploadFileName;
			
			File saveFile=new File(uploadPath,uploadFileName);
			try {
				MultipartFile.transferTo(saveFile);
				vo.setUuid(uuid.toString()); // uuid는 랜덤수임 그걸 vo에 넣어준거임
				vo.setUploadPath(uploadFolderPath); //  
				list.add(vo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("list",list); // 객체바인딩
		}
	}
	private String getFolder() {
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		Date date =new Date(); // util에있는거 임포트하셈
		String str =sdf.format(date); // 2021/07/29
		
		return str.replace("-", "/");
				
	}
	//													MIME TYPE(application/octet-stream
	@GetMapping(value = "/download.do",produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	//@ResponseBody //download.jsp(X)
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent ,String fileName) {
		   //응답은 하나의형태로밖에 응답을 못한다 하지만 ResponseEntity 사용시 디테일하게 응답이 가능하다.
		Resource resource =new FileSystemResource("C:\\upload\\"+fileName);
		String resourceName =resource.getFilename();// 날짜빼고 나오는 부분
		System.out.println(resourceName);// 파일의 이름
		
		String resourceOriginalName=resourceName.substring(resourceName.indexOf("_")+1); //UUID없애는 부분 원래이름으로 슬라이싱하는부분
		// 다운로드 작업
		HttpHeaders headers =new HttpHeaders(); //서버에 응답하게 해주는 부분 
		try {
			String downloadName=null;
			// URLEncoder는 익스랑 edge에서 사용함
			if(userAgent.contains("Trident")) {
		           System.out.println("IE");
		           downloadName=URLEncoder.encode(resourceOriginalName,"UTF-8").replaceAll("\\+"," ");
		         }else if(userAgent.contains("Edge")) {
		           System.out.println("Edge");
		           downloadName=URLEncoder.encode(resourceOriginalName,"UTF-8");
		           
		         }else{
				System.out.println("Chrome");
				downloadName= new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");// 한글 안꺠지게 인코딩해주는 부분
			}
			
			
			headers.add("Content-Disposition", "attachment;filename="+downloadName); // 파일 첨부하는 부분
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);// ajax 사용시 쓰면 좋다 지금은 형식적으로 사용한것일뿐
	}
	

}
