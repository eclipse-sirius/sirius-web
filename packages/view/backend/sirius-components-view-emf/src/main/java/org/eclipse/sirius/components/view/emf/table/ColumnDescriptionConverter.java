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
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.DomainClassPredicate;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.view.emf.ViewIconURLsProvider;

/**
 * Converts a View-based column description into an equivalent ColumnDescription.
 *
 * @author frouene
 */
public class ColumnDescriptionConverter {

    private final ITableIdProvider tableIdProvider;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    public ColumnDescriptionConverter(ITableIdProvider tableIdProvider, Function<VariableManager, String> semanticTargetIdProvider, Function<VariableManager, String> semanticTargetKindProvider) {
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.semanticTargetIdProvider = Objects.requireNonNull(semanticTargetIdProvider);
        this.semanticTargetKindProvider = Objects.requireNonNull(semanticTargetKindProvider);
    }

    public List<ColumnDescription> convert(List<org.eclipse.sirius.components.view.table.ColumnDescription> columnDescriptions, AQLInterpreter interpreter) {
        return columnDescriptions.stream().map(columnDescription -> this.convert(columnDescription, interpreter)).toList();
    }

    private ColumnDescription convert(org.eclipse.sirius.components.view.table.ColumnDescription columnDescription, AQLInterpreter interpreter) {
        return ColumnDescription.newColumnDescription(this.tableIdProvider.getId(columnDescription))
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .headerLabelProvider(variableManager -> this.evaluateString(interpreter, variableManager, columnDescription.getHeaderLabelExpression()))
                .headerIconURLsProvider(new ViewIconURLsProvider(interpreter, columnDescription.getHeaderIconExpression()))
                .headerIndexLabelProvider(variableManager -> this.evaluateString(interpreter, variableManager, columnDescription.getHeaderIndexLabelExpression()))
                .semanticElementsProvider(this.getColumnSemanticElementsProvider(columnDescription, interpreter))
                .shouldRenderPredicate(this.getShouldRenderPredicate(columnDescription.getPreconditionExpression(), interpreter))
                .isResizablePredicate(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), columnDescription.getIsResizableExpression()).asBoolean().orElse(false))
                .initialWidthProvider(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), columnDescription.getInitialWidthExpression()).asInt().orElse(-1))
                .filterVariantProvider(variableManager -> this.evaluateString(interpreter, variableManager, columnDescription.getFilterWidgetExpression()))
                .isSortablePredicate(variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), columnDescription.getIsSortableExpression()).asBoolean().orElse(false))
                .build();
    }

    private Function<VariableManager, List<Object>> getColumnSemanticElementsProvider(org.eclipse.sirius.components.view.table.ColumnDescription elementDescription, AQLInterpreter interpreter) {
        return variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), elementDescription.getSemanticCandidatesExpression());
            List<Object> candidates = result.asObjects().orElse(List.of());
            if (elementDescription.getDomainType() == null || elementDescription.getDomainType().isBlank()) {
                return candidates;
            }
            return candidates.stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .filter(candidate -> new DomainClassPredicate(Optional.ofNullable(elementDescription.getDomainType()).orElse("")).test(candidate.eClass()))
                    .map(Object.class::cast)
                    .toList();
        };
    }

    private Predicate<VariableManager> getShouldRenderPredicate(String preconditionExpression, AQLInterpreter interpreter) {
        return variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression);
            return result.asBoolean().orElse(true);
        };
    }

    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse("");
    }
}
