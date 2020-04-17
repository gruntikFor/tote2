package tote.mvc;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeansController {

    @Autowired
    private ApplicationContext ctx;

    @GetMapping({ "/beans" })
    public String start(Model model) {

        Map<String, String> map = new TreeMap<String, String>();

        for (String name : ctx.getBeanDefinitionNames()) {
            map.put(name, ctx.getBean(name).getClass().getName());
        }

        model.addAttribute("beans", map);
        return "beans";
    }

}
