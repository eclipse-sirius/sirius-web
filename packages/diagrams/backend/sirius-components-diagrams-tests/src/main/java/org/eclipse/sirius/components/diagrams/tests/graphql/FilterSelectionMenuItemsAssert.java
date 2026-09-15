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
 * Custom assertion class used to perform tests on a FilterSelectionMenuItems.
 *
 * @author mcharfadi
 */
public class FilterSelectionMenuItemsAssert {

    private final String result;

    public FilterSelectionMenuItemsAssert(String result) {
        this.result = Objects.requireNonNull(result);
    }

    public FilterSelectionMenuItemsAssert hasMenuItemIds(Consumer<List<String>> consumer) {
        List<String> quickAccessToolLabels = JsonPath.read(this.result, "$.data.viewer.editingContext.representation.description.toolbar.filterSelectionMenuItems.[*].id");
        consumer.accept(quickAccessToolLabels);
        return this;
    }
}
