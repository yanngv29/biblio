package edu.ensim.biblio.cucumber.stepdefs;

import edu.ensim.biblio.BiblioApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = BiblioApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
