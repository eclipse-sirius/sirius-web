/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.compatibility.emf.properties.EStructuralFeatureLabelProvider;
import org.eclipse.sirius.components.compatibility.emf.properties.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Provides the default description of the widget to use to support an EString feature.
 *
 * @author sbegaudeau
 */
public class CustomizableEStringIfDescriptionProvider {
    private static final String IF_DESCRIPTION_ID = "EString";

    private static final String TEXTAREA_DESCRIPTION_ID = "Textarea";

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final ITextfieldCustomizer customizer;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    public CustomizableEStringIfDescriptionProvider(ComposedAdapterFactory composedAdapterFactory, IPropertiesValidationProvider propertiesValidationProvider, ITextfieldCustomizer customizer, Function<VariableManager, String> semanticTargetIdProvider) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.customizer = Objects.requireNonNull(customizer);
        this.semanticTargetIdProvider = Objects.requireNonNull(semanticTargetIdProvider);
    }

    public IfDescription getIfDescription() {
        // @formatter:off
        return IfDescription.newIfDescription(IF_DESCRIPTION_ID)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .predicate(this.getPredicate())
                .controlDescriptions(List.of(this.getTextareaDescription()))
                .build();
        // @formatter:on
    }

    private Function<VariableManager, Boolean> getPredicate() {
        return variableManager -> {
            var optionalEAttribute = variableManager.get(ViewPropertiesDescriptionRegistryConfigurer.ESTRUCTURAL_FEATURE, EAttribute.class);
            return optionalEAttribute.filter(eAttribute -> {
                EClassifier eType = eAttribute.getEType();
                boolean isTextfield = eType.equals(EcorePackage.Literals.ESTRING) || Objects.equals(eType.getInstanceClassName(), String.class.getName());
                return isTextfield && this.customizer.handles(eAttribute);
            }).isPresent();
        };
    }

    private TextareaDescription getTextareaDescription() {
        // @formatter:off
        var builder = TextareaDescription.newTextareaDescription(TEXTAREA_DESCRIPTION_ID)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(new WidgetIdProvider())
                .labelProvider(this.getLabelProvider())
                .isReadOnlyProvider(variableManager -> false)
                .valueProvider(this.getValueProvider())
                .newValueHandler(this.getNewValueHandler())
                .diagnosticsProvider(this.propertiesValidationProvider.getDiagnosticsProvider())
                .kindProvider(this.propertiesValidationProvider.getKindProvider())
                .messageProvider(this.propertiesValidationProvider.getMessageProvider());
        // @formatter:on
        Optional.ofNullable(this.customizer.getStyleProvider()).ifPresent(builder::styleProvider);
        Optional.ofNullable(this.customizer.getCompletionProposalsProvider()).ifPresent(builder::completionProposalsProvider);
        return builder.build();
    }

    private Function<VariableManager, String> getLabelProvider() {
        return new EStructuralFeatureLabelProvider(ViewPropertiesDescriptionRegistryConfigurer.ESTRUCTURAL_FEATURE, this.composedAdapterFactory);
    }

    private Function<VariableManager, String> getValueProvider() {
        return variableManager -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEAttribute = variableManager.get(ViewPropertiesDescriptionRegistryConfigurer.ESTRUCTURAL_FEATURE, EAttribute.class);

            if (optionalEObject.isPresent() && optionalEAttribute.isPresent()) {
                EObject eObject = optionalEObject.get();
                EAttribute eAttribute = optionalEAttribute.get();

                Object value = eObject.eGet(eAttribute);
                if (value != null && !eAttribute.isMany()) {
                    return EcoreUtil.convertToString(EcorePackage.Literals.ESTRING, value);
                }
            }

            return "";
        };
    }

    private BiFunction<VariableManager, String, IStatus> getNewValueHandler() {
        return (variableManager, newValue) -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEAttribute = variableManager.get(ViewPropertiesDescriptionRegistryConfigurer.ESTRUCTURAL_FEATURE, EAttribute.class);
            if (optionalEObject.isPresent() && optionalEAttribute.isPresent()) {
                EObject eObject = optionalEObject.get();
                EAttribute eAttribute = optionalEAttribute.get();

                String value = EcoreUtil.createFromString(EcorePackage.Literals.ESTRING, newValue).toString();
                eObject.eSet(eAttribute, value);

                return new Success();
            }
            return new Failure("");
        };
    }
}
