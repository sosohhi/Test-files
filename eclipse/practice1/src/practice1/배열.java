package practice1;

public class 배열 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
//		배열 표현
		
		 int[] odds = {1, 3, 4, 5, 7, 9};
		 String[] weeks = {"월", "화", "수", "목", "금"};
	        
	        // 배열의 길이 설정하기
	     String[] weekss = new String[7];
	     weekss[0] = "월";
	     weekss[1] = "화";
	     weekss[2] = "수";
	     weekss[3] = "목";
	     weekss[4] = "금";
	     weekss[5] = "토";
	     weekss[6] = "일";
	        
	        // 배열값에 접근하기
	     System.out.println(odds[1]);  // 3 출력
	     System.out.println(weekss[3]); // 목 출력
		// 초깃값 없이 배열 변수를 만들 때에는 7과 같은 숫잣값을 넣어 길이를 정해줘야 한다.
//	     배열의 길이 구하기
	     for (int i =0; i<weekss.length;i++) {
	    	 System.out.print(weekss[i]);
	     }
		



	}

}
