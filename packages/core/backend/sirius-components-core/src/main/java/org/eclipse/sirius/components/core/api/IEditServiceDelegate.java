/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.core.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for the delegation service of the edition of the domain objects.
 *
 * @author arichard
 */
public interface IEditServiceDelegate {

    boolean canHandle(Object object);

    boolean canHandle(IEditingContext editingContext);

    List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, boolean suggested, String referenceKind);

    List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind);

    Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId);

    Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId);

    void delete(Object object);

    void editLabel(Object object, String labelField, String newValue);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author arichard
     */
    class NoOp implements IEditServiceDelegate {

        @Override
        public boolean canHandle(Object object) {
            return false;
        }

        @Override
        public boolean canHandle(IEditingContext editingContext) {
            return false;
        }

        @Override
        public List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, boolean suggested, String referenceKind) {
            return List.of();
        }

        @Override
        public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String classId, String referenceName) {
            return List.of();
        }

        @Override
        public Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId) {
            return Optional.empty();
        }

        @Override
        public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId) {
            return Optional.empty();
        }

        @Override
        public void delete(Object object) {
        }

        @Override
        public void editLabel(Object object, String labelField, String newValue) {
        }

    }
}
