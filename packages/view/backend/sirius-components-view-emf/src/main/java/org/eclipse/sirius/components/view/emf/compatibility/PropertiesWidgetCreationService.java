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
package org.eclipse.sirius.components.view.emf.compatibility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.AQLTextfieldCustomizer;
import org.eclipse.sirius.components.view.emf.configuration.ViewPropertiesDescriptionServiceConfiguration;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetComponent;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Customizes the properties view for some of the View DSL elements.
 *
 * @author mcharfadi
 */
@Service
public class PropertiesWidgetCreationService implements IPropertiesWidgetCreationService {

    private static final String EMPTY = "";
    private final IPropertiesConfigurerService propertiesConfigurerService;
    private final IObjectService objectService;
    private final IEditService editService;
    private final IEMFKindService emfKindService;
    private final IFeedbackMessageService feedbackMessageService;
    private final AQLTextfieldCustomizer aqlTextfieldCustomizer;

    public PropertiesWidgetCreationService(IPropertiesConfigurerService propertiesConfigurerService, ViewPropertiesDescriptionServiceConfiguration parameters, AQLTextfieldCustomizer aqlTextfieldCustomizer) {
        this.propertiesConfigurerService = Objects.requireNonNull(propertiesConfigurerService);
        this.objectService = Objects.requireNonNull(parameters.getObjectService());
        this.editService = Objects.requireNonNull(parameters.getEditService());
        this.emfKindService = Objects.requireNonNull(parameters.getEmfKindService());
        this.feedbackMessageService = Objects.requireNonNull(parameters.getFeedbackMessageService());
        this.aqlTextfieldCustomizer = Objects.requireNonNull(aqlTextfieldCustomizer);
    }

    @Override
    public PageDescription createSimplePageDescription(String id, GroupDescription groupDescription, Predicate<VariableManager> canCreatePredicate) {
        return PageDescription.newPageDescription(id)
                .idProvider(variableManager -> "page")
                .labelProvider(variableManager -> "Properties")
                .semanticElementsProvider(this.propertiesConfigurerService.getSemanticElementsProvider())
                .canCreatePredicate(canCreatePredicate)
                .groupDescriptions(List.of(groupDescription))
                .build();
    }

    @Override
    public GroupDescription createSimpleGroupDescription(List<AbstractControlDescription> controls) {
        return GroupDescription.newGroupDescription("group")
                .idProvider(variableManager -> "group")
                .labelProvider(variableManager -> "General")
                .semanticElementsProvider(this.propertiesConfigurerService.getSemanticElementsProvider())
                .controlDescriptions(controls)
                .build();
    }

    @Override
    public CheckboxDescription createCheckbox(String id, String title, Function<Object, Boolean> reader, BiConsumer<Object, Boolean> writer, Object feature,  Optional<Function<VariableManager, String>> helpTextProvider) {
        Function<VariableManager, Boolean> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(reader).orElse(Boolean.FALSE);
        BiFunction<VariableManager, Boolean, IStatus> newValueHandler = (variableManager, newValue) -> {
            var optionalDiagramMapping = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalDiagramMapping.isPresent()) {
                writer.accept(optionalDiagramMapping.get(), newValue);
                return new Success();
            } else {
                return new Failure("");
            }
        };

