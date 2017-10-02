/**
 * 
 */
function today(){
	return new Date().toISOString().slice(0,10).replace(/-/g,"");
}

function onAddClick(elm){
	var elmID = $(elm);
	var newID = elmID.attr("id").replace("btn","");
	var  imageHTML = "<div class='col-md-3'>"+
    "<div id='thumb2" + newID  + "' class='thumbnail'>"+
      "<a class='wishLink' id='link" + newID + "' target='_blank'>"+
        "<img class='wishImg' id='" +  newID+ "' src='" + $("#" +newID ).attr('src') + "' alt='Lights' style='width:100%;maxheight:200px'  />"+
      "</a>"+
        "<div class='caption'>"+
        	"<div class='row' style='height:40px' >"+
	          	"<div class='col-md-1'>"+
	          		"<button class='btn btn-primary' id='btn"+ newID  +"' style='align-content:right' onclick='onDelClick(this)' >Del</button>"+
	          	"</div>"+
        	"</div>"+
    		
        "</div>"+
    "</div>";
	
	$("#wishlist").append(imageHTML);
	$("#thumb"+newID).remove();
	
}

function onDelClick(elm){
	var elmID = $(elm);
	var id = elmID.attr("id").replace("btn","");
	$("#thumb2" + id).remove()
	
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
		    	var photoUrl =  bestPhoto.prefix + bestPhoto.width + "x" + bestPhoto.height + bestPhoto.suffix
		    	elm.attr('src',photoUrl);
		    	var linkid = "#link" + elm.attr('id')
		    	$(linkid).attr('href',photoUrl)
		    	
		    });
	});
	
});



