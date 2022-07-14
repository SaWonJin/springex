package sa.won.domain;

import lombok.Data;

//@Data로 get,set toString, 생성자를 자동으로 만들어준다. 
@Data
public class Ticket {

	private int tno;
	private String owner;
	private String grade;
}
