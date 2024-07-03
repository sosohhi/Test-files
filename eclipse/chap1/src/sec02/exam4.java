package sec02;

public class exam4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] numbers = {"one", "two", "three"};
		for(int i=0; i<numbers.length; i++) {
		    System.out.println(numbers[i]);
		}
		int[] marks = {90, 25, 67, 45, 80};
		for (int i = 0; i< marks.length; i++ ) {
			if (marks[i] >= 60) {
				System.out.println((i+1)+"번 학생은 합격입니다.");
			}
			else {
				System.out.println((i+1) + "번 학생은 불합격입니다.");
			}
		}


	}

}
