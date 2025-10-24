/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

package org.eclipse.sirius.web.application.table.customcells;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.eclipse.sirius.components.view.emf.table.ITableIdProvider;
import org.eclipse.sirius.components.view.emf.table.api.ICustomCellConverter;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based checkbox cell description into an equivalent ICellDescription.
 *
 * @author Jerome Gout
 */
@Service
public class CheckboxCellConverter implements ICustomCellConverter {

    private final IIdentityService identityService;

    private final ITableIdProvider tableIdProvider;

    public CheckboxCellConverter(IIdentityService identityService, ITableIdProvider tableIdProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
    }

    @Override
    public Optional<ICellDescription> convert(CellDescription viewCellDescription, AQLInterpreter interpreter) {
        if (viewCellDescription.getCellWidgetDescription() instanceof CellCheckboxWidgetDescription) {
            Function<VariableManager, String> targetObjectIdProvider = variableManager -> this.getSelf(viewCellDescription, interpreter, variableManager)
                    .map(this.identityService::getId)
                    .orElse("");

            Function<VariableManager, String> targetObjectKindProvider = variableManager -> this.getSelf(viewCellDescription, interpreter, variableManager)
                    .map(this.identityService::getKind)
                    .orElse("");

            Predicate<VariableManager> canCreatePredicate =
                    variableManager -> interpreter.evaluateExpression(variableManager.getVariables(), viewCellDescription.getPreconditionExpression()).asBoolean().orElse(false);

            BiFunction<VariableManager, Object, Boolean> cellValueProvider = (variableManager, columnTargetObject) -> {
                var child = variableManager.createChild();
                child.put("columnTargetObject", columnTargetObject);
                return interpreter.evaluateExpression(variableManager.getVariables(), viewCellDescription.getValueExpression())
                        .asBoolean()
                        .orElse(false);
            };

            BiFunction<VariableManager, Object, String> cellTooltipValueProvider = (variableManager, columnTargetObject) -> {
                var child = variableManager.createChild();
                child.put("columnTargetObject", columnTargetObject);
                return interpreter.evaluateExpression(variableManager.getVariables(), viewCellDescription.getTooltipExpression()).asString().orElse("");
            };

            return Optional.of(CheckboxCellDescription.newCheckboxCellDescription(tableIdProvider.getId(viewCellDescription))
                    .targetObjectIdProvider(targetObjectIdProvider)
                    .targetObjectKindProvider(targetObjectKindProvider)
                    .canCreatePredicate(canCreatePredicate)
                    .cellValueProvider(cellValueProvider)
                    .cellTooltipValueProvider(cellTooltipValueProvider)
                    .build());
        }
        return Optional.empty();
    }

    private Optional<Object> getSelf(CellDescription viewCellDescription, AQLInterpreter interpreter, VariableManager variableManager) {
        Optional<Object> optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
        if (viewCellDescription.getSelectedTargetObjectExpression() != null && !viewCellDescription.getSelectedTargetObjectExpression().isBlank()) {
            optionalSelf = interpreter.evaluateExpression(variableManager.getVariables(), viewCellDescription.getSelectedTargetObjectExpression()).asObject();
        }
        return optionalSelf;
    }
}
