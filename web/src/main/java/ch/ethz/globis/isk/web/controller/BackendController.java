package ch.ethz.globis.isk.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/backends")
@SessionAttributes
public class BackendController {

    private static final Log LOG = LogFactory.getLog(BackendController.class);

    @ResponseBody
    @RequestMapping(value = {"/change"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String changeCurrentBackend(HttpServletRequest request,
                                       @RequestParam(value = "currentBackend") String currentBackend) {
        HttpSession session = request.getSession();
        session.setAttribute("currentBackend", currentBackend);
        LOG.info("Changed current backend to " + session.getAttribute("currentBackend"));
        return currentBackend;
    }
}