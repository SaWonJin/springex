package sa.won.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import sa.won.domain.Criteria;
import sa.won.domain.boardVO;


//@RunWith 으로 현재 테스트 코드가 스프링을 실행하는 역할을 할 것이라는 것을 표시한다.  
@RunWith(SpringJUnit4ClassRunner.class)

//@ContextConfiguration 을 통해 지정된 클래스나 문자열을 이용해서 필요한 객체들을 스프링 내에 객체로 등록하게 된다. 
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")

//@Log4j는 Lombok을 이용해서 로그를 기록하는 logger를 변수로 생성한다. 
@Log4j
public class BoardMapperTests {
	
	
	//@Autowired는 해당 인스턴스 변수가 스프링으로부터 자동으로 주입해 달라는 표시다. 
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	//위와 같은 경우는 BoardMapper mapper = new BoardMapper(); 과 같다.
	
	
	//@Test는 JUnit에서 테스트 대상을 표시하는 어노테이션입니다. 
	// Run As >> JunitTest를  실행해서 테스트 결과를 확인합니다. 
	
	// select 
	@Test
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board));
	}
	
	//insert 인데 bno값이 없는 값
	@Test
	public void testInsert() {
		boardVO board = new boardVO();
		
		board.setTitle("새로 작성한글");
		board.setContent("새로 작성하는 내용");
		board.setWriter("newuser");
		
		mapper.insert(board);
		
		log.info(board);
	}
	
	//insert인데 bno 값이 있는 값
	@Test
	public void testInsertSelectKey() {
		boardVO board = new boardVO();
		
		board.setTitle("새로 작성한글 select key");
		board.setContent("새로 작성하는 내용 select key");
		board.setWriter("newuser");
		
		mapper.insertSelectKey(board);
		
		log.info(board);
	}
	
	
	//1개 글 읽어오기
	@Test
	public void testRead() {
		boardVO board = mapper.read(5L);
		
		log.info(board);
	}
	
	//delete 삭제하기 <근데 오류나면서 삭제됨>
	@Test
	public void testDelete() {
		
		log.info("DELETE COUNT: " +mapper.delete(16L));
	}
	
	//update 수정하기 
	@Test
	public void testUpdate() {
		
		boardVO board = new boardVO();
		// 실행 전에 번호가 있는지 확인해야 한다. 
		
		board.setBno(5L); // Long형 이여서 L자가 붙어야 한다. 
		board.setTitle("수정된 제목");
		board.setContent("수정된 내용");
		board.setWriter("us");
		
		int count = mapper.update(board);
		log.info("UPDATE COUNT : "+ count);
		
	}
	
	@Test
	public void testPaging() {
		
		Criteria cri = new Criteria();
		
		cri.setPageNum(3);
		cri.setAmount(10);
		
		List<boardVO> list = mapper.getListWithPaging(cri);
		
		list.forEach(board -> log.info(board.getBno()));
	}

	
}
