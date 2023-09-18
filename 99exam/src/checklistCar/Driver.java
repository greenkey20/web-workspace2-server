package checklistCar;

public class Driver {
	
	// 필드부
	private String name;
	private int age;
	
	// 메소드부 내용 없음
	
	// 생성자 및 getter, setter 만들어야 하는지 못 들음 ㅠ.ㅠ -> private 필드가 있는 경우 getter, setter 필요할 것 같아서 평가 후에 추가
	public Driver() {
		super();
	}

	public Driver(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Driver [name=" + name + ", age=" + age + "]";
	}
	
}
