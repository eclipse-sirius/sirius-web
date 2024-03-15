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

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;

/**
 * Get a View representation description from its representation description Id.
 *
 * @author arichard
 */
public interface IViewRepresentationDescriptionSearchService {

    Optional<RepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId);

    List<View> findViewsBySourceId(IEditingContext editingContext, String sourceId);

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
        public List<View> findViewsBySourceId(IEditingContext editingContext, String sourceId) {
            return List.of();
        }
    }
}
