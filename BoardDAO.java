package com.samsung.board.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.samsung.board.vo.BoardVO;
import com.samsung.emp.utils.JDBCUtils;

public class BoardDAO {
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	public ArrayList<BoardVO> getBoardList() {
		ArrayList<BoardVO> list = new ArrayList<>();
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from board";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				BoardVO vo = new BoardVO();
				vo.setSeq(rs.getInt("seq"));
				vo.setTitle(rs.getString("title"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegDate(rs.getDate("regdate"));
				vo.setCnt(rs.getInt("cnt"));
				vo.setUserid(rs.getString("userid"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, stmt, conn);
		}
		return list;

	}

	public void addBoard(BoardVO vo) {
		try {
			conn = JDBCUtils.getConnection();
			String sql = "insert into board(seq, title, nickname, content, regdate, userid) "
					+ "values( (select nvl(max(seq), 0)+1 from board), "
					+ "?, ?, ?, sysdate, 'guest')";
			stmt = conn.prepareStatement(sql);

			// 4단계 => 쿼리에 들어갈 변수들을 세팅 작업
			// ?에 대한 세팅 작업
			stmt.setString(1, vo.getTitle());
			stmt.setString(2, vo.getNickname());
			stmt.setString(3, vo.getContent());

			// 5단계 => 쿼리를 실행하고 그 결과값을 받아온다.
			int cnt = stmt.executeUpdate();

			System.out.println(cnt + "개 정상 입력되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(stmt, conn);
		}

	}

	public BoardVO getBoard(BoardVO vo) {
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from board where seq=?";
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, vo.getSeq());

			rs = stmt.executeQuery();

			if (rs.next()) {
				vo.setSeq(rs.getInt("seq"));
				vo.setTitle(rs.getString("title"));
				vo.setNickname(rs.getString("nickname"));
				vo.setRegDate(rs.getDate("regdate"));
				vo.setCnt(rs.getInt("cnt"));
				vo.setUserid(rs.getString("userid"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, stmt, conn);
		}
		return vo;
	}
	
	public void deleteBoard(BoardVO vo){
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JDBCUtils.getConnection();
			
			String sql = "delete from board where seq = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, vo.getSeq());
			
			int cnt = stmt.executeUpdate();
			
			System.out.println(cnt+"개 정상 삭제되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(stmt, conn);
		}
	}
	
	public void updateBoard(BoardVO vo){
		try {
			conn = JDBCUtils.getConnection();
			
			String sql = "update board set title = ?, content = ? where seq=?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, vo.getTitle());
			stmt.setString(2, vo.getContent());
			stmt.setInt(3, vo.getSeq());
			
			int cnt = stmt.executeUpdate();
			
			System.out.println(cnt+"개 정상 수정되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(stmt, conn);
		}

	}
	
	
	
	
	
	
	
	
	
	

}
