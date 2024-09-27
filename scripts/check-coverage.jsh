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
  new ModuleCoverage("sirius-components-core", 78.0),
  new ModuleCoverage("sirius-components-collaborative", 87.0),
  new ModuleCoverage("sirius-components-graphql-api", 58.0),
  new ModuleCoverage("sirius-components-charts", 76.0),
  new ModuleCoverage("sirius-components-collaborative-charts", 83.0),
  new ModuleCoverage("sirius-components-diagrams", 81.0),
  new ModuleCoverage("sirius-components-collaborative-diagrams", 81.0),
  new ModuleCoverage("sirius-components-diagrams-graphql", 65.0),
  new ModuleCoverage("sirius-components-gantt", 91.0),
  new ModuleCoverage("sirius-components-collaborative-gantt", 83.0),
  new ModuleCoverage("sirius-components-gantt-graphql", 91.0),
  new ModuleCoverage("sirius-components-deck", 90.0),
  new ModuleCoverage("sirius-components-collaborative-deck", 54.0),
  new ModuleCoverage("sirius-components-deck-graphql", 61.0),
  new ModuleCoverage("sirius-components-forms", 84.0),
  new ModuleCoverage("sirius-components-collaborative-forms", 92.0),
  new ModuleCoverage("sirius-components-forms-graphql", 83.0),
  new ModuleCoverage("sirius-components-widget-reference", 83.0),
  new ModuleCoverage("sirius-components-collaborative-widget-reference", 82.0),
  new ModuleCoverage("sirius-components-widget-reference-graphql", 51.0),
  new ModuleCoverage("sirius-components-formdescriptioneditors", 76.0),
  new ModuleCoverage("sirius-components-collaborative-formdescriptioneditors", 88.0),
  new ModuleCoverage("sirius-components-formdescriptioneditors-graphql", 59.0),
  new ModuleCoverage("sirius-components-trees", 87.0),
  new ModuleCoverage("sirius-components-collaborative-trees", 92.0),
  new ModuleCoverage("sirius-components-trees-graphql", 98.0),
  new ModuleCoverage("sirius-components-tables", 85.0),
  new ModuleCoverage("sirius-components-collaborative-tables", 89.0),
  new ModuleCoverage("sirius-components-tables-graphql", 98.0),
  new ModuleCoverage("sirius-components-selection", 92.0),
  new ModuleCoverage("sirius-components-collaborative-selection", 98.0),
  new ModuleCoverage("sirius-components-selection-graphql", 100.0),
  new ModuleCoverage("sirius-components-validation", 86.0),
  new ModuleCoverage("sirius-components-collaborative-validation", 85.0),
  new ModuleCoverage("sirius-components-validation-graphql", 100.0),
  new ModuleCoverage("sirius-components-portals", 76.0),
  new ModuleCoverage("sirius-components-collaborative-portals", 87.0),
  new ModuleCoverage("sirius-components-portals-graphql", 82.0),
  new ModuleCoverage("sirius-components-interpreter", 80.0),
  new ModuleCoverage("sirius-components-emf", 85.0),
  new ModuleCoverage("sirius-components-graphql", 54.0),
  new ModuleCoverage("sirius-components-web", 54.0),
  new ModuleCoverage("sirius-components-domain", 72.0),
  new ModuleCoverage("sirius-components-domain-edit", 64.0),
  new ModuleCoverage("sirius-components-domain-emf", 95.0),
  new ModuleCoverage("sirius-components-view", 64.0),
  new ModuleCoverage("sirius-components-view-edit", 43.0),
  new ModuleCoverage("sirius-components-view-diagram", 57.0),
  new ModuleCoverage("sirius-components-view-diagram-edit", 24.0),
  new ModuleCoverage("sirius-components-view-form", 48.0),
  new ModuleCoverage("sirius-components-view-form-edit", 0.0),
  new ModuleCoverage("sirius-components-widget-reference-view", 34.0),
  new ModuleCoverage("sirius-components-widget-reference-view-edit", 4.0),
  new ModuleCoverage("sirius-components-view-gantt", 63.0),
  new ModuleCoverage("sirius-components-view-gantt-edit", 34.0),
  new ModuleCoverage("sirius-components-view-deck", 50.0),
  new ModuleCoverage("sirius-components-view-deck-edit", 28.0),
  new ModuleCoverage("sirius-components-view-tree", 52.0),
  new ModuleCoverage("sirius-components-view-tree-edit", 17.0),
  new ModuleCoverage("sirius-components-view-emf", 73.0),
  new ModuleCoverage("sirius-components-view-builder", 44.0),
  new ModuleCoverage("sirius-components-task", 37.0),
  new ModuleCoverage("sirius-components-task-edit", 3.0),
  new ModuleCoverage("sirius-components-view-diagram-customnodes", 57.0),
  new ModuleCoverage("sirius-components-view-diagram-customnodes-edit", 71.0),
  new ModuleCoverage("sirius-web-domain", 93.0),
  new ModuleCoverage("sirius-web-application", 93.0),
  new ModuleCoverage("sirius-web-infrastructure", 93.0),
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
double expectedGlobalCoverage = 64.0;
boolean isValidCoverage = global >= expectedGlobalCoverage;
display("total", global, expectedGlobalCoverage);

for (var moduleCoverage: moduleCoverageData) {
  var coverage = checkCoverage(moduleCoverage.moduleName());
  display(moduleCoverage.moduleName(), coverage, moduleCoverage.expectedCoverage());
  isValidCoverage = isValidCoverage && coverage >= moduleCoverage.expectedCoverage();
}

/exit isValidCoverage ? 0 : 1
