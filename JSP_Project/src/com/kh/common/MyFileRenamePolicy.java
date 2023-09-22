package com.kh.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

// 2022.1.12(수) 16h10
// implements 키워드를 사용해서 interface를 구현해야 함 -> 이 클래스는 interface로부터 상속받은, 미완성된, 추상메소드 rename()을 오버라이딩해서 반드시 구현해야 함; The type MyFileRenamePolicy must implement the inherited abstract method FileRenamePolicy.rename(File)
public class MyFileRenamePolicy implements FileRenamePolicy {
	// 16h45 어떤 학우님의 오류 = 이 클래스를 abstract class(추상클래스)로 만들어서 'cannot instantiate the type MyFileRenamePolicy' Java.lang.Error <- 추상클래스는 부모클래스/참조자료형으로는 사용 가능 + 객체 생성은 불가능
	
	@Override
	public File rename(File originFile) { // 기존의 파일을 전달받아서 파일명 수정 작업 후에 수정된 파일을 반환시켜줌; 매개변수 = File 객체, return 값 = File 객체
		// getName() -> 매개변수로 전달받은 원본 파일로부터 원본 파일명 뽑기
		String originName = originFile.getName(); // "aaa.jpg"
		
		// 규칙을 만들어 최대한 이름이 겹치지 않도록 수정 파일명 만들기
		// 규칙 = 파일이 업로드된 시간(연,월,일,시,분,초) + 5자리(10000~59999) random 값 + 확장자(원본 파일의 확장자 그대로)
		/* e.g. 원본명 -> 수정명
		 * aaa.jpg -> 20220112162001xxxxx.jpg 형태 -> 나의 질문 = 강사님의 필기 2022011239333xxxxx에서 39333은 어떤 의미이지?
		 */
		
		// 단계1) 파일이 업로드된 시간 추출 -> String currentTime
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // 여기서의 Date 클래스 = java.util(o) sql(x)
		
		// 단계2) 5자리 random 값 추출 -> int ranNum
		int ranNum = (int)(Math.random() * 50000) + 10000;
		
		// 단계3) 확장자 뽑기 -> String ext
		String ext = originName.substring(originName.lastIndexOf(".")); // String 클래스의 lastIndexOf(찾고자 하는 문자열) 메소드 활용 -> 원본 파일명 중간에 .이 (여러 개) 포함될 것을 대비해서 가장 마지막에 있는 . 기준으로 문자열 추출/뽑아내기 + substring()
		// 2022.1.31(월) 9h55 Java 정석 p.470
		// indexOf("문자열" 또는 '문자') -> 주어진 문자(열)가 문자열에 존재하는지 확인해서 그 위치(index)를 알려줌 vs 못 찾으면 -1 반환
		// lastIndexOf('문자(코드)') -> 지정된 문자(코드)를 문자열의 오른쪽 끝에서부터 찾아서 위치(index)를 알려줌 vs 못 찾으면 -1 반환
		// subString(int begin) 또는 subString(int begin, int end) -> 주어진 시작 위치(begin)부터 끝 위치(end) 범위에 포함된 문자열을 얻음
		
		// 단계1~3 조합해서 수정 파일명 변수에 담기
		String changeName = currentTime + ranNum + ext; // kakaotalk의 경우 이름 제일 앞에 "kakaotalk" +
		
		// 기존/원본 파일에 수정된 파일명을 적용시켜서 return
		return new File(originFile.getParent(), changeName);
	}

}
