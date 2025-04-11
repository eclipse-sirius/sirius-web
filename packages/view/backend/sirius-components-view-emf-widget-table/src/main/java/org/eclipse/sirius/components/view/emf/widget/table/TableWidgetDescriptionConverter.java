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
package org.eclipse.sirius.components.view.emf.widget.table;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.table.CellDescriptionConverter;
import org.eclipse.sirius.components.view.emf.table.ColumnDescriptionConverter;
import org.eclipse.sirius.components.view.emf.table.ITableIdProvider;
import org.eclipse.sirius.components.view.emf.table.RowDescriptionConverter;
import org.eclipse.sirius.components.view.emf.table.api.ICustomCellConverter;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.widget.table.TableWidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based TableWidgetDescription into its API equivalent.
 *
 * @author frouene
 */
@Service
public class TableWidgetDescriptionConverter implements IWidgetDescriptionConverter {

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    private final ITableIdProvider tableIdProvider;

    private final List<ICustomCellConverter> customCellConverters;

    public TableWidgetDescriptionConverter(IObjectService objectService, IFormIdProvider widgetIdProvider, ITableIdProvider tableIdProvider, List<ICustomCellConverter> customCellConverters) {
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.customCellConverters = Objects.requireNonNull(customCellConverters);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription viewTableWidgetDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewTableWidgetDescription);

            Function<VariableManager, String> targetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.objectService::getId)
                    .orElse("");
            WidgetIdProvider idProvider = new WidgetIdProvider();
            StringValueProvider labelProvider = new StringValueProvider(interpreter, Optional.ofNullable(viewTableWidgetDescription.getLabelExpression()).orElse(""));
            Predicate<VariableManager> isStripeRowPredicate = variableManager -> {
                if (viewTableWidgetDescription.getUseStripedRowsExpression() != null) {
                    return interpreter.evaluateExpression(variableManager.getVariables(), viewTableWidgetDescription.getUseStripedRowsExpression()).asBoolean().orElse(false);
                }
                return false;
            };

            var tableDescription = TableDescription.newTableDescription(descriptionId)
                    .label("")
                    .labelProvider(labelProvider)
                    .canCreatePredicate(variableManager -> true)
                    .targetObjectIdProvider(targetIdProvider)
                    .targetObjectKindProvider(variableManager -> "")
                    .isStripeRowPredicate(isStripeRowPredicate)
                    .iconURLsProvider(variableManager -> List.of())
                    .columnDescriptions(new ColumnDescriptionConverter(this.tableIdProvider, targetIdProvider, variableManager -> "").convert(viewTableWidgetDescription.getColumnDescriptions(), interpreter))
                    .cellDescriptions(new CellDescriptionConverter(this.tableIdProvider, this.objectService, this.customCellConverters).convert(viewTableWidgetDescription.getCellDescriptions(), interpreter))
                    .lineDescription(new RowDescriptionConverter(this.tableIdProvider, targetIdProvider, variableManager -> "").convert(viewTableWidgetDescription.getRowDescription(), interpreter))
                    .enableSubRows(false)
                    .pageSizeOptionsProvider(variableManager -> List.of(5, 10, 20, 50))
                    .defaultPageSizeIndexProvider(variableManager -> 1)
                    .build();

            var tableWidgetDescription = TableWidgetDescription.newTableWidgetDescription(descriptionId)
                    .targetObjectIdProvider(targetIdProvider)
                    .idProvider(idProvider)
                    .labelProvider(labelProvider)
                    .tableDescription(tableDescription)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewTableWidgetDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewTableWidgetDescription.getHelpExpression()))
                    .isReadOnlyProvider(new ReadOnlyValueProvider(interpreter, viewTableWidgetDescription.getIsEnabledExpression()))
                    .build();
            return Optional.of(tableWidgetDescription);
        }
        return Optional.empty();
    }
}
