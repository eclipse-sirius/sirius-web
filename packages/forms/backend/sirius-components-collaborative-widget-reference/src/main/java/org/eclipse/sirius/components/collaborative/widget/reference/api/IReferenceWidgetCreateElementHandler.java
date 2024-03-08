/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Handle a reference widget create element operations.
 *
 * @author frouene
 */
public interface IReferenceWidgetCreateElementHandler {

    boolean canHandle(String descriptionId);

    List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, String referenceKind, String descriptionId);

    List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind, String descriptionId);

    Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId, String descriptionId);

    Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId, String descriptionId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author frouene
     */
    class NoOp implements IReferenceWidgetCreateElementHandler {

        @Override
        public boolean canHandle(String descriptionId) {
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
        public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId, String descriptionId) {
            return Optional.empty();
        }

        @Override
        public Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId, String descriptionId) {
            return Optional.empty();
        }
    }

}
