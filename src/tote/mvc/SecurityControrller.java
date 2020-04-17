package tote.mvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tote.entity.Bet;
import tote.entity.Category;
import tote.entity.Event;
import tote.entity.Result;
import tote.entity.User;
import tote.service.BetService;
import tote.service.CategoryService;
import tote.service.EventService;
import tote.service.ResultService;
import tote.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class SecurityControrller {

    @Autowired
    private UserService userSrv;
    @Autowired
    private CategoryService catSrv;
    @Autowired
    private EventService eventSvr;
    @Autowired
    private ResultService resultSrv;
    @Autowired
    private BetService betSrv;

    @PostMapping("/authorization.html")
    public String check(@RequestParam("username") String login, @RequestParam("password") String pass) {
        return "index";
    }

    @GetMapping("/admin.html")
    public String getDiscipline(Principal principal, Model model) {
        model.addAttribute("categories", catSrv.getCategory());
        return "add-discipline";
    }

    @PostMapping("/add-dis.html")
    public String addDiscipline(@RequestParam("discipline") String discipline, Model model) {
        Category category = new Category(discipline);
        catSrv.add(category);
        for (Category i : catSrv.getCategory()) {
            System.out.println(i.getName() + "__________________\n\n\n");
        }
        model.addAttribute("categories", catSrv.getCategory());
        return "redirect:/admin/admin.html";
    }

    @PostMapping("/admin.html")
    public String addDiscipline() {
        return "add-discipline";

    }

    @GetMapping("/add-event.html")
    public String getEvent(Model model) {
        model.addAttribute("categories", catSrv.getCategory());
        return "add-event";
    }

    @PostMapping("/add-eve.html")
    public String addEvent(Model model, @RequestParam("discipline") Long idCategory, @RequestParam("event") String name,
            @RequestParam("date") String date) {
        Event event = new Event(date, idCategory, name);
        eventSvr.add(event);
        System.out.println(event.getName() + "added\n\n");
        model.addAttribute("categories", catSrv.getCategory());
        return "redirect:/admin/add-event.html";
    }

    @PostMapping("/add-event.html")
    public String addEvent() {
        return "add-event";
    }

    @GetMapping("/add-outcome.html")
    public String getOutcome(Model model) {
        model.addAttribute("categories", catSrv.getCategory());
        model.addAttribute("events", eventSvr.getEvent());
        return "add-outcome";
    }

    @PostMapping("/add-out.html")
    public String addOutcome(Model model, @RequestParam("discipline") Long idCategory,
            @RequestParam("event") Long idevent, @RequestParam("outcome") String name,
            @RequestParam("coeff") Double coeff) {
        Result res = new Result(name, coeff, idevent);
        resultSrv.add(res);
        model.addAttribute("categories", catSrv.getCategory());
        model.addAttribute("events", eventSvr.getEvent());
        return "redirect:/admin/add-outcome.html";
    }

    @PostMapping("/add-outcome.html")
    public String addOutcome(Model model) {
        model.addAttribute("categories", catSrv.getCategory());
        return "add-outcome";
    }

    @GetMapping("/state-outcomes.html")
    public String getOutcomes(Model model) {

        List<Event> events = eventSvr.getEvent();
        for (Event i : events) {
            i.setResults(resultSrv.findByEventId(i.getId()));
        }
        Collections.reverse(events);
        model.addAttribute("events", events);
        return "state-outcomes";
    }

    @PostMapping("/state-outcomes.html")
    public String setOutcomes(Model model, @RequestParam("betId") Long id, @RequestParam("state") int state) {
        Result result = resultSrv.getResult(id);
        result.setState(state);
        if (state == 1) {
            for (Bet i : betSrv.getBets()) {
                if (i.getIdRes() == id) {
                    User user = userSrv.getUser(i.getIdUser());
                    user.setBillValue(
                            user.getBillValue().add(i.getBetValue().multiply(new BigDecimal(result.getCoef())))
                                    .setScale(2, RoundingMode.CEILING));
                    userSrv.save(user);
                }
            }
        }
        resultSrv.save(result);
        List<Event> events = eventSvr.getEvent();
        for (Event i : events) {
            i.setResults(resultSrv.findByEventId(i.getId()));
        }
        Collections.reverse(events);
        model.addAttribute("events", events);
        return "state-outcomes";
    }

}
