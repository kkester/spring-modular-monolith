package mart.mono;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "mart.mono")
class ArchTests {

    @ArchTest
    void ensureControllers(JavaClasses classes) {
            noClasses()
                    .that()
                    .areAnnotatedWith(RestController.class)
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..db..")
                    .check(classes);
    }

    @ArchTest
    void ensureServices(JavaClasses classes) {
        noClasses()
                .that()
                .areAnnotatedWith(Service.class)
                .should()
                .dependOnClassesThat()
                .areAnnotatedWith(RestController.class)
                .check(classes);
    }
}