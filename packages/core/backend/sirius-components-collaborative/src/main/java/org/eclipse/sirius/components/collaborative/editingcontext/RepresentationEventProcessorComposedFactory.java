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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to create the various representation event processors.
 * <p>
 * In order to create a representation event processor, this class will look for the proper representation event
 * processor factory and invoke it.
 * </p>
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationEventProcessorComposedFactory implements IRepresentationEventProcessorComposedFactory {

    private final List<IRepresentationEventProcessorFactory> factories;

    public RepresentationEventProcessorComposedFactory(List<IRepresentationEventProcessorFactory> factories) {
        this.factories = Objects.requireNonNull(factories);
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IRepresentationConfiguration configuration, IEditingContext editingContext) {
        return this.factories.stream()
                .filter(factory -> factory.canHandle(configuration))
                .findFirst()
                .flatMap(factory -> factory.createRepresentationEventProcessor(configuration, editingContext));
    }

}
