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
package org.eclipse.sirius.components.widget.reference.tests.graphql;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;

/**
 * Custom assertions on the root creation descriptions of a reference widget.
 *
 * @author tgiraudet
 */
public class ReferenceWidgetRootCreationDescriptionsAssert {

    private final GraphQLResult result;

    public ReferenceWidgetRootCreationDescriptionsAssert(GraphQLResult result) {
        this.result = Objects.requireNonNull(result);
    }

    public ReferenceWidgetRootCreationDescriptionsAssert hasCreationDescriptionIds(String... expectedIds) {
        assertThat(this.result.errors()).isEmpty();
        assertThat(this.getCreationDescriptionIds()).containsExactly(expectedIds);
        return this;
    }

    public String getCreationDescriptionId() {
        assertThat(this.result.errors()).isEmpty();
        var creationDescriptionIds = this.getCreationDescriptionIds();
        assertThat(creationDescriptionIds).hasSize(1);
        return creationDescriptionIds.get(0);
    }

    private List<String> getCreationDescriptionIds() {
        return JsonPath.read(this.result.data(), "$.data.viewer.editingContext.referenceWidgetRootCreationDescriptions[*].id");
    }
}
