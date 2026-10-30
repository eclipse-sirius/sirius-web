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

package org.eclipse.sirius.components.collaborative.widget.reference.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetReflectiveRemoveService;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Performs reflective remove operations on reference widgets.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceWidgetReflectiveRemoveService implements IReferenceWidgetReflectiveRemoveService {

    private final IObjectSearchService objectSearchService;

    private final IIdentityService identityService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IReferenceMessageService messageService;

    public ReferenceWidgetReflectiveRemoveService(IObjectSearchService objectSearchService, IIdentityService identityService, IFeedbackMessageService feedbackMessageService,
            IReferenceMessageService messageService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.identityService = Objects.requireNonNull(identityService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IStatus remove(IEditingContext editingContext, ReferenceWidget referenceWidget, String itemId) {
        var optionalOwner = this.objectSearchService.getObject(editingContext, referenceWidget.getOwnerId());

        IStatus result = new Failure(this.messageService.invalidIds());

        if (optionalOwner.isPresent() && optionalOwner.get() instanceof EObject owner) {
            var feature = owner.eClass().getEStructuralFeature(referenceWidget.getReferenceName());

            if (feature instanceof EReference reference) {
                result = this.removeReferenceValue(owner, reference, itemId);
            }
        }

        return result;
    }

    private IStatus removeReferenceValue(EObject owner, EReference reference, String itemId) {
        if (reference.isMany()) {
            var values = (List<?>) owner.eGet(reference);
            values.stream()
                    .filter(value -> Objects.equals(this.identityService.getId(value), itemId))
                    .findFirst()
                    .ifPresent(values::remove);
        } else {
            owner.eUnset(reference);
        }
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }
}
