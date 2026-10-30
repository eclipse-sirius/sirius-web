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
import org.eclipse.sirius.components.view.emf.diagram.api.INodeStyleReferenceWidgetDescriptionPredicate;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Adds objects to node style references.
 *
 * @author tgiraudet
 */
@Service
public class NodeStyleReferenceWidgetAddElementHandler implements IReferenceWidgetAddElementHandler {

    private final IReferenceWidgetReflectiveAddService referenceWidgetReflectiveAddService;

    private final INodeStyleReferenceWidgetDescriptionPredicate nodeStyleReferenceWidgetDescriptionPredicate;

    public NodeStyleReferenceWidgetAddElementHandler(IReferenceWidgetReflectiveAddService referenceWidgetReflectiveAddService,
            INodeStyleReferenceWidgetDescriptionPredicate nodeStyleReferenceWidgetDescriptionPredicate) {
        this.referenceWidgetReflectiveAddService = Objects.requireNonNull(referenceWidgetReflectiveAddService);
        this.nodeStyleReferenceWidgetDescriptionPredicate = Objects.requireNonNull(nodeStyleReferenceWidgetDescriptionPredicate);
    }

    @Override
    public boolean canHandle(FormDescription formDescription, ReferenceWidget referenceWidget) {
        return this.nodeStyleReferenceWidgetDescriptionPredicate.test(referenceWidget.getDescriptionId());
    }

    @Override
    public IStatus add(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, List<String> newValueIds) {
        return this.referenceWidgetReflectiveAddService.add(editingContext, referenceWidget, newValueIds);
    }

}
