package sa.won.domain;

import lombok.Data;

@Data
public class Criteria {
//Criteria는 검색의 기준을 의미한다.
	
	private int pageNum;
	//pageNum = 페이지 번호
	private int amount;
	// amount = 데이터 수
	
	//검색처리를 위한 추가
	private String type;
	private String keyword;
	
	//
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
   
	public Criteria() {
		this(1,10);
	}
	
	//검색처리를 위한 추가
	/*getTypeArr에는 (T,C,W)로 구성되어 있다. (쿼리문에서)
	검색 조건을 배열로 만들어서 한 번에 처리하기 위해 아래 처럼 작성한다.
	type이 null이라면 new String[] {} 이 동작해 배열을 생성하고 
	type이 null이 아니라면 split()으로 한 자씩 꺼내서 배열에 넣어준다. */
	public String[] getTypeArr() {
		return type == null? new String[] {}: type.split("");
	}
	
	
}
