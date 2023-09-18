package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {
	// 2022.1.4(화) 15h35
	// service 클래스의 중요한 역할 = Connection 객체 생성해서 dao에 넘겨주기
	
	public Member loginMember(String userId, String userPwd) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) dao에 요청
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		
		// 3) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 4) controller로 결과 넘기기
		return m;
	} // loginMember() 종료
	
	// 2022.1.5(수) 15h40
	public int insertMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		
		// DML 처리는 트랜잭션 처리 반드시 해 줘야 함 -> DB에 변경 내용 적용
		if (result > 0) { // DB에 insert 성공했다면 -> result = 1
			JDBCTemplate.commit(conn);
		} else { // 실패했다면 -> result = 0
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	} // insertMember() 종료
	
	// 2022.1.7(금) 11h30
	public Member updateMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().updateMember(conn, m);
		
		// 2022.1.7(금) 14h
		// 회원 정보 변경/update를 성공한 경우, 정보가 갱신된 회원의 객체를 다시 조회해오기
		// controller에서 updateMem 객체 받았을 때 update 성공(못)했는지 판단이 필요하므로, commit하는 if문 안(x) 여기(o)에 선언
		Member updateMem = null;
		
		// 트랜잭션 처리
		if (result > 0) { // 회원 정보 수정 성공한 경우
			JDBCTemplate.commit(conn);
			updateMem = new MemberDao().selectMember(conn, m.getUserId());
		} else { // 회원 정보 수정 실패한 경우
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return updateMem;
	} // updateMember() 종료
	
	// 2022.1.7(금) 16h25
	public Member updatePwdMember(String userId, String userPwd, String updatePwd) {
		Connection conn = JDBCTemplate.getConnection();
		
		// 비밀번호를 update하는 dao 메소드 호출 -> update문을 수행하는 것이므로 int로 반환 자료 받음
		int result = new MemberDao().updatePwdMember(conn, userId, userPwd, updatePwd);
		
		// 변경된 비밀번호가 응답 페이지에 반영되어야 하므로, 변경된 회원 정보를 담을 변수 updateMem 선언 + dao 호출 결과에 따라 '성공'인 경우 commit한 뒤에 새로 바뀐 회원 정보를 다시 받아옴 
		Member updateMem = null;
		
		if (result > 0) { // 비밀번호 변경 성공
			JDBCTemplate.commit(conn);
			updateMem = new MemberDao().selectMember(conn, userId); // userId로 행/정보 찾아주는 dao 메소드 -> 갱신된 회원 객체/정보를 다시 받아옴
		} else { // 비밀번호 변경 실패 -> 이 경우에는 회원 정보에 변경 사항이 없는 바(?내가 이해한 논리가 맞나?), null인 updateMem을 보냄
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return updateMem;
	} // updatePwdMember() 종료
	
	// 2022.1.7(금) 17h30 수업 실습 + 주말 숙제 -> 2022.1.10(월) 10h15 강사님 설명
	public int deleteMember(String userId, String userPwd) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().deleteMember(conn, userId, userPwd);
		
//		Member deleteMem = null; // 내가 숙제할 때 만든 것 = 탈퇴한 회원 정보(status 'N' 포함)를 controller에 넘겨서 응답 페이지 지정 시 활용하고자 했음
		
		if (result > 0) { // 회원 탈퇴 처리 성공 시
			JDBCTemplate.commit(conn);
//			deleteMem = new MemberDao().selectMember(conn, userId); // 내가 숙제할 때 만든 것
		} else { // 회원 탈퇴 처리 실패 시
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	} // deleteMember() 종료
	
	// 2022.1.17(월) 15h45
	public int idCheck(String checkId) {
		Connection conn = JDBCTemplate.getConnection();
		
		int count = new MemberDao().idCheck(conn, checkId);
		
		JDBCTemplate.close(conn);
		
		return count;
	} // idCheck() 종료

}
