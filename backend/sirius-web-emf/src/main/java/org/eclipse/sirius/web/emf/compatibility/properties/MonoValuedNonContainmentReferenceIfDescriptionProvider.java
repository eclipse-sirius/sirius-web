/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.emf.compatibility.properties;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.web.compat.forms.WidgetIdProvider;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.emf.compatibility.properties.api.IPropertiesValidationProvider;
import org.eclipse.sirius.web.forms.components.SelectComponent;
import org.eclipse.sirius.web.forms.description.IfDescription;
import org.eclipse.sirius.web.forms.description.SelectDescription;
import org.eclipse.sirius.web.representations.Failure;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides the default description of the widget to use to support mono-valued non-containment reference.
 *
 * @author sbegaudeau
 */
public class MonoValuedNonContainmentReferenceIfDescriptionProvider {

    private static final String ID_DESCRIPTION_ID = "MonoValued NonContainment Reference"; //$NON-NLS-1$

    private static final String SELECT_DESCRIPTION_ID = "Select"; //$NON-NLS-1$

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IObjectService objectService;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final Logger logger = LoggerFactory.getLogger(MonoValuedNonContainmentReferenceIfDescriptionProvider.class);

    public MonoValuedNonContainmentReferenceIfDescriptionProvider(ComposedAdapterFactory composedAdapterFactory, IObjectService objectService,
            IPropertiesValidationProvider propertiesValidationProvider) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.objectService = Objects.requireNonNull(objectService);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
    }

    public IfDescription getIfDescription() {
        // @formatter:off
        return IfDescription.newIfDescription(ID_DESCRIPTION_ID)
                .predicate(this.getPredicate())
                .widgetDescription(this.getSelectDescription())
                .build();
        // @formatter:on
    }

    private Function<VariableManager, Boolean> getPredicate() {
        return variableManager -> {
            var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);
            return optionalEReference.filter(eReference -> {
                boolean isCandidate = true;
                isCandidate = isCandidate && !eReference.isMany();
                isCandidate = isCandidate && !eReference.isContainment();
                return isCandidate;
            }).isPresent();
        };
    }

    private SelectDescription getSelectDescription() {
        // @formatter:off
        return SelectDescription.newSelectDescription(SELECT_DESCRIPTION_ID)
                .idProvider(new WidgetIdProvider())
                .labelProvider(this.getLabelProvider())
                .valueProvider(this.getValueProvider())
                .optionsProvider(this.getOptionsProvider())
                .optionIdProvider(this.getOptionIdProvider())
                .optionLabelProvider(this.getOptionLabelProvider())
                .newValueHandler(this.getNewValueHandler())
                .diagnosticsProvider(this.propertiesValidationProvider.getDiagnosticsProvider())
                .kindProvider(this.propertiesValidationProvider.getKindProvider())
                .messageProvider(this.propertiesValidationProvider.getMessageProvider())
                .build();
        // @formatter:on
    }

    private BiFunction<VariableManager, String, IStatus> getNewValueHandler() {
        return (variableManager, newValue) -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);
            var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);

            IStatus result = new Failure(""); //$NON-NLS-1$
            if (optionalEObject.isPresent() && optionalEReference.isPresent()) {
                EObject eObject = optionalEObject.get();
                EReference eReference = optionalEReference.get();

                if (newValue == null || newValue.isBlank()) {
                    try {
                        eObject.eUnset(eReference);
                        result = new Success();
                    } catch (IllegalArgumentException | ClassCastException | ArrayStoreException exception) {
                        this.logger.warn(exception.getMessage(), exception);
                    }
                } else {
                    // @formatter:off
                    var optionalNewValueToSet = optionalEditingContext.flatMap(context -> this.objectService.getObject(context, newValue))
                            .filter(EObject.class::isInstance)
                            .map(EObject.class::cast);
                    // @formatter:on
                    if (optionalNewValueToSet.isPresent()) {
                        EObject newValueToSet = optionalNewValueToSet.get();
                        try {
                            eObject.eSet(eReference, newValueToSet);
                            result = new Success();
                        } catch (IllegalArgumentException | ClassCastException | ArrayStoreException exception) {
                            this.logger.warn(exception.getMessage(), exception);
                        }
                    }
                }
            }
            return result;
        };
    }

    private Function<VariableManager, String> getLabelProvider() {
        return new EStructuralFeatureLabelProvider(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, this.composedAdapterFactory);
    }

    private Function<VariableManager, String> getValueProvider() {
        return variableManager -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            Object eStructuralFeature = variableManager.getVariables().get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE);

            if (object instanceof EObject && eStructuralFeature instanceof EReference) {
                EObject eObject = (EObject) object;
                EReference eReference = (EReference) eStructuralFeature;

                Object value = eObject.eGet(eReference);
                if (value != null) {
                    return this.objectService.getId(value);
                }
            }
            return null;
        };
    }

    private Function<VariableManager, List<Object>> getOptionsProvider() {
        return new EStructuralFeatureChoiceOfValueProvider(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, this.composedAdapterFactory);
    }

    private Function<VariableManager, String> getOptionIdProvider() {
        return variableManager -> {
            Object object = variableManager.getVariables().get(SelectComponent.CANDIDATE_VARIABLE);
            String objectId = this.objectService.getId(object);
            return objectId;
        };
    }

    private Function<VariableManager, String> getOptionLabelProvider() {
        return variableManager -> {
            Object object = variableManager.getVariables().get(SelectComponent.CANDIDATE_VARIABLE);
            String objectLabel = this.objectService.getFullLabel(object);
            return objectLabel;
        };
    }

}
