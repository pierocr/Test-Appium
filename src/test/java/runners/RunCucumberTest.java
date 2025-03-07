package runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps")

// 🔹 Fuerza que los reportes vayan a build/allure-results/
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value =
        "pretty, " +
                "json:build/allure-results/cucumber.json, " +
                "html:build/reports/cucumber-report.html, " +
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class RunCucumberTest {
}
