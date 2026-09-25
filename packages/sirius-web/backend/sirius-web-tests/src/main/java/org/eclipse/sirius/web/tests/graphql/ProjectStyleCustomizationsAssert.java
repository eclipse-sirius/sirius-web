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
package org.eclipse.sirius.web.tests.graphql;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.assertions.PageInfoAssert;

/**
 * Custom assertion class used to perform tests on project style customizations query results.
 *
 * @author gcoutable
 */
public class ProjectStyleCustomizationsAssert {

    private static final String PAGE_INFO_PATH = "$.data.viewer.project.styleCustomizations.pageInfo";

    private final GraphQLResult result;

    public ProjectStyleCustomizationsAssert(GraphQLResult result) {
        this.result = Objects.requireNonNull(result);
    }

    public ProjectStyleCustomizationsAssert hasNoErrors() {
        assertThat(this.result.errors()).isEmpty();
        return this;
    }

    public ProjectStyleCustomizationsAssert hasPageInfo(Consumer<PageInfoAssert> consumer) {
        Objects.requireNonNull(consumer).accept(new PageInfoAssert(this.result.data(), PAGE_INFO_PATH));
        return this;
    }

    public ProjectStyleCustomizationsAssert hasStyleCustomizationIds(String... expectedIds) {
        List<String> styleCustomizationIds = JsonPath.read(this.result.data(), "$.data.viewer.project.styleCustomizations.edges[*].node.id");
        assertThat(styleCustomizationIds).containsExactly(expectedIds);
        return this;
    }
}
