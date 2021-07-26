package kr.smhrd.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.smhrd.domain.BoardVO;
import kr.smhrd.mapper.BoardMapper;

//POJO
@Controller
public class BoardController{ //new BoardController(); ->Spring container(DI)
	// @Autowired
	@Inject
	// @Resource("boardMapper")
	private BoardMapper BoardMapper;
	//게시판리스트를 가져오는 동작
	//HandlerMapping
	@RequestMapping("/boardList.do")// void로 하면 디펄트값인 boardList.do에있는  boardList가됨 그래서 같게하면 void로해도됨
	public void boardList(Model model) {
	List<BoardVO> list=BoardMapper.boardList();
	
	model.addAttribute("list", list);// 객체바인딩 ->ModelAndView ->Model
	
	//return "boardList";// ViewResolver--->WEB-INF/views/boardList.jsp
	}

	@RequestMapping("/boardForm.do")
	public void boardForm() {
		
		//return "boardForm"; //voardForm.jsp
	}
	@RequestMapping("/boardInsert.do")
	public String boardInsert(BoardVO vo) { //파라메터수집(자동) ->new BoardVO(
		BoardMapper.boardInsert(vo);		
		return "redirect:/boardList.do"; 
	}
	@RequestMapping(value="/boardCount.do",method=RequestMethod.GET)
	public String boardCount(int idx ) {
		BoardMapper.boardCount(idx);
		return "redirect:/boardContent.do?idx="+idx;
	}
	
	@RequestMapping("/boardContent.do")
	public void boardContent(int idx ,Model model){
		BoardVO vo = BoardMapper.boardContent(idx);
		model.addAttribute("vo",vo);
	//	return "boardContent";
	}
	@RequestMapping("/boardDelete.do")
	public String boardDelete(int idx ) {
		BoardMapper.boardDelete(idx);
		return "redirect:/boardList.do"; 
	}
	@RequestMapping(value="/boardUpdate.do", method=RequestMethod.POST)
	public String boardUpdate(BoardVO vo) {
		BoardMapper.boardUpdate(vo);		
		return "redirect:/boardList.do";
	}
// --------------------------Ajax-------------------------------
	@RequestMapping("/boardListAjax.do")
	public @ResponseBody List<BoardVO> boardListAjax(){// @ResponseBody가 있으면 JSON으로 응답하겠다는거임
		List<BoardVO> list=BoardMapper.boardListAjax(); // 게시판 전체리스트 가져오기
		return list; //객체를 리턴 -----{JSON API}-->String 변환 --->응답
	}

	@RequestMapping("/boardDeleteAjax.do")
	public @ResponseBody int boardDeleteAjax(int idx ) {
		 BoardMapper.boardDeleteAjax(idx);
		 int cnt =1;
		return cnt; 
	}
}

