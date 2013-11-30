function google_maps_init(div_id, latitude, longitude, coordinates) {        var mapOptions = {                zoom: 15,                center: new google.maps.LatLng(latitude, longitude),                mapTypeId: google.maps.MapTypeId.TERRAIN        };        var map = new google.maps.Map(document.getElementById(div_id), mapOptions);        var path = new google.maps.Polyline({                path: coordinates,            geodesic: true,            strokeColor: '#2f7ed8',            strokeOpacity: 1.0,            strokeWeight: 5        });        path.setMap(map);}