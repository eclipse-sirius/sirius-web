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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.services.api.document.IStereotypeRegistry;
import org.eclipse.sirius.web.services.api.document.Stereotype;

/**
 * Registry containing all the stereotypes.
 *
 * @author sbegaudeau
 */
public class StereotypeRegistry implements IStereotypeRegistry {
    private final Map<UUID, Stereotype> id2stereotypes = new LinkedHashMap<>();

    @Override
    public void add(Stereotype stereotype) {
        this.id2stereotypes.put(stereotype.getId(), stereotype);
    }

    public Optional<Stereotype> getStereotype(UUID id) {
        return Optional.ofNullable(this.id2stereotypes.get(id));
    }

    public List<Stereotype> getStereotypes() {
        return this.id2stereotypes.values().stream().collect(Collectors.toUnmodifiableList());
    }

}
