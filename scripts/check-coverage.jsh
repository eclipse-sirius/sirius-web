double coverage = 0.0;

var totalStart = "<td>Total</td>";
var resultStart = "<td class=\"ctr2\">";
var resultEnd = "%</td>";

var path = Paths.get("packages/releng/backend/sirius-components-test-coverage/target/site/jacoco-aggregate/index.html");
var optionalLine = Files.readAllLines(path).stream().filter(line -> line.contains(totalStart)).findFirst();
if (optionalLine.isPresent()) {
  var line = optionalLine.get();
  var totalIndex = line.indexOf(totalStart);
  var startIndex = line.indexOf(resultStart, totalIndex);
  var endIndex = line.indexOf(resultEnd, startIndex);
  var result = line.substring(startIndex + resultStart.length(), endIndex);

  coverage = Double.parseDouble(result.trim());
  System.out.println(coverage + " % global code coverage");
}
/exit coverage >= 39.0 ? 0 : 1