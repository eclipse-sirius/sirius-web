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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.tables.api.IEditCellHandler;
import org.eclipse.sirius.components.core.api.IEditService;
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
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.form.LabelDescription;
import org.eclipse.sirius.components.view.form.TextfieldDescription;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.CellWidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Edit cell generic handler for all tables defined using the view table DSL.
 *
 * @author Jerome Gout
 */
@Service
public class ViewEditCellEventHandler implements IEditCellHandler {

    private final IFeedbackMessageService feedbackMessageService;

    private final IEditService editService;

    private final IObjectSearchService objectSearchService;

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final ITableIdProvider tableIdProvider;

    public ViewEditCellEventHandler(IFeedbackMessageService feedbackMessageService, IEditService editService, IObjectSearchService objectSearchService,
            ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate,
            IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IViewAQLInterpreterFactory aqlInterpreterFactory, ITableIdProvider tableIdProvider) {
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.editService = Objects.requireNonNull(editService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
    }

    @Override
    public boolean canHandle(TableDescription tableDescription) {
        return this.viewRepresentationDescriptionPredicate.test(tableDescription);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TableDescription tableDescription, ICell cell, Line row, Column column, Object newValue) {
        var optionalTableDescription = this.viewRepresentationDescriptionSearchService
                .findById(editingContext, tableDescription.getId())
                .filter(org.eclipse.sirius.components.view.table.TableDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.table.TableDescription.class::cast);
        if (optionalTableDescription.isPresent()) {
            var viewTableDescription = optionalTableDescription.get();
            AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewTableDescription.eContainer());

            VariableManager variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put("cell", cell);
            variableManager.put("row", row);
            variableManager.put("column", column);
            variableManager.put("newValue", newValue);
            var optionalRowSemanticObject = this.objectSearchService.getObject(editingContext, row.getTargetObjectId());
            if (optionalRowSemanticObject.isPresent()) {
                variableManager.put(VariableManager.SELF, optionalRowSemanticObject.get());

                String cellDescriptionId = ""; // TODO how to retrieve cellDescriptionId, from graphQL mutation?
                CellDescription viewCellDescription = viewTableDescription.getCellDescriptions().stream()
                        .filter(cellDescription -> cellDescriptionId.equals(this.tableIdProvider.getId(cellDescription)))
                        .findFirst()
                        .orElse(null);
                if (viewCellDescription != null) {
                    return this.executeCellWidgetDescription(variableManager, interpreter, viewCellDescription.getCellWidgetDescription());
                }
            }
        }
        return this.buildFailureWithFeedbackMessages("Something went wrong while handling the context menu action");
    }

    private IStatus executeCellWidgetDescription(VariableManager variableManager, AQLInterpreter interpreter, CellWidgetDescription cellWidgetDescription) {
        IStatus status = this.buildFailureWithFeedbackMessages("Not supported cell widget");
        if (cellWidgetDescription instanceof TextfieldDescription textfieldDescription) {
            status = this.executeOperations(variableManager, interpreter, textfieldDescription.getBody());
        } else if (cellWidgetDescription instanceof LabelDescription) {
            status = this.buildSuccessWithSemanticChangeAndFeedbackMessages();
        }

        return status;
    }

    private IStatus executeOperations(VariableManager variableManager, AQLInterpreter interpreter, List<Operation> operations) {
        OperationInterpreter operationInterpreter = new OperationInterpreter(interpreter, this.editService);
        Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
        if (optionalVariableManager.isEmpty()) {
            return this.buildFailureWithFeedbackMessages("Something went wrong while handling the cell edit operations");
        } else {
            return this.buildSuccessWithSemanticChangeAndFeedbackMessages();
        }
    }

    private Failure buildFailureWithFeedbackMessages(String technicalMessage) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(technicalMessage, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }

    private Success buildSuccessWithSemanticChangeAndFeedbackMessages() {
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }
}
