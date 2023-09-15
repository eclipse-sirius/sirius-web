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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.compatibility.emf.properties.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetComponent;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription;

/**
 * Provides the default description of the widget to use to support non-containment reference.
 *
 * @author frouene
 */
public class NonContainmentReferenceIfDescriptionProvider {

    private static final String REFERENCE_WIDGET_DESCRIPTION_ID = "NonContainmentReferenceIfDescriptionProvider.ReferenceWidget";

    private static final String ID_DESCRIPTION_ID = "NonContainment Reference";

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IObjectService objectService;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final IEMFKindService emfKindService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IEditService editService;

    public NonContainmentReferenceIfDescriptionProvider(ComposedAdapterFactory composedAdapterFactory, IObjectService objectService, IEditService editService, IEMFKindService emfKindService,
            IFeedbackMessageService feedbackMessageService, IPropertiesValidationProvider propertiesValidationProvider, Function<VariableManager, String> semanticTargetIdProvider) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.semanticTargetIdProvider = Objects.requireNonNull(semanticTargetIdProvider);
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
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
                .iconURLProvider(variableManager -> List.of())
                .itemsProvider(this::getReferenceValue)
                .itemIdProvider(this::getItemId)
                .itemKindProvider(this::getItemKind)
                .itemLabelProvider(this::getItemLabel)
                .itemImageURLProvider(this::getItemIconURL)
                .ownerKindProvider(this::getTypeName)
                .referenceKindProvider(this::getReferenceKind)
                .isContainmentProvider(this::isContainment)
                .isManyProvider(this::isMany)
                .styleProvider(variableManager -> null)
                .ownerIdProvider(this::getOwnerId)
                .diagnosticsProvider(this.propertiesValidationProvider.getDiagnosticsProvider())
                .kindProvider(this.propertiesValidationProvider.getKindProvider())
                .messageProvider(this.propertiesValidationProvider.getMessageProvider())
                .clearHandlerProvider(this::handleClearReference)
                .itemRemoveHandlerProvider(this::handleRemoveValue)
                .setHandlerProvider(this::handleSetReference)
                .addHandlerProvider(this::handleAddReferenceValues)
                .createElementHandlerProvider(this::handleCreateElement)
                .moveHandlerProvider(this::handleMoveReferenceValue)
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

    private List<String> getItemIconURL(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getImagePath).orElse(List.of());
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

