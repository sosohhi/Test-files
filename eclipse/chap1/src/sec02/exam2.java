package sec02;

public class exam2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int treeHit = 0;
		while (treeHit<10) {
			treeHit++;
			System.out.println("나무를"+treeHit+"번 찍었습니다");
			if (treeHit == 10) {
				System.out.println("나무 넘어갑니다");
			}
		}

	}

}
