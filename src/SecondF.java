package src;

public class SecondF {
	private int In3;
	public int In4;
	
	public void call(Integer In) {
		Integer In2 = new Integer(In);
		In2 = In*2;
		In3 = In*3;
		System.out.format("here  a new is %d : %d : %d\n", In, In2, In3);
		System.out.println("Hello, World second wor");
	}
}
