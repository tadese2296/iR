public class Locations {
	
public	String name;
public	double latitude,longitude;
	
	Locations() {
		latitude = 0;
		longitude = 0;
		name = "";
	}
	
	
	Locations(String n,double la,double lo)
	{
		latitude=la;
		longitude=lo;
		name=n;
	}
	
	public String toString() {
		return name;
	}

}


