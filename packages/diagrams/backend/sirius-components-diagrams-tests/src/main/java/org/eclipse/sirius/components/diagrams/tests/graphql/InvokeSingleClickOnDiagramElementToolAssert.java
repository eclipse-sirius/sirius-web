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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;

/**
 * Custom assertion class used to perform tests on the result of the invocation of a single click on one diagram element.
 *
 * @author sbegaudeau
 */
public class InvokeSingleClickOnDiagramElementToolAssert {

    private final String result;

    public InvokeSingleClickOnDiagramElementToolAssert(String result) {
        this.result = Objects.requireNonNull(result);
    }

    public String getResult() {
        return result;
    }

    public InvokeSingleClickOnDiagramElementToolAssert isSuccess() {
        String typename = JsonPath.read(this.result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
        assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        return this;
    }

    public InvokeSingleClickOnDiagramElementToolAssert isError() {
        String typename = JsonPath.read(this.result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
        return this;
    }

    public InvokeSingleClickOnDiagramElementToolAssert hasSelection(Consumer<WorkbenchSelection> consumer) {
        List<String> rawSelection = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.newSelection.entries[*].id");
        var selection = new WorkbenchSelection(rawSelection.stream().map(id -> new WorkbenchSelectionEntry(id)).toList());

        consumer.accept(selection);
        return this;
    }
}
