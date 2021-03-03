import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CreateJsFile {

	void create(ArrayList<Locations> a) {
		
        String ans="var flightPlanCoordinates =";
        
        String str="[";
        
        int n=a.size();
        
        for(int i=0;i<n;i++)
        {
            str+="{lat:";
            str+=a.get(i).latitude;
            str+=", lng:";
            str+=a.get(i).longitude;
            str+="},";
        }
        
        
        String src,dest,srcLocation="",destLocation="";
        
        src=a.get(0).name;
        dest=a.get(n-1).name;
        
        srcLocation+="{lat:";
        srcLocation+=a.get(0).latitude;
        srcLocation+=", lng:";
        srcLocation+=a.get(0).longitude;
        srcLocation+="},";
        
        destLocation+="{lat:";
        destLocation+=a.get(n-1).latitude;
        destLocation+=", lng:";
        destLocation+=a.get(n-1).longitude;
        destLocation+="},";
        
        
        str+="];";
        
        ans+=str;
		
		System.out.println(ans);

		 String FILENAME = "C:\\Users\\dell\\Desktop\\myscripts.js";
		 
			BufferedWriter bw = null;
			FileWriter fw = null;
			
		try
		{
			
			String content ="";
			
			String str1=" function initMap() {" + "\n"+
			      "  var map = new google.maps.Map(document.getElementById('map'),"+ "\n"+
			      "  { "+ "\n"+
			      "    zoom: 18,"+ "\n"+
			      "    center: {lat: 21.124180, lng:79.050505 },"+ "\n"+
			      "    mapTypeId: 'terrain'"+ "\n"+
			      "  });" +  ans + "\n"+ "\n"+
			        
			      "  var flightPath = new google.maps.Polyline({ " + "\n"+
			      "    path: flightPlanCoordinates,"+ "\n"+
			      "    geodesic: true,"+ "\n"+
			      "    strokeColor: '#0000FF',"+ "\n"+
			      "    strokeOpacity: 1.0,"+ "\n"+
			      "    strokeWeight: 6"+ "\n"+
			      "  });"+ "\n"+

			      "  flightPath.setMap(map);"+ "\n"+
			      
			"var srcMarker = new google.maps.Marker({"+"\n"+
		    "position:"+ srcLocation+" "+
		     "label: 'S',"+"\n"+
		     "map: map ,"+"\n"+
		     "animation:google.maps.Animation.DROP ," + "\n" +
		     "title:"+"\""+src+"\""+"\n"+
		  "});"+"\n"+


		"var destMarker = new google.maps.Marker({"+"\n"+
	    "position:"+ destLocation+" "+
	     "label: 'D',"+"\n"+
	     "map: map ,"+"\n"+
	     "animation:google.maps.Animation.DROP ," + "\n" +
	      "title:"+"\""+dest+"\""+"\n"+
	  "});"+"\n"+

	  "destMarker.setMap(map);"+"\n"+
		"srcMarker.setMap(map);"+ "\n"+
	  
 		" var LatLng = new google.maps.LatLng(0.0, 0.0);" + "\n" +
	  
		
		 "var iconBase = 'http://maps.google.com/mapfiles/kml/paddle/';" + "\n" +
		 "var marker = new google.maps.Marker({" + "\n" +
		 "position: LatLng," + "\n" +
		 "map: map," + "\n" +
		 "title:\"Current Position\"," + "\n" +
		 "icon:iconBase+'blu-circle-lv.png'," + "\n" +
		 
		"});" + "\n" +
		
		"marker.setMap(map);"+ "\n"+
		
		
		
	
   
		"function getLocation()" + "\n" +
		"{" + "\n" +
		
		"if (navigator.geolocation)" + "\n" +
		"{" + "\n" +
		    "navigator.geolocation.getCurrentPosition(function (p) {" + "\n" +
		       "  LatLng = new google.maps.LatLng(p.coords.latitude, p.coords.longitude);" + "\n" +
					"marker.setPosition(LatLng);"+"\n"+ 
		      "});" + "\n" +
		"}"  + "\n" +
		
		"else" + "\n" +
		"{" + "\n" +
		    "alert('Geo Location feature is not supported in this browser.');" + "\n" +
		"}" + "\n" +
		
		"}" + "\n" +
		
		"var myVar;" + "\n" +
		
		  "myVar=setInterval(getLocation,3000);" + "\n" +
		"}" + "\n" ;
			

			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(content+str1);
		}
		
		
		catch (IOException e)
		{

			e.printStackTrace();

		} 
		
	finally {

				try {
	
					if (bw != null)
						bw.close();
	
					if (fw != null)
						fw.close();
	
					}
				
				catch (IOException ex)
				{
	
					ex.printStackTrace();
	
				}
			
			}

	}

}



