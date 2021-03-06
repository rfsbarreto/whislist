package br.com.rfsbarreto

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import java.util.Date
import java.text.SimpleDateFormat
import java.net.URL
import java.net.HttpURLConnection
import org.json.JSONObject
import org.json.JSONException

@Controller
class HomeController {
	
	@RequestMapping("/home")
	fun home(@ModelAttribute("token") token: String, model: Model):String {
		val starttime = System.currentTimeMillis()
		println("entering home")
		model.addAttribute("token",token)
		model.addAttribute("recentList",getFoursquareResponse(token))
		model.addAttribute("user",getMyUser(token))
		println(System.currentTimeMillis()-starttime)
		return "home"
	}
	
	fun getFoursquareResponse(token: String): List<Recent> {
		val recent = arrayListOf<Recent>()
		val today = SimpleDateFormat("yyyyMMdd").format(Date()) 
		val url = URL("https://api.foursquare.com/v2/checkins/recent?oauth_token="+
					  token+"&v="+today)
		 with(url.openConnection() as HttpURLConnection){
                        requestMethod = "GET"

                        connect()
                        println("\nSending 'GET' request to URL : $url")
                        println("Response Code : $responseCode")
                        println(responseMessage)
                        inputStream.bufferedReader().use {
                            var inputLine = it.readLine()
                            val obj = JSONObject(inputLine).getJSONObject("response").getJSONArray("recent")
							var qtdVenues: Int = 0
							if (obj.length()-1 > 30 ){
								qtdVenues = 30
							}else{
								qtdVenues = obj.length()-1
							}
								
							
							for (i in 0..qtdVenues){
								val rec = Recent()
								val item: JSONObject = obj.getJSONObject(i)
								item.getJSONObject("user").getString("firstName").let {
									rec.firstName = it
								}
								
								try{
									val venue = item.getJSONObject("venue")
									venue.getString("name").let{
										rec.venueName = it
									}
									rec.venueId =venue.getString("id")
								} catch (e: JSONException){
									e.printStackTrace()
									rec.venueName = "Unknowm"
								}
								
								recent.add(rec)
							}
							return recent
                        }
                    }
	}
	
	fun getMyUser( token: String): User {
		val today = SimpleDateFormat("yyyyMMdd").format(Date())
		val url = URL("https://api.foursquare.com/v2/users/self?oauth_token=$token&v=+$today")
		
		var myUser = User()
		with(url.openConnection() as HttpURLConnection){
                        requestMethod = "GET"
                        println("\nSending 'GET' request to URL : $url")
                        println("Response Code : $responseCode")
                        try{
	                        connect()
	                        inputStream.bufferedReader().use {
								try{
		                            var inputLine = it.readLine()
		                            val userJSon = JSONObject(inputLine).getJSONObject("response").getJSONObject("user")
									val userPhotoJSon = JSONObject(inputLine).getJSONObject("response").getJSONObject("user").getJSONObject("photo")
									myUser.name = userJSon.getString("firstName") + " " + userJSon.getString("lastName")
									myUser.photoUrl = userPhotoJSon.getString("prefix") + "130x130" +  userPhotoJSon.getString("suffix") 
								
									return myUser
								} catch (e: JSONException){
										e.printStackTrace()
								}
							
							
	                        }
                        }catch(e: Exception){
							e.printStackTrace()

						}
			 return myUser 
		}		
                 
	}
	
}