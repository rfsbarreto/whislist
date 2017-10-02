/**
 * 
 */
function login(){
    window.location.href = "https://foursquare.com/oauth2/authenticate"+
                                                                   "?client_id=UWK204OQ0PELRWTYNEOFBW43GL5UIBJ2EPXWCU4HTU3HK0VQ"+
                                                                   "&response_type=code"+
                                                                   "&redirect_uri=http://localhost:3000/foursquare/callback";
}
