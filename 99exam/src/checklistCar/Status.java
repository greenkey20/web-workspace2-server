package checklistCar;

public class Status {
	
	// 필드부
	private int speed;
	private String oilStatus;
	
	// 메소드부 내용 없음
	
	// 생성자 및 getter, setter 만들어야 하는지 못 들음 ㅠ.ㅠ -> private 필드가 있는 경우 getter, setter 필요할 것 같아서 평가 후에 추가
	public Status() {
		super();
	}

	public Status(int speed, String oilStatus) {
		super();
		this.speed = speed;
		this.oilStatus = oilStatus;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getOilStatus() {
		return oilStatus;
	}

	public void setOilStatus(String oilStatus) {
		this.oilStatus = oilStatus;
	}

	@Override
	public String toString() {
		return "Status [speed=" + speed + ", oilStatus=" + oilStatus + "]";
	}
	
}
