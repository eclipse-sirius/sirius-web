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

import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.ReferenceWidgetVariables;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IViewReferenceWidgetCreateElementExecutor;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Executes the creation body of a View-based reference widget.
 *
 * @author tgiraudet
 */
@Service
public class ViewReferenceWidgetCreateElementExecutor implements IViewReferenceWidgetCreateElementExecutor {

    private final IObjectSearchService objectSearchService;

    private final IReferenceMessageService messageService;

    private final IOperationExecutor operationExecutor;

    private final IViewAQLInterpreterFactory viewAQLInterpreterFactory;

    private final IFeedbackMessageService feedbackMessageService;

    public ViewReferenceWidgetCreateElementExecutor(IObjectSearchService objectSearchService, IReferenceMessageService messageService,
            IOperationExecutor operationExecutor, IViewAQLInterpreterFactory viewAQLInterpreterFactory, IFeedbackMessageService feedbackMessageService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.viewAQLInterpreterFactory = Objects.requireNonNull(viewAQLInterpreterFactory);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    public IStatus execute(IEMFEditingContext editingContext, ReferenceWidget referenceWidget, ReferenceWidgetDescription description,
            Object creationContainer, EReference creationReference, EClass creationType) {
        IStatus status = new Failure(this.messageService.failedToExecuteCreateReferenceAction());
        if (EcoreUtil.getRootContainer(description) instanceof View view) {
            var optionalOwner = this.objectSearchService.getObject(editingContext, referenceWidget.getOwnerId())
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast);
            if (optionalOwner.isPresent() && optionalOwner.get().eClass().getEStructuralFeature(referenceWidget.getReferenceName()) instanceof EReference reference) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);
                variableManager.put(RepresentationVariables.SELF.name(), optionalOwner.get());
                variableManager.put(ReferenceWidgetVariables.REFERENCE.name(), reference);
                variableManager.put(ReferenceWidgetVariables.CREATION_CONTAINER.name(), creationContainer);
                variableManager.put(ReferenceWidgetVariables.CREATION_REFERENCE.name(), creationReference);
                variableManager.put(ReferenceWidgetVariables.CREATION_TYPE.name(), creationType);

                var result = this.operationExecutor.execute(this.viewAQLInterpreterFactory.createInterpreter(editingContext, view), variableManager,
                        description.getCreateButton().getBody());
                if (result.status() == OperationExecutionStatus.SUCCESS) {
                    Map<String, Object> parameters = result.newInstances().values().stream().findFirst()
                            .map(object -> Map.of("object", object))
                            .orElseGet(Map::of);
                    status = new Success(ChangeKind.SEMANTIC_CHANGE, parameters, this.feedbackMessageService.getFeedbackMessages());
                }
            } else if (optionalOwner.isPresent()) {
                status = new Failure(this.messageService.referenceNotFound(referenceWidget.getReferenceName(), optionalOwner.get().eClass().getName()));
            }
        }
        return status;
    }
}
