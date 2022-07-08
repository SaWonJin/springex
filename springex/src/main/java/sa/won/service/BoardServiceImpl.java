package sa.won.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import sa.won.domain.Criteria;
import sa.won.domain.boardVO;
import sa.won.mapper.BoardMapper;

@Log4j
@Service

//@AllArgsConstructor는 모든 파라미터를 이용하는 생성자를 만든다.
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	//spring 4.3이상에서 자동 처리
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	// 글 새로 입력하기
	@Override
	public void register(boardVO board) {

		log.info("register........." + board);
		
		mapper.insertSelectKey(board);
		
	}

	// 글 하나 가져오기
	@Override
	public boardVO get(Long bno) {
		
		log.info("get ----------------------" + bno);
		return mapper.read(bno);
	}

	
	// 글 수정 하기
	@Override
	public boolean modify(boardVO board) {
		
		log.info("modify-------------------" + board);
		return mapper.update(board) == 1;
	}

	
	// 글 삭제 하기 
	@Override
	public boolean remove(Long bno) {
		
		log.info("remove--------------------" + bno);
		return mapper.delete(bno) == 1;
	}


//	@Override // 전체 목록 가져오는 
//	public List<boardVO> getList() {
//		
//		log.info("get List --------------------");
//		return mapper.getList();
//	}
	
	
	//전체 리스츠 가져오기 + 페이징 처리 
	@Override
	public List<boardVO> getList(Criteria cri) {
	
		log.info("get List with criteria : --------------------" + cri);
		
		return mapper.getListWithPaging(cri); 
	}

	//총 개수 가져오기 
	// 페이징 처리할 때 필요한 기능이다.
	@Override
	public int getTotal(Criteria cri) {

		log.info("get totla count");
		
		return mapper.getTotalCount(cri);
	
	}

	
	
	
}
