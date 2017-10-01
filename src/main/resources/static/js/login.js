/**
 * 
 */
function login(){
    window.location.href = "https://foursquare.com/oauth2/authenticate"+
                                                                   "?client_id=IIFMMASNLTXM000XEKIP1H5ORRFZNL2KMOC1N43SBYJRRAE3"+
                                                                   "&response_type=code"+
                                                                   "&redirect_uri=http://localhost:3000/foursquare/callback";
}
