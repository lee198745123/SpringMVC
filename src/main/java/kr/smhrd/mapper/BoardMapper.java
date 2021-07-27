package kr.smhrd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.smhrd.domain.BoardVO;
@Mapper
public interface BoardMapper {

		//SQL(x) --->SQL Mapper file(XML)
		public List<BoardVO>  boardList();
		public void boardInsert(BoardVO vo); // insert SQL	
		
		
		@Select("select *from tbl_board where idx=#{idx}")
		public BoardVO boardContent(int idx);//select SQL
		
		@Update("update tbl_board set count=count+1 where idx=#{idx}")
		public void boardCount(int idx);
		
		@Delete("delete from tbl_board where idx=#{idx}")
		public void boardDelete(int idx);
		
		@Update(" update tbl_board set title = #{title}, contents = #{contents} where idx = #{idx}")
		public void boardUpdate( BoardVO vo);
		
// -----------------------------------------------Ajax-------------------------------------		
		public List<BoardVO> boardListAjax();
		
		@Delete("delete from tbl_board where idx=#{idx}")
		public int boardDeleteAjax(int idx);
		
		
		
		
}
