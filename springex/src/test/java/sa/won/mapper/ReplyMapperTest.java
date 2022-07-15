package sa.won.mapper;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import sa.won.domain.ReplyVO;

//@RunWith 으로 현재 테스트 코드가 스프링을 실행하는 역할을 할 것이라는 것을 표시한다.  
@RunWith(SpringJUnit4ClassRunner.class)

//@ContextConfiguration 을 통해 지정된 클래스나 문자열을 이용해서 필요한 객체들을 스프링 내에 객체로 등록하게 된다. 
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")

//@Log4j는 Lombok을 이용해서 로그를 기록하는 logger를 변수로 생성한다. 
@Log4j
public class ReplyMapperTest {
	
	private Long[] bnoArr = {589840L, 589839L, 589838L, 589837L, 589836L};

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	
	// 댓글을 위한 테스트 위에 있는 bnoArr에 있는 게시판 번호로 댓글을 넣어주기 위한 코드 
	@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			
			ReplyVO vo = new ReplyVO();
			
			//게시물의 번호
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트" + i);
			vo.setReplyer("replyer"+ i);
			
			mapper.insert(vo);
		});
	}
	
	//특정 댓글 읽기 테스트
	@Test
	public void testRead() {
		
		Long tergetRno = 5L;
		
		ReplyVO vo = mapper.read(tergetRno);
		
		log.info(vo);
	}
	
	//특정 댓글 삭제 테스트
	@Test
	public void testDelete() {
		
		int targetRno = 1;
		
		mapper.delete(targetRno);
	}
	
	//댓글 수정 테스트
	@Test
	public void testUpdate() {
		
		Long targetRno = 10L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		vo.setReply("수정댓글입니다.");
		
		int count = mapper.update(vo);
		
		log.info("UPDATE COUNT : "+count);
	}
	
	@Test
	public void testMapper() {
		log.info(mapper);
	}
}
