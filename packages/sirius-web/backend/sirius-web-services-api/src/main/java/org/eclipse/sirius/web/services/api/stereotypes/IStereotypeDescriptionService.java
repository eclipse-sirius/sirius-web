/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.services.api.stereotypes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.configuration.StereotypeDescription;

/**
 * The stereotype descriptions service.
 *
 * @author sbegaudeau
 */
public interface IStereotypeDescriptionService {
    List<StereotypeDescription> getStereotypeDescriptions(String editingContextId);

    Optional<StereotypeDescription> getStereotypeDescriptionById(String editingContextId, UUID stereotypeId);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IStereotypeDescriptionService {

        @Override
        public List<StereotypeDescription> getStereotypeDescriptions(String editingContextId) {
            return List.of();
        }

        @Override
        public Optional<StereotypeDescription> getStereotypeDescriptionById(String editingContextId, UUID stereotypeId) {
            return Optional.empty();
        }

    }
}
