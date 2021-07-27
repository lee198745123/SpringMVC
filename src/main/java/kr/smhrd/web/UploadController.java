package kr.smhrd.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

	@GetMapping("/uploadForm.do")
	public void uploadForm() {
		// uploadForm.jsp
	}
	@PostMapping("/uploadFormAction.do")
	public void uploadFormAction(MultipartFile[] uploadFile){
		
		String uploadFolder="C:\\upload";
		String uploadFolderPath =getFolder(); //2021\07\27 이런식으로 들가겠지 ?
		File uploadPath= new File(uploadFolder,uploadFolderPath);
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs(); // 디렉토리 생성
		}
		for(MultipartFile MultipartFile: uploadFile) {
			System.out.print(MultipartFile.getOriginalFilename()+":");
			System.out.println(MultipartFile.getSize());
			
			String uploadFileName= MultipartFile.getOriginalFilename();
			UUID uuid =UUID.randomUUID();//자바에서 랜덤수 제공 해주는 객체
			uploadFileName=uuid.toString()+"_"+uploadFileName;
			
			File saveFile=new File(uploadPath,uploadFileName);
			try {
				MultipartFile.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	private String getFolder() {
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		Date date =new Date(); // util에있는거 임포트하셈
		String str =sdf.format(date);
		
		return str.replace("-", File.separator);
				
	}
}
