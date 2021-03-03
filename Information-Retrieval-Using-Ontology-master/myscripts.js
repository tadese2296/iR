 function initMap() {
  var map = new google.maps.Map(document.getElementById('map'),
  { 
    zoom: 18,
    center: {lat: 21.124180, lng:79.050505 },
    mapTypeId: 'terrain'
  });var flightPlanCoordinates =[{lat:21.12481, lng:79.052197},{lat:21.124869, lng:79.051773},{lat:21.124493, lng:79.051708},{lat:21.124364, lng:79.051688},{lat:21.124184, lng:79.051657},{lat:21.124053, lng:79.05164},{lat:21.124075, lng:79.051346},{lat:21.124135, lng:79.050681},{lat:21.124162, lng:79.050615},{lat:21.124432, lng:79.049788},{lat:21.125027, lng:79.050212},];

  var flightPath = new google.maps.Polyline({ 
    path: flightPlanCoordinates,
    geodesic: true,
    strokeColor: '#0000FF',
    strokeOpacity: 1.0,
    strokeWeight: 6
  });
  flightPath.setMap(map);
var srcMarker = new google.maps.Marker({
position:{lat:21.12481, lng:79.052197}, label: 'S',
map: map ,
animation:google.maps.Animation.DROP ,
title:"CSE"
});
var destMarker = new google.maps.Marker({
position:{lat:21.125027, lng:79.050212}, label: 'D',
map: map ,
animation:google.maps.Animation.DROP ,
title:"Chemical"
});
destMarker.setMap(map);
srcMarker.setMap(map);
var LatLng = new google.maps.LatLng(0.0, 0.0);
var iconBase = 'http://maps.google.com/mapfiles/kml/paddle/';
var marker = new google.maps.Marker({
position: LatLng,
map: map,
title:"Current Position",
icon:iconBase+'blu-circle-lv.png',
});
marker.setMap(map);
function getLocation()
{
if (navigator.geolocation)
{
navigator.geolocation.getCurrentPosition(function (p) {
  LatLng = new google.maps.LatLng(p.coords.latitude, p.coords.longitude);
marker.setPosition(LatLng);
});
}
else
{
alert('Geo Location feature is not supported in this browser.');
}
}
var myVar;
myVar=setInterval(getLocation,3000);
}
