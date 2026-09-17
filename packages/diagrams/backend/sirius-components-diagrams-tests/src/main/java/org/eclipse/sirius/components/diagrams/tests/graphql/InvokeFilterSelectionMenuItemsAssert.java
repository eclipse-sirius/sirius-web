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

import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.InvokeFilterSelectionSuccessPayload;

import com.jayway.jsonpath.JsonPath;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Custom assertion class used to perform tests on a InvokeFilterSelection payload.
 *
 * @author mcharfadi
 */
public class InvokeFilterSelectionMenuItemsAssert {

    private final String result;

    public InvokeFilterSelectionMenuItemsAssert(String result) {
        this.result = Objects.requireNonNull(result);
    }

    public InvokeFilterSelectionMenuItemsAssert isSuccess() {
        String typename = JsonPath.read(result, "$.data.invokeFilterSelection.__typename");
        assertThat(typename).isEqualTo(InvokeFilterSelectionSuccessPayload.class.getSimpleName());
        return this;
    }

    public InvokeFilterSelectionMenuItemsAssert hasNewSelection(Consumer<List<String>> consumer) {
        List<String> newSelection = JsonPath.read(this.result, "$.data.invokeFilterSelection.newSelection[*]");
        consumer.accept(newSelection);
        return this;
    }
}
