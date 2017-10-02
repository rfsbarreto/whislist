/**
 * 
 */
function today(){
	return new Date().toISOString().slice(0,10).replace(/-/g,"");
}

$(document).ready(function() {
	$(".venueImg").each(function( index,element ) {
		  console.log( "teste");
		  var elm = $(this);
		  var url = "https://api.foursquare.com/v2/venues/" + elm.attr('id') +
	        "?oauth_token=" + token + "&v=+" + today();
		  $.ajax({
		        url: url
		    }).then(function(data) {
		    	var bestPhoto = data.response.venue.bestPhoto
		    	console.log(bestPhoto)
		    	var photoUrl =  bestPhoto.prefix + bestPhoto.width + "x" + bestPhoto.height + bestPhoto.suffix
		       elm.attr('src',photoUrl);
		    });
	});
   
});



