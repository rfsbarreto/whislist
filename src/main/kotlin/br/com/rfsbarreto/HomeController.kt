package br.com.rfsbarreto

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute

@Controller
class HomeController {
	
	@RequestMapping("/home")
	fun home(@ModelAttribute("token") token: String, model: Model):String {
		println("entering home")
		model.addAttribute("token",token)
		return "home"
	} 
	
}