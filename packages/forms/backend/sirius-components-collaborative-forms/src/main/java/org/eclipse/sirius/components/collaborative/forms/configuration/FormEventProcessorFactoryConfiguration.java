/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.configuration;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormPostProcessor;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.springframework.context.annotation.Configuration;

/**
 * Bundles the common dependencies that most {@link IRepresentationEventProcessorFactory} implementations for Form Representation need
 * into a single object for convenience.
 *
 * @author frouene
 */
@Configuration
public class FormEventProcessorFactoryConfiguration {

    private final IObjectService objectService;

    private final List<IFormEventHandler> formEventHandlers;

    private final Optional<IFormPostProcessor> optionalFormProcessor;

    public FormEventProcessorFactoryConfiguration(IObjectService objectService, List<IFormEventHandler> formEventHandlers, Optional<IFormPostProcessor> optionalFormProcessor) {
        this.objectService = Objects.requireNonNull(objectService);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.optionalFormProcessor = Objects.requireNonNull(optionalFormProcessor);
    }

    public IObjectService getObjectService() {
        return this.objectService;
    }

    public List<IFormEventHandler> getFormEventHandlers() {
        return this.formEventHandlers;
    }

    public IFormPostProcessor getFormPostProcessor() {
        return this.optionalFormProcessor.orElse(new IFormPostProcessor.NoOp());
    }
}
