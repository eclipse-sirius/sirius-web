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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetRemoveHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.form.api.IViewFormDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IViewReferenceWidgetRemoveOperationExecutor;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Service to remove a chosen element in the reference widget from the specified reference in the context of a representation described with the View DSL.
 *
 * @author tgiraudet
 */
@Service
public class ViewReferenceWidgetRemoveElementHandler implements IReferenceWidgetRemoveHandler {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IObjectSearchService objectSearchService;

    private final IViewFormDescriptionSearchService viewFormDescriptionSearchService;

    private final IIdentityService identityService;

    private final IViewReferenceWidgetRemoveOperationExecutor removeOperationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IReferenceMessageService messageService;

    public ViewReferenceWidgetRemoveElementHandler(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IObjectSearchService objectSearchService,
            IViewFormDescriptionSearchService viewFormDescriptionSearchService, IIdentityService identityService,
            IViewReferenceWidgetRemoveOperationExecutor removeOperationExecutor, IFeedbackMessageService feedbackMessageService,
            IReferenceMessageService messageService) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.viewFormDescriptionSearchService = Objects.requireNonNull(viewFormDescriptionSearchService);
        this.identityService = Objects.requireNonNull(identityService);
        this.removeOperationExecutor = Objects.requireNonNull(removeOperationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget) {
        return this.findReferenceWidgetDescription(editingContext, referenceWidget.getDescriptionId()).isPresent();
    }

    @Override
    public IStatus remove(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, String itemId) {
        var optionalWidgetDescription = this.findReferenceWidgetDescription(editingContext, referenceWidget.getDescriptionId());
        if (optionalWidgetDescription.isPresent() && optionalWidgetDescription.get().getRemove() == null) {
            return new Success();
        }

        var optionalOwner = this.objectSearchService.getObject(editingContext, referenceWidget.getOwnerId());
        Optional<Object> optionalSelf = this.objectSearchService.getObject(editingContext, referenceWidget.getTargetObjectId());

        IStatus result = new Failure(this.messageService.invalidIds());

        if (optionalOwner.isPresent() && optionalOwner.get() instanceof EObject owner && optionalWidgetDescription.isPresent() && optionalSelf.isPresent()) {
            var feature = owner.eClass().getEStructuralFeature(referenceWidget.getReferenceName());
            var widgetDescription = optionalWidgetDescription.get();
            var remove = widgetDescription.getRemove();

            if (feature instanceof EReference reference) {
                if (remove.getBody().isEmpty()) {
                    // When the reference widget doesn't provide any body, we fall back to the default behavior
                    result = this.defaultRemove(owner, reference, itemId);
                } else if (EcoreUtil.getRootContainer(widgetDescription) instanceof View view) {
                    // If we have a body then the behavior is the interpretation of the body
                    result = this.objectSearchService.getObject(editingContext, itemId)
                            .map(valueToRemove -> this.removeOperationExecutor.execute(editingContext, view, optionalSelf.get(), owner, reference, valueToRemove, remove.getBody()))
                            .orElseGet(() -> new Failure(this.messageService.invalidIds()));
                }
            }
        }

        return result;
    }

    private IStatus defaultRemove(EObject owner, EReference reference, String itemId) {
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

    private Optional<ReferenceWidgetDescription> findReferenceWidgetDescription(IEditingContext editingContext, String descriptionId) {
        return this.viewFormDescriptionSearchService.findFormElementDescriptionById(editingContext, descriptionId)
                .filter(ReferenceWidgetDescription.class::isInstance)
                .map(ReferenceWidgetDescription.class::cast);
    }
}
