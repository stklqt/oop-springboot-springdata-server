package de.andrena.springworkshop.controllers;

import de.andrena.springworkshop.facades.EventFacade;
import de.andrena.springworkshop.facades.EventFacadeImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AgendaController {

    private EventFacade eventFacade = new EventFacadeImpl();

    @RequestMapping( value = "/agenda")
    public String agenda(final Model model)
    {
        model.addAttribute("events", eventFacade.getAllEvents());
        return "mainPage";
    }

    @RequestMapping(value = "/search")
    public String searchByTitle(@RequestParam(value = "title", defaultValue = "") final String title, final Model model) {
        model.addAttribute("events", eventFacade.getEventWithTitle(title));
        return "searchPage";
    }

    @RequestMapping(value = "/reactAgenda")
    public String reactAgenda() {
        return "mainPageReact";
    }
}
