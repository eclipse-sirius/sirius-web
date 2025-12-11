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

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.emf.forms.api.IEMFFormIfDescriptionProvider;
import org.eclipse.sirius.components.emf.forms.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.emf.forms.api.IWidgetReadOnlyProvider;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Provides the default description of the widget to use to support an EBoolean feature.
 *
 * @author sbegaudeau
 */
@Service
public class EBooleanIfDescriptionProvider implements IEMFFormIfDescriptionProvider {

    public static final String IF_DESCRIPTION_ID = "EBoolean";

    private static final String CHECKBOX_DESCRIPTION_ID = "Checkbox";

    private final IIdentityService identityService;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final IWidgetReadOnlyProvider widgetReadOnlyProvider;

    public EBooleanIfDescriptionProvider(IIdentityService identityService, IPropertiesValidationProvider propertiesValidationProvider, IWidgetReadOnlyProvider widgetReadOnlyProvider) {
        this.identityService = Objects.requireNonNull(identityService);
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
                .controlDescriptions(List.of(this.getCheckboxDescription()))
                .build());
    }

    private Function<VariableManager, Boolean> getPredicate() {
        return variableManager -> {
            var optionalEAttribute = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            return optionalEAttribute.filter(eAttribute -> !eAttribute.isMany()
                    && (Objects.equals(eAttribute.getEType().getInstanceClassName(), EcorePackage.Literals.EBOOLEAN.getInstanceClassName())
                            || Objects.equals(eAttribute.getEType().getInstanceClassName(), EcorePackage.Literals.EBOOLEAN_OBJECT.getInstanceClassName()))).isPresent();
        };
    }

    private CheckboxDescription getCheckboxDescription() {
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        return CheckboxDescription.newCheckboxDescription(CHECKBOX_DESCRIPTION_ID)
                .targetObjectIdProvider(targetObjectIdProvider)
                .idProvider(new WidgetIdProvider())
                .labelProvider(this.getLabelProvider())
                .valueProvider(this.getValueProvider())
                .newValueHandler(this.getNewValueHandler())
                .diagnosticsProvider(this.propertiesValidationProvider.getDiagnosticsProvider())
                .kindProvider(this.propertiesValidationProvider.getKindProvider())
                .messageProvider(this.propertiesValidationProvider.getMessageProvider())
                .isReadOnlyProvider(this.widgetReadOnlyProvider)
                .build();
    }

    private Function<VariableManager, String> getLabelProvider() {
        return new EStructuralFeatureLabelProvider(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE);
    }

    private Function<VariableManager, Boolean> getValueProvider() {
        return variableManager -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEAttribute = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            if (optionalEObject.isPresent() && optionalEAttribute.isPresent()) {
                EObject eObject = optionalEObject.get();
                EAttribute eAttribute = optionalEAttribute.get();

                Object value = eObject.eGet(eAttribute);
                if (value != null) {
                    return Boolean.valueOf(value.toString());
                }
            }
            return Boolean.FALSE;
        };
    }

    private BiFunction<VariableManager, Boolean, IStatus> getNewValueHandler() {
        return (variableManager, newValue) -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEAttribute = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EAttribute.class);
            IStatus result = new Failure("");
            if (optionalEObject.isPresent() && optionalEAttribute.isPresent()) {
                EObject eObject = optionalEObject.get();
                EAttribute eAttribute = optionalEAttribute.get();

                if (newValue == null) {
                    eObject.eUnset(eAttribute);
                } else {
                    eObject.eSet(eAttribute, newValue);
                }
                result = new Success();
            }
            return result;
        };
    }
}
