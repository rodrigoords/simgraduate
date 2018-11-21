package br.com.metrocamp.simgraduate.simulation.controller;

import br.com.metrocamp.simgraduate.simulation.model.Simulation;
import br.com.metrocamp.simgraduate.utils.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("simulations")
public class SimulationController extends BaseController<Simulation> { }
