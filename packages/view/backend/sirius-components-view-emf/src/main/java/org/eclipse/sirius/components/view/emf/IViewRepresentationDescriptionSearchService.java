/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;

/**
 * Get a View representation description from its representation description Id.
 *
 * @author arichard
 */
public interface IViewRepresentationDescriptionSearchService {

    Optional<RepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId);

    Optional<NodeDescription> findViewNodeDescriptionById(IEditingContext editingContext, String nodeDescriptionId);

    Optional<EdgeDescription> findViewEdgeDescriptionById(IEditingContext editingContext, String edgeDescriptionId);

    Optional<FormElementDescription> findViewFormElementDescriptionById(IEditingContext editingContext, String formDescriptionId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author frouene
     */
    class NoOp implements IViewRepresentationDescriptionSearchService {

        @Override
        public Optional<RepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
            return Optional.empty();
        }

        @Override
        public Optional<NodeDescription> findViewNodeDescriptionById(IEditingContext editingContext, String nodeDescriptionId) {
            return Optional.empty();
        }

        @Override
        public Optional<EdgeDescription> findViewEdgeDescriptionById(IEditingContext editingContext, String edgeDescriptionId) {
            return Optional.empty();
        }

        @Override
        public Optional<FormElementDescription> findViewFormElementDescriptionById(IEditingContext editingContext, String formDescriptionId) {
            return Optional.empty();
        }
    }
}
