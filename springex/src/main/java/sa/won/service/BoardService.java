package sa.won.service;

import java.util.List;

import sa.won.domain.Criteria;
import sa.won.domain.boardVO;

public interface BoardService {

	public void register(boardVO board);
	
	public boardVO get(Long bno);
	
	public boolean modify(boardVO board);
	
	public boolean remove(Long bno);
	
	//public List<boardVO> getList();
	
	//페이징 처리를 위해 변경
	public List<boardVO> getList(Criteria cri);
	
	//totalcount를 위해 추가
	public int getTotal(Criteria cri);
}
