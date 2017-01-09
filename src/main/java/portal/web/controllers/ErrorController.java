package portal.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errors")
public class ErrorController {

	@RequestMapping(value="/400")
  public String handle400() {
      return "400";
  }

	@RequestMapping(value="/401")
  public String handle401() {
      return "401";
  }

	@RequestMapping(value="/403")
  public String handle403() {
      return "403";
  }

	@RequestMapping(value="/404")
  public String handle404() {
      return "404";
  }

	@RequestMapping(value="/405")
  public String handle405() {
      return "405";
  }

	@RequestMapping(value="/500")
  public String handle500() {
      return "error";
  }
}
