/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.forms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.web.spring.collaborative.forms.api.FormConfiguration;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
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

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory;

    public FormEventProcessorFactory(IRepresentationDescriptionSearchService representationDescriptionSearchService, IObjectService objectService,
            IRepresentationSearchService representationSearchService, List<IFormEventHandler> formEventHandlers, ISubscriptionManagerFactory subscriptionManagerFactory,
            IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.widgetSubscriptionManagerFactory = Objects.requireNonNull(widgetSubscriptionManagerFactory);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof FormConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof FormConfiguration) {
            FormConfiguration formConfiguration = (FormConfiguration) configuration;

            Optional<Form> optionalForm = this.representationSearchService.findById(editingContext, formConfiguration.getId(), Form.class);
            if (optionalForm.isPresent()) {
                Form form = optionalForm.get();
                // @formatter:off
                Optional<FormDescription> optionalFormDescription = this.representationDescriptionSearchService.findById(editingContext, form.getDescriptionId())
                        .filter(FormDescription.class::isInstance)
                        .map(FormDescription.class::cast);
                // @formatter:on
                Optional<Object> optionalObject = this.objectService.getObject(editingContext, form.getTargetObjectId());
                if (optionalFormDescription.isPresent() && optionalObject.isPresent()) {
                    FormDescription formDescription = optionalFormDescription.get();
                    Object object = optionalObject.get();

                    IRepresentationEventProcessor formEventProcessor = new FormEventProcessor(editingContext, formDescription, formConfiguration.getId(), object, this.formEventHandlers,
                            this.subscriptionManagerFactory.create(), this.widgetSubscriptionManagerFactory.create());

                    // @formatter:off
                    return Optional.of(formEventProcessor)
                            .filter(representationEventProcessorClass::isInstance)
                            .map(representationEventProcessorClass::cast);
                    // @formatter:on

                }
            }
        }
        return Optional.empty();
    }

}
