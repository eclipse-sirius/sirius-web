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
package org.eclipse.sirius.components.view.emf.table;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;
import org.eclipse.sirius.components.view.emf.ViewIconURLsProvider;

/**
 * Converts a View-based line description into an equivalent LineDescription.
 *
 * @author frouene
 */
public class RowDescriptionConverter {

    private final ITableIdProvider tableIdProvider;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    public RowDescriptionConverter(ITableIdProvider tableIdProvider, Function<VariableManager, String> semanticTargetIdProvider, Function<VariableManager, String> semanticTargetKindProvider) {
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.semanticTargetIdProvider = Objects.requireNonNull(semanticTargetIdProvider);
        this.semanticTargetKindProvider = Objects.requireNonNull(semanticTargetKindProvider);
    }

    public LineDescription convert(org.eclipse.sirius.components.view.table.RowDescription rowDescription, AQLInterpreter interpreter) {
        return LineDescription.newLineDescription(this.tableIdProvider.getId(rowDescription))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .semanticElementsProvider(this.getRowSemanticElementsProvider(rowDescription, interpreter))
                .headerLabelProvider(variableManager -> this.evaluateString(interpreter, variableManager, rowDescription.getHeaderLabelExpression()))
                .headerIconURLsProvider(new ViewIconURLsProvider(interpreter, rowDescription.getHeaderIconExpression()))
                .headerIndexLabelProvider(variableManager -> this.evaluateString(interpreter, variableManager, rowDescription.getHeaderIndexLabelExpression()))
                .isResizablePredicate(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), rowDescription.getIsResizableExpression()).asBoolean().orElse(false))
                .initialHeightProvider(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), rowDescription.getInitialHeightExpression()).asInt().orElse(-1))
                .depthLevelProvider(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), rowDescription.getDepthLevelExpression()).asInt().orElse(0))
                .hasChildrenProvider(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), rowDescription.getHasChildrenExpression()).asBoolean().orElse(false))
                .build();
    }

    private Function<VariableManager, PaginatedData> getRowSemanticElementsProvider(org.eclipse.sirius.components.view.table.RowDescription elementDescription, AQLInterpreter interpreter) {
        return variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), elementDescription.getSemanticCandidatesExpression())
                .asObject()
                .filter(PaginatedData.class::isInstance)
                .map(PaginatedData.class::cast)
                .orElseGet(() -> new PaginatedData(List.of(), false, false, 0));
    }


    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse("");
    }
}
