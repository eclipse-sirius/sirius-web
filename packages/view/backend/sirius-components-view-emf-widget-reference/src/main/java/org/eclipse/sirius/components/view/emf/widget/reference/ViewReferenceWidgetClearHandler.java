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
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetClearHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.form.api.IViewFormDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Handles clear operations declared by View reference widgets.
 *
 * @author tgiraudet
 */
@Service
public class ViewReferenceWidgetClearHandler implements IReferenceWidgetClearHandler {

    private static final String REFERENCE_NAME = "referenceName";

    private static final String REFERENCE_OWNER = "referenceOwner";

    private final IObjectSearchService objectSearchService;

    private final IViewFormDescriptionSearchService viewFormDescriptionSearchService;

    private final IViewAQLInterpreterFactory viewAQLInterpreterFactory;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IReferenceMessageService messageService;

    public ViewReferenceWidgetClearHandler(IObjectSearchService objectSearchService, IViewFormDescriptionSearchService viewFormDescriptionSearchService,
            IViewAQLInterpreterFactory viewAQLInterpreterFactory, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IReferenceMessageService messageService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.viewFormDescriptionSearchService = Objects.requireNonNull(viewFormDescriptionSearchService);
        this.viewAQLInterpreterFactory = Objects.requireNonNull(viewAQLInterpreterFactory);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String descriptionId) {
        return this.findReferenceWidgetDescription(editingContext, descriptionId).isPresent();
    }

    @Override
    public IStatus clear(IEditingContext editingContext, ReferenceWidget referenceWidget) {
        var optionalReferenceWidgetDescription = this.findReferenceWidgetDescription(editingContext, referenceWidget.getDescriptionId());
        return optionalReferenceWidgetDescription
                .map(referenceWidgetDescription -> this.execute(editingContext, referenceWidget, referenceWidgetDescription))
                .orElseGet(() -> new Failure(this.messageService.failedToExecuteClearReferenceAction()));
    }

    private IStatus execute(IEditingContext editingContext, ReferenceWidget referenceWidget, ReferenceWidgetDescription referenceWidgetDescription) {
        if (referenceWidgetDescription.getClearButton() == null || referenceWidgetDescription.getClearButton().getBody().isEmpty()) {
            return new Success();
        }

        IStatus status = new Failure(this.messageService.failedToExecuteClearReferenceAction());

        if (EcoreUtil.getRootContainer(referenceWidgetDescription) instanceof View view) {
            Optional<EObject> optionalReferenceOwner = this.objectSearchService.getObject(editingContext, referenceWidget.getOwnerId())
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast);

            if (optionalReferenceOwner.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(RepresentationVariables.SELF.name(), optionalReferenceOwner.get());
                variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);
                variableManager.put(REFERENCE_NAME, referenceWidget.getReferenceName());
                variableManager.put(REFERENCE_OWNER, optionalReferenceOwner.get());

                var result = this.operationExecutor.execute(this.viewAQLInterpreterFactory.createInterpreter(editingContext, view), variableManager,
                        referenceWidgetDescription.getClearButton().getBody());
                if (result.status() == OperationExecutionStatus.FAILURE) {
                    List<Message> errorMessages = new ArrayList<>();
                    errorMessages.add(new Message(this.messageService.failedToExecuteClearReferenceAction(), MessageLevel.ERROR));
                    errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
                    status = new Failure(errorMessages);
                } else {
                    status = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
                }
            }
        }
        return status;
    }

    private Optional<ReferenceWidgetDescription> findReferenceWidgetDescription(IEditingContext editingContext, String descriptionId) {
        return this.viewFormDescriptionSearchService.findFormElementDescriptionById(editingContext, descriptionId)
                .filter(ReferenceWidgetDescription.class::isInstance)
                .map(ReferenceWidgetDescription.class::cast);
    }
}
