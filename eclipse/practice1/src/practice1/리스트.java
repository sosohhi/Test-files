package practice1;

import java.util.ArrayList;
import java.util.Arrays;
public class 리스트 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//리스트 자료형 (ArrayList, Vector, LinkedList)
		
		ArrayList<String> pitches = new ArrayList();
		pitches.add(0,"138");
		pitches.add(1,"129");
		pitches.add(2,"142"); // 앞에 자료의 위치 넣을 수도 있음 안넣으면 위에서 작성한 순서대로 나타남 
		
		System.out.println(pitches);
		
		// get : ArrayList의 get메서드 사용시 특정 인덱스의 값 추출가능
		System.out.println(pitches.get(1));
		
		// size : ArrayList요소의 개수를 리턴한다
		System.out.println(pitches.size());
		
		// contains : 리스트에 원하는 항목이 있는지 판별해 결과를 boolean으로 리턴
		System.out.println(pitches.contains("142"));
		
		// remove(객체)/remove(인덱스)
		
		// 첫번째 방법 >  remove(객체) : 리스테에서 객체에 해당하는 항목 삭제 후 true나 false 리턴
		System.out.println(pitches.remove("129"));
		
		// 두번째 방법 > remove(인덱스) : 인덱스에 해당하는 항목을 삭제후 그 항목을 리턴
		
		System.out.println(pitches.remove(0));
		
		// 다양한 방법으로 ArrayList 사용하기
		// 이미 데이터 배열이 있을시에는 아래와 같이 호출해서 쓸 수 있디
		
//		import java.util.ArrayList;
//		import java.util.Arrays;
//
//		public class Sample {
//		    public static void main(String[] args) {
//		        ArrayList<String> pitches = new ArrayList<>(Arrays.asList("138", "129", "142"));
//		        System.out.println(pitches);
		
		
//		    }
		// String.join
		}

		    
		
		

	}

}
