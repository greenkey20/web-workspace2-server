package checklistCar;

// 같은 패키지 안에 있는 자료형/클래스는 import할 필요 없음; package-friendly
//import checklistCar.Driver;
//import checklistCar.Car;

public class BuyCar {
	
	// 필드부
	private Driver bDriver;
	private Car carType;

	// 매개변수 있는 생성자를 만들었을 때, jvm이 기본 생성자 안 만들어주므로, 기본 생성자도 만들어둠
	public BuyCar() {
		super();
	}
	
	public BuyCar(Driver bDriver, Car carType) {
		super();
		this.bDriver = bDriver;
		this.carType = carType;
	}

	// 매개변수 1개 받는 생성자 <- 클래스 다이어그램에 따라 만듦
	public BuyCar(Driver d) {
		super();
		this.bDriver = d;
	}

	// 생성자 및 getter, setter 만들어야 하는지 못 들음 ㅠ.ㅠ -> private 필드가 있는 경우 getter, setter 필요할 것 같아서 평가 후에 추가
	public Driver getbDriver() {
		return bDriver;
	}

	public void setbDriver(Driver bDriver) {
		this.bDriver = bDriver;
	}

	public Car getCarType() {
		return carType;
	}

	public void setCarType(Car carType) {
		this.carType = carType;
	}

	@Override
	public String toString() {
		return "BuyCar [bDriver=" + bDriver + ", carType=" + carType + "]";
	}

}
