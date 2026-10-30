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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetReflectiveRemoveService;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetRemoveHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.view.emf.diagram.api.IEdgeStyleReferenceWidgetDescriptionPredicate;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Removes objects from edge style references.
 *
 * @author tgiraudet
 */
@Service
public class EdgeStyleReferenceWidgetRemoveElementHandler implements IReferenceWidgetRemoveHandler {

    private final IReferenceWidgetReflectiveRemoveService referenceWidgetReflectiveRemoveService;

    private final IEdgeStyleReferenceWidgetDescriptionPredicate edgeStyleReferenceWidgetDescriptionPredicate;

    public EdgeStyleReferenceWidgetRemoveElementHandler(IReferenceWidgetReflectiveRemoveService referenceWidgetReflectiveRemoveService,
            IEdgeStyleReferenceWidgetDescriptionPredicate edgeStyleReferenceWidgetDescriptionPredicate) {
        this.referenceWidgetReflectiveRemoveService = Objects.requireNonNull(referenceWidgetReflectiveRemoveService);
        this.edgeStyleReferenceWidgetDescriptionPredicate = Objects.requireNonNull(edgeStyleReferenceWidgetDescriptionPredicate);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget) {
        return this.edgeStyleReferenceWidgetDescriptionPredicate.test(referenceWidget.getDescriptionId());
    }

    @Override
    public IStatus remove(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, String itemId) {
        return this.referenceWidgetReflectiveRemoveService.remove(editingContext, referenceWidget, itemId);
    }

}
