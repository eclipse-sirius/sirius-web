/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.view.emf.widget.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.ReferenceWidgetVariables;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IViewReferenceWidgetRemoveOperationExecutor;
import org.springframework.stereotype.Service;

/**
 * Executes the removal body of a View-based reference widget.
 *
 * @author tgiraudet
 */
@Service
public class ViewReferenceWidgetRemoveOperationExecutor implements IViewReferenceWidgetRemoveOperationExecutor {

    private final IViewAQLInterpreterFactory viewAQLInterpreterFactory;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IReferenceMessageService messageService;

    public ViewReferenceWidgetRemoveOperationExecutor(IViewAQLInterpreterFactory viewAQLInterpreterFactory, IOperationExecutor operationExecutor,
            IFeedbackMessageService feedbackMessageService, IReferenceMessageService messageService) {
        this.viewAQLInterpreterFactory = Objects.requireNonNull(viewAQLInterpreterFactory);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, View view, Object self, EObject owner, EReference reference, Object valueToRemove, List<Operation> operations) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);
        variableManager.put(RepresentationVariables.SELF.name(), self);
        variableManager.put(ReferenceWidgetVariables.REFERENCE_OWNER.name(), owner);
        variableManager.put(ReferenceWidgetVariables.REFERENCE.name(), reference);
        variableManager.put(ReferenceWidgetVariables.VALUE_TO_REMOVE.name(), valueToRemove);

        var interpreter = this.viewAQLInterpreterFactory.createInterpreter(editingContext, view);
        var result = this.operationExecutor.execute(interpreter, variableManager, operations);
        if (result.status() == OperationExecutionStatus.FAILURE) {
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message(this.messageService.unableToRemoveReferenceValue(), MessageLevel.ERROR));
            errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
            return new Failure(errorMessages);
        }

        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }
}
