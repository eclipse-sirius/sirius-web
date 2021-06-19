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
package org.eclipse.sirius.web.services.documents;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.api.configuration.StereotypeDescription;
import org.eclipse.sirius.web.services.api.stereotypes.IStereotypeDescriptionService;

/**
 * Implementation of the stereotype description service which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpStereotypeDescriptionService implements IStereotypeDescriptionService {

    @Override
    public List<StereotypeDescription> getStereotypeDescriptions(UUID editingContextId) {
        return new ArrayList<>();
    }

    @Override
    public Optional<StereotypeDescription> getStereotypeDescriptionById(UUID editingContextId, UUID stereotypeId) {
        return Optional.empty();
    }

}
