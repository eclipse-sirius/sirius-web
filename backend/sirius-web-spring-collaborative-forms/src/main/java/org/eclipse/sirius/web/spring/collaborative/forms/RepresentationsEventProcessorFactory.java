/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.representations.SemanticRepresentationMetadata;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.web.spring.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IRepresentationsDescriptionProvider;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
import org.eclipse.sirius.web.spring.collaborative.forms.api.RepresentationsConfiguration;
import org.springframework.stereotype.Service;

/**
 * Used to create the representations event processors.
 *
 * @author gcoutable
 */
@Service
public class RepresentationsEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationsDescriptionProvider representationsDescriptionProvider;

    private final IObjectService objectService;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    public RepresentationsEventProcessorFactory(IRepresentationsDescriptionProvider representationsDescriptionProvider, IObjectService objectService, List<IFormEventHandler> formEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory, IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.representationsDescriptionProvider = Objects.requireNonNull(representationsDescriptionProvider);
        this.objectService = Objects.requireNonNull(objectService);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.widgetSubscriptionManagerFactory = Objects.requireNonNull(widgetSubscriptionManagerFactory);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof RepresentationsConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IFormEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof RepresentationsConfiguration) {
            RepresentationsConfiguration representationsConfiguration = (RepresentationsConfiguration) configuration;

            FormDescription formDescription = this.representationsDescriptionProvider.getRepresentationsDescription();
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, representationsConfiguration.getObjectId());
            if (optionalObject.isPresent()) {
                Object object = optionalObject.get();
                // @formatter:off
                ISemanticRepresentationMetadata formMetadata =  SemanticRepresentationMetadata.newRepresentationMetadata(representationsConfiguration.getId())
                        .label(RepresentationsEventProcessorFactory.this.objectService.getFullLabel(object))
                        .kind(Form.KIND)
                        .descriptionId(formDescription.getId())
                        .targetObjectId(representationsConfiguration.getObjectId())
                        .build();
                FormCreationParameters formCreationParameters = FormCreationParameters.newFormCreationParameters(representationsConfiguration.getId())
                        .editingContext(editingContext)
                        .formDescription(formDescription)
                        .object(object)
                        .build();
                // @formatter:on

                FormEventProcessor formEventProcessor = new FormEventProcessor(formCreationParameters, formMetadata, this.formEventHandlers, this.subscriptionManagerFactory.create(),
                        this.widgetSubscriptionManagerFactory.create(), this.representationRefreshPolicyRegistry);

                // @formatter:off
                return Optional.of(formEventProcessor)
                        .filter(representationEventProcessorClass::isInstance)
                        .map(representationEventProcessorClass::cast);
                // @formatter:on
            }
        }

        return Optional.empty();
    }

}
