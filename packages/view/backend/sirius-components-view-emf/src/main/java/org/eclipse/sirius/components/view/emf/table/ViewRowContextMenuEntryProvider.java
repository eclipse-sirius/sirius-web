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

package org.eclipse.sirius.components.view.emf.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.tables.api.IRowContextMenuEntryProvider;
import org.eclipse.sirius.components.collaborative.tables.dto.RowContextMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.springframework.stereotype.Service;

/**
 * Table row context menu entries provider for view table model.
 *
 * @author Jerome Gout
 */
@Service
public class ViewRowContextMenuEntryProvider implements IRowContextMenuEntryProvider {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final IObjectSearchService objectSearchService;

    private final Function<EObject, UUID> idProvider = (eObject) -> UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());

    public ViewRowContextMenuEntryProvider(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate,
            IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IViewAQLInterpreterFactory aqlInterpreterFactory, IObjectSearchService objectSearchService) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TableDescription tableDescription, Table table, Line row) {
        return this.viewRepresentationDescriptionPredicate.test(tableDescription);
    }

    @Override
    public List<RowContextMenuEntry> getRowContextMenuEntries(IEditingContext editingContext, TableDescription tableDescription, Table table, Line row) {
        var optionalTableDescription = this.viewRepresentationDescriptionSearchService
                .findById(editingContext, tableDescription.getId())
                .filter(org.eclipse.sirius.components.view.table.TableDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.table.TableDescription.class::cast);
        if (optionalTableDescription.isPresent()) {
            var viewTableDescription = optionalTableDescription.get();

            AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewTableDescription.eContainer());

            VariableManager variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(TableDescription.TABLE, table);
            variableManager.put(LineDescription.SELECTED_ROW, row);
            variableManager.put(LineDescription.ID, row.getId());
            var optionalSemanticObject = this.objectSearchService.getObject(editingContext, row.getTargetObjectId());
            if (optionalSemanticObject.isPresent()) {
                var semanticObject = optionalSemanticObject.get();
                variableManager.put(VariableManager.SELF, semanticObject);
                return viewTableDescription.getRowDescription().getContextMenuEntries().stream()
                        .filter(viewAction -> this.isValidActionPrecondition(viewAction, variableManager, interpreter))
                        .map(rowContextMenuEntry -> this.convertContextAction(rowContextMenuEntry, variableManager, interpreter))
                        .toList();
            }
        }
        return List.of();
    }

    private boolean isValidActionPrecondition(org.eclipse.sirius.components.view.table.RowContextMenuEntry viewContextAction, VariableManager variableManager, AQLInterpreter interpreter) {
        var precondition = viewContextAction.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            return this.evaluateBoolean(variableManager, interpreter, precondition);
        }
        return false;
    }

    private RowContextMenuEntry convertContextAction(org.eclipse.sirius.components.view.table.RowContextMenuEntry viewTreeItemContextAction, VariableManager variableManager, AQLInterpreter interpreter) {
        var id = this.idProvider.apply(viewTreeItemContextAction).toString();
        var label = this.evaluateString(variableManager, interpreter, viewTreeItemContextAction.getLabelExpression());
        var iconURL = this.evaluateStringList(variableManager, interpreter, viewTreeItemContextAction.getIconURLExpression());

        return new RowContextMenuEntry(id, label, iconURL);
    }

    private String evaluateString(VariableManager variableManager, AQLInterpreter interpreter, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asString()
                .orElse("");
    }

    private List<String> evaluateStringList(VariableManager variableManager, AQLInterpreter interpreter, String expression) {
        List<String> values = new ArrayList<>();
        if (expression != null && !expression.isBlank()) {
            Optional<List<Object>> optionalResult = interpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects();
            if (optionalResult.isPresent()) {
                values = optionalResult.get().stream().filter(String.class::isInstance).map(String.class::cast).toList();
            }
        }
        return values;
    }

    private Boolean evaluateBoolean(VariableManager variableManager, AQLInterpreter interpreter, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asBoolean()
                .orElse(true);
    }
}
