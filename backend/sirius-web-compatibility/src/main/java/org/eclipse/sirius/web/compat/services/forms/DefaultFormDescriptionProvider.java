/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.compat.services.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.web.api.configuration.IPropertiesDefaultFormDescriptionProvider;
import org.eclipse.sirius.web.forms.description.AbstractControlDescription;
import org.eclipse.sirius.web.forms.description.ForDescription;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.forms.description.GroupDescription;
import org.eclipse.sirius.web.forms.description.IfDescription;
import org.eclipse.sirius.web.forms.description.PageDescription;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.springframework.stereotype.Service;

/**
 * Used to provide the default form description.
 *
 * @author lfasani
 */
@Service
public class DefaultFormDescriptionProvider implements IPropertiesDefaultFormDescriptionProvider {

    public static final String ESTRUCTURAL_FEATURE = "eStructuralFeature"; //$NON-NLS-1$

    private final IObjectService objectService;

    private final ComposedAdapterFactory composedAdapterFactory;

    public DefaultFormDescriptionProvider(IObjectService objectService, ComposedAdapterFactory composedAdapterFactory) {
        this.objectService = Objects.requireNonNull(objectService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
    }

    @Override
    public FormDescription getFormDescription() {
        List<GroupDescription> groupDescriptions = new ArrayList<>();
        GroupDescription groupDescription = this.getGroupDescription();

        groupDescriptions.add(groupDescription);

        List<PageDescription> pageDescriptions = new ArrayList<>();
        PageDescription firstPageDescription = this.getPageDescription(groupDescriptions);
        pageDescriptions.add(firstPageDescription);

        // @formatter:off
        Function<VariableManager, String> labelProvider = variableManager -> {
            return Optional.ofNullable(variableManager.getVariables().get(VariableManager.SELF))
                    .map(this.objectService::getFullLabel)
                    .orElse("Properties"); //$NON-NLS-1$
        };

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse(null);

        return FormDescription.newFormDescription(IRepresentationDescriptionService.DEFAULT_FORM_DESCRIPTION)
                .label("Default form description") //$NON-NLS-1$
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(labelProvider)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> false)
                .pageDescriptions(pageDescriptions)
                .groupDescriptions(groupDescriptions)
                .build();
        // @formatter:on
    }

    private PageDescription getPageDescription(List<GroupDescription> groupDescriptions) {
        // @formatter:off
        return PageDescription.newPageDescription("firstPageId") //$NON-NLS-1$
                .idProvider(variableManager -> "Main") //$NON-NLS-1$
                .labelProvider(variableManager -> "Main") //$NON-NLS-1$
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(variableManager -> true)
                .build();
        // @formatter:on
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();

        Function<VariableManager, List<Object>> iterableProvider = variableManager -> {
            List<Object> objects = new ArrayList<>();

            Object self = variableManager.getVariables().get(VariableManager.SELF);
            if (self instanceof EObject) {
                EObject eObject = (EObject) self;

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
        ifDescriptions.add(new EStringIfDescriptionProvider(this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new EBooleanIfDescriptionProvider(this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new EEnumIfDescriptionProvider(this.composedAdapterFactory).getIfDescription());

        ifDescriptions.add(new MonoValuedNonContainmentReferenceIfDescriptionProvider(this.composedAdapterFactory, this.objectService).getIfDescription());
        ifDescriptions.add(new MultiValuedNonContainmentReferenceIfDescriptionProvider(this.composedAdapterFactory, this.objectService).getIfDescription());

        ifDescriptions.add(new NumberIfDescriptionProvider(EcorePackage.Literals.EINT, this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new NumberIfDescriptionProvider(EcorePackage.Literals.EINTEGER_OBJECT, this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new NumberIfDescriptionProvider(EcorePackage.Literals.EDOUBLE, this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new NumberIfDescriptionProvider(EcorePackage.Literals.EDOUBLE_OBJECT, this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new NumberIfDescriptionProvider(EcorePackage.Literals.EFLOAT, this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new NumberIfDescriptionProvider(EcorePackage.Literals.EFLOAT_OBJECT, this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new NumberIfDescriptionProvider(EcorePackage.Literals.ELONG, this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new NumberIfDescriptionProvider(EcorePackage.Literals.ELONG_OBJECT, this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new NumberIfDescriptionProvider(EcorePackage.Literals.ESHORT, this.composedAdapterFactory).getIfDescription());
        ifDescriptions.add(new NumberIfDescriptionProvider(EcorePackage.Literals.ESHORT_OBJECT, this.composedAdapterFactory).getIfDescription());

        // @formatter:off
        ForDescription forDescription = ForDescription.newForDescription("forId") //$NON-NLS-1$
                .iterator(ESTRUCTURAL_FEATURE)
                .iterableProvider(iterableProvider)
                .ifDescriptions(ifDescriptions)
                .build();
        // @formatter:on

        controlDescriptions.add(forDescription);

        // @formatter:off
        return GroupDescription.newGroupDescription("groupId") //$NON-NLS-1$
                .idProvider(variableManager -> "Core Properties") //$NON-NLS-1$
                .labelProvider(variableManager -> "Core Properties") //$NON-NLS-1$
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(controlDescriptions)
                .build();
        // @formatter:on
    }

}
