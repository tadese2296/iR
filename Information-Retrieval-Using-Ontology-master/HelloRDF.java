import java.util.ArrayList;
import java.util.HashMap;

import com.bbn.parliament.jena.joseki.client.RemoteModel;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class HelloRDF {
	
	
	
	double findBearing(Locations src, Locations dest)
	{
		
		double latSrc=Math.toRadians((src.latitude)),longSrc=Math.toRadians(src.longitude);
		double latDest=Math.toRadians(dest.latitude),longDest=Math.toRadians(dest.longitude),bearing;
		
		
		
		double y = Math.sin(longDest-longSrc) * Math.cos(latDest);
		double x = Math.cos(latSrc)*Math.sin(latDest) -
		        Math.sin(latSrc)*Math.cos(latDest)*Math.cos(longDest-longSrc);
		double brng = Math.toDegrees(Math.atan2(y, x));
		
		return brng;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    int minDistanceDijkstras(double dist[], Boolean sptSet[], int n)
    {
        
        double min = Double.MAX_VALUE;
        int min_index=-1;
 
        for (int v = 0; v < n; v++)
            if (sptSet[v] == false && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }
 
        return min_index;
    }

    // added part
    
	String dijkstras(int src,double time[][], int n, int dest,ArrayList<Locations> a, int type, ArrayList<Locations> tracePath) 
	{
		
	       	String ans = "";
			
			double dist[] = new double[n];
	       	double journeyDistance;
			Boolean sptSet[] = new Boolean[n];
			String path;
			
			//System.out.println("Reached Dijkstras");
			
			int [] parent = new int [n];
			
			parent[0]=-1;

			for (int i = 0; i <n; i++)
			{
			dist[i] = Double.MAX_VALUE;
			sptSet[i] = false;
			}
			
			dist[src] = 0;
			
			for (int count = 0; count < n-1; count++)
			{

				int u = minDistanceDijkstras(dist, sptSet,n);			
				sptSet[u] = true;
	
				for (int v = 0; v < n; v++)
				{
					if (!sptSet[v] && time[u][v]!=0 &&dist[u] != Double.MAX_VALUE &&dist[u]+time[u][v] < dist[v])
						{
							dist[v] = dist[u] + time[u][v];
							parent[v]=u;
						}
				}
			
			}
			
			
			
			journeyDistance=findDistanceOfJourney(parent,dest,a,type,dest);
			
			
			ans += "The journey distance is " + Math.round(journeyDistance*100.0)/100.0 + " kilometers.\n";
			
			path=printPath( parent,dest,a,tracePath);
			int last = path.lastIndexOf('-');
			path = path.substring(0, last-1);
			ans += "The journey path is " + a.get(0).name + " --> " + path + "\n";
			
			ans += "The time " + Math.round(dist[dest]*60*100.0)/100.0 + " minutes.\n\n";
			
			return ans;
	}
	
	
	
	//added part
	
	double findDistanceOfJourney(int parent[],int  j, ArrayList<Locations>a,int type, int prev)
	{
		if(j==-1)
			return 0;
		
		double d=findDistanceOfJourney(parent, parent[j], a, type,  j);
		
		return d+calDistance(a.get(prev),a.get(j)); 
				
	}
	
	// added part
	
	String printPath(int parent[], int j,ArrayList<Locations> a,ArrayList<Locations> tracePath )
	{
	    
	    if (parent[j]==-1)
	        {
	    	tracePath.add(a.get(j));
	    	return "";
	        }
	 
	    String str= printPath(parent, parent[j],a,tracePath);
	    	
	    	tracePath.add(a.get(j));
	    		
	    return (str+a.get(j).name+" --> ");
	   
	}
	
	
	
	
	
	int getDestIndex(ArrayList<Locations> a,Locations dest)
	{
		
		int i=0;
		
		//System.out.println("dest = " + dest.name);
		
		for(i=0;i<a.size();i++)
		{
			//System.out.println("a[i] = " + a.get(i).name);
			if(((a.get(i)).name).equals(dest.name)) {
				return i;
			}
		}
	
		return -1;
		
	}
	
	
	
	Locations midPoint(Locations src, Locations dest)
	 {
		 
		double lat1=src.latitude, lon1=src.longitude, lat2=dest.latitude, lon2=dest.longitude;

	    double dLon = Math.toRadians(lon2 - lon1);

	    lat1 = Math.toRadians(lat1);
	    lat2 = Math.toRadians(lat2);
	    lon1 = Math.toRadians(lon1);

	    double Bx = Math.cos(lat2) * Math.cos(dLon);
	    double By = Math.cos(lat2) * Math.sin(dLon);
	    double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
	    double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

	    Locations mid=new Locations("midPoint",Math.toDegrees(lat3),Math.toDegrees(lon3));
	    return mid;
	    
	}
	
	 double toRad(Double value)
	 {
	        return value * Math.PI / 180;
	 }
	 
	 
	double calDistance(Locations src, Locations dest)
	{
		double lat1=src.latitude, lon1=src.longitude, lat2=dest.latitude, lon2=dest.longitude;
		
		final int R = 6371; // Radius of the earth
		
		   Double latDistance = toRad(lat2-lat1);
	        Double lonDistance = toRad(lon2-lon1);
	        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
	                   Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * 
	                   Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	        Double distance = R * c;
	         
	       
		 return distance;
		
		
	}
	
	

	
	void makeArray(ArrayList<Locations> a, double r, Locations lmid, Locations l,  HashMap<String,Integer> hm)
	{
		double distance=calDistance(l,lmid);
		
		if(distance<=r)
		{
			
			a.add(l);
			hm.put(l.name, 1);
			
			Locations [] adjecentPoints= new Locations[10000]; 
			
			// Put all the adjacent points in this array
			//DONE
			
			ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
			queryStr.append("PREFIX ab: <http://www.google.com#> SELECT * WHERE { ab:" + l.name + " ab:direct ?conn. ?conn ab:latitude ?lat. ?conn ab:longitude ?long.}");
			Query sparqlSelectQuery = queryStr.asQuery();
			QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:8089/parliament/sparql", sparqlSelectQuery);
			
			int k = 0;
			
			try{
				ResultSet results = qe.execSelect();
			
				for(;results.hasNext();){
				
					//System.out.println("i m here");
					QuerySolution soln = results.nextSolution();
				
					RDFNode conn = soln.get("conn");
					RDFNode lat = soln.get("lat");
					RDFNode longi = soln.get("long");
					//System.out.println(conn.toString() + " " + lat.toString() + " " + longi.toString());
					
					int index = conn.toString().indexOf('#');
					String name = conn.toString().substring(index+1);
					
					
					
					adjecentPoints[k] = new Locations(name, Double.parseDouble(lat.toString()), Double.parseDouble(longi.toString()));
					//System.out.println(adjecentPoints[k].name);
					k++;
					
				}
			}catch(Exception e){
				System.out.println("Query error " + e);
			}finally{
				qe.close();
			}
			
			//System.out.println(hm);
			
			
			for(int j=0;j<k;j++)
			{
				
				//System.out.println("adjacent " + adjecentPoints[j].name);
				if(hm.containsKey(adjecentPoints[j].name) == false)
				{
					makeArray(a,r,lmid,adjecentPoints[j],hm);
				}
			}
			
			
		}
		
	}
	
	
	
	double getWeight(ArrayList<Locations> a, int i, int j, int type)
	{
		double t=0;
		
		
		// spqrql query to get time according to type
		
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		queryStr.append("PREFIX ab: <http://www.google.com#> SELECT ?type WHERE { ab:"+a.get(i).name+" ab:"+a.get(j).name+" ?type.}");
		Query sparqlSelectQuery = queryStr.asQuery();
		QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:8089/parliament/sparql", sparqlSelectQuery);
		
		double speedM = 0.0, speedC = 0.0, speedW = 0.0;
		
		try{
			ResultSet results = qe.execSelect();
		
			for(;results.hasNext();){
			
				QuerySolution soln = results.nextSolution();
			
				RDFNode typeNode = soln.get("type");
				String mode = typeNode.toString().substring(0, typeNode.toString().indexOf('_'));
				
				//System.out.println(mode);
				
				if(mode.equals("motor")) {
					speedM = Double.parseDouble(typeNode.toString().substring(typeNode.toString().indexOf('_')+1)) * 3.6;
				}
				else if(mode.equals("cycle")) {
					speedC = Double.parseDouble(typeNode.toString().substring(typeNode.toString().indexOf('_')+1)) * 3.6;
				}
				else {
					speedW = Double.parseDouble(typeNode.toString().substring(typeNode.toString().indexOf('_')+1)) * 3.6;
				}
			}
		}catch(Exception e){
			System.out.println("Query error " + e);
		}finally{
			qe.close();
		}
		
		double dist = calDistance(a.get(i), a.get(j));
		
		if(type == 1) {
			t = dist/speedM;
		}
		else if(type == 2) {
			t = dist/speedC;
		}
		else {
			t = dist/speedW;
		}
		
		//System.out.println("time = " + speedC + " " +  speedM + " " + speedW );
		
		return t;
	}
	
	void makeMatrix(ArrayList<Locations> a, double time[][], int type)
	{
		int i,j,l=a.size();
		
		
		for(i=0;i<l;i++)
			for(j=0;j<l;j++)
			{
				
				time[i][j]=getWeight(a,i,j,type);
			}
	}
	
	String[] findTime(Locations src, Locations dest, int choice,  ArrayList<Locations> tracePath)
	{
		
		double radius=0;
		String[] t = new String [3];
		
		//System.out.println("Reached FindTime");
		
		int count=0;
		
		ArrayList<Locations> a= new ArrayList<Locations>();
		HashMap<String,Integer> hm=new HashMap<String,Integer>(); 
		Locations lmid= midPoint(src,dest);
		
		//System.out.println(lmid.latitude);
		
		int d=-1;
		
		while(d==-1)
		{
			count++;
			radius+=1;
			
			makeArray(a,radius,lmid,src,hm);
			double[][] timeM = new double[a.size()][a.size()];
			double[][] timeC = new double[a.size()][a.size()];
			double[][] timeW = new double[a.size()][a.size()];
			
			if(choice == 1)
				makeMatrix(a,timeM,1);
			else if(choice == 2)
				makeMatrix(a,timeC,2);
			else if(choice == 3)
				makeMatrix(a,timeW,3);
			
			//System.out.println(a);
			
			d=getDestIndex(a,dest);
			//System.out.println("d = " + d);
			
			if(d!=-1)
			{
				if(choice == 1) {
					t[0]=dijkstras(0,timeM,a.size(),d,a,1,tracePath);
				}
				else if(choice == 2) {
					t[1]=dijkstras(0,timeC,a.size(),d,a,2,tracePath);
				}
				else if(choice == 3){
					t[2]=dijkstras(0,timeW,a.size(),d,a,3,tracePath);
				}
				
			}
			
			if(count>5)
				{System.out.println("fff");d=1;}
	
		}
		
	
		return t;
	}
	
	
	public  String first(String s1, String s2, String s3, ArrayList<Locations> tracePath) {
	
		RemoteModel rmParliament = new RemoteModel(
				"http://localhost:8089/parliament/sparql",
				"http://locahost:8089/parliament/bulk");
		ParameterizedSparqlString queryStr = new ParameterizedSparqlString();
		
		
		HelloRDF rdf=new HelloRDF();
		{
		
			String src,dest; 
			
			src = s1;
			dest = s2;
			
			double latSrc=0,longSrc=0,latDest=0,longDest=0;
			
			//fetch src and dest and put in the array
			//
			//DONE
			
			//src lat andd long
			queryStr = new ParameterizedSparqlString();
			queryStr.append("PREFIX ab: <http://www.google.com#> SELECT ?lat ?long WHERE { ab:"+src+" ab:latitude ?lat; ab:longitude ?long.}");
			Query sparqlSelectQuery = queryStr.asQuery();
			QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:8089/parliament/sparql", sparqlSelectQuery);
			
			
			try{
				ResultSet results = qe.execSelect();
			
				for(;results.hasNext();){
				
					QuerySolution soln = results.nextSolution();
				
					RDFNode o1 = soln.get("lat");
					RDFNode o2 = soln.get("long");
					latSrc = Double.parseDouble(o1.toString());
					longSrc = Double.parseDouble(o2.toString());
				}
			}catch(Exception e){
				System.out.println("Query error " + e);
			}finally{
				qe.close();
			}
			
			//dest lat and long
			
			queryStr = new ParameterizedSparqlString();
			queryStr.append("PREFIX ab: <http://www.google.com#> SELECT ?lat ?long WHERE { ab:" + dest + " ab:latitude ?lat; ab:longitude ?long.}");
			sparqlSelectQuery = queryStr.asQuery();
			qe = QueryExecutionFactory.sparqlService("http://localhost:8089/parliament/sparql", sparqlSelectQuery);
			
			try{
				ResultSet results =qe.execSelect();
			
				for(;results.hasNext();){
				
					QuerySolution soln =results.nextSolution();
				
					RDFNode o1 = soln.get("lat");
					RDFNode o2 = soln.get("long");
					latDest = Double.parseDouble(o1.toString());
					longDest = Double.parseDouble(o2.toString());
				}
			}catch(Exception e){
				System.out.println("Query error " + e);
			}finally{
				qe.close();
			}
			
			System.out.println(latSrc + " " + longSrc + " " + latDest + " " + longDest);
			
			
			Locations[] a= new Locations[2];
	
			a[0]=new Locations(src,latSrc,longSrc);
			a[1]=new Locations(dest,latDest,longDest);
			
			
			//System.out.println(latSrc);
			String[] getTime = new String[3];
			
			int choice = Integer.parseInt(s3);
			
			getTime=rdf.findTime(a[0],a[1],choice,tracePath);
			System.out.println(getTime[choice-1]);
			
			return getTime[choice-1];
			
			
		}
		

	}

}