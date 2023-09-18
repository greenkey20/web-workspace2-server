package checklistCar;

public class Benz extends Car {
	
	// 필드부
	public int price;
	private String model;
	
	// 생성자 및 getter, setter 만들어야 하는지 못 들음 ㅠ.ㅠ -> private 필드가 있는 경우 getter, setter 필요할 것 같아서 평가 후에 추가
	public Benz() {
		super();
	}

	public Benz(int price, String model) {
		super();
		this.price = price;
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "Benz [price=" + price + ", model=" + model + "]";
	}

	// 메소드부
	@Override
	public void drive() {
		
	}
	
}
