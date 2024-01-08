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

record ModuleCoverage(String moduleName, double expectedCoverage) {}
var moduleCoverageData = List.of(
  new ModuleCoverage("sirius-components-annotations", 0.0),
  new ModuleCoverage("sirius-components-annotations-spring", 0.0),
  new ModuleCoverage("sirius-components-representations", 76.0),
  new ModuleCoverage("sirius-components-core", 62.0),
  new ModuleCoverage("sirius-components-collaborative", 83.0),
  new ModuleCoverage("sirius-components-graphql-api", 46.0),
  new ModuleCoverage("sirius-components-charts", 52.0),
  new ModuleCoverage("sirius-components-collaborative-charts", 23.0),
  new ModuleCoverage("sirius-components-diagrams", 76.0),
  new ModuleCoverage("sirius-components-collaborative-diagrams", 66.0),
  new ModuleCoverage("sirius-components-diagrams-graphql", 26.0),
  new ModuleCoverage("sirius-components-gantt", 0.0),
  new ModuleCoverage("sirius-components-collaborative-gantt", 16.0),
  new ModuleCoverage("sirius-components-gantt-graphql", 32.0),
  new ModuleCoverage("sirius-components-deck", 0.0),
  new ModuleCoverage("sirius-components-collaborative-deck", 15.0),
  new ModuleCoverage("sirius-components-deck-graphql", 36.0),
  new ModuleCoverage("sirius-components-forms", 81.0),
  new ModuleCoverage("sirius-components-collaborative-forms", 85.0),
  new ModuleCoverage("sirius-components-forms-graphql", 57.0),
  new ModuleCoverage("sirius-components-widget-reference", 70.0),
  new ModuleCoverage("sirius-components-collaborative-widget-reference", 54.0),
  new ModuleCoverage("sirius-components-widget-reference-graphql", 28.0),
  new ModuleCoverage("sirius-components-formdescriptioneditors", 18.0),
  new ModuleCoverage("sirius-components-collaborative-formdescriptioneditors", 83.0),
  new ModuleCoverage("sirius-components-formdescriptioneditors-graphql", 50.0),
  new ModuleCoverage("sirius-components-trees", 81.0),
  new ModuleCoverage("sirius-components-collaborative-trees", 63.0),
  new ModuleCoverage("sirius-components-trees-graphql", 51.0),
  new ModuleCoverage("sirius-components-selection", 0.0),
  new ModuleCoverage("sirius-components-collaborative-selection", 7.0),
  new ModuleCoverage("sirius-components-selection-graphql", 20.0),
  new ModuleCoverage("sirius-components-validation", 0.0),
  new ModuleCoverage("sirius-components-collaborative-validation", 5.0),
  new ModuleCoverage("sirius-components-validation-graphql", 22.0),
  new ModuleCoverage("sirius-components-portals", 65.0),
  new ModuleCoverage("sirius-components-collaborative-portals", 43.0),
  new ModuleCoverage("sirius-components-portals-graphql", 59.0),
  new ModuleCoverage("sirius-components-interpreter", 80.0),
  new ModuleCoverage("sirius-components-emf", 67.0),
  new ModuleCoverage("sirius-components-compatibility", 55.0),
  new ModuleCoverage("sirius-components-compatibility-emf", 78.0),
  new ModuleCoverage("sirius-components-graphql", 54.0),
  new ModuleCoverage("sirius-components-web", 54.0),
  new ModuleCoverage("sirius-components-starter", 72.0),
  new ModuleCoverage("sirius-components-domain", 72.0),
  new ModuleCoverage("sirius-components-domain-edit", 49.0),
  new ModuleCoverage("sirius-components-domain-design", 29.0),
  new ModuleCoverage("sirius-components-domain-emf", 98.0),
  new ModuleCoverage("sirius-components-view", 57.0),
  new ModuleCoverage("sirius-components-view-edit", 10.0),
  new ModuleCoverage("sirius-components-view-diagram", 53.0),
  new ModuleCoverage("sirius-components-view-diagram-edit", 3.0),
  new ModuleCoverage("sirius-components-view-form", 42.0),
  new ModuleCoverage("sirius-components-view-form-edit", 1.0),
  new ModuleCoverage("sirius-components-widget-reference-view", 29.0),
  new ModuleCoverage("sirius-components-widget-reference-view-edit", 3.0),
  new ModuleCoverage("sirius-components-view-gantt", 40.0),
  new ModuleCoverage("sirius-components-view-gantt-edit", 7.0),
  new ModuleCoverage("sirius-components-view-deck", 34.0),
  new ModuleCoverage("sirius-components-view-deck-edit", 4.0),
  new ModuleCoverage("sirius-components-view-emf", 58.0),
  new ModuleCoverage("sirius-components-view-builder", 26.0),
  new ModuleCoverage("sirius-components-task", 48.0),
  new ModuleCoverage("sirius-components-task-edit", 3.0),
  new ModuleCoverage("sirius-components-task-starter", 63.0),
  new ModuleCoverage("sirius-components-flow-starter", 78.0),
  new ModuleCoverage("sirius-web-customwidgets", 57.0),
  new ModuleCoverage("sirius-web-customwidgets-edit", 7.0),
  new ModuleCoverage("sirius-web-customnodes", 12.0),
  new ModuleCoverage("sirius-web-customnodes-edit", 5.0),
  new ModuleCoverage("sirius-web-domain", 80.0),
  new ModuleCoverage("sirius-web-application", 92.0),
  new ModuleCoverage("sirius-web-infrastructure", 86.0),
  new ModuleCoverage("sirius-web-starter", 98.0),
  new ModuleCoverage("sirius-web", 37.0)
);

void display(String module, double coverage, double expected) {
  var status = "";
  if (coverage == expected) {
    status = "OK";
  } else if (coverage < expected) {
    status = "more tests are required";
  } else if (coverage > expected) {
    status = "update expected code coverage";
  }

  System.out.format("%-60s%10.2f%10.2f%35s", module, coverage, expected, status);
  System.out.println();
}

// Log the most important code coverage metrics
System.out.println("####################################################################################################################");
System.out.println("#############################################                          #############################################");
System.out.println("#############################################   Code Coverage Results  #############################################");
System.out.println("#############################################                          #############################################");
System.out.println("####################################################################################################################");
System.out.println();
System.out.format("%-60s%10s%10s%35s", "Module", "Coverage", "Expected", "Status");
System.out.println();


// Check global code coverage
double global = checkCoverage("");
double expectedGlobalCoverage = 51.0;
boolean isValidCoverage = global >= expectedGlobalCoverage;
display("total", global, expectedGlobalCoverage);

for (var moduleCoverage: moduleCoverageData) {
  var coverage = checkCoverage(moduleCoverage.moduleName());
  display(moduleCoverage.moduleName(), coverage, moduleCoverage.expectedCoverage());
  isValidCoverage = isValidCoverage && coverage >= moduleCoverage.expectedCoverage();
}

/exit isValidCoverage ? 0 : 1
