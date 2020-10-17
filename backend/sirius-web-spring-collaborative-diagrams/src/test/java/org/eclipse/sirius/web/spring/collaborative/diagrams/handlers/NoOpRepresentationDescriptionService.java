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
package org.eclipse.sirius.web.spring.collaborative.diagrams.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;

/**
 * Implementation of the representation description service which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpRepresentationDescriptionService implements IRepresentationDescriptionService {

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(Object clazz) {
        return new ArrayList<>();
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions() {
        return new ArrayList<>();
    }

    @Override
    public Optional<IRepresentationDescription> findRepresentationDescriptionById(UUID id) {
        return Optional.empty();
    }

}
