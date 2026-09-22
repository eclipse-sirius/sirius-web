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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetClearHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Clears Details View references.
 *
 * @author tgiraudet
 */
@Service
public class DetailsViewReferenceWidgetClearHandler implements IReferenceWidgetClearHandler {

    private final IObjectSearchService objectSearchService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IReferenceMessageService messageService;

    public DetailsViewReferenceWidgetClearHandler(IObjectSearchService objectSearchService,
            IFeedbackMessageService feedbackMessageService, IReferenceMessageService messageService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(FormDescription formDescription) {
        return formDescription.getId().equals(PropertiesEventProcessorFactory.DETAILS_VIEW_ID);
    }

    @Override
    public IStatus clear(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget) {
        var optionalOwner = this.objectSearchService.getObject(editingContext, referenceWidget.getOwnerId())
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);

        if (optionalOwner.isPresent()) {
            EObject owner = optionalOwner.get();
            String referenceName = referenceWidget.getReferenceName();

            if (owner.eClass().getEStructuralFeature(referenceName) instanceof EReference reference) {
                if (reference.isMany()) {
                    ((List<?>) owner.eGet(reference)).clear();
                } else {
                    owner.eUnset(reference);
                }
                return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
            }
        }

        return new Failure(this.messageService.unableToClearReference());
    }
}
