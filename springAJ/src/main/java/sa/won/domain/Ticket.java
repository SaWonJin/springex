package sa.won.domain;

import lombok.Data;

//@Data�� get,set toString, �����ڸ� �ڵ����� ������ش�. 
@Data
public class Ticket {

	private int tno;
	private String owner;
	private String grade;
}
