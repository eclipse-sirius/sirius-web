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
package org.eclipse.sirius.web.services.representations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * Registry containing all the representation descriptions.
 *
 * @author sbegaudeau
 */
public class RepresentationDescriptionRegistry implements IRepresentationDescriptionRegistry {

    private final Map<String, IRepresentationDescription> id2representationDescriptions = new HashMap<>();

    @Override
    public void add(IRepresentationDescription representationDescription) {
        this.id2representationDescriptions.put(representationDescription.getId(), representationDescription);
    }

    public Optional<IRepresentationDescription> getRepresentationDescription(String id) {
        return Optional.ofNullable(this.id2representationDescriptions.get(id));
    }

    public List<IRepresentationDescription> getRepresentationDescriptions() {
        return this.id2representationDescriptions.values().stream().collect(Collectors.toList());
    }

}
