package src;

public class Hello {
	private static Integer In = new Integer(5);

	public static void main(String[] args) {
		SecondF S = new SecondF();

		System.out.format("here  a new is %d\n", In);
		System.out.println("Hello, World");
		Newfct(In);
		S.call(In);
		S.call(In*5);
		S.In4 = 8;
	}

	private static void Newfct(Integer In) {
		System.out.format("here  a new is %d\n", In);
		System.out.println("Hello, World2");
	}



}
