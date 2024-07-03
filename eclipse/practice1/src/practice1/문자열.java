package practice1;

public class 문자열 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		문자열 자바 포현 방법
		// (1) 리터럴(literal) 표기 방식
		//고정된 값을 그대로 대입
		String a = "Happy Java";
		
		// (2) new 키워드 표기 방식
		// 항상 새로운 String 객체를 만듦

		String b = new String("Happy days");
		
		// 첫 번째 장식은 동일한 리터럴을 사용할 경우 새로운
		// String을 만들지 않고 기존에 만든 것을 재활용한다.
		
		// 원시(primitive) 자료형
		// new 키워드로 값을 생성할 수 없다.
		
		boolean result = true;
		char c ='A';
		int i = 1000000;
		
		// String은 리터럴 표기 방식을 사용할 수 있지만 
		// 원시자료형에 포함되지 않음
		
//		문자열 내장 메서드
		
		// equals : 문지기 같은지 비교하여 결괏값 리턴(문자열은 반드시 equals사용)
		String q = "hello";
		String w = "java";
		String e = "hello";
		System.out.println(q.equals(w)); // false출력
		System.out.println(q.equals(e)); // true출력
		
		// indexOf : 문자열에서 특정 문자열이 시작되는 위치를 리턴
		String r = "Hello Java";
		System.out.println(r.indexOf("Java")); //6
		
		// contains : 특정 문자열이 포함되어 있는지 여부 리턴
		System.out.println(r.contains("Java")); //true
		
		//charAt : 문자열에서 특정 위치의 문자를 리턴한다.
		System.out.println(r.charAt(6)); //J
		
		//replaceAll : 문자열에서 특정 문자열을 다른 문자열로 바꿀 때 사용
		System.out.println(r.replaceAll("Java", "World")); //Hello World
		
		// substring : 문자열에서 특정 문자열을 뽑아낼 때 사용
		System.out.println(r.substring(0,4)); //Hell
		
		// toUpperCase : 문자열을 모두 대문자로 변경
		System.out.println(r.toUpperCase()); //HELLO JAVA
		
		// split : 문자열을 특정한 구분자로 나누어 문자열 배열로 리턴
		String t = "an:bn:cn:dn";
		String[] result1 = t.split(":"); // {a,b,c,d}
		
//		문자열 포매팅 : 문자열 안에 어떤 값을 삽입하는 방법
		
		// 숫자 바로 대입
		System.out.println(String.format("I eat %d apples.",3)); // "I eat 3 apples
		
		// 문자열 바로 대입하기
		System.out.println(String.format("I eat %s apples", "five")); // I eat five apples
		
		// 숫자값을 나타내는 변수 대입하기
		int number = 3;
		System.out.println(String.format("I eat %d apples",number)); //I eat 3 apples
		
		// 값을 2개 이상 넣기
		int numbers = 10;
		String day = "three";
		System.out.println(String.format("I ate %d apples. so I was sick for %s days.",numbers,day));
		
//		문자열 포맷 코드 응용
		//정렬과 공백 표현하기
		System.out.println(String.format("%-10sjane","hi")); //hi        jane > hi왼쪽정렬+공백 > 전체길이 10 + jane출력
		
		// 소수점 표현
		System.out.println(String.format("%.4f", 3.42134234)); // 3.4213
		System.out.println(String.format("%10.4f", 3.42134234)); //    3.4213 >>문자 오른쪽 정렬, 문자와 공백포함 전체길이 10
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		
		
		
		
		
		
		

	}

}
