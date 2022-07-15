package sa.won.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import sa.won.domain.Criteria;
import sa.won.domain.ReplyVO;
import sa.won.mapper.ReplyMapper;

@Service
@Log4j
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService{
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Override
	public int register(ReplyVO vo) {
		
		log.info("register........." + vo);
		
		return mapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		
		log.info("get............"+rno);
		
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {

		log.info("modify........." + vo);
		
		return mapper.update(vo);
	}

	@Override
	public int remove(int rno) {

		log.info("modify.........." + rno);
		
		return mapper.delete(rno);
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, long bno) {

		log.info("get Reply List of a Board" + bno);
		
		return mapper.getListWithPaging(cri, bno);
	}

}
