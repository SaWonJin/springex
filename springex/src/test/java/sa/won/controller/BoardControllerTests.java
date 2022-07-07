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

//@RunWith 으로 현재 테스트 코드가 스프링을 실행하는 역할을 할 것이라는 것을 표시한다.  
@RunWith(SpringJUnit4ClassRunner.class)

//@ContextConfiguration 을 통해 지정된 클래스나 문자열을 이용해서 필요한 객체들을 스프링 내에 객체로 등록하게 된다. 
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})

//Test for controller
// Servlet의 ServletContext를 이용하기 위한 어노테이션, 스프링에서는 WebApplicationContext 존재를 이용하기 위함
@WebAppConfiguration

//@Log4j는 Lombok을 이용해서 로그를 기록하는 logger를 변수로 생성한다. 
@Log4j
public class BoardControllerTests {

	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mock;
	
	
	//@before이 붙은 애는 import할 때 JUnit을 이용해야 한다. 
	//@Before가 적용된 메서드는 모든 테스트전에 매번 실행되는 메서드가 된다. 
	@Before
	public void setup() {
		this.mock = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	//MockMvc는 말 그대로 가짜 'mvc'라고 생각하면 된다. 
	// MockMvcRequestBuilders를 이용해 GET방식으로 호출할 수 있다. 
	
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
				.param("title", "테스트 새글 제목")
				.param("content", "테스틑 새글 내용")
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
	//param 을 이용해서 bno값을 가져옵니다. 
	
	
	@Test
	public void testModify() throws Exception {
		
		String resultPage = mock
				.perform(MockMvcRequestBuilders.post("/board/modify")
						.param("bno", "1")
						.param("title", "수정쓰")
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
