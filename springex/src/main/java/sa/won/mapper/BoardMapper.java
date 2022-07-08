package sa.won.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import sa.won.domain.Criteria;
import sa.won.domain.boardVO;

public interface BoardMapper {
	//xml파일의 id와 메서드 명이 같다면 자동으로 매칭된다.
	

	//@Select("select * from tbl_board where bno > 0")
	public List<boardVO> getList();
	
	//페이징을 위해 앞서 만든 Criteria타입을 파라미터로 받는 메서드 생성
	public List<boardVO> getListWithPaging(Criteria cri);
	
	//페이징 후 totlacount를 알아오기 위한 메서드 생성
	public int getTotalCount(Criteria cri);
	
	public void insert(boardVO board);
	
	public void insertSelectKey(boardVO board);
	
	public boardVO read(Long bno);
	
	public int delete(Long bno);
	
	public int update(boardVO board);
}
