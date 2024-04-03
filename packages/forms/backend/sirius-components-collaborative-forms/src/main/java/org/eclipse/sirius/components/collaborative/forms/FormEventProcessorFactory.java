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
package org.eclipse.sirius.components.collaborative.forms;

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
import org.eclipse.sirius.components.collaborative.forms.api.FormConfiguration;
import org.eclipse.sirius.components.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.IFormPostProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorConfiguration;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.springframework.stereotype.Service;

/**
 * Used to create the form event processors.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class FormEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectService objectService;

    private final IRepresentationSearchService representationSearchService;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IFormPostProcessor formPostProcessor;

    public FormEventProcessorFactory(RepresentationEventProcessorFactoryConfiguration configuration, List<IWidgetDescriptor> widgetDescriptors, FormEventProcessorFactoryConfiguration formConfiguration) {
        this.representationDescriptionSearchService = Objects.requireNonNull(configuration.getRepresentationDescriptionSearchService());
        this.representationSearchService = Objects.requireNonNull(configuration.getRepresentationSearchService());
        this.subscriptionManagerFactory = Objects.requireNonNull(configuration.getSubscriptionManagerFactory());
        this.objectService = Objects.requireNonNull(formConfiguration.getObjectService());
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.formEventHandlers = Objects.requireNonNull(formConfiguration.getFormEventHandlers());
        this.widgetSubscriptionManagerFactory = Objects.requireNonNull(formConfiguration.getWidgetSubscriptionManagerFactory());
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(configuration.getRepresentationRefreshPolicyRegistry());
        this.formPostProcessor = Objects.requireNonNull(formConfiguration.getFormPostProcessor());
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof FormConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof FormConfiguration formConfiguration) {

            Optional<Form> optionalForm = this.representationSearchService.findById(editingContext, formConfiguration.getId(), Form.class);
            if (optionalForm.isPresent()) {
                Form form = optionalForm.get();
                Optional<FormDescription> optionalFormDescription = this.representationDescriptionSearchService.findById(editingContext, form.getDescriptionId())
                        .filter(FormDescription.class::isInstance)
                        .map(FormDescription.class::cast);
                Optional<Object> optionalObject = this.objectService.getObject(editingContext, form.getTargetObjectId());
                if (optionalFormDescription.isPresent() && optionalObject.isPresent()) {
                    FormDescription formDescription = optionalFormDescription.get();
                    Object object = optionalObject.get();

                    FormCreationParameters formCreationParameters = FormCreationParameters.newFormCreationParameters(formConfiguration.getId())
                            .editingContext(editingContext)
                            .formDescription(formDescription)
                            .object(object)
                            .label(form.getLabel())
                            .selection(List.of())
                            .build();

                    IRepresentationEventProcessor formEventProcessor = new FormEventProcessor(
                            new FormEventProcessorConfiguration(editingContext, this.objectService, formCreationParameters, this.widgetDescriptors, this.formEventHandlers),
                            this.subscriptionManagerFactory.create(),
                            this.widgetSubscriptionManagerFactory.create(),
                            this.representationSearchService,
                            this.representationRefreshPolicyRegistry,
                            this.formPostProcessor);

                    return Optional.of(formEventProcessor)
                            .filter(representationEventProcessorClass::isInstance)
                            .map(representationEventProcessorClass::cast);

                }
            }
        }
        return Optional.empty();
    }

}
