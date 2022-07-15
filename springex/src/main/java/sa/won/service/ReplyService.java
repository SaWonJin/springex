package sa.won.service;

import java.util.List;

import sa.won.domain.Criteria;
import sa.won.domain.ReplyVO;

public interface ReplyService {
	
	public int register(ReplyVO vo);
	
	public ReplyVO get(Long rno);
	
	public int modify(ReplyVO vo);
	
	public int remove(int rno);
	
	public List<ReplyVO> getList(Criteria cri, long bno);
}
