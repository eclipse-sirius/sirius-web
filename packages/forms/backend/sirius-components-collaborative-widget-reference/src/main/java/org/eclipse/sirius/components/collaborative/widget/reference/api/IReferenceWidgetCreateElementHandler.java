/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.api;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;

/**
 * Handle a reference widget create element operations.
 *
 * @author frouene
 */
public interface IReferenceWidgetCreateElementHandler {

    boolean canHandle(FormDescription formDescription);

    List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, String referenceKind, String descriptionId);

    List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind, String descriptionId);

    IStatus createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId, ReferenceWidget referenceWidget);

    IStatus createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId, ReferenceWidget referenceWidget);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author frouene
     */
    class NoOp implements IReferenceWidgetCreateElementHandler {

        @Override
        public boolean canHandle(FormDescription formDescription) {
            return true;
        }

        @Override
        public List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, String referenceKind, String descriptionId) {
            return List.of();
        }

        @Override
        public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind, String descriptionId) {
            return List.of();
        }

        @Override
        public IStatus createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId, ReferenceWidget referenceWidget) {
            return new Failure("");
        }

        @Override
        public IStatus createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId, ReferenceWidget referenceWidget) {
            return new Failure("");
        }
    }

}
