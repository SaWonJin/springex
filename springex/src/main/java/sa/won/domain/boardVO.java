package sa.won.domain;


import java.util.Date;

import lombok.Data;

@Data
public class boardVO {
	
	
	//lombok 을 이용해서 생성자/getter-setter/toString()등을 만들어 낸다. 
	// 이를 위해 위의 @Data를 사용한다. 
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
	
}
