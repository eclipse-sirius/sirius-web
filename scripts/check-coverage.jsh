double checkCoverage(String module) {
  double coverage = 0.0;

  var totalStart = "<td>Total</td>";
  var resultStart = "<td class=\"ctr2\">";
  var resultEnd = "%</td>";

  var modulePath = module;
  if (!module.isBlank()) {
    modulePath += "/";
  }
  try {
    var path = Paths.get("packages/releng/backend/sirius-components-test-coverage/target/site/jacoco-aggregate/" + modulePath + "index.html");
    var optionalLine = Files.readAllLines(path).stream().filter(line -> line.contains(totalStart)).findFirst();
    if (optionalLine.isPresent()) {
      var line = optionalLine.get();
      var totalIndex = line.indexOf(totalStart);
      var startIndex = line.indexOf(resultStart, totalIndex);
      var endIndex = line.indexOf(resultEnd, startIndex);
      var result = line.substring(startIndex + resultStart.length(), endIndex);
      var resultToParse = result.replaceAll("\\p{Z}","").trim();

      coverage = Double.parseDouble(resultToParse);
    }
  } catch (IOException exception) {
    System.out.println(exception.getMessage());
  }
  return coverage;
}

// Check global code coverage
double global = checkCoverage("");

// Check Sirius Web code coverage
double siriusWebDomain = checkCoverage("sirius-web-domain");
double siriusWebInfrastructure = checkCoverage("sirius-web-infrastructure");
double siriusWebApplication = checkCoverage("sirius-web-application");
double siriusWebStarter = checkCoverage("sirius-web-starter");

// Check the code coverage of some of the most important representations
double siriusComponentsDiagrams = checkCoverage("sirius-components-diagrams");
double siriusComponentsCollaborativeDiagrams = checkCoverage("sirius-components-collaborative-diagrams");
double siriusComponentsForms = checkCoverage("sirius-components-forms");
double siriusComponentsCollaborativeForms = checkCoverage("sirius-components-collaborative-forms");

// Check the code coverage of the view DSL
double siriusComponentsViewDiagram = checkCoverage("sirius-components-view-diagram");
double siriusComponentsViewForm = checkCoverage("sirius-components-view-form");
double siriusComponentsViewEMF = checkCoverage("sirius-components-view-emf");

// Expected code coverage values
double expectedGlobalCoverage = 50.0;
double expectedSiriusComponentsDiagramsCoverage = 76.0;
double expectedSiriusComponentsCollaborativeDiagramsCoverage = 66.0;
double expectedSiriusComponentsFormsCoverage = 76.0;
double expectedSiriusComponentsCollaborativeFormsCoverage = 79.0;
double expectedSiriusComponentsViewDiagramCoverage = 53.0;
double expectedSiriusComponentsViewFormCoverage = 42.0;
double expectedSiriusComponentsViewEMFCoverage = 57.0;
double expectedSiriusWebDomainCoverage = 59.0;
double expectedSiriusWebInfrastructureCoverage = 83.0;
double expectedSiriusWebApplicationCoverage = 84.0;
double expectedSiriusWebStarterCoverage = 95.0;


void display(String module, double coverage, double expected) {
  System.out.format("%-42s%10.2f%10.2f", module, coverage, expected);
  System.out.println();
}

// Log the most important code coverage metrics
System.out.println("##############################################################");
System.out.println("##################   Code Coverage Results  ##################");
System.out.println("##############################################################");
System.out.println();
System.out.format("%-42s%10s%10s", "Module", "Coverage", "Expected");
System.out.println();
display("total", global, expectedGlobalCoverage);
display("sirius-components-diagrams", siriusComponentsDiagrams, expectedSiriusComponentsDiagramsCoverage);
display("sirius-components-collaborative-diagrams", siriusComponentsCollaborativeDiagrams, expectedSiriusComponentsCollaborativeDiagramsCoverage);
display("sirius-components-forms", siriusComponentsForms, expectedSiriusComponentsFormsCoverage);
display("sirius-components-collaborative-forms", siriusComponentsCollaborativeForms, expectedSiriusComponentsCollaborativeFormsCoverage);
display("sirius-components-view-diagram", siriusComponentsViewDiagram, expectedSiriusComponentsViewDiagramCoverage);
display("sirius-components-view-form", siriusComponentsViewForm, expectedSiriusComponentsViewFormCoverage);
display("sirius-components-view-emf", siriusComponentsViewEMF, expectedSiriusComponentsViewEMFCoverage);
display("sirius-web-domain", siriusWebDomain, expectedSiriusWebDomainCoverage);
display("sirius-web-infrastructure", siriusWebInfrastructure, expectedSiriusWebInfrastructureCoverage);
display("sirius-web-application", siriusWebApplication, expectedSiriusWebApplicationCoverage);
display("sirius-web-starter", siriusWebStarter, expectedSiriusWebStarterCoverage);

boolean isValidCoverage = global >= expectedGlobalCoverage &&
  (siriusComponentsDiagrams >= expectedSiriusComponentsDiagramsCoverage) &&
  (siriusComponentsCollaborativeDiagrams >= expectedSiriusComponentsCollaborativeDiagramsCoverage) &&
  (siriusComponentsForms >= expectedSiriusComponentsFormsCoverage) &&
  (siriusComponentsCollaborativeForms >= expectedSiriusComponentsCollaborativeFormsCoverage) &&
  (siriusComponentsViewDiagram >= expectedSiriusComponentsViewDiagramCoverage) &&
  (siriusComponentsViewForm >= expectedSiriusComponentsViewFormCoverage) &&
  (siriusComponentsViewEMF >= expectedSiriusComponentsViewEMFCoverage) &&
  (siriusWebDomain >= expectedSiriusWebDomainCoverage) &&
  (siriusWebInfrastructure >= expectedSiriusWebInfrastructureCoverage) &&
  (siriusWebApplication >= expectedSiriusWebApplicationCoverage) &&
  (siriusWebStarter >= expectedSiriusWebStarterCoverage);

/exit isValidCoverage ? 0 : 1