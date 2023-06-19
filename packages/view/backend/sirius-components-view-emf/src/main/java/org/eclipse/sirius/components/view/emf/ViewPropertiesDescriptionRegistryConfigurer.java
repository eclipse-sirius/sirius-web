/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.compatibility.emf.properties.EBooleanIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.EEnumIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.EStringIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.MonoValuedNonContainmentReferenceIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.MultiValuedNonContainmentReferenceIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.NumberIfDescriptionProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.emf.diagram.NodeStylePropertiesConfigurer;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.springframework.stereotype.Service;

/**
 * Defines the properties view for the View DSL elements, which specializes/customizes the default rules. The
 * implementation is mostly a fork of the default rules in PropertiesDefaultDescriptionProvider, with two differences:
 * <ol>
 * <li>it only {@link #handles(VariableManager) applies} to elements from the View DSL which do not have
 * {@link #TYPES_WITH_CUSTOM_PROPERTIES their own even more specific properties definition}.</li>
 * <li>it uses a {@link CustomizableEStringIfDescriptionProvider customizable variant} of the generic
 * {@link EStringIfDescriptionProvider} so that specific text fields can get special treatment (a specific style and
 * completion proposal provider).</li>
 * </ol>
 *
 * @author pcdavid
 */
@Service
public class ViewPropertiesDescriptionRegistryConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    public static final String ESTRUCTURAL_FEATURE = "eStructuralFeature";

    private static final List<EPackage> VIEW_PACKAGES = List.of(
            ViewPackage.eINSTANCE,
            DiagramPackage.eINSTANCE,
            FormPackage.eINSTANCE
    );

    /**
     * These types have even more specific properties definition, see {@link NodeStylePropertiesConfigurer}.
     */
    // @formatter:off
    private static final List<EClass> TYPES_WITH_CUSTOM_PROPERTIES = List.of(
            DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION,
            DiagramPackage.Literals.ICON_LABEL_NODE_STYLE_DESCRIPTION,
            DiagramPackage.Literals.RECTANGULAR_NODE_STYLE_DESCRIPTION
    );
    // @formatter:on
    private final IObjectService objectService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final IEMFMessageService emfMessageService;

    private final List<ITextfieldCustomizer> customizers;

    public ViewPropertiesDescriptionRegistryConfigurer(IObjectService objectService, ComposedAdapterFactory composedAdapterFactory, IPropertiesValidationProvider propertiesValidationProvider,
            IEMFMessageService emfMessageService, List<ITextfieldCustomizer> customizers) {
        this.objectService = Objects.requireNonNull(objectService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.emfMessageService = Objects.requireNonNull(emfMessageService);
        this.customizers = List.copyOf(customizers);
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
                return this.objectService.getLabel(self);
            }
            return UUID.randomUUID().toString();
        };

        // @formatter:off
        return PageDescription.newPageDescription(UUID.nameUUIDFromBytes("view_properties_description".getBytes()).toString())
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(this::handles)
                .build();
        // @formatter:on
    }

    private boolean handles(VariableManager variableManager) {
        // @formatter:off
        Optional<EObject> selectedElement = variableManager.get(VariableManager.SELF, Object.class)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        boolean isViewElement = selectedElement
                .map(this::isViewElement)
                .orElse(false);
        boolean hasCustomProperties = selectedElement.map(EObject::eClass).filter(TYPES_WITH_CUSTOM_PROPERTIES::contains).isPresent();
        // @formatter:on
        return isViewElement && !hasCustomProperties;
    }

    private boolean isViewElement(EObject element) {
        return VIEW_PACKAGES.contains(element.eClass().getEPackage()) ||
               element.eClass().getEAllSuperTypes().stream()
                      .anyMatch(eClass -> VIEW_PACKAGES.contains(eClass.getEPackage()));
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();

        Function<VariableManager, List<?>> iterableProvider = variableManager -> {
            List<Object> objects = new ArrayList<>();

            Object self = variableManager.getVariables().get(VariableManager.SELF);
            if (self instanceof EObject eObject) {

                // @formatter:off
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
                // @formatter:on
            }
            return objects;
        };

        List<IfDescription> ifDescriptions = new ArrayList<>();
        for (ITextfieldCustomizer customizer : this.customizers) {
            ifDescriptions.add(new CustomizableEStringIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider, customizer).getIfDescription());
        }
        ITextfieldCustomizer fallbackCustomizer = new ITextfieldCustomizer.NoOp() {
            @Override
            public boolean handles(EAttribute eAttribute) {
                return ViewPropertiesDescriptionRegistryConfigurer.this.customizers.stream().noneMatch(customizer -> customizer.handles(eAttribute));
            }
        };
        ifDescriptions.add(new CustomizableEStringIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider, fallbackCustomizer).getIfDescription());
        ifDescriptions.add(new EBooleanIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider).getIfDescription());
        ifDescriptions.add(new EEnumIfDescriptionProvider(this.composedAdapterFactory, this.propertiesValidationProvider).getIfDescription());

        ifDescriptions.add(new MonoValuedNonContainmentReferenceIfDescriptionProvider(this.composedAdapterFactory, this.objectService, this.propertiesValidationProvider).getIfDescription());
        ifDescriptions.add(new MultiValuedNonContainmentReferenceIfDescriptionProvider(this.composedAdapterFactory, this.objectService, this.propertiesValidationProvider).getIfDescription());

        // @formatter:off
        var numericDataTypes = List.of(
                EcorePackage.Literals.EINT,
                EcorePackage.Literals.EINTEGER_OBJECT,
                EcorePackage.Literals.EDOUBLE,
                EcorePackage.Literals.EDOUBLE_OBJECT,
                EcorePackage.Literals.EFLOAT,
                EcorePackage.Literals.EFLOAT_OBJECT,
                EcorePackage.Literals.ELONG,
                EcorePackage.Literals.ELONG_OBJECT,
                EcorePackage.Literals.ESHORT,
                EcorePackage.Literals.ESHORT_OBJECT,
                ViewPackage.Literals.LENGTH
                );
        // @formatter:on
        for (var dataType : numericDataTypes) {
            ifDescriptions.add(new NumberIfDescriptionProvider(dataType, this.composedAdapterFactory, this.propertiesValidationProvider, this.emfMessageService).getIfDescription());
        }

        // @formatter:off
        ForDescription forDescription = ForDescription.newForDescription("forId")
                .iterator(ESTRUCTURAL_FEATURE)
                .iterableProvider(iterableProvider)
                .ifDescriptions(ifDescriptions)
                .build();
        // @formatter:on

        controlDescriptions.add(forDescription);

        // @formatter:off
        return GroupDescription.newGroupDescription("groupId")
                .idProvider(variableManager -> "Core Properties")
                .labelProvider(variableManager -> "Core Properties")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(controlDescriptions)
                .build();
        // @formatter:on
    }

}
