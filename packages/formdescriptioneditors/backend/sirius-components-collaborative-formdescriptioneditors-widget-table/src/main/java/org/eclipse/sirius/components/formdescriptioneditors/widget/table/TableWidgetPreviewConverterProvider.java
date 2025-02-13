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
package org.eclipse.sirius.components.formdescriptioneditors.widget.table;

import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetPreviewConverterProvider;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.TableWidgetDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.PaginatedData;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.view.widget.tablewidget.util.TableWidgetSwitch;
import org.springframework.stereotype.Service;

/**
 * Provides the widget converter needed for the table widget preview in the context of a Form Description Editor.
 *
 * @author frouene
 */
@Service
public class TableWidgetPreviewConverterProvider implements IWidgetPreviewConverterProvider {

    @Override
    public Switch<AbstractWidgetDescription> getWidgetConverter(FormDescriptionEditorDescription formDescriptionEditorDescription, VariableManager variableManager) {
        return new TableWidgetSwitch<>() {
            @Override
            public AbstractWidgetDescription caseTableWidgetDescription(org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription viewTableDescription) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VariableManager.SELF, viewTableDescription);
                String id = formDescriptionEditorDescription.getTargetObjectIdProvider().apply(childVariableManager);
                return TableWidgetPreviewConverterProvider.this.getTableWidgetDescription(viewTableDescription, id);
            }
        };
    }

    private TableWidgetDescription getTableWidgetDescription(org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription viewTableDescription, String id) {
        var lineDescription = LineDescription.newLineDescription(UUID.randomUUID().toString())
                .targetObjectIdProvider(vm -> "")
                .targetObjectKindProvider(vm -> "")
                .semanticElementsProvider(vm -> new PaginatedData(List.of(), false, false, 0))
                .headerLabelProvider(vm -> "")
                .headerIconURLsProvider(vm -> List.of())
                .headerIndexLabelProvider(vm -> "")
                .isResizablePredicate(vm -> false)
                .initialHeightProvider(vm -> -1)
                .depthLevelProvider(vm -> 0)
                .build();
        var tableDescription = TableDescription.newTableDescription(UUID.randomUUID().toString())
                .label("")
                .labelProvider(vm -> this.getWidgetLabel(viewTableDescription, "Table"))
                .canCreatePredicate(vm -> true)
                .targetObjectIdProvider(vm -> "")
                .targetObjectKindProvider(vm -> "")
                .isStripeRowPredicate(vm -> false)
                .iconURLsProvider(vm -> List.of())
                .columnDescriptions(List.of())
                .cellDescriptions(List.of())
                .lineDescription(lineDescription)
                .build();
        return TableWidgetDescription.newTableWidgetDescription(UUID.randomUUID().toString())
                .idProvider(vm -> id)
                .targetObjectIdProvider(vm -> "")
                .labelProvider(vm -> this.getWidgetLabel(viewTableDescription, "Table"))
                .diagnosticsProvider(vm -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .helpTextProvider(vm -> "")
                .tableDescription(tableDescription)
                .build();
    }

    public String getWidgetLabel(org.eclipse.sirius.components.view.form.WidgetDescription widgetDescription, String defaultLabel) {
        String widgetLabel = defaultLabel;
        String name = widgetDescription.getName();
        String labelExpression = widgetDescription.getLabelExpression();
        if (labelExpression != null && !labelExpression.isBlank() && !labelExpression.startsWith("aql:")) {
            widgetLabel = labelExpression;
        } else if (name != null && !name.isBlank()) {
            widgetLabel = name;
        }
        return widgetLabel;
    }
}
