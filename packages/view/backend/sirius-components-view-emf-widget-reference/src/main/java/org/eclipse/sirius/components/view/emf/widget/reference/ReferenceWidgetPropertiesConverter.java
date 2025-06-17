/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.widget.reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IReferenceWidgetPropertiesConverter;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetComponent;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription.Builder;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetStyle;
import org.springframework.stereotype.Service;

/**
 * Used to convert the properties of the reference widget.
 *
 * @author sbegaudeau
 */
@Service
public class ReferenceWidgetPropertiesConverter implements IReferenceWidgetPropertiesConverter {

    private final IIdentityService identityService;

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final ILabelService labelService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IEMFKindService emfKindService;

    public ReferenceWidgetPropertiesConverter(IIdentityService identityService, IReadOnlyObjectPredicate readOnlyObjectPredicate, ILabelService labelService, ComposedAdapterFactory composedAdapterFactory, IEMFKindService emfKindService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.labelService = Objects.requireNonNull(labelService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.emfKindService = Objects.requireNonNull(emfKindService);
    }

    @Override
    public void convert(Builder referenceWidgetDescriptionBuilder, ReferenceWidgetDescription viewReferenceWidgetDescription, AQLInterpreter interpreter) {
        Function<VariableManager, String> semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(identityService::getId)
                .orElse(null);

        referenceWidgetDescriptionBuilder
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(semanticTargetIdProvider)
                .labelProvider(new StringValueProvider(interpreter, viewReferenceWidgetDescription.getLabelExpression()))
                .iconURLProvider(variableManager -> List.of())
                .isReadOnlyProvider(this.getReadOnlyValueProvider(interpreter, viewReferenceWidgetDescription.getIsEnabledExpression()))
                .itemsProvider(variableManager -> this.getReferenceValue(interpreter, viewReferenceWidgetDescription, variableManager))
                .optionsProvider(variableManager -> this.getReferenceOptions(interpreter, viewReferenceWidgetDescription, variableManager))
                .itemIdProvider(this::getItemId)
                .itemKindProvider(this::getItemKind)
                .itemLabelProvider(this::getItemLabel)
                .itemIconURLProvider(this::getItemIconURL)
                .ownerKindProvider(this::getOwnerKind)
                .referenceKindProvider(variableManager -> this.getReferenceKind(interpreter, variableManager, viewReferenceWidgetDescription))
                .isContainmentProvider(variableManager -> this.isContainment(interpreter, variableManager, viewReferenceWidgetDescription))
                .isManyProvider(variableManager -> this.isMany(interpreter, variableManager, viewReferenceWidgetDescription))
                .ownerIdProvider(variableManager -> this.getOwnerId(interpreter, viewReferenceWidgetDescription, variableManager))
                .styleProvider(this.getStyleProvider(viewReferenceWidgetDescription, interpreter))
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "");
    }

    private Function<VariableManager, Boolean> getReadOnlyValueProvider(AQLInterpreter interpreter, String expression) {
        return variableManager -> {
            boolean isReadOnly = variableManager.get(VariableManager.SELF, Object.class)
                    .filter(this.readOnlyObjectPredicate)
                    .isPresent();

            if (!isReadOnly && expression != null && !expression.isBlank()) {
                Result result = interpreter.evaluateExpression(variableManager.getVariables(), expression);
                isReadOnly = result.asBoolean()
                        .map(value -> !value)
                        .orElse(Boolean.FALSE);
            }
            return isReadOnly;
        };
    }