    private String getTypeName(VariableManager variableManager) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);

        if (referenceOwner != null && optionalEReference.isPresent()) {
            return this.emfKindService.getKind(optionalEReference.get().getEContainingClass());
        }
        return "";
    }

    private String getReferenceKind(VariableManager variableManager) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);

        if (referenceOwner != null && optionalEReference.isPresent()) {
            return this.emfKindService.getKind(optionalEReference.get().getEReferenceType());
        }
        return "";
    }

    private boolean isContainment(VariableManager variableManager) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);

        if (referenceOwner != null && optionalEReference.isPresent()) {
            return optionalEReference.get().isContainment();
        }
        return false;
    }

    private boolean isMany(VariableManager variableManager) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);

        if (referenceOwner != null && optionalEReference.isPresent()) {
            return optionalEReference.get().isMany();
        }
        return false;
    }

    private IStatus createErrorStatus(String message) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(message, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }

    private IStatus handleClearReference(VariableManager variableManager) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);

        if (referenceOwner != null && optionalEReference.isPresent()) {
            EReference reference = optionalEReference.get();
            if (reference.isMany()) {
                ((List<?>) referenceOwner.eGet(reference)).clear();
            } else {
                referenceOwner.eUnset(reference);
            }
        } else {
            return this.createErrorStatus("Something went wrong while clearing the reference.");
        }
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private IStatus handleRemoveValue(VariableManager variableManager) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);
        Optional<Object> item = this.getItem(variableManager);

        if (referenceOwner != null && optionalEReference.isPresent()) {
            EReference reference = optionalEReference.get();
            if (reference.isMany()) {
                ((List<?>) referenceOwner.eGet(reference)).remove(item.get());
            } else {
                referenceOwner.eUnset(reference);
            }
        } else {
            return this.createErrorStatus("Something went wrong while removing a reference value.");
        }
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private IStatus handleSetReference(VariableManager variableManager) {
        IStatus result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);
        Optional<Object> item = variableManager.get(ReferenceWidgetComponent.NEW_VALUE, Object.class);

        if (referenceOwner != null && optionalEReference.isPresent()) {
            EReference reference = optionalEReference.get();
            if (reference.isMany()) {
                result = this.createErrorStatus("Multiple-valued reference can only accept a list of values");
            } else {
                referenceOwner.eSet(reference, item.get());
            }
        } else {
            result = this.createErrorStatus("Something went wrong while setting the reference value.");
        }
        return result;
    }

    private IStatus handleAddReferenceValues(VariableManager variableManager) {
        IStatus result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);
        Optional<List<Object>> newValues = variableManager.get(ReferenceWidgetComponent.NEW_VALUE, (Class<List<Object>>) (Class<?>) List.class);

        if (newValues.isEmpty()) {
            result = this.createErrorStatus("Something went wrong while adding reference values.");
        } else if (referenceOwner != null && optionalEReference.isPresent()) {
            EReference reference = optionalEReference.get();
            if (reference.isMany()) {
                ((List<Object>) referenceOwner.eGet(reference)).addAll(newValues.get());
            } else {
                new Failure("Single-valued reference can only accept a single value");
            }
        } else {
            result = this.createErrorStatus("Something went wrong while adding reference values.");
        }
        return result;
    }

    private Object handleCreateElement(VariableManager variableManager) {
        Optional<Object> result = Optional.empty();
        Optional<Boolean> optionalIsChild = variableManager.get(ReferenceWidgetComponent.IS_CHILD_CREATION_VARIABLE, Boolean.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        String creationDescriptionId = variableManager.get(ReferenceWidgetComponent.CREATION_DESCRIPTION_ID_VARIABLE, String.class).orElse("");
        if (optionalIsChild.isPresent() && optionalEditingContext.isPresent()) {
            if (optionalIsChild.get()) {
                EObject parent = variableManager.get(ReferenceWidgetComponent.PARENT_VARIABLE, EObject.class).orElse(null);
                result = this.editService.createChild(optionalEditingContext.get(), parent, creationDescriptionId);
            } else {
                UUID documentId = variableManager.get(ReferenceWidgetComponent.DOCUMENT_ID_VARIABLE, UUID.class).orElse(UUID.randomUUID());
                String domainId = variableManager.get(ReferenceWidgetComponent.DOMAIN_ID_VARIABLE, String.class).orElse("");
                result = this.editService.createRootObject(optionalEditingContext.get(), documentId, domainId, creationDescriptionId);
            }
        }
        return result.orElse(null);
    }

    private IStatus handleMoveReferenceValue(VariableManager variableManager) {
        IStatus result = this.createErrorStatus("Something went wrong while reordering reference values.");
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        var optionalEReference = variableManager.get(PropertiesDefaultDescriptionProvider.ESTRUCTURAL_FEATURE, EReference.class);
        Optional<Object> item = this.getItem(variableManager);
        Optional<Integer> fromIndex = variableManager.get(ReferenceWidgetComponent.MOVE_FROM_VARIABLE, Integer.class);
        Optional<Integer> toIndex = variableManager.get(ReferenceWidgetComponent.MOVE_TO_VARIABLE, Integer.class);

        if (referenceOwner != null && optionalEReference.isPresent()) {
            EReference reference = optionalEReference.get();
            if (item.isPresent() && fromIndex.isPresent() && toIndex.isPresent()) {
                if (reference.isMany()) {
                    List<Object> values = (List<Object>) referenceOwner.eGet(reference);
                    var valueItem = values.get(fromIndex.get().intValue());
                    if (valueItem != null && valueItem.equals(item.get()) && (values instanceof EList<Object> eValues)) {
                        eValues.move(toIndex.get().intValue(), fromIndex.get().intValue());
                        result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
                    }
                } else {
                    result = this.createErrorStatus("Only values of multiple-valued references can be reordered.");
                }
            }
        }
        return result;
    }

}
