/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.studio.views.details.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.emf.forms.EStringIfDescriptionProvider;
import org.eclipse.sirius.components.emf.forms.WidgetReadOnlyProvider;
import org.eclipse.sirius.components.emf.forms.api.IEMFFormIfDescriptionProvider;
import org.eclipse.sirius.components.emf.forms.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.ITextfieldCustomizer;
import org.eclipse.sirius.web.application.studio.views.details.services.api.IStudioDetailsViewWidgetDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Provides widget descriptions for Studio details view forms.
 *
 * @author tgiraudet
 */
@Service
public class StudioDetailsViewWidgetDescriptionProvider implements IStudioDetailsViewWidgetDescriptionProvider {

    public static final String ESTRUCTURAL_FEATURE = "eStructuralFeature";

    private final IIdentityService identityService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final List<ITextfieldCustomizer> customizers;

    private final List<IEMFFormIfDescriptionProvider> emfFormIfDescriptionProviders;

    private final WidgetReadOnlyProvider widgetReadOnlyProvider;

    public StudioDetailsViewWidgetDescriptionProvider(IIdentityService identityService, ComposedAdapterFactory composedAdapterFactory, IPropertiesValidationProvider propertiesValidationProvider,
                                                      List<ITextfieldCustomizer> customizers, List<IEMFFormIfDescriptionProvider> emfFormIfDescriptionProviders, WidgetReadOnlyProvider widgetReadOnlyProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.customizers = Objects.requireNonNull(customizers);
        this.emfFormIfDescriptionProviders = Objects.requireNonNull(emfFormIfDescriptionProviders);
        this.widgetReadOnlyProvider = Objects.requireNonNull(widgetReadOnlyProvider);
    }

    @Override
    public List<AbstractControlDescription> getWidgets() {
        Function<VariableManager, List<?>> iterableProvider = variableManager -> {
            List<Object> objects = new ArrayList<>();

            Object self = variableManager.getVariables().get(VariableManager.SELF);
            if (self instanceof EObject eObject) {

                List<IItemPropertyDescriptor> propertyDescriptors = Optional.ofNullable(this.composedAdapterFactory.adapt(eObject, IItemPropertySource.class))
                        .filter(IItemPropertySource.class::isInstance)
                        .map(IItemPropertySource.class::cast)
                        .map(iItemPropertySource -> iItemPropertySource.getPropertyDescriptors(eObject))
                        .orElse(new ArrayList<>());

                propertyDescriptors.stream()
                        .map(propertyDescriptor -> propertyDescriptor.getFeature(eObject))
                        .filter(EStructuralFeature.class::isInstance)
                        .map(EStructuralFeature.class::cast)
                        .forEach(objects::add);
            }
            return objects;
        };
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();

        Function<VariableManager, String> semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        List<AbstractControlDescription> ifDescriptions = new ArrayList<>();
        for (ITextfieldCustomizer customizer : this.customizers) {
            ifDescriptions.add(new CustomizableEStringIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider, customizer, semanticTargetIdProvider, this.widgetReadOnlyProvider).getIfDescription());
        }
        ITextfieldCustomizer fallbackCustomizer = new ITextfieldCustomizer.NoOp() {
            @Override
            public boolean handles(EAttribute eAttribute, EObject eObject) {
                return StudioDetailsViewWidgetDescriptionProvider.this.customizers.stream().noneMatch(customizer -> customizer.handles(eAttribute, eObject));
            }
        };
        ifDescriptions.add(new CustomizableEStringIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider, fallbackCustomizer, semanticTargetIdProvider, this.widgetReadOnlyProvider).getIfDescription());
        this.emfFormIfDescriptionProviders.stream()
                .map(IEMFFormIfDescriptionProvider::getIfDescriptions)
                .flatMap(Collection::stream)
                .filter(ifDescription -> !ifDescription.getId().equals(EStringIfDescriptionProvider.IF_DESCRIPTION_ID))
                .forEach(ifDescriptions::add);

        ForDescription forDescription = ForDescription.newForDescription("forId")
                .targetObjectIdProvider(semanticTargetIdProvider)
                .iterator(ESTRUCTURAL_FEATURE)
                .iterableProvider(iterableProvider)
                .controlDescriptions(ifDescriptions)
                .build();

        controlDescriptions.add(forDescription);

        return controlDescriptions;
    }

}
