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
package org.eclipse.sirius.web.application.views.details.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetAddElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Service to add to Details View references.
 *
 * @author tgiraudet
 */
@Service
public class DetailsViewReferenceWidgetAddElementHandler implements IReferenceWidgetAddElementHandler {

    private final IObjectSearchService objectSearchService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IReferenceMessageService referenceMessageService;

    private final IReferenceMessageService messageService;

    public DetailsViewReferenceWidgetAddElementHandler(IObjectSearchService objectSearchService,
            IFeedbackMessageService feedbackMessageService, IReferenceMessageService referenceMessageService, IReferenceMessageService messageService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.referenceMessageService = Objects.requireNonNull(referenceMessageService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(FormDescription formDescription) {
        return formDescription.getId().equals(PropertiesEventProcessorFactory.DETAILS_VIEW_ID);
    }

    @Override
    public IStatus add(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, List<String> newValueIds) {
        var optionalOwner = this.objectSearchService.getObject(editingContext, referenceWidget.getOwnerId())
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);

        var newValues = newValueIds.stream().flatMap(id -> this.flatMapAndLogMessageIfAbsent(editingContext, id)).toList();

        if (optionalOwner.isPresent()) {
            EObject owner = optionalOwner.get();
            String referenceName = referenceWidget.getReferenceName();

            if (owner.eClass().getEStructuralFeature(referenceName) instanceof EReference reference) {
                ((List<Object>) owner.eGet(reference)).addAll(newValues);
                return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
            }
        }

        return new Failure(this.messageService.unableToExecuteAddAction());
    }

    private Stream<Object> flatMapAndLogMessageIfAbsent(IEditingContext editingContext, String newValueId) {
        var optionalObject = this.objectSearchService.getObject(editingContext, newValueId);
        if (optionalObject.isEmpty()) {
            this.feedbackMessageService.addFeedbackMessage(new Message(this.referenceMessageService.objectNotFound(newValueId), MessageLevel.WARNING));
        }
        return optionalObject.stream();
    }

}
