/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.api.RepresentationEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.FormDescriptionEditorConfiguration;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorCreationService;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorEventHandler;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorEventProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.springframework.stereotype.Service;

/**
 * Used to create the form description editor event processors.
 *
 * @author arichard
 */
@Service
public class FormDescriptionEditorEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IFormDescriptionEditorCreationService formDescriptionEditorCreationService;

    private final IRepresentationSearchService representationSearchService;

    private final List<IFormDescriptionEditorEventHandler> formDescriptionEditorEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    public FormDescriptionEditorEventProcessorFactory(RepresentationEventProcessorFactoryConfiguration configuration, IFormDescriptionEditorCreationService formDescriptionEditormCreationService,
            List<IFormDescriptionEditorEventHandler> formDescriptionEditorEventHandlers) {
        this.representationDescriptionSearchService = Objects.requireNonNull(configuration.getRepresentationDescriptionSearchService());
        this.formDescriptionEditorCreationService = Objects.requireNonNull(formDescriptionEditormCreationService);
        this.representationSearchService = Objects.requireNonNull(configuration.getRepresentationSearchService());
        this.formDescriptionEditorEventHandlers = Objects.requireNonNull(formDescriptionEditorEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(configuration.getSubscriptionManagerFactory());
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(configuration.getRepresentationRefreshPolicyRegistry());
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IFormDescriptionEditorEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof FormDescriptionEditorConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IFormDescriptionEditorEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof FormDescriptionEditorConfiguration) {
            FormDescriptionEditorConfiguration formDescriptionEditorConfiguration = (FormDescriptionEditorConfiguration) configuration;

            Optional<FormDescriptionEditor> optionalFormDescriptionEditor = this.representationSearchService.findById(editingContext, formDescriptionEditorConfiguration.getId(),
                    FormDescriptionEditor.class);
            if (optionalFormDescriptionEditor.isPresent()) {
                FormDescriptionEditor formDescriptionEditor = optionalFormDescriptionEditor.get();
                FormDescriptionEditorContext formDescriptionEditorContext = new FormDescriptionEditorContext(formDescriptionEditor);
                var parameters = FormDescriptionEditorEventProcessorParameters.newFormDescriptionEditorEventProcessorParameters()
                        .editingContext(editingContext)
                        .formDescriptionEditorContext(formDescriptionEditorContext)
                        .formDescriptionEditorEventHandlers(this.formDescriptionEditorEventHandlers)
                        .subscriptionManager(this.subscriptionManagerFactory.create())
                        .formDescriptionEditorCreationService(this.formDescriptionEditorCreationService)
                        .representationDescriptionSearchService(this.representationDescriptionSearchService)
                        .representationSearchService(this.representationSearchService)
                        .representationRefreshPolicyRegistry(this.representationRefreshPolicyRegistry)
                        .build();
                IRepresentationEventProcessor formDescriptionEditorEventProcessor = new FormDescriptionEditorEventProcessor(parameters);

                return Optional.of(formDescriptionEditorEventProcessor).filter(representationEventProcessorClass::isInstance).map(representationEventProcessorClass::cast);
            }
        }
        return Optional.empty();
    }
}
