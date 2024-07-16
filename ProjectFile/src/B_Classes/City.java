package B_Classes;

public class City {
	
	//=== Attributes --------------------------------------------------------
	private String city_name;
	private int petrol_cost;
	private int hotel_cost;
	private int stage;
	
	
	//=== Constructor --------------------------------------------------------
	public City(String city_name, int petrol_cost, int hotel_cost, int stage) {
		this.city_name = city_name;
		this.petrol_cost = petrol_cost;
		this.hotel_cost = hotel_cost;
		this.stage=stage;
	}


	//=== Getter and Setter --------------------------------------------------
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	/////////////////////////////////////
	public int getPetrol_cost() {
		return petrol_cost;
	}
	public void setPetrol_cost(int petrol_cost) {
		this.petrol_cost = petrol_cost;
	}
	/////////////////////////////////////
	public int getHotel_cost() {
		return hotel_cost;
	}
	public void setHotel_cost(int hotel_cost) {
		this.hotel_cost = hotel_cost;
	}
	/////////////////////////////////////
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}


	//=== Methods ============================================================
	
	// toString
	@Override
	public String toString() {
		return "[City name: " + city_name + ",   Petrol Cost: " + petrol_cost + ",   Hotel cost: " + hotel_cost+",  Stage: "+stage+"]";
	}
	
	
	// Print Data
	public void printData() {
		System.out.println("City Name   : "+city_name+"\n"
						  +"Petrol Cost : "+petrol_cost+"\n"
						  +"Hotel Cost  : "+hotel_cost+"\n"
						  +"Stage       : "+stage);
	}
	
}
