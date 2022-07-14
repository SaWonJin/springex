package sa.won.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;
import sa.won.domain.SampleVO;
import sa.won.domain.Ticket;

//@RestController는 JSP와 달리 순수한 데이터를 반환하는 형태이므로 다양한 포맷의 데이터를 전송할 수 있습니다. 
//기존 @controller는 문자열을 반환하는 경우에는 JSP파일의 이름으로 처리하지만 @RestController는 순수한 데이터가된다.
@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {

	
	@GetMapping(value="/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		
		log.info("MIME TYPE: "+MediaType.TEXT_PLAIN_VALUE);
		
		return "안녕하세요";
	}
	
	@GetMapping(value="/getSample", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,
												MediaType.APPLICATION_ATOM_XML_VALUE})
	public SampleVO getSample() {
		return new SampleVO(112, "스타", "로드");
	}
	
	
	//Produces가 없어도 돌아는 간다. 
	@GetMapping(value = "/getSample2")
	public SampleVO getSample2() {
		return new SampleVO(113, "마이노", "로켓");
	}
	
	
	//여러 데이터를 한 번에 전송하기 위해서 배열이나, 리스트를 보내기도 한다. 
	//getList()는 내부적으로 1부터 10미만까지의 루프를 처리하면서 SampleVO객체를 만들어 List<SampleVO>를 리턴합니다.
	@GetMapping(value = "/getList")
	public List<SampleVO> getList(){
		
		return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i+"First", i+"Last")).collect(Collectors.toList());
		//return 옆에 코드들은 그냥 배열안에 넣기 위한 루프다.
	}
	
	//	REST방식으로 호출하는 경우 화면 자체가 아니라 데이터 자체를 전송하는 방식
	// 따라서 데이터를 요청한 쪽에서는 정상적인 데이터인지 비정상적인 데이터인지를 구분할 수 있는 확실한 방법을 제공해야 한다. 
	// ResponseEntity는 데이터와 함께 HTTP헤더의 상태 메시지 등을 같이 전달하는 용도로 사용합니다. 
	@GetMapping(value="/check", params = {"height", "weight"})
	
	//check 는 반드시 'height','weight'를 파라미터로 받습니다.
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		
		SampleVO vo = new SampleVO(0, ""+height, "" + weight);
		
		ResponseEntity<SampleVO> result = null;
		
		//height값이 150보다 작다면 502(bad_geteway)상태코드와 데이터를 전송하고, 그렇지 않다면 200코드(OK)와 데이터를 전송한다. 
		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	
	//@RestController는 기존의 @controller에서 사용하던 일반적인 타입이나 사용자가 정의한 타입(클래스)을 사용합니다.
	//
	//@PathVariable : URL경로의 일부를 파라미터로 사용할 때 이용
	//@RequestBody : JSON 데이터를 원하는 타입의 객체로 변환해야 하는 경우에 주로 사용
	
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		return new String[] {
				"category :" + cat, "productid :" +pid
		};
	}
	
	//@Requsestody는 전달된 용청(request)의 내용(body)을 이용해서 해당 파라미터의 타입으로 변환을 요구합니다. 
	
	
	
	//@PostMapping으로 처리한 이유는 @requestodyu가 말 그대로 요청한 내용을 처리하기 때문에 
	// 일반적인 파라미터 전달방식을 사용할 수 없기 때문이다. 
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		
		log.info("convert...........ticket"+ticket);
		
		return ticket;
	}
}
