package br.com.rfsbarreto

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.ui.Model
import java.net.URL
import java.net.HttpURLConnection
import org.json.JSONObject
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class FoursquareCallbackController {
	
	@RequestMapping("/foursquare/callback")
	fun callback(@RequestParam(value="code") code:String, model: Model, rattr: RedirectAttributes): String{
		var token: String = ""
		code.let {
                    val url = URL("https://foursquare.com/oauth2/access_token"+
                    "?client_id=R4D0LMB24U3XIURHFYHURODMP2TCDWOFAAIUD5XRFDQXHA4D"+
                    "&client_secret=2PR5AWJG1UEWBYJQKTW1FY4J1Y1YQ2QIDGGX103ESPFRFUZL"+
                    "&grant_type=authorization_code"+
                    "&redirect_uri=http://localhost:3000/foursquare/callback"+
                    "&code=$code")

                    with(url.openConnection() as HttpURLConnection){
                        requestMethod = "GET"

                        connect()
                        println("\nSending 'GET' request to URL : $url")
                        println("Response Code : $responseCode")
                        println(responseMessage)
                        inputStream.bufferedReader().use {
                            var inputLine = it.readLine()
                            token = JSONObject(inputLine).getString("access_token")
                        }
                    }


                }
		if (token.isNotEmpty()){
			rattr.addFlashAttribute("token",token)
			return "redirect:/home.html"
		} else
			return "error"
			
	}
}