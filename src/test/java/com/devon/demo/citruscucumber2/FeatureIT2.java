package com.devon.demo.citruscucumber2;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//@CucumberOptions(features = {"C:\\DiwenWorkspace\\citrus-demo\\src\\test\\java\\com"
//		+ "\\devon\\demo\\citruscucumber2\\is_it_friday_yet.feature"},
//		strict = true )
@CucumberOptions(
		plugin = { "com.consol.citrus.cucumber.CitrusReporter" } )
public class FeatureIT2 {}
