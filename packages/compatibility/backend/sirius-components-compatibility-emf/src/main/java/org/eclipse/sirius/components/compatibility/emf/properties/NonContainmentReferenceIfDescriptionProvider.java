/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.compatibility.emf.properties;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetComponent;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription;

/**
 * Provides the default description of the widget to use to support non-containment reference.
 *
 * @author frouene
 */
public class NonContainmentReferenceIfDescriptionProvider {

    private static final String ID_DESCRIPTION_ID = "NonContainment Reference";

    private static final String REFERENCE_WIDGET_DESCRIPTION_ID = "ReferenceWidget";

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IObjectService objectService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    public NonContainmentReferenceIfDescriptionProvider(ComposedAdapterFactory composedAdapterFactory, IObjectService objectService, Function<VariableManager, String> semanticTargetIdProvider) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.objectService = Objects.requireNonNull(objectService);
        this.semanticTargetIdProvider = Objects.requireNonNull(semanticTargetIdProvider);
    }

    public IfDescription getIfDescription() {
        return IfDescription.newIfDescription(ID_DESCRIPTION_ID)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .predicate(this.getPredicate())
                .controlDescriptions(List.of(this.getReferenceWidgetDescription()))
                .build();
    }

    private Function<VariableManager, Boolean> getPredicate() {
        return variableManager -> {
            var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);
            return optionalEReference.filter(eReference -> !eReference.isContainment()).isPresent();
        };
    }

    private ReferenceWidgetDescription getReferenceWidgetDescription() {
        return ReferenceWidgetDescription.newReferenceWidgetDescription(REFERENCE_WIDGET_DESCRIPTION_ID)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(new WidgetIdProvider())
                .labelProvider(this.getLabelProvider())
                .optionsProvider(this.getOptionsProvider())
                .iconURLProvider(variableManager -> "")
                .itemsProvider(this::getReferenceValue)
                .itemIdProvider(this::getItemId)
                .itemKindProvider(this::getItemKind)
                .itemLabelProvider(this::getItemLabel)
                .itemImageURLProvider(this::getItemIconURL)
                .settingProvider(this::resolveSetting)
                .styleProvider(variableManager -> null)
                .ownerIdProvider(this::getOwnerId)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .build();
    }

    private List<?> getReferenceValue(VariableManager variableManager) {
        List<?> value = List.of();
        EStructuralFeature.Setting setting = this.resolveSetting(variableManager);
        if (setting != null) {
            var rawValue = setting.get(true);
            if (setting.getEStructuralFeature().isMany()) {
                value = (List<?>) rawValue;
            } else if (rawValue != null) {
                value = List.of(rawValue);
            } else {
                value = List.of();
            }
        }
        return value;
    }

    private Function<VariableManager, List<?>> getOptionsProvider() {
        return new EStructuralFeatureChoiceOfValueProvider(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, this.composedAdapterFactory);
    }

    private EStructuralFeature.Setting resolveSetting(VariableManager variableManager) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);

        if (referenceOwner != null && optionalEReference.isPresent()) {
            return ((InternalEObject) referenceOwner).eSetting(optionalEReference.get());
        } else {
            return null;
        }
    }

    private Optional<Object> getItem(VariableManager variableManager) {
        return variableManager.get(ReferenceWidgetComponent.ITEM_VARIABLE, Object.class);
    }

    private String getItemLabel(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getLabel).orElse("");
    }

    private String getItemIconURL(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getImagePath).orElse("");
    }

    private String getItemKind(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getKind).orElse("");
    }

    private String getItemId(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getId).orElse("");
    }

    private String getOwnerId(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, EObject.class).map(this.objectService::getId).orElse("");
    }

    private Function<VariableManager, String> getLabelProvider() {
        return new EStructuralFeatureLabelProvider(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, this.composedAdapterFactory);
    }


}
