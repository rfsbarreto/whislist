/**
 * 
 */
function login(){
    window.location.href = "https://foursquare.com/oauth2/authenticate"+
                                                                   "?client_id=R4D0LMB24U3XIURHFYHURODMP2TCDWOFAAIUD5XRFDQXHA4D"+
                                                                   "&response_type=code"+
                                                                   "&redirect_uri=http://localhost:3000/foursquare/callback";
}