    private List<?> getReferenceValue(AQLInterpreter interpreter, ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        List<?> value = List.of();
        EStructuralFeature.Setting setting = this.resolveSetting(interpreter, referenceDescription, variableManager);
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

    private EStructuralFeature.Setting resolveSetting(AQLInterpreter interpreter, ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        EObject owner = this.getReferenceOwner(interpreter, variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = new StringValueProvider(interpreter, Optional.ofNullable(referenceDescription.getReferenceNameExpression()).orElse("")).apply(variableManager);

        if (owner != null && owner.eClass().getEStructuralFeature(referenceName) instanceof EReference reference) {
            return ((InternalEObject) owner).eSetting(reference);
        } else {
            return null;
        }
    }

    private List<?> getReferenceOptions(AQLInterpreter interpreter, ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        EObject owner = this.getReferenceOwner(interpreter, variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = new StringValueProvider(interpreter, Optional.ofNullable(referenceDescription.getReferenceNameExpression()).orElse("")).apply(variableManager);
        if (owner != null && owner.eClass().getEStructuralFeature(referenceName) instanceof EReference eReference) {
            Object adapter = this.composedAdapterFactory.adapt(owner, IItemPropertySource.class);
            if (adapter instanceof IItemPropertySource itemPropertySource) {
                IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(owner, eReference);
                List<?> referenceOptions;
                if (descriptor != null) {
                    referenceOptions = descriptor.getChoiceOfValues(owner).stream()
                            .filter(Objects::nonNull)
                            .toList();
                } else {
                    referenceOptions = Arrays.asList(ItemPropertyDescriptor.getReachableObjectsOfType(owner, eReference.getEReferenceType()).toArray());
                }
                return referenceOptions.stream()
                        .filter(option -> !this.isOptionAncestorAndContainmentReference((EObject) option, owner, eReference))
                        .toList();
            }
        }
        return new ArrayList<>();
    }

    private EObject getReferenceOwner(AQLInterpreter interpreter, VariableManager variableManager, String referenceOwnerExpression) {
        String safeValueExpression = Optional.ofNullable(referenceOwnerExpression).orElse("");
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        if (!safeValueExpression.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression);
            referenceOwner = result.asObject()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .orElse(referenceOwner);
        }
        return referenceOwner;
    }

    private boolean isOptionAncestorAndContainmentReference(EObject option, EObject self, EReference eReference) {
        return eReference.isContainment() && EcoreUtil.isAncestor(option, self);
    }

    private Optional<Object> getItem(VariableManager variableManager) {
        return variableManager.get(ReferenceWidgetComponent.ITEM_VARIABLE, Object.class);
    }

    private String getItemId(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.identityService::getId).orElse("");
    }

    private String getItemKind(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.identityService::getKind).orElse("");
    }

    private String getItemLabel(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.labelService::getStyledLabel).map(Object::toString).orElse("");
    }

    private List<String> getItemIconURL(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.labelService::getImagePaths).orElse(List.of());
    }

    private String getOwnerKind(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, EObject.class)
                .map(self -> this.emfKindService.getKind(self.eClass()))
                .orElse("");
    }

    private String getReferenceKind(AQLInterpreter interpreter, VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        return this.getReference(interpreter, variableManager, referenceDescription)
                .flatMap(reference -> Optional.of(this.emfKindService.getKind(reference.getEReferenceType())))
                .orElse("");
    }

    private boolean isContainment(AQLInterpreter interpreter, VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        return this.getReference(interpreter, variableManager, referenceDescription)
                .flatMap(reference -> Optional.of(reference.isContainment()))
                .orElse(false);
    }

    private boolean isMany(AQLInterpreter interpreter, VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        return this.getReference(interpreter, variableManager, referenceDescription)
                .flatMap(reference -> Optional.of(reference.isMany()))
                .orElse(false);
    }

    private Optional<EReference> getReference(AQLInterpreter interpreter, VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        EObject owner = this.getReferenceOwner(interpreter, variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = new StringValueProvider(interpreter, Optional.ofNullable(referenceDescription.getReferenceNameExpression()).orElse("")).apply(variableManager);
        return Optional.ofNullable(owner)
                .map(EObject::eClass)
                .map(klass -> klass.getEStructuralFeature(referenceName))
                .filter(EReference.class::isInstance)
                .map(EReference.class::cast);
    }

    private String getOwnerId(AQLInterpreter interpreter, ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        EObject owner = this.getReferenceOwner(interpreter, variableManager, referenceDescription.getReferenceOwnerExpression());
        return this.identityService.getId(owner);
    }

    private Function<VariableManager, ReferenceWidgetStyle> getStyleProvider(ReferenceWidgetDescription viewReferenceWidgetDescription, AQLInterpreter interpreter) {
        return variableManager -> {
            var effectiveStyle = viewReferenceWidgetDescription.getConditionalStyles().stream()
                    .filter(style -> interpreter.evaluateExpression(variableManager.getVariables(), style.getCondition()).asBoolean().orElse(Boolean.FALSE))
                    .map(ReferenceWidgetDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewReferenceWidgetDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ReferenceWidgetStyleProvider(effectiveStyle).apply(variableManager);
        };
    }
}
