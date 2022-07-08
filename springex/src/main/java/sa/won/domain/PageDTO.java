package sa.won.domain;

import lombok.Data;

@Data
public class PageDTO {
	//PageDTO는 생성자를 정의하고 Criteria 와 전체 데이터 수(total)를 파라미터로 지정합니다. 
	/* Criteria안에는 페이지에서 보여주는 데이터 수(amount)와 현재 페이지 번호(pageNum)를 가지고 있기 떄문에
	   이를 이용해서 필요한 모든 애용을 계산할 수 있습니다. */
	
	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	private int total;
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0))* 10;
		
		this.startPage = this.endPage -9;
		
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		
			if(realEnd < this.endPage) {
				this.endPage = realEnd;
			}
			//위의 if식이 필요한 이유는 혹시 모를 오류에 대비하기 위함이다. 
		
		this.prev = this.startPage > 1;
		
		this.next = this.endPage < realEnd;
	}
}

/*
 페이징 처리할 때 필요한 정보들
 
 1. 현재 페이지 번호 (pageNum)
 2. 이전과 다음으로 이동 가능한 링크의 표시 여부 (prev, next)
 3.화면에서 보여지는 페이지의 시작번호와 끝 번호 (startPage, endPage)
 
 
 //페이지 번호가 10개씩 보인다고 가정한다. 
 [1] 페이징 끝 번호(endPage) 계산
 
 	this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0))* 10;
 	
 	위와 같이 계산하는 이유 : Long형으로 되어 있기 때문에 (int)를 넣어 정수값으로 바꿔준다. 
 						Math.ceil은 값을 올림 해준다. 
 						ex> PageNum이 1~10 까지는 10을 보여주고
 							11부터는 올림 하면 2가 되기때문에 20을 보여준다. 
 
 	끝 번호를 먼저 계산하는 이유는	시작 번호를 계산하기 수월하기 때문입니다. 
 
 [2] 페이징 시작 번호(startPage) 계산
 
 	this.startPage = this.endPage -9;
 	
 	만일 화면에 10개씩 보여준다면 시작 번호(startPage)는 무조건 끝 번호 (endPage)에서 9라는 값을 뺀 값이 된다.
 
 [3] total을 통한 endPage의 재계산
 
 	전체 데이터 수(total)를 이용해서 진짜 끝 페이지(realEnd)가 몇 번까지 되는지를 계산한다.
 	진짜 끝 페이지(realEnd)가 구해둔 끝 번호(endPage) 보다 작다면 끝 번호는 작은 값이 되어야 한다. 
 
 [4] 이전(prev)과 다음(next) 계산
 
 	이전의 경우는 시작 번호가 1보다 큰 경우라면 존재하게 된다. 
 	
 	다음으로 가는 링크의 경우 위의 realEnd가 끝 번호(endPage) 보다 큰 경우에만 존재하게 된다. 
 
 */
