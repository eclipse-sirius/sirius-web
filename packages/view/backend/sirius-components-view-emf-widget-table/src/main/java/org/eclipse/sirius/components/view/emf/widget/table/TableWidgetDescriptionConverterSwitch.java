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
import org.eclipse.sirius.components.forms.description.TableWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.table.CellDescriptionConverter;
import org.eclipse.sirius.components.view.emf.table.ColumnDescriptionConverter;
import org.eclipse.sirius.components.view.emf.table.ITableIdProvider;
import org.eclipse.sirius.components.view.emf.table.RowDescriptionConverter;
import org.eclipse.sirius.components.view.widget.tablewidget.util.TableWidgetSwitch;

/**
 * Converts a View-based TableWidgetDescription into its API equivalent.
 *
 * @author frouene
 */
public class TableWidgetDescriptionConverterSwitch extends TableWidgetSwitch<Optional<AbstractWidgetDescription>> {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final IFormIdProvider widgetIdProvider;

    private final ITableIdProvider tableIdProvider;


    public TableWidgetDescriptionConverterSwitch(AQLInterpreter interpreter, IObjectService objectService,
            IFormIdProvider widgetIdProvider, ITableIdProvider tableIdProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.semanticTargetIdProvider = variableManager -> {
            Optional<Object> optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
            return optionalSelf
                    .map(this.objectService::getId)
                    .orElseGet(() -> optionalSelf.map(Object::toString).orElse(""));
        };
    }

    @Override
    public Optional<AbstractWidgetDescription> caseTableWidgetDescription(org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription viewTableWidgetDescription) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewTableWidgetDescription);
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewTableWidgetDescription.getLabelExpression());
        Predicate<VariableManager> isStripeRowPredicate = variableManager -> {
            if (viewTableWidgetDescription.getUseStripedRowsExpression() != null) {
                return this.interpreter.evaluateExpression(variableManager.getVariables(), viewTableWidgetDescription.getUseStripedRowsExpression()).asBoolean().orElse(false);
            }
            return false;
        };
        var tableDescription = TableDescription.newTableDescription(descriptionId)
                .label("")
                .labelProvider(labelProvider)
                .canCreatePredicate(variableManager -> true)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(variableManager -> "")
                .isStripeRowPredicate(isStripeRowPredicate)
                .iconURLsProvider(variableManager -> List.of())
                .columnDescriptions(new ColumnDescriptionConverter(this.tableIdProvider, this.semanticTargetIdProvider,
                        variableManager -> "").convert(viewTableWidgetDescription.getColumnDescriptions(), this.interpreter))
                .cellDescriptions(new CellDescriptionConverter(this.tableIdProvider, this.objectService).convert(viewTableWidgetDescription.getCellDescriptions(), this.interpreter))
                .lineDescription(new RowDescriptionConverter(this.tableIdProvider, this.semanticTargetIdProvider, variableManager -> "").convert(viewTableWidgetDescription.getRowDescription(),
                        this.interpreter))
                .enableSubRows(false)
                .pageSizeOptionsProvider(variableManager -> List.of(5, 10, 20, 50))
                .defaultPageSizeIndexProvider(variableManager -> 1)
                .build();
        var tableWidgetDescription = TableWidgetDescription.newTableWidgetDescription(descriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .tableDescription(tableDescription)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewTableWidgetDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewTableWidgetDescription.getHelpExpression()))
                .isReadOnlyProvider(new ReadOnlyValueProvider(this.interpreter, viewTableWidgetDescription.getIsEnabledExpression()))
                .build();
        return Optional.of(tableWidgetDescription);
    }

    private StringValueProvider getStringValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return new StringValueProvider(this.interpreter, safeValueExpression);
    }
}
