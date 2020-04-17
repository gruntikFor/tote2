package tote.mvc;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tote.entity.Bet;
import tote.entity.Event;
import tote.entity.User;
import tote.service.BetService;
import tote.service.CategoryService;
import tote.service.EventService;
import tote.service.ResultService;
import tote.service.UserService;

@Controller
public class UsersController {

    private static final double INITIAL_CAPITAL = 200.0;

    @Autowired
    private CategoryService catSrv;
    @Autowired
    private EventService eventService;
    @Autowired
    private ResultService resSrv;
    @Autowired
    private BetService betSrv;
    @Autowired
    private UserService userSrv;

    @GetMapping({ "/main.html", "/index.html" })
    public String start(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("categories", catSrv.getCategory());
        return "main";
    }

    @GetMapping("/feedback.html")
    public String feedback(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "feedback";
    }

    @GetMapping("/bets.html")
    public String bets(Model model, Principal principal) {
        List<Event> events = eventService.getEvent();
        for (Event i : events) {
            i.setResults(resSrv.findByEventId(i.getId()));
        }

        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        Collections.reverse(events);
        model.addAttribute("events", events);
        return "bets";
    }

    @GetMapping("/addbet.html")
    public String addbet(Model model, @RequestParam("coeff[]") Long[] resId, @RequestParam("bet") BigDecimal bet,
            Principal principal) {

        User user = userSrv.getUser(principal.getName());
        for (Long i : resId) {
            user.setBillValue(user.getBillValue().subtract(bet));
            Bet b = new Bet();
            b.setIdRes(i);
            b.setBetValue(bet);
            b.setIdUser(user.getId());
            betSrv.save(b);
        }
        userSrv.save(user);

        List<Event> events = eventService.getEvent();
        for (Event i : events) {
            i.setResults(resSrv.findByEventId(i.getId()));
        }

        model.addAttribute("events", events);
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        ArrayList<Event> list = (ArrayList<Event>) eventService.getEvent();
        return "bets";
    }

    @GetMapping("/registration.html")
    public String reg() {
        return "registration";
    }

    @PostMapping("/registration.html")
    public String addUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (userSrv.getUser(username) != null) {
            return "registration";
        }
        User user = new User();
        user.setName(username);
        user.setPass(password);
        user.setBillValue(new BigDecimal(INITIAL_CAPITAL));
        userSrv.save(user);
        return "redirect: login.html";
    }

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public String auth() {
        return "authorization";
    }

    @GetMapping("/error.html")
    public String error(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "error";
    }
}
