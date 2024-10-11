/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.emf.forms.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.forms.DateTimeType;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.DateTimeDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Provides the default description of the widget to use to support DataType feature of type java.time.LocalDate.
 *
 * @author lfasani
 */
public class LocalDateIfDescriptionProvider {

    private static final String IF_DESCRIPTION_ID = "java.time.LocalDate";

    private static final String DATE_DESCRIPTION_ID = "Date";

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    public LocalDateIfDescriptionProvider(ComposedAdapterFactory composedAdapterFactory, IPropertiesValidationProvider propertiesValidationProvider, Function<VariableManager, String> semanticTargetIdProvider) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.semanticTargetIdProvider = Objects.requireNonNull(semanticTargetIdProvider);
    }

    public IfDescription getIfDescription() {
        return IfDescription.newIfDescription(IF_DESCRIPTION_ID)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .predicate(this.getPredicate())
                .controlDescriptions(List.of(this.getDateTimeDescription()))
                .build();
    }

    private Function<VariableManager, Boolean> getPredicate() {
        return variableManager -> {
            var optionalEAttribute = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            return optionalEAttribute.filter(eAttribute -> {
                EClassifier eType = eAttribute.getEType();
                return !eAttribute.isMany() && Objects.equals(eType.getInstanceClassName(), LocalDate.class.getName());
            }).isPresent();
        };
    }

    private DateTimeDescription getDateTimeDescription() {
        return DateTimeDescription.newDateTimeDescription(DATE_DESCRIPTION_ID)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .labelProvider(this.getLabelProvider())
                .stringValueProvider(this.getValueProvider())
                .newValueHandler(this.getNewValueHandler())
                .diagnosticsProvider(this.propertiesValidationProvider.getDiagnosticsProvider())
                .kindProvider(this.propertiesValidationProvider.getKindProvider())
                .messageProvider(this.propertiesValidationProvider.getMessageProvider())
                .type(DateTimeType.DATE)
                .isReadOnlyProvider(this.getIsReadOnlyProvider())
                .build();
    }

    private Function<VariableManager, Boolean> getIsReadOnlyProvider() {
        return variableManager -> variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class)
                .map(eAttribute -> !eAttribute.isChangeable())
                .orElse(false);
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
                if (value instanceof LocalDate localDate) {
                    return DateTimeFormatter.ISO_LOCAL_DATE.format(localDate);
                }
            }
            return "";
        };
    }

    private BiFunction<VariableManager, String, IStatus> getNewValueHandler() {
        return (variableManager, newValue) -> {
            IStatus status = new Failure("");
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEAttribute = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            if (optionalEObject.isPresent() && optionalEAttribute.isPresent()) {
                EObject eObject = optionalEObject.get();
                EAttribute eAttribute = optionalEAttribute.get();

                if (newValue == null || newValue.isBlank()) {
                    eObject.eSet(eAttribute, null);
                    status = new Success();
                } else {
                    LocalDate localDate = LocalDate.parse(newValue);
                    eObject.eSet(eAttribute, localDate);
                    status = new Success();
                }
            }
            return status;
        };
    }
}
