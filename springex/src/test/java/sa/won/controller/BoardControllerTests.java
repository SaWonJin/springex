package sa.won.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//@RunWith ���� ���� �׽�Ʈ �ڵ尡 �������� �����ϴ� ������ �� ���̶�� ���� ǥ���Ѵ�.  
@RunWith(SpringJUnit4ClassRunner.class)

//@ContextConfiguration �� ���� ������ Ŭ������ ���ڿ��� �̿��ؼ� �ʿ��� ��ü���� ������ ���� ��ü�� ����ϰ� �ȴ�. 
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})

//Test for controller
// Servlet�� ServletContext�� �̿��ϱ� ���� ������̼�, ������������ WebApplicationContext ���縦 �̿��ϱ� ����
@WebAppConfiguration

//@Log4j�� Lombok�� �̿��ؼ� �α׸� ����ϴ� logger�� ������ �����Ѵ�. 
@Log4j
public class BoardControllerTests {

	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mock;
	
	
	//@before�� ���� �ִ� import�� �� JUnit�� �̿��ؾ� �Ѵ�. 
	//@Before�� ����� �޼���� ��� �׽�Ʈ���� �Ź� ����Ǵ� �޼��尡 �ȴ�. 
	@Before
	public void setup() {
		this.mock = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	//MockMvc�� �� �״�� ��¥ 'mvc'��� �����ϸ� �ȴ�. 
	// MockMvcRequestBuilders�� �̿��� GET������� ȣ���� �� �ִ�. 
	
	@Test
	public void testList() throws Exception {
		
		log.info(
			mock.perform(MockMvcRequestBuilders.get("/board/list"))
			.andReturn()
			.getModelAndView()
			.getModelMap()
			);
		
	}
	
	
	@Test
	public void testRegister() throws Exception{
		
		String resultPage = mock.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "�׽�Ʈ ���� ����")
				.param("content", "�׽��z ���� ����")
				.param("writer", "usersss"))
				.andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	
	@Test
	public void testGet() throws Exception{
		
		log.info(mock.perform(MockMvcRequestBuilders
							  .get("/board/get")
							  .param("bno", "2"))
					.andReturn()
					.getModelAndView()
					.getModelMap());
	}
	//param �� �̿��ؼ� bno���� �����ɴϴ�. 
	
	
	@Test
	public void testModify() throws Exception {
		
		String resultPage = mock
				.perform(MockMvcRequestBuilders.post("/board/modify")
						.param("bno", "1")
						.param("title", "������")
						.param("content", "modify's")
						.param("writer", "useruser"))
				.andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	
	
	@Test
	public void testRemove() throws Exception{
		
		String resultPage = mock.perform(MockMvcRequestBuilders.post("/board/remove")
							.param("bno", "24"))
				.andReturn()
				.getModelAndView()
				.getViewName();
		
		log.info(resultPage);
				
	}
}
