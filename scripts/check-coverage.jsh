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
  new ModuleCoverage("sirius-components-core", 73.0),
  new ModuleCoverage("sirius-components-collaborative", 87.0),
  new ModuleCoverage("sirius-components-graphql-api", 53.0),
  new ModuleCoverage("sirius-components-charts", 76.0),
  new ModuleCoverage("sirius-components-collaborative-charts", 81.0),
  new ModuleCoverage("sirius-components-diagrams", 79.0),
  new ModuleCoverage("sirius-components-collaborative-diagrams", 74.0),
  new ModuleCoverage("sirius-components-diagrams-graphql", 34.0),
  new ModuleCoverage("sirius-components-gantt", 88.0),
  new ModuleCoverage("sirius-components-collaborative-gantt", 65.0),
  new ModuleCoverage("sirius-components-gantt-graphql", 77.0),
  new ModuleCoverage("sirius-components-deck", 90.0),
  new ModuleCoverage("sirius-components-collaborative-deck", 46.0),
  new ModuleCoverage("sirius-components-deck-graphql", 55.0),
  new ModuleCoverage("sirius-components-forms", 81.0),
  new ModuleCoverage("sirius-components-collaborative-forms", 91.0),
  new ModuleCoverage("sirius-components-forms-graphql", 70.0),
  new ModuleCoverage("sirius-components-widget-reference", 83.0),
  new ModuleCoverage("sirius-components-collaborative-widget-reference", 55.0),
  new ModuleCoverage("sirius-components-widget-reference-graphql", 44.0),
  new ModuleCoverage("sirius-components-formdescriptioneditors", 76.0),
  new ModuleCoverage("sirius-components-collaborative-formdescriptioneditors", 86.0),
  new ModuleCoverage("sirius-components-formdescriptioneditors-graphql", 68.0),
  new ModuleCoverage("sirius-components-trees", 83.0),
  new ModuleCoverage("sirius-components-collaborative-trees", 78.0),
  new ModuleCoverage("sirius-components-trees-graphql", 73.0),
  new ModuleCoverage("sirius-components-selection", 80.0),
  new ModuleCoverage("sirius-components-collaborative-selection", 95.0),
  new ModuleCoverage("sirius-components-selection-graphql", 100.0),
  new ModuleCoverage("sirius-components-validation", 86.0),
  new ModuleCoverage("sirius-components-collaborative-validation", 85.0),
  new ModuleCoverage("sirius-components-validation-graphql", 100.0),
  new ModuleCoverage("sirius-components-portals", 76.0),
  new ModuleCoverage("sirius-components-collaborative-portals", 79.0),
  new ModuleCoverage("sirius-components-portals-graphql", 71.0),
  new ModuleCoverage("sirius-components-interpreter", 80.0),
  new ModuleCoverage("sirius-components-emf", 84.0),
  new ModuleCoverage("sirius-components-compatibility", 55.0),
  new ModuleCoverage("sirius-components-compatibility-emf", 78.0),
  new ModuleCoverage("sirius-components-graphql", 54.0),
  new ModuleCoverage("sirius-components-web", 54.0),
  new ModuleCoverage("sirius-components-starter", 72.0),
  new ModuleCoverage("sirius-components-domain", 72.0),
  new ModuleCoverage("sirius-components-domain-edit", 63.0),
  new ModuleCoverage("sirius-components-domain-design", 29.0),
  new ModuleCoverage("sirius-components-domain-emf", 98.0),
  new ModuleCoverage("sirius-components-view", 62.0),
  new ModuleCoverage("sirius-components-view-edit", 21.0),
  new ModuleCoverage("sirius-components-view-diagram", 56.0),
  new ModuleCoverage("sirius-components-view-diagram-edit", 6.0),
  new ModuleCoverage("sirius-components-view-form", 49.0),
  new ModuleCoverage("sirius-components-view-form-edit", 1.0),
  new ModuleCoverage("sirius-components-widget-reference-view", 34.0),
  new ModuleCoverage("sirius-components-widget-reference-view-edit", 4.0),
  new ModuleCoverage("sirius-components-view-gantt", 50.0),
  new ModuleCoverage("sirius-components-view-gantt-edit", 7.0),
  new ModuleCoverage("sirius-components-view-deck", 44.0),
  new ModuleCoverage("sirius-components-view-deck-edit", 4.0),
  new ModuleCoverage("sirius-components-view-emf", 70.0),
  new ModuleCoverage("sirius-components-view-builder", 37.0),
  new ModuleCoverage("sirius-components-task", 58.0),
  new ModuleCoverage("sirius-components-task-edit", 3.0),
  new ModuleCoverage("sirius-components-task-starter", 73.0),
  new ModuleCoverage("sirius-components-flow-starter", 79.0),
  new ModuleCoverage("sirius-components-view-diagram-customnodes", 57.0),
  new ModuleCoverage("sirius-components-view-diagram-customnodes-edit", 62.0),
  new ModuleCoverage("sirius-web-domain", 88.0),
  new ModuleCoverage("sirius-web-application", 93.0),
  new ModuleCoverage("sirius-web-infrastructure", 87.0),
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
double expectedGlobalCoverage = 61.0;
boolean isValidCoverage = global >= expectedGlobalCoverage;
display("total", global, expectedGlobalCoverage);

for (var moduleCoverage: moduleCoverageData) {
  var coverage = checkCoverage(moduleCoverage.moduleName());
  display(moduleCoverage.moduleName(), coverage, moduleCoverage.expectedCoverage());
  isValidCoverage = isValidCoverage && coverage >= moduleCoverage.expectedCoverage();
}

/exit isValidCoverage ? 0 : 1
