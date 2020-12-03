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
import java.util.function.Predicate;

import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;

/**
 * Service used to query the representation descriptions available.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
public class RepresentationDescriptionService implements IRepresentationDescriptionService {

    private final RepresentationDescriptionRegistry registry;

    public RepresentationDescriptionService(RepresentationDescriptionRegistry registry) {
        this.registry = Objects.requireNonNull(registry);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(Object clazz) {
        List<IRepresentationDescription> result = new ArrayList<>();
        for (IRepresentationDescription description : this.registry.getRepresentationDescriptions()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IRepresentationDescription.CLASS, clazz);
            Predicate<VariableManager> canCreatePredicate = description.getCanCreatePredicate();
            boolean canCreate = canCreatePredicate.test(variableManager);
            if (canCreate) {
                result.add(description);
            }
        }
        return result;
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
