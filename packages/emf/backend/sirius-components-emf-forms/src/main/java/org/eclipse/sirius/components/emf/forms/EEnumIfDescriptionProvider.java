/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.emf.forms.api.IEMFFormIfDescriptionProvider;
import org.eclipse.sirius.components.emf.forms.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.emf.forms.api.IWidgetReadOnlyProvider;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.forms.description.RadioDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Provides the default description of the widget to use to support an EEnum feature.
 *
 * @author sbegaudeau
 */
@Service
public class EEnumIfDescriptionProvider implements IEMFFormIfDescriptionProvider {

    public static final String IF_DESCRIPTION_ID = "EEnum";

    private static final String RADIO_DESCRIPTION_ID = "Radio";

    private final Logger logger = LoggerFactory.getLogger(EEnumIfDescriptionProvider.class);

    private final IIdentityService identityService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final IWidgetReadOnlyProvider widgetReadOnlyProvider;

    public EEnumIfDescriptionProvider(IIdentityService identityService, ComposedAdapterFactory composedAdapterFactory, IPropertiesValidationProvider propertiesValidationProvider, IWidgetReadOnlyProvider widgetReadOnlyProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.widgetReadOnlyProvider = Objects.requireNonNull(widgetReadOnlyProvider);
    }

    @Override
    public List<IfDescription> getIfDescriptions() {
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        return List.of(IfDescription.newIfDescription(IF_DESCRIPTION_ID)
                .targetObjectIdProvider(targetObjectIdProvider)
                .predicate(this.getPredicate())
                .controlDescriptions(List.of(this.getRadioDescription()))
                .build());
    }

    private Function<VariableManager, Boolean> getPredicate() {
        return variableManager -> {
            var optionalEAttribute = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            return optionalEAttribute.map(EAttribute::getEType)
                    .filter(EEnum.class::isInstance)
                    .isPresent();
        };
    }

    private RadioDescription getRadioDescription() {
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        return RadioDescription.newRadioDescription(RADIO_DESCRIPTION_ID)
                .targetObjectIdProvider(targetObjectIdProvider)
                .idProvider(new WidgetIdProvider())
                .labelProvider(this.getLabelProvider())
                .optionsProvider(this.getOptionsProvider())
                .optionSelectedProvider(this.getOptionSelectedProvider())
                .optionIdProvider(this.getOptionIdProvider())
                .optionLabelProvider(this.getOptionLabelProvider())
                .newValueHandler(this.getNewValueHandler())
                .diagnosticsProvider(this.propertiesValidationProvider.getDiagnosticsProvider())
                .kindProvider(this.propertiesValidationProvider.getKindProvider())
                .messageProvider(this.propertiesValidationProvider.getMessageProvider())
                .isReadOnlyProvider(this.widgetReadOnlyProvider)
                .build();
    }

    private Function<VariableManager, String> getLabelProvider() {
        return new EStructuralFeatureLabelProvider(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, this.composedAdapterFactory);
    }

    private Function<VariableManager, List<?>> getOptionsProvider() {
        return variableManager -> {
            Object feature = variableManager.getVariables().get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE);
            if (feature instanceof EAttribute) {
                EDataType eEnum = ((EAttribute) feature).getEAttributeType();
                if (eEnum instanceof EEnum) {
                    return ((EEnum) eEnum).getELiterals().stream()
                            .map(EEnumLiteral::getInstance)
                            .toList();
                }
            }
            return new ArrayList<>();
        };
    }

    private Function<VariableManager, String> getOptionIdProvider() {
        return variableManager -> {
            Object litteral = variableManager.getVariables().get(SelectComponent.CANDIDATE_VARIABLE);
            if (litteral instanceof Enumerator) {
                return Integer.valueOf(((Enumerator) litteral).getValue()).toString();
            }
            return "";
        };
    }

    private Function<VariableManager, String> getOptionLabelProvider() {
        return new EEnumLiteralLabelProvider(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, this.composedAdapterFactory);
    }

    private Function<VariableManager, Boolean> getOptionSelectedProvider() {
        return variableManager -> {
            var optionalEnumerator = variableManager.get(SelectComponent.CANDIDATE_VARIABLE, Enumerator.class);
            if (optionalEnumerator.isPresent()) {
                Enumerator enumerator = optionalEnumerator.get();
                String optionLitteralId = Integer.valueOf(enumerator.getValue()).toString();

                var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
                var optionalEStructuralFeature = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EStructuralFeature.class);
                if (optionalEObject.isPresent() && optionalEStructuralFeature.isPresent()) {
                    EObject eObject = optionalEObject.get();
                    EStructuralFeature eStructuralFeature = optionalEStructuralFeature.get();

                    Object value = eObject.eGet(eStructuralFeature);
                    if (value instanceof Enumerator) {
                        String selectedLitteralId = Integer.valueOf(((Enumerator) value).getValue()).toString();
                        return optionLitteralId.equals(selectedLitteralId);
                    }
                }
            }
            return false;
        };
    }

    private BiFunction<VariableManager, String, IStatus> getNewValueHandler() {
        return (variableManager, newValue) -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEAttribute = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);

            try {
                int id = Integer.valueOf(newValue).intValue();
                if (optionalEObject.isPresent() && optionalEAttribute.isPresent()) {
                    EObject eObject = optionalEObject.get();
                    EAttribute eAttribute = optionalEAttribute.get();
                    EClassifier eType = eAttribute.getEType();
                    if (eType instanceof EEnum eEnum) {
                        EEnumLiteral literal = eEnum.getEEnumLiteral(id);
                        if (literal != null) {
                            Object value = EcoreUtil.createFromString(eEnum, literal.getLiteral());
                            eObject.eSet(eAttribute, value);
                        }
                    }
                }
            } catch (NumberFormatException exception) {
                this.logger.warn(exception.getMessage(), exception);
                return new Failure("");
            }
            return new Success();
        };
    }

}
