package sa.won.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import sa.won.domain.Criteria;
import sa.won.domain.ReplyVO;
import sa.won.service.ReplyService;

@RequestMapping("/replies")
@RestController
@Log4j
@AllArgsConstructor
// 스프링 4.3이상에서 @AllArgsConstructor를 이용해서 ReplyService타입의 객체를 
// 필요로 하는 생성자를 만들어서 사용합니다.
public class ReplyController {

	
	private ReplyService service;
	
	
//	REST방식으로 처리할 때 주의할 점 
	//- 브라우저나 외부에서 서버를 호출할 때 데이터의 포맷과 서버에서 보내주는 데이터의 타입을
	// 명확히 설계해야 하는 것입니다. 
	
	//Post방식을 이용해서 JSP에 form태그 같은 곳에서 값입력을 해야 동작하고 
	// consumes 에 json을 줘서 JSON방식의 데이터만 처리하도록 했고,
	// produces 를 통해 문자열을 반환하도록 설계한다. 
	@PostMapping(value = "/new", consumes = "application/json",
				produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		
		log.info("ReplyVO :"+ vo);
		
		int insertCount = service.register(vo);
		
		log.info("Reply INSERT COUNT : " + insertCount);
		
		
		//삼항 연산으로 처리했다. 
		//댓글이 추가되었으면 insertCount가 1이 올라간다. 
		// 1dlaus "success"를 보여주고 그렇지 않다면 그 밑에 오류를 보여준다.
		return insertCount == 1 ?
				new ResponseEntity<>("success", HttpStatus.OK) :
					new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	//Criteria를 이용해서 파라미터를 수집한다. 
	// {bno} , {page}의 값을 받아오면 된다. 
	@GetMapping(value = "/pages/{bno}/{page}",
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,
						MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("page") int page,
												 @PathVariable("bno") Long bno) {
		
		log.info("getList.....................");
		Criteria cri = new Criteria(page,10);
		
		log.info(cri);
		
		return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
	}
	
	//댓글 조회
	@GetMapping(value= "/{rno}", produces = {MediaType.APPLICATION_ATOM_XML_VALUE,
											 MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {
		
		log.info("get :" + rno);
		
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
	}
	
	//댓글 삭제
	@DeleteMapping(value="/{rno}", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("rno") int rno){
		
		log.info("ewmove : "+ rno);
		
		return service.remove(rno) == 1 ?
				new ResponseEntity<>("success", HttpStatus.OK) :
					new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//댓글 수정
	//댓글 수정은 'PUT'방식이나 'PATCH'방식을 이용하도록 처리한다.
	//실제 수정되는 데이터는 JSON포맷이므로 @Requestody를 이용해서 처리한다. 
	//@Requestody로 처리되는 데이터는 일반 파라미터나 @PathVariable파라미터를 처리할 수 없기 때문에 직접 처리해 주는 부분을 주의해아 한다.
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
					value = "/{rno}",
					consumes = "application/json",
					produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo,
										 @PathVariable("rno") Long rno) {
		
		vo.setBno(rno);
		
		log.info("rno : "+ rno);
		log.info("modify :" + vo);
		
		return service.modify(vo) == 1 ?
				new ResponseEntity<>("success", HttpStatus.OK) :
					new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
