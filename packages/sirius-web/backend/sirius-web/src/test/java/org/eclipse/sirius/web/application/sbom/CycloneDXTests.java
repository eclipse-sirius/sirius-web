/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.application.sbom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assumptions.assumeThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Used to test the sbom created by CycloneDX.
 *
 * @author sbegaudeau
 */
public class CycloneDXTests {

    @Test
    @DisplayName("Given the Sirius Web server, when the SBOM is created, then all values are properly configured")
    public void givenSiriusWebServerWhenTheSBOMIsCreatedThenAllValuesAreProperlyConfigured() {
        assumeThat(System.getenv("CI") != null);
        try {
            var path = Paths.get(this.getClass().getResource("/META-INF/sbom/application.cdx.json").toURI());
            var content = Files.readString(path, Charset.defaultCharset());

            var rootNode = new ObjectMapper().readTree(content);
            this.assertIsValid(rootNode.get("metadata").get("component"));

            var componentsNode = rootNode.get("components");
            if (componentsNode.isArray()) {
                ArrayNode componentsArray = (ArrayNode) componentsNode;
                var siriusWebNodes = componentsArray.valueStream()
                        .filter(componentNode -> componentNode.get("name").asText().startsWith("sirius-web"))
                        .toList();

                assertThat(siriusWebNodes).isNotEmpty().allSatisfy(this::assertIsValid);
            }

        } catch (URISyntaxException | IOException exception) {
            fail(exception.getMessage());
        }
    }

    private void assertIsValid(JsonNode jsonNode) {
        var json = jsonNode.toString();
        var name = jsonNode.get("name").asText();

        List<String> licenseIds = JsonPath.read(json, "$.licenses[*].license.id");
        assertThat(licenseIds)
                .withFailMessage(() -> "Invalid license id for " + name)
                .hasSize(1)
                .allMatch("EPL-2.0"::equals);

        List<String> licenseURLs = JsonPath.read(json, "$.licenses[*].license.url");
        assertThat(licenseURLs)
                .withFailMessage(() -> "Invalid license URL for " + name)
                .hasSize(1)
                .allMatch("https://www.eclipse.org/legal/epl-2.0"::equals);

        List<String> websites = JsonPath.read(json, "$.externalReferences[?(@.type == 'website')].url");
        assertThat(websites)
                .withFailMessage(() -> "Invalid website URL for " + name)
                .hasSize(1)
                .allMatch("https://eclipse.dev/sirius/sirius-web.html"::equals);

        List<String> distributionIntakes = JsonPath.read(json, "$.externalReferences[?(@.type == 'distribution-intake')].url");
        assertThat(distributionIntakes)
                .withFailMessage(() -> "Invalid distribution intake URL for " + name)
                .hasSize(1)
                .allMatch("https://maven.pkg.github.com/eclipse-sirius/sirius-web"::equals);

        List<String> vcs = JsonPath.read(json, "$.externalReferences[?(@.type == 'vcs')].url");
        assertThat(vcs)
                .withFailMessage(() -> "Invalid VCS URL for " + name)
                .hasSize(1)
                .allMatch("https://github.com/eclipse-sirius/sirius-web"::equals);
    }

}
