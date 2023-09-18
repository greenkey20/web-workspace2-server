package checklistCar;

public class Audi extends Car {
	
	// 필드부
	private int price;
	
	// 생성자 및 getter, setter 만들어야 하는지 못 들음 ㅠ.ㅠ -> private 필드가 있는 경우 getter, setter 필요할 것 같아서 평가 후에 추가
	public Audi() {
		super();
	}

	public Audi(int price) {
		super();
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Audi [price=" + price + "]";
	}

	// 메소드부
	@Override
	public void drive() {
		
	}

}
