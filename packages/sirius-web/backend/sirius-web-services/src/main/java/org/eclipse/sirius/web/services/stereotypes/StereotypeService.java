/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.services.stereotypes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.services.api.document.Stereotype;
import org.eclipse.sirius.web.services.api.stereotypes.IStereotypeService;

/**
 * Service used to query the stereotype descriptions available.
 *
 * @author sbegaudeau
 */
public class StereotypeService implements IStereotypeService {
    private final StereotypeRegistry registry;

    public StereotypeService(StereotypeRegistry registry) {
        this.registry = Objects.requireNonNull(registry);
    }

    @Override
    public List<Stereotype> getStereotypes(String editingContextId) {
        return this.registry.getStereotypes();
    }

    @Override
    public Optional<Stereotype> getStereotypeById(String editingContextId, UUID id) {
        return this.registry.getStereotype(id);
    }
}
