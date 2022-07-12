package sa.won.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import sa.won.domain.Criteria;
import sa.won.domain.PageDTO;
import sa.won.domain.boardVO;
import sa.won.service.BoardService;

@Controller
@Log4j
@RequestMapping("/board")
@AllArgsConstructor
public class BoardController {
	
	private BoardService service;
	
	
	// 페이징 추가 됨으로써 cri객체 들어감 
	//Criteria 클래스를 만들어 두면 아래와 같이 평하게 하나의 타입만으로 파라미터나 리턴타입을 사용할 수 있기 때문에 편리하다.
	@GetMapping("/list")
	public void list(Criteria cri ,Model model) {
		
		log.info("list" + cri);
		
		model.addAttribute("list", service.getList(cri));
		
		//totalcount를 위한 객체 생성
		int total = service.getTotal(cri);
		
		//페이징을 위한 추가
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	//addAttribute를 통해 BoardServiceImpl객체의 getList() 결과를 담아 전달합니다. 
	
	
	//RedirectAttributes를 파라미터로 지정하는 이유는 등록 작업이 끝난 후 다시 목록 화면으로 이동하기 위함이다. 
	//리턴시 redirect: 접두어를 사용하는데 이를 이용하면 스프링MVC가 내부적으로 response.sendRedirect()를 처리한다. 
	@PostMapping("/register")
	public String register(boardVO board, RedirectAttributes rttr) {
		
		log.info("register : "+ board);
		
		service.register(board);
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}
	
	//게시물의 등록 작업은 POST방식으로 처리하지만 , 화면에서 입력을 받아야 하므로 GET방식으로 입력 페이지를 볼 수 있도록 만들어준다. 
	@GetMapping("/register")
	public void register() {
		//register() 는 입력 페이지를 보여주는 역할만을 하기 때문에 별도의 처리가 필요하지 않다.
		//p.235
	}
	
	
	//@RequestParam("bno") 를 통해 bno값을 좀 더 명시적으로 처리한다. 
	//화면 쪽으로 해당 번호의 게시물을 전달해야 하기 때문에 model을 파라미터로 지정한다. 
	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long bno, Model model, @ModelAttribute("cri")Criteria cri) {
		//@ModelAttribute는 자동으로 Model에 데이터를 지정한 이름으로 담아줍니다.
		//위에는 Criteria에 있는 데이터들을 cri라는 이름으로 지정해 넣어줍니다.  
		
		log.info("/get or modify");
		model.addAttribute("board", service.get(bno));
	}
	
	
	//service.modify()는 수정 여부를 boolean으로 처리하므로 
	@PostMapping("/modify")
	public String modify(boardVO board, RedirectAttributes rttr, @ModelAttribute("cri")Criteria cri) {
		
		log.info("modify : " + board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", board.getBno());
		}
		
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		// 검색 기능이 추가 되었기 때문에 아래 두개 추가해준다. 
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	
	//삭제는 되는데 int값 이라는 오류가 뜸 ...??
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr, @ModelAttribute("cri")Criteria cri) {
		
		log.info("remove" +bno);
		
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		
		// 수정이랑 삭제부분에 아래 추가하면 원해 보던 페이지로 넘어가게 만들었다는데 안넘어가진다. 
		// 애초에 pageNum=1 / amount=10 으로 잡히는데..?
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		// 검색 기능이 추가 되었기 때문에 아래 두개 추가해준다. 
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());
		
		return "redirect:/board/list";
		//리다이렉트는 get 방식으로 이루어진다. 
	}
}
