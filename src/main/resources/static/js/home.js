/**
 * 
 */
alert("oi")

function queryParam(key) {
    key = key.replace(/[*+?^$.\[\]{}()|\\\/]/g, "\\$&"); // escape RegEx meta chars
    var match = location.search.match(new RegExp("[?&]"+key+"=([^&]+)(&|$)"));
    return match && decodeURIComponent(match[1].replace(/\+/g, " "));
}

token = queryParam("token");

$(document).ready(function() {

    $("#demo").html("hello " + token);

});


