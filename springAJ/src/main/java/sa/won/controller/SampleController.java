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

//@RestController�� JSP�� �޸� ������ �����͸� ��ȯ�ϴ� �����̹Ƿ� �پ��� ������ �����͸� ������ �� �ֽ��ϴ�. 
//���� @controller�� ���ڿ��� ��ȯ�ϴ� ��쿡�� JSP������ �̸����� ó�������� @RestController�� ������ �����Ͱ��ȴ�.
@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {

	
	@GetMapping(value="/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		
		log.info("MIME TYPE: "+MediaType.TEXT_PLAIN_VALUE);
		
		return "�ȳ��ϼ���";
	}
	
	@GetMapping(value="/getSample", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,
												MediaType.APPLICATION_ATOM_XML_VALUE})
	public SampleVO getSample() {
		return new SampleVO(112, "��Ÿ", "�ε�");
	}
	
	
	//Produces�� ��� ���ƴ� ����. 
	@GetMapping(value = "/getSample2")
	public SampleVO getSample2() {
		return new SampleVO(113, "���̳�", "����");
	}
	
	
	//���� �����͸� �� ���� �����ϱ� ���ؼ� �迭�̳�, ����Ʈ�� �����⵵ �Ѵ�. 
	//getList()�� ���������� 1���� 10�̸������� ������ ó���ϸ鼭 SampleVO��ü�� ����� List<SampleVO>�� �����մϴ�.
	@GetMapping(value = "/getList")
	public List<SampleVO> getList(){
		
		return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i+"First", i+"Last")).collect(Collectors.toList());
		//return ���� �ڵ���� �׳� �迭�ȿ� �ֱ� ���� ������.
	}
	
	//	REST������� ȣ���ϴ� ��� ȭ�� ��ü�� �ƴ϶� ������ ��ü�� �����ϴ� ���
	// ���� �����͸� ��û�� �ʿ����� �������� ���������� ���������� ������������ ������ �� �ִ� Ȯ���� ����� �����ؾ� �Ѵ�. 
	// ResponseEntity�� �����Ϳ� �Բ� HTTP����� ���� �޽��� ���� ���� �����ϴ� �뵵�� ����մϴ�. 
	@GetMapping(value="/check", params = {"height", "weight"})
	
	//check �� �ݵ�� 'height','weight'�� �Ķ���ͷ� �޽��ϴ�.
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		
		SampleVO vo = new SampleVO(0, ""+height, "" + weight);
		
		ResponseEntity<SampleVO> result = null;
		
		//height���� 150���� �۴ٸ� 502(bad_geteway)�����ڵ�� �����͸� �����ϰ�, �׷��� �ʴٸ� 200�ڵ�(OK)�� �����͸� �����Ѵ�. 
		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	
	//@RestController�� ������ @controller���� ����ϴ� �Ϲ����� Ÿ���̳� ����ڰ� ������ Ÿ��(Ŭ����)�� ����մϴ�.
	//
	//@PathVariable : URL����� �Ϻθ� �Ķ���ͷ� ����� �� �̿�
	//@RequestBody : JSON �����͸� ���ϴ� Ÿ���� ��ü�� ��ȯ�ؾ� �ϴ� ��쿡 �ַ� ���
	
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		return new String[] {
				"category :" + cat, "productid :" +pid
		};
	}
	
	//@Requsestody�� ���޵� ��û(request)�� ����(body)�� �̿��ؼ� �ش� �Ķ������ Ÿ������ ��ȯ�� �䱸�մϴ�. 
	
	
	
	//@PostMapping���� ó���� ������ @requestodyu�� �� �״�� ��û�� ������ ó���ϱ� ������ 
	// �Ϲ����� �Ķ���� ���޹���� ����� �� ���� �����̴�. 
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		
		log.info("convert...........ticket"+ticket);
		
		return ticket;
	}
}
