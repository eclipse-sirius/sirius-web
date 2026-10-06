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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetCreateElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Creates objects from Details View reference widgets.
 *
 * @author tgiraudet
 */
@Service
public class DetailsViewReferenceWidgetCreateElementHandler implements IReferenceWidgetCreateElementHandler {

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IReferenceMessageService messageService;

    public DetailsViewReferenceWidgetCreateElementHandler(IEditService editService, IFeedbackMessageService feedbackMessageService, IReferenceMessageService messageService) {
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(FormDescription formDescription) {
        return formDescription.getId().equals(PropertiesEventProcessorFactory.DETAILS_VIEW_ID);
    }

    @Override
    public List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, String referenceKind, String descriptionId) {
        return this.editService.getRootCreationDescriptions(editingContext, domainId, false, referenceKind);
    }

    @Override
    public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind, String descriptionId) {
        return this.editService.getChildCreationDescriptions(editingContext, kind, referenceKind);
    }

    @Override
    public IStatus createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId, ReferenceWidget referenceWidget) {
        return this.toStatus(this.editService.createRootObject(editingContext, documentId, domainId, rootObjectCreationDescriptionId));
    }

    @Override
    public IStatus createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId, ReferenceWidget referenceWidget) {
        return this.toStatus(this.editService.createChild(editingContext, object, childCreationDescriptionId));
    }

    private IStatus toStatus(Optional<Object> optionalObject) {
        return optionalObject.<IStatus>map(object -> new Success(ChangeKind.SEMANTIC_CHANGE, Map.of("object", object), this.feedbackMessageService.getFeedbackMessages()))
                .orElseGet(() -> {
                    List<Message> messages = new ArrayList<>(this.feedbackMessageService.getFeedbackMessages());
                    messages.add(new Message(this.messageService.failedToExecuteCreateReferenceAction(), MessageLevel.ERROR));
                    return new Failure(messages);
                });
    }
}
