package sa.won.domain;

import lombok.Data;

@Data
public class Criteria {
//Criteria는 검색의 기준을 의미한다.
	
	private int pageNum;
	private int amount;
	
	//
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
   
	public Criteria() {
		this(1,10);
	}
	
	
}
