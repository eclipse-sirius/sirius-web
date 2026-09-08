/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventProcessorInitializer;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.collaborative.forms.services.api.IFormCapabilitiesService;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.forms.Form;
import org.springframework.stereotype.Service;

/**
 * Used to create the form event processors.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class FormEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IFormEventProcessorInitializer formEventProcessorInitializer;

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<IFormEventHandler> formEventHandlers;

    private final List<ITableEventHandler> tableEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IFormCapabilitiesService formCapabilitiesService;

    public FormEventProcessorFactory(IFormEventProcessorInitializer formEventProcessorInitializer, IRepresentationSearchService representationSearchService,
            ISubscriptionManagerFactory subscriptionManagerFactory, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            FormEventProcessorFactoryConfiguration formConfiguration) {
        this.formEventProcessorInitializer = Objects.requireNonNull(formEventProcessorInitializer);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.formEventHandlers = Objects.requireNonNull(formConfiguration.getFormEventHandlers());
        this.tableEventHandlers = Objects.requireNonNull(formConfiguration.getTableEventHandlers());
        this.formCapabilitiesService = Objects.requireNonNull(formConfiguration.getFormCapabilitiesService());
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return this.representationSearchService.existByIdAndKind(editingContext, representationId, List.of(Form.KIND));
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        return this.formEventProcessorInitializer.getRefreshedRepresentation(editingContext, representationId)
                .map(formContext -> (IRepresentationEventProcessor) new FormEventProcessor(editingContext, formContext, this.formEventHandlers, this.tableEventHandlers,
                        this.subscriptionManagerFactory.create(), this.representationDescriptionSearchService, this.formCapabilitiesService));
    }

}
