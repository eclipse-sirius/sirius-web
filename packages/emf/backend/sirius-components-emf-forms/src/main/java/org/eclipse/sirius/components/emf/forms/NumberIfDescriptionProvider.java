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
package org.eclipse.sirius.components.emf.forms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.emf.forms.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Provides the default description of the widget to use to support numbers.
 *
 * @author sbegaudeau
 */
public class NumberIfDescriptionProvider {

    private static final String TEXTFIELD_DESCRIPTION_ID = "Textfield";

    private final EDataType eDataType;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final IEMFMessageService emfMessageService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    public NumberIfDescriptionProvider(EDataType eDataType, ComposedAdapterFactory composedAdapterFactory, IPropertiesValidationProvider propertiesValidationProvider,
            IEMFMessageService emfMessageService, Function<VariableManager, String> semanticTargetIdProvider) {
        this.eDataType = Objects.requireNonNull(eDataType);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.emfMessageService = Objects.requireNonNull(emfMessageService);
        this.semanticTargetIdProvider = Objects.requireNonNull(semanticTargetIdProvider);
    }

    public IfDescription getIfDescription() {
        return IfDescription.newIfDescription(this.eDataType.getName())
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .predicate(this.getPredicate())
                .controlDescriptions(List.of(this.getTextfieldDescription()))
                .build();
    }

    private Function<VariableManager, Boolean> getPredicate() {
        return variableManager -> {
            var optionalEAttribute = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            return optionalEAttribute.filter(eAttribute -> eAttribute.getEType().equals(this.eDataType)).isPresent();
        };
    }

    private TextfieldDescription getTextfieldDescription() {
        return TextfieldDescription.newTextfieldDescription(TEXTFIELD_DESCRIPTION_ID)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(this.getIdProvider())
                .labelProvider(this.getLabelProvider())
                .valueProvider(this.getValueProvider())
                .newValueHandler(this.getNewValueHandler())
                .diagnosticsProvider(this.propertiesValidationProvider.getDiagnosticsProvider())
                .kindProvider(this.propertiesValidationProvider.getKindProvider())
                .messageProvider(this.propertiesValidationProvider.getMessageProvider())
                .build();
    }

    private Function<VariableManager, String> getIdProvider() {
        return variableManager -> {
            var eObjectPart = variableManager.get(VariableManager.SELF, EObject.class);
            var featurePart = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EObject.class);
            return this.getEObjectId(eObjectPart) + this.getEObjectId(featurePart);
        };
    }

    private String getEObjectId(Optional<EObject> potentialEObject) {
        return potentialEObject.map(EcoreUtil::getURI)
                .map(Object::toString)
                .orElse("");
    }

    private Function<VariableManager, String> getLabelProvider() {
        return new EStructuralFeatureLabelProvider(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, this.composedAdapterFactory);
    }

    private Function<VariableManager, String> getValueProvider() {
        return variableManager -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEAttribute = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            if (optionalEObject.isPresent() && optionalEAttribute.isPresent()) {
                EObject eObject = optionalEObject.get();
                EAttribute eAttribute = optionalEAttribute.get();

                Object value = eObject.eGet(eAttribute);
                return EcoreUtil.convertToString(this.eDataType, value);
            }

            return "";
        };
    }

    private BiFunction<VariableManager, String, IStatus> getNewValueHandler() {
        return (variableManager, newValue) -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEAttribute = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);

            IStatus result = new Failure("");
            if (optionalEObject.isPresent() && optionalEAttribute.isPresent()) {
                EObject eObject = optionalEObject.get();
                EAttribute eAttribute = optionalEAttribute.get();

                try {
                    Object value = EcoreUtil.createFromString(this.eDataType, newValue);
                    eObject.eSet(eAttribute, value);
                    result = new Success();
                } catch (NumberFormatException nfe) {
                    result = new Failure(this.emfMessageService.invalidNumber(newValue));
                }
            }
            return result;
        };
    }

}
