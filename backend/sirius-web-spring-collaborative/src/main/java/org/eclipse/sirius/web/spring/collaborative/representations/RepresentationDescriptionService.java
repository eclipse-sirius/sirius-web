/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.representations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;

/**
 * Service used to query the representation descriptions available.
 *
 * @author sbegaudeau
 */
public class RepresentationDescriptionService implements IRepresentationDescriptionService {

    private final RepresentationDescriptionRegistry registry;

    public RepresentationDescriptionService(RepresentationDescriptionRegistry registry) {
        this.registry = Objects.requireNonNull(registry);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(Object clazz) {
        // @formatter:off
        var diagramDescriptions = this.registry.getRepresentationDescriptions().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .collect(Collectors.toList());
        // @formatter:on

        List<IRepresentationDescription> representationDescriptions = new ArrayList<>();
        for (DiagramDescription diagramDescription : diagramDescriptions) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IRepresentationDescription.CLASS, clazz);
            boolean canCreate = diagramDescription.getCanCreatePredicate().test(variableManager);
            if (canCreate) {
                representationDescriptions.add(diagramDescription);
            }
        }
        return representationDescriptions;
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions() {
        return this.registry.getRepresentationDescriptions();
    }

    @Override
    public Optional<IRepresentationDescription> findRepresentationDescriptionById(UUID id) {
        return this.registry.getRepresentationDescription(id);
    }

}
