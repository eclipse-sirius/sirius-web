/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.core.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for the edition of the domain objects.
 *
 * @author sbegaudeau
 */
public interface IEditService {

    List<Domain> getDomains(String editingContextId);

    List<ChildCreationDescription> getRootCreationDescriptions(String editingContextId, String domainId, boolean suggested);

    List<ChildCreationDescription> getChildCreationDescriptions(String editingContextId, String kind);

    Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId);

    Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId);

    void delete(Object object);

    void editLabel(Object object, String labelField, String newValue);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IEditService {

        @Override
        public List<Domain> getDomains(String editingContextId) {
            return List.of();
        }

        @Override
        public List<ChildCreationDescription> getRootCreationDescriptions(String editingContextId, String domainId, boolean suggested) {
            return List.of();
        }

        @Override
        public List<ChildCreationDescription> getChildCreationDescriptions(String editingContextId, String classId) {
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
