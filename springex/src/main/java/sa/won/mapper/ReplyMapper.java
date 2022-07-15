package sa.won.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sa.won.domain.Criteria;
import sa.won.domain.ReplyVO;

public interface ReplyMapper {
	
	//댓글 입력하기
	public int insert(ReplyVO vo);
	
	//특정 댓글 읽기
	public ReplyVO read(Long rno);
	
	//특정 댓글 삭제하기 
	public int delete(int rno);
	
	//댓글 수정하기 
	public int update(ReplyVO reply);
	
	//페이징
	//Myatis는 두 개 이상의 데이처를 파라미터로 전달하기 위해서는 
	// 1) 별도의 객체로 구성 
	// 2) Map을 이용하는 방식
	// 3) @Param을 이용해서 이름을 사용하는 방식 입니다. 
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") Long bno);
}
