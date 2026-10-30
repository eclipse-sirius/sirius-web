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
package org.eclipse.sirius.components.view.emf.diagram.handlers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetAddElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetReflectiveAddService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.view.emf.diagram.api.IEdgeStyleReferenceWidgetDescriptionPredicate;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Adds objects to edge style references.
 *
 * @author tgiraudet
 */
@Service
public class EdgeStyleReferenceWidgetAddElementHandler implements IReferenceWidgetAddElementHandler {

    private final IReferenceWidgetReflectiveAddService referenceWidgetReflectiveAddService;

    private final IEdgeStyleReferenceWidgetDescriptionPredicate edgeStyleReferenceWidgetDescriptionPredicate;

    public EdgeStyleReferenceWidgetAddElementHandler(IReferenceWidgetReflectiveAddService referenceWidgetReflectiveAddService,
            IEdgeStyleReferenceWidgetDescriptionPredicate edgeStyleReferenceWidgetDescriptionPredicate) {
        this.referenceWidgetReflectiveAddService = Objects.requireNonNull(referenceWidgetReflectiveAddService);
        this.edgeStyleReferenceWidgetDescriptionPredicate = Objects.requireNonNull(edgeStyleReferenceWidgetDescriptionPredicate);
    }

    @Override
    public boolean canHandle(FormDescription formDescription, ReferenceWidget referenceWidget) {
        return this.edgeStyleReferenceWidgetDescriptionPredicate.test(referenceWidget.getDescriptionId());
    }

    @Override
    public IStatus add(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, List<String> newValueIds) {
        return this.referenceWidgetReflectiveAddService.add(editingContext, referenceWidget, newValueIds);
    }

}
