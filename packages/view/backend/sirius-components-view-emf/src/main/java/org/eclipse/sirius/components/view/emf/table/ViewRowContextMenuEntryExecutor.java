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
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.tables.api.IRowContextMenuEntryExecutor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.springframework.stereotype.Service;

/**
 * This class is in charge of delegate row context menu entry invocation for table view DSL.
 *
 * @author Jerome Gout
 */
@Service
public class ViewRowContextMenuEntryExecutor implements IRowContextMenuEntryExecutor {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final IFeedbackMessageService feedbackMessageService;

    private final IOperationExecutor operationExecutor;

    private final IObjectSearchService objectSearchService;

    public ViewRowContextMenuEntryExecutor(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService,
                                           IViewAQLInterpreterFactory aqlInterpreterFactory, IFeedbackMessageService feedbackMessageService, IOperationExecutor operationExecutor, IObjectSearchService objectSearchService) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public boolean canExecute(IEditingContext editingContext, TableDescription tableDescription, Table table, Line row, String rowMenuContextEntryId) {
        return this.viewRepresentationDescriptionPredicate.test(tableDescription);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, TableDescription tableDescription, Table table, Line row, String rowMenuContextEntryId) {
        var optionalTableDescription = this.viewRepresentationDescriptionSearchService
                .findById(editingContext, tableDescription.getId())
                .filter(org.eclipse.sirius.components.view.table.TableDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.table.TableDescription.class::cast);
        if (optionalTableDescription.isPresent()) {
            var viewTableDescription = optionalTableDescription.get();

            if (viewTableDescription.eContainer() instanceof View view) {
                AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, view);

                VariableManager variableManager = new VariableManager();
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put(TableDescription.TABLE, table);
                variableManager.put(LineDescription.SELECTED_ROW, row);
                var optionalSemanticObject = this.objectSearchService.getObject(editingContext, row.getTargetObjectId());
                if (optionalSemanticObject.isPresent()) {
                    var semanticObject = optionalSemanticObject.get();
                    variableManager.put(VariableManager.SELF, semanticObject);
                }
                var contextMenuEntry = viewTableDescription.getRowDescription().getContextMenuEntries().stream()
                        .filter(entry -> Objects.equals(rowMenuContextEntryId, UUID.nameUUIDFromBytes(EcoreUtil.getURI(entry).toString().getBytes()).toString()))
                        .findFirst();
                if (contextMenuEntry.isPresent()) {
                    return this.executeOperations(variableManager, interpreter, contextMenuEntry.get().getBody());
                }
            }
        }
        return this.buildFailureWithFeedbackMessages("Something went wrong while handling the context menu action");
    }

    private IStatus executeOperations(VariableManager variableManager, AQLInterpreter interpreter, List<Operation> operations) {
        var result = this.operationExecutor.execute(interpreter, variableManager, operations);
        if (result.status() == OperationExecutionStatus.FAILURE) {
            return this.buildFailureWithFeedbackMessages("Something went wrong while handling the context menu action");
        }
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private Failure buildFailureWithFeedbackMessages(String message) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(message, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }
}
