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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.eclipse.sirius.components.tables.descriptions.IconLabelCellDescription;
import org.eclipse.sirius.components.tables.descriptions.TextareaCellDescription;
import org.eclipse.sirius.components.tables.descriptions.TextfieldCellDescription;
import org.eclipse.sirius.components.view.emf.ViewIconURLsProvider;
import org.eclipse.sirius.components.view.emf.table.api.ICustomCellConverter;
import org.eclipse.sirius.components.view.table.CellLabelWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription;

/**
 * Converts a View-based cell description into an equivalent ICellDescription.
 *
 * @author frouene
 */
public class CellDescriptionConverter {

    private final ITableIdProvider tableIdProvider;

    private final IObjectService objectService;

    private final List<ICustomCellConverter> customCellConverters;

    public CellDescriptionConverter(ITableIdProvider tableIdProvider, IObjectService objectService, List<ICustomCellConverter> customCellConverters) {
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.objectService = Objects.requireNonNull(objectService);
        this.customCellConverters = Objects.requireNonNull(customCellConverters);
    }

    public List<ICellDescription> convert(List<org.eclipse.sirius.components.view.table.CellDescription> cellDescriptions, AQLInterpreter interpreter) {
        return cellDescriptions.stream()
                .map(cellDescription -> this.convert(cellDescription, interpreter))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }


    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private Optional<ICellDescription> convert(org.eclipse.sirius.components.view.table.CellDescription viewCellDescription, AQLInterpreter interpreter) {
        Optional<ICellDescription> optionalICellDescription = Optional.empty();

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            Optional<Object> optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
            if (viewCellDescription.getSelectedTargetObjectExpression() != null && !viewCellDescription.getSelectedTargetObjectExpression().isBlank()) {
                optionalSelf = interpreter.evaluateExpression(variableManager.getVariables(), viewCellDescription.getSelectedTargetObjectExpression()).asObject();
            }
            return optionalSelf
                    .map(this.objectService::getId)
                    .orElse("");
        };

        Function<VariableManager, String> targetObjectKindProvider = variableManager -> {
            Optional<Object> optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
            if (viewCellDescription.getSelectedTargetObjectExpression() != null && !viewCellDescription.getSelectedTargetObjectExpression().isBlank()) {
                optionalSelf = interpreter.evaluateExpression(variableManager.getVariables(), viewCellDescription.getSelectedTargetObjectExpression()).asObject();
            }
            return optionalSelf
                    .map(this.objectService::getKind)
                    .orElse("");
        };

        Predicate<VariableManager> canCreatePredicate =
                variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), viewCellDescription.getPreconditionExpression()).asBoolean().orElse(false);

        BiFunction<VariableManager, Object, String> cellValueProvider = (variableManager, columnTargetObject) -> {
            var child = variableManager.createChild();
            child.put("columnTargetObject", columnTargetObject);
            return this.evaluateString(interpreter, child, viewCellDescription.getValueExpression());
        };

        if (viewCellDescription.getCellWidgetDescription() instanceof CellTextfieldWidgetDescription cellTextfieldWidgetDescription) {
            optionalICellDescription = Optional.of(TextfieldCellDescription.newTextfieldCellDescription(this.tableIdProvider.getId(viewCellDescription))
                    .targetObjectIdProvider(targetObjectIdProvider)
                    .targetObjectKindProvider(targetObjectKindProvider)
                    .canCreatePredicate(canCreatePredicate)
                    .cellValueProvider(cellValueProvider)
                    .build());
        } else if (viewCellDescription.getCellWidgetDescription() instanceof CellLabelWidgetDescription cellLabelWidgetDescription) {
            optionalICellDescription = Optional.of(IconLabelCellDescription.newIconLabelCellDescription(this.tableIdProvider.getId(viewCellDescription))
                    .targetObjectIdProvider(targetObjectIdProvider)
                    .targetObjectKindProvider(targetObjectKindProvider)
                    .canCreatePredicate(canCreatePredicate)
                    .cellValueProvider(cellValueProvider)
                    .cellIconURLsProvider((variableManager, columnTargetObject) -> {
                        var child = variableManager.createChild();
                        child.put("columnTargetObject", columnTargetObject);
                        return new ViewIconURLsProvider(interpreter, cellLabelWidgetDescription.getIconExpression()).apply(child);
                    })
                    .build());
        } else if (viewCellDescription.getCellWidgetDescription() instanceof CellTextareaWidgetDescription) {
            optionalICellDescription = Optional.of(TextareaCellDescription.newTextareaCellDescription(this.tableIdProvider.getId(viewCellDescription))
                    .targetObjectIdProvider(targetObjectIdProvider)
                    .targetObjectKindProvider(targetObjectKindProvider)
                    .canCreatePredicate(canCreatePredicate)
                    .cellValueProvider(cellValueProvider)
                    .build());
        } else {
            optionalICellDescription = this.customCellConverters.stream()
                    .map(converter -> converter.convert(viewCellDescription, interpreter, this.tableIdProvider, this.objectService))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findFirst();
        }
        return optionalICellDescription;
    }


    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse("");
    }
}
