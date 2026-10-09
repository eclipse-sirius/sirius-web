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
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetAddElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.api.ReferenceWidgetVariables;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.form.api.IViewFormDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Service to add chosen elements from the reference widget to the specified reference in the context of a representation described with the View DSL.
 *
 * @author tgiraudet
 */
@Service
public class ViewReferenceWidgetAddElementHandler implements IReferenceWidgetAddElementHandler {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewFormDescriptionSearchService viewFormDescriptionSearchService;

    private final IObjectSearchService objectSearchService;

    private final IOperationExecutor operationExecutor;

    private final IViewAQLInterpreterFactory viewAQLInterpreterFactory;

    private final IFeedbackMessageService feedbackMessageService;

    private final IReferenceMessageService referenceMessageService;

    public ViewReferenceWidgetAddElementHandler(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewFormDescriptionSearchService viewFormDescriptionSearchService,
            IObjectSearchService objectSearchService,
            IOperationExecutor operationExecutor, IViewAQLInterpreterFactory viewAQLInterpreterFactory, IFeedbackMessageService feedbackMessageService,
            IReferenceMessageService referenceMessageService) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewFormDescriptionSearchService = Objects.requireNonNull(viewFormDescriptionSearchService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.viewAQLInterpreterFactory = Objects.requireNonNull(viewAQLInterpreterFactory);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.referenceMessageService = Objects.requireNonNull(referenceMessageService);
    }

    @Override
    public boolean canHandle(FormDescription formDescription) {
        return this.viewRepresentationDescriptionPredicate.test(formDescription);
    }

    @Override
    public IStatus add(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, List<String> newValueIds) {
        var optionalWidgetDescription = this.findReferenceWidgetDescription(editingContext, referenceWidget.getDescriptionId());
        var optionalOwner = this.objectSearchService.getObject(editingContext, referenceWidget.getOwnerId());
        var newValues = newValueIds.stream().flatMap(id -> this.flatMapAndLogMessageIfAbsent(editingContext, id)).toList();
        IStatus result = new Failure(this.referenceMessageService.unableToExecuteAddAction());

        if (optionalWidgetDescription.isPresent() && optionalOwner.isPresent() && optionalOwner.get() instanceof EObject owner) {
            var widgetDescription = optionalWidgetDescription.get();
            var body = widgetDescription.getAddBody();
            var feature = owner.eClass().getEStructuralFeature(referenceWidget.getReferenceName());

            if (feature instanceof EReference reference) {
                if (body != null && EcoreUtil.getRootContainer(widgetDescription) instanceof View view) {
                    // If we have a body then the behavior is the interpretation of the body
                    result = this.addFromOperation(editingContext, view, owner, reference, newValues, body.getBody());
                } else {
                    // When the reference widget doesn't provide any body, we fall back to the default behavior
                    result = this.defaultAdd(owner, reference, newValues);
                }
            } else {
                result = new Failure(this.referenceMessageService.referenceNotFound(referenceWidget.getReferenceName(), owner.eClass().getName()));
            }
        }

        return result;
    }

    private IStatus addFromOperation(IEditingContext editingContext, View view, EObject owner, EReference reference, List<Object> selectedObjects, List<Operation> operations) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);
        variableManager.put(RepresentationVariables.SELF.name(), owner);
        variableManager.put(ReferenceWidgetVariables.REFERENCE.name(), reference);
        variableManager.put(ReferenceWidgetVariables.NEW_VALUES.name(), selectedObjects);

        var interpreter = this.viewAQLInterpreterFactory.createInterpreter(editingContext, view);
        var result = this.operationExecutor.execute(interpreter, variableManager, operations);
        if (result.status() == OperationExecutionStatus.FAILURE) {
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message(this.referenceMessageService.unableToSetReferenceValue(), MessageLevel.ERROR));
            errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
            return new Failure(errorMessages);
        }

        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private IStatus defaultAdd(EObject owner, EReference reference, List<Object> newValues) {
        ((List<Object>) owner.eGet(reference)).addAll(newValues);
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private Stream<Object> flatMapAndLogMessageIfAbsent(IEditingContext editingContext, String newValueId) {
        var optionalObject = this.objectSearchService.getObject(editingContext, newValueId);
        if (optionalObject.isEmpty()) {
            this.feedbackMessageService.addFeedbackMessage(new Message(this.referenceMessageService.objectNotFound(newValueId), MessageLevel.WARNING));
        }
        return optionalObject.stream();
    }

    private Optional<ReferenceWidgetDescription> findReferenceWidgetDescription(IEditingContext editingContext, String descriptionId) {
        return this.viewFormDescriptionSearchService.findFormElementDescriptionById(editingContext, descriptionId)
                .filter(ReferenceWidgetDescription.class::isInstance)
                .map(ReferenceWidgetDescription.class::cast);
    }
}
