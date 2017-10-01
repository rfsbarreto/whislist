package br.com.rfsbarreto

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class IndexController {
	
	@RequestMapping("/ind")
	fun index() = "index"
}