        var builder = CheckboxDescription.newCheckboxDescription(id)
                .idProvider(variableManager -> id)
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .labelProvider(variableManager -> title)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(feature))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider());
        helpTextProvider.ifPresent(builder::helpTextProvider);
        return builder.build();
    }

    @Override
    public TextareaDescription createExpressionField(String id, String title, Function<Object, String> reader, BiConsumer<Object, String> writer, Object feature) {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(reader).orElse(EMPTY);
        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            var optionalDiagramMapping = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalDiagramMapping.isPresent()) {
                writer.accept(optionalDiagramMapping.get(), newValue);
                return new Success();
            } else {
                return new Failure("");
            }
        };

        return TextareaDescription.newTextareaDescription(id)
                .idProvider(variableManager -> id)
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .labelProvider(variableManager -> title)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(feature))
                .completionProposalsProvider(this.aqlTextfieldCustomizer.getCompletionProposalsProvider())
                .styleProvider(this.aqlTextfieldCustomizer.getStyleProvider())
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
    }

    @Override
    public TextfieldDescription createTextField(String id, String title, Function<Object, String> reader, BiConsumer<Object, String> writer, Object feature) {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(reader).orElse(EMPTY);
        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            var optionalDiagramMapping = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalDiagramMapping.isPresent()) {
                writer.accept(optionalDiagramMapping.get(), newValue);
                return new Success();
            } else {
                return new Failure("");
            }
        };

        return TextfieldDescription.newTextfieldDescription(id)
                .idProvider(variableManager -> id)
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .labelProvider(variableManager -> title)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(feature))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
    }

    @Override
    public ReferenceWidgetDescription createReferenceWidget(String id, String label, Object feature, Function<VariableManager, List<?>> optionsProvider) {
        return ReferenceWidgetDescription.newReferenceWidgetDescription(id)
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .idProvider(variableManager -> id)
                .labelProvider(variableManager -> label)
                .optionsProvider(optionsProvider)
                .iconURLProvider(variableManager -> "")
                .itemsProvider(variableManager -> this.getReferenceValue(variableManager, feature))
                .itemIdProvider(variableManager -> this.getItem(variableManager).map(this.objectService::getId).orElse(""))
                .itemKindProvider(variableManager -> this.getItem(variableManager).map(this.objectService::getKind).orElse(""))
                .itemLabelProvider(variableManager -> this.getItem(variableManager).map(this.objectService::getLabel).orElse(""))
                .itemImageURLProvider(variableManager -> this.getItem(variableManager).map(this.objectService::getImagePath).orElse(""))
                .ownerKindProvider(variableManager -> this.getTypeName(variableManager, feature))
                .referenceKindProvider(variableManager -> this.getReferenceKind(variableManager, feature))
                .isContainmentProvider(variableManager -> this.isContainment(variableManager, feature))
                .isManyProvider(variableManager -> this.isMany(variableManager, feature))
                .styleProvider(variableManager -> null)
                .ownerIdProvider(variableManager -> variableManager.get(VariableManager.SELF, EObject.class).map(this.objectService::getId).orElse(""))
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(feature))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .clearHandlerProvider(variableManager -> this.handleClearReference(variableManager, feature))
                .itemRemoveHandlerProvider(variableManager -> this.handleRemoveValue(variableManager, feature))
                .setHandlerProvider(variableManager -> this.handleSetReference(variableManager, feature))
                .addHandlerProvider(variableManager -> this.handleAddReferenceValues(variableManager, feature))
                .createElementHandlerProvider(variableManager -> this.handleCreateElement(variableManager, feature))
                .moveHandlerProvider(variableManager -> this.handleMoveReferenceValue(variableManager, feature))
                .build();
    }

    private Optional<Object> getItem(VariableManager variableManager) {
        return variableManager.get(ReferenceWidgetComponent.ITEM_VARIABLE, Object.class);
    }

    private List<?> getReferenceValue(VariableManager variableManager, Object feature) {
        List<?> value = List.of();
        EStructuralFeature.Setting setting = this.resolveSetting(variableManager, feature);
        if (setting != null) {
            var rawValue = setting.get(true);
            if (rawValue != null) {
                value = List.of(rawValue);
            } else {
                value = List.of();
            }
        }
        return value;
    }

    private EStructuralFeature.Setting resolveSetting(VariableManager variableManager, Object feature) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        if (referenceOwner != null && feature instanceof EReference reference) {
            return ((InternalEObject) referenceOwner).eSetting(reference);
        } else {
            return null;
        }
    }

    private String getTypeName(VariableManager variableManager, Object feature) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        if (referenceOwner != null && feature instanceof EReference reference) {
            return this.emfKindService.getKind(reference.getEContainingClass());
        }
        return "";
    }


    private String getReferenceKind(VariableManager variableManager, Object feature) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        if (referenceOwner != null && feature instanceof EReference reference) {
            return this.emfKindService.getKind(reference.getEReferenceType());
        }
        return "";
    }

    private boolean isContainment(VariableManager variableManager, Object feature) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        if (referenceOwner != null && feature instanceof EReference reference) {
            return reference.isContainment();
        }
        return false;
    }

    private boolean isMany(VariableManager variableManager, Object feature) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        if (referenceOwner != null && feature instanceof EReference reference) {
            return reference.isMany();
        }
        return false;
    }

    private IStatus createErrorStatus(String message) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(message, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }

    private IStatus handleClearReference(VariableManager variableManager, Object feature) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);

        if (referenceOwner != null  && feature instanceof EReference reference) {
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

    private IStatus handleRemoveValue(VariableManager variableManager, Object feature) {
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        Optional<Object> item = this.getItem(variableManager);

        if (referenceOwner != null  && feature instanceof EReference reference) {
            if (reference.isMany()) {
                ((List<?>) reference.eGet(reference)).remove(item.get());
            } else {
                reference.eUnset(reference);
            }
        } else {
            return this.createErrorStatus("Something went wrong while removing a reference value.");
        }
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private IStatus handleSetReference(VariableManager variableManager, Object feature) {
        IStatus result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        Optional<Object> item = variableManager.get(ReferenceWidgetComponent.NEW_VALUE, Object.class);

        if (referenceOwner != null  && feature instanceof EReference reference) {
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

    private IStatus handleAddReferenceValues(VariableManager variableManager, Object feature) {
        IStatus result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        Optional<List<Object>> newValues = variableManager.get(ReferenceWidgetComponent.NEW_VALUE, (Class<List<Object>>) (Class<?>) List.class);

        if (newValues.isEmpty()) {
            result = this.createErrorStatus("Something went wrong while adding reference values.");
        } else if (referenceOwner != null  && feature instanceof EReference reference) {
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

    private Object handleCreateElement(VariableManager variableManager, Object feature) {
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

    private IStatus handleMoveReferenceValue(VariableManager variableManager, Object feature) {
        IStatus result = this.createErrorStatus("Something went wrong while reordering reference values.");
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        Optional<Object> item = this.getItem(variableManager);
        Optional<Integer> fromIndex = variableManager.get(ReferenceWidgetComponent.MOVE_FROM_VARIABLE, Integer.class);
        Optional<Integer> toIndex = variableManager.get(ReferenceWidgetComponent.MOVE_TO_VARIABLE, Integer.class);

        if (referenceOwner != null  && feature instanceof EReference reference) {
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
