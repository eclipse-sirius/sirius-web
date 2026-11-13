/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import com.jayway.jsonpath.JsonPath;

/**
 * Assertions on the result of a layout configurations query.
 *
 * @author frouene
 * @since v2026.12.0
 */
public class GetLayoutConfigurationAssert {

    private static final String LAYOUT_CONFIGURATIONS_PATH = "$.data.viewer.editingContext.representation.description.layoutConfigurations";

    private final String result;

    public GetLayoutConfigurationAssert(String result) {
        this.result = Objects.requireNonNull(result);
    }

    public GetLayoutConfigurationAssert hasConfigurationIds(Consumer<List<String>> consumer) {
        List<String> ids = JsonPath.read(this.result, LAYOUT_CONFIGURATIONS_PATH + "[*].id");
        consumer.accept(ids);
        return this;
    }

    public GetLayoutConfigurationAssert hasConfigurationLabels(Consumer<List<String>> consumer) {
        List<String> labels = JsonPath.read(this.result, LAYOUT_CONFIGURATIONS_PATH + "[*].label");
        consumer.accept(labels);
        return this;
    }

    public GetLayoutConfigurationAssert hasLayoutAlgorithms(Consumer<List<String>> consumer) {
        List<String> algorithms = JsonPath.read(this.result, LAYOUT_CONFIGURATIONS_PATH + "[*].layoutOptions[?(@.key=='elk.algorithm')].value");
        consumer.accept(algorithms);
        return this;
    }
}
