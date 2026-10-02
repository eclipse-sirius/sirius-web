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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetCreateElementHandler;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.springframework.stereotype.Service;

/**
 * Creates objects from View-based reference widgets.
 *
 * @author tgiraudet
 */
@Service
public class ViewReferenceWidgetCreateElementHandler implements IReferenceWidgetCreateElementHandler {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IEditService editService;

    public ViewReferenceWidgetCreateElementHandler(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IEditService editService) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public boolean canHandle(FormDescription formDescription) {
        return this.viewRepresentationDescriptionPredicate.test(formDescription);
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
    public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId, String descriptionId) {
        return this.editService.createRootObject(editingContext, documentId, domainId, rootObjectCreationDescriptionId);
    }

    @Override
    public Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId, String descriptionId) {
        return this.editService.createChild(editingContext, object, childCreationDescriptionId);
    }
}
