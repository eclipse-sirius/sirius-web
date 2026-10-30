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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetSetElementHandler;
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
 * Service to set a chosen element from the reference widget to the specified reference in the context of a representation described with the View DSL.
 *
 * @author tgiraudet
 */
@Service
public class ViewReferenceWidgetSetElementHandler implements IReferenceWidgetSetElementHandler {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewFormDescriptionSearchService viewFormDescriptionSearchService;

    private final IObjectSearchService objectSearchService;

    private final IOperationExecutor operationExecutor;

    private final IViewAQLInterpreterFactory viewAQLInterpreterFactory;

    private final IFeedbackMessageService feedbackMessageService;

    private final IReferenceMessageService referenceMessageService;

    public ViewReferenceWidgetSetElementHandler(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewFormDescriptionSearchService viewFormDescriptionSearchService,
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
    public boolean canHandle(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget) {
        return this.findReferenceWidgetDescription(editingContext, referenceWidget.getDescriptionId()).isPresent();
    }

    @Override
    public IStatus set(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, String newValueId) {
        var optionalWidgetDescription = this.findReferenceWidgetDescription(editingContext, referenceWidget.getDescriptionId());
        if (optionalWidgetDescription.isPresent() && optionalWidgetDescription.get().getSet() == null) {
            return new Success();
        }
        var optionalOwner = this.objectSearchService.getObject(editingContext, referenceWidget.getOwnerId());
        Optional<Object> optionalSelf = this.objectSearchService.getObject(editingContext, referenceWidget.getTargetObjectId());
        var optionalNewValue = this.objectSearchService.getObject(editingContext, newValueId);
        IStatus result = new Failure(this.referenceMessageService.unableToExecuteSetAction());
        if (optionalNewValue.isEmpty()) {
            result = new Failure(this.referenceMessageService.invalidIds());
        } else if (optionalWidgetDescription.isPresent() && optionalOwner.isPresent() && optionalSelf.isPresent() && optionalOwner.get() instanceof EObject owner) {
            var widgetDescription = optionalWidgetDescription.get();
            var body = widgetDescription.getSet().getBody();
            var feature = owner.eClass().getEStructuralFeature(referenceWidget.getReferenceName());
            var self = optionalSelf.get();

            if (feature instanceof EReference reference) {
                if (body.isEmpty()) {
                    // When the reference widget doesn't provide any body, we fall back to the default behavior
                    result = this.defaultSet(owner, reference, optionalNewValue.get());
                } else if (EcoreUtil.getRootContainer(widgetDescription) instanceof View view) {
                    // If we have a body then the behavior is the interpretation of the body
                    result = this.setFromOperation(editingContext, view, self, owner, reference, optionalNewValue.get(), body);
                }
            } else {
                result = new Failure(this.referenceMessageService.referenceNotFound(referenceWidget.getReferenceName(), owner.eClass().getName()));
            }
        }

        return result;
    }

    private IStatus setFromOperation(IEditingContext editingContext, View view, Object self, EObject owner, EReference reference, Object selectedObject, List<Operation> operations) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);
        variableManager.put(RepresentationVariables.SELF.name(), self);
        variableManager.put(ReferenceWidgetVariables.REFERENCE_OWNER.name(), owner);
        variableManager.put(ReferenceWidgetVariables.REFERENCE.name(), reference);
        variableManager.put(ReferenceWidgetVariables.NEW_VALUE.name(), selectedObject);

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

    private IStatus defaultSet(EObject owner, EReference reference, Object newValue) {
        if (reference.isMany()) {
            return new Failure(this.referenceMessageService.unableToExecuteSetAction());
        }
        owner.eSet(reference, newValue);
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private Optional<ReferenceWidgetDescription> findReferenceWidgetDescription(IEditingContext editingContext, String descriptionId) {
        return this.viewFormDescriptionSearchService.findFormElementDescriptionById(editingContext, descriptionId)
                .filter(ReferenceWidgetDescription.class::isInstance)
                .map(ReferenceWidgetDescription.class::cast);
    }
}
