package sec01;

public class exam1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "Hello, Java";
		System.out.println(a.indexOf("Java"));// 문자열의 위치 반환
		String b = "Hi Python";
		
		if (a.equals(b)) {
			System.out.println("같다");
		}
		else {
			System.out.println("같지않다");
		}
		
		System.out.println(a.contains("Java")); // 특정 문자열 포함되어있는지 여부 리턴
		System.out.println(a.charAt(7)); //특정 위치의 문자 리턴
		
		System.out.println(a.replaceAll("Java", "World"));
		System.out.println(a.substring(0,4)); // 특정 문자열 추출
		System.out.println(a.toUpperCase()); // 문자열 대문자로 변경
		String c = "jin:ja:la:go";
		String[] result = c.split(":");
		System.out.println(c);
		System.out.println(String.format("I eat %d apple.", 3));
		System.out.printf("I eat %d apples.",3);
		System.out.printf("I eat %s apples","five");
		int number = 10;
		String day = "three";
		System.out.println(String.format("I ate %d apples. so I was sick for %s days.", number, day));
		System.out.println(String.format("Error is %d%%.", 98));  // "Error is 98%." 출력
		
		StringBuffer sb = new StringBuffer(); // StringBuffer 객체 sb생성
		sb.append("Hi");
		sb.append(" ");
		sb.append("Good boy");
		String result_1 = sb.toString();
		System.out.println(result_1);
		
		StringBuilder sb = new StringBuilder();
		sb.append("hello");
		sb.append(" ");
		sb.append("jump to java");
		String result = sb.toString();
		System.out.println(result);



	}

}
