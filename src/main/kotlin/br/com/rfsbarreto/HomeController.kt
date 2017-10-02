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
							for (i in 0..20){
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
									
									
									rec.venuePhoto = getVenuePhotoURL(venue.getString("id"),today,token)
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
	
	fun getVenuePhotoURL(venueID: String,today: String, token: String): String{
		val url = URL("https://api.foursquare.com/v2/venues/$venueID?oauth_token=$token&v=+$today")
		var urlPhoto: String  = ""
		with(url.openConnection() as HttpURLConnection){
                        requestMethod = "GET"
                        println("\nSending 'GET' request to URL : $url")
                        println("Response Code : $responseCode")
                        try{
	                        connect()
	                        inputStream.bufferedReader().use {
								try{
		                            var inputLine = it.readLine()
		                            val obj = JSONObject(inputLine).getJSONObject("response").getJSONObject("venue")
									val bestPhoto = obj.getJSONObject("bestPhoto")
									urlPhoto = bestPhoto.getString("prefix") + bestPhoto.getInt("width").toString() + "x" + bestPhoto.getInt("height").toString() + bestPhoto.getString("suffix")
								} catch (e: JSONException){
										e.printStackTrace()
								}
								
								return urlPhoto
								
	                        }
                        }catch(e: Exception){
							e.printStackTrace()
							return "http://picture-cdn.wheretoget.it/8yl60v-i.jpg"
						} 
		}		
                        
	}
	
}