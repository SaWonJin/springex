package sa.won.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import sa.won.domain.boardVO;

//@RunWith ���� ���� �׽�Ʈ �ڵ尡 �������� �����ϴ� ������ �� ���̶�� ���� ǥ���Ѵ�.  
@RunWith(SpringJUnit4ClassRunner.class)

//@ContextConfiguration �� ���� ������ Ŭ������ ���ڿ��� �̿��ؼ� �ʿ��� ��ü���� ������ ���� ��ü�� ����ϰ� �ȴ�. 
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")

//@Log4j�� Lombok�� �̿��ؼ� �α׸� ����ϴ� logger�� ������ �����Ѵ�. 
@Log4j
public class BoardServiceTests {
	
	@Setter(onMethod_ = {@Autowired})
	private BoardService service;
	
	@Test
	public void testExist() {
		
		log.info(service);
		assertNotNull(service);
		//assertNotNull()�� service ������ null�� �ƴϾ�߸� �׽�Ʈ�� �����Ѵٴ� ���� �ǹ��Ѵ�. 
	}
	
	@Test
	public void testRegister() {
		boardVO board = new boardVO();
		
		board.setTitle("���� �ۼ��ϴ� ��");
		board.setContent("���� �ۼ��ϴ� �۳���");
		board.setWriter("mococo");
		
		service.register(board);
		
		log.info("������ �Խù��� ��ȣ : " + board.getBno());
	}
	
//	@Test
//	public void testGetList() {
//		
//		service.getList().forEach(board -> log.info(board));
//	}
	
	@Test
	public void testGet() {
		
		log.info(service.get(1L));
	}
	
	@Test
	public void testDelete() {
		
		log.info("REMOVE RESULT : " +service.remove(1L));
	}
	
	@Test
	public void testUpdate() {
		
		boardVO board = service.get(19L);
		
		if(board == null) {
			return;
		}
		
		board.setTitle("�����մϴ�.");
		log.info("MODIFY RESULT : " +service.modify(board));
	}

}
