package de.andrena.springworkshop.controllers;

import de.andrena.springworkshop.facades.EventFacade;
import de.andrena.springworkshop.facades.EventFacadeImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AgendaController {

    private EventFacade eventFacade = new EventFacadeImpl();

    @RequestMapping( value = "/agenda")
    public String events(final Model model)
    {

        model.addAttribute("events", eventFacade.getAllEvents());
        return "mainPage";
    }

}
