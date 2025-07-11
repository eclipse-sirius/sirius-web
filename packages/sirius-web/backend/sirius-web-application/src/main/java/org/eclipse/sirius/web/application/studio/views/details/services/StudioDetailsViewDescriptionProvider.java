/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.forms.EStringIfDescriptionProvider;
import org.eclipse.sirius.components.emf.forms.api.IEMFFormIfDescriptionProvider;
import org.eclipse.sirius.components.emf.forms.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.emf.FixedColorPropertiesConfigurer;
import org.eclipse.sirius.components.view.emf.ITextfieldCustomizer;
import org.eclipse.sirius.components.view.emf.diagram.NodeStylePropertiesConfigurer;
import org.springframework.stereotype.Service;

/**
 * Defines the properties view for the View DSL elements, which specializes/customizes the default rules. The
 * implementation is mostly a fork of the default rules in PropertiesDefaultDescriptionProvider, with two differences:
 * <ol>
 * <li>it only {@link #handles(VariableManager) applies} to elements from the View DSL which do not have
 * {@link #TYPES_WITH_CUSTOM_PROPERTIES their own even more specific properties definition}.</li>
 * <li>it uses a {@link CustomizableEStringIfDescriptionProvider customizable variant} of the generic
 * EStringIfDescriptionProvider so that specific text fields can get special treatment (a specific style and
 * completion proposal provider).</li>
 * </ol>
 *
 * @author pcdavid
 */
@Service
public class StudioDetailsViewDescriptionProvider implements IPropertiesDescriptionRegistryConfigurer {

    public static final String ESTRUCTURAL_FEATURE = "eStructuralFeature";

    /**
     * These types have even more specific properties definition, see {@link NodeStylePropertiesConfigurer}
     * or {@link FixedColorPropertiesConfigurer}.
     */
    private static final List<EClass> TYPES_WITH_CUSTOM_PROPERTIES = List.of(
            DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION,
            DiagramPackage.Literals.ICON_LABEL_NODE_STYLE_DESCRIPTION,
            DiagramPackage.Literals.RECTANGULAR_NODE_STYLE_DESCRIPTION,
            DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION,
            DiagramPackage.Literals.EDGE_STYLE,
            ViewPackage.Literals.FIXED_COLOR
    );

    private final IObjectService objectService;

    private final ILabelService labelService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final List<ITextfieldCustomizer> customizers;

    private final List<IEMFFormIfDescriptionProvider> emfFormIfDescriptionProviders;

    public StudioDetailsViewDescriptionProvider(IObjectService objectService, ILabelService labelService, ComposedAdapterFactory composedAdapterFactory, IPropertiesValidationProvider propertiesValidationProvider,
                                                List<ITextfieldCustomizer> customizers, List<IEMFFormIfDescriptionProvider> emfFormIfDescriptionProviders) {
        this.objectService = Objects.requireNonNull(objectService);
        this.labelService = Objects.requireNonNull(labelService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.customizers = Objects.requireNonNull(customizers);
        this.emfFormIfDescriptionProviders = Objects.requireNonNull(emfFormIfDescriptionProviders);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.getPageDescription());
    }

    private PageDescription getPageDescription() {
        List<GroupDescription> groupDescriptions = new ArrayList<>();
        GroupDescription groupDescription = this.getGroupDescription();

        groupDescriptions.add(groupDescription);

        return this.getPageDescription(groupDescriptions);
    }

    private PageDescription getPageDescription(List<GroupDescription> groupDescriptions) {
        Function<VariableManager, String> idProvider = variableManager -> {
            var optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalSelf.isPresent()) {
                Object self = optionalSelf.get();
                return this.objectService.getId(self);
            }
            return UUID.randomUUID().toString();
        };

        Function<VariableManager, String> labelProvider = variableManager -> {
            var optionalSelf = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalSelf.isPresent()) {
                Object self = optionalSelf.get();
                return this.labelService.getStyledLabel(self).toString();
            }
            return UUID.randomUUID().toString();
        };

        return PageDescription.newPageDescription(UUID.nameUUIDFromBytes("view_properties_description".getBytes()).toString())
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(this::handles)
                .build();
    }

    private boolean handles(VariableManager variableManager) {
        Optional<EObject> selectedElement = variableManager.get(VariableManager.SELF, Object.class)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        boolean isViewElement = selectedElement
                .map(this::isViewElement)
                .orElse(false);
        boolean hasCustomProperties = selectedElement.map(EObject::eClass).filter(TYPES_WITH_CUSTOM_PROPERTIES::contains).isPresent();
        return isViewElement && !hasCustomProperties;
    }

    private boolean isViewElement(EObject element) {
        return element instanceof View || element != null && this.isViewElement(element.eContainer());
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();

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

        Function<VariableManager, String> semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse(null);

        List<AbstractControlDescription> ifDescriptions = new ArrayList<>();
        for (ITextfieldCustomizer customizer : this.customizers) {
            ifDescriptions.add(new CustomizableEStringIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider, customizer, semanticTargetIdProvider).getIfDescription());
        }
        ITextfieldCustomizer fallbackCustomizer = new ITextfieldCustomizer.NoOp() {
            @Override
            public boolean handles(EAttribute eAttribute, EObject eObject) {
                return StudioDetailsViewDescriptionProvider.this.customizers.stream().noneMatch(customizer -> customizer.handles(eAttribute, eObject));
            }
        };
        ifDescriptions.add(new CustomizableEStringIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider, fallbackCustomizer, semanticTargetIdProvider).getIfDescription());
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

        return GroupDescription.newGroupDescription("groupId")
                .idProvider(variableManager -> "Core Properties")
                .labelProvider(variableManager -> "Core Properties")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(controlDescriptions)
                .build();
    }

}
