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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.AQLTextfieldCustomizer;
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
    private final AQLTextfieldCustomizer aqlTextfieldCustomizer;
    public PropertiesWidgetCreationService(IPropertiesConfigurerService propertiesConfigurerService, IObjectService objectService, AQLTextfieldCustomizer aqlTextfieldCustomizer) {
        this.propertiesConfigurerService = Objects.requireNonNull(propertiesConfigurerService);
        this.objectService = Objects.requireNonNull(objectService);
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
                .completionProposalsProvider(aqlTextfieldCustomizer.getCompletionProposalsProvider())
                .styleProvider(aqlTextfieldCustomizer.getStyleProvider())
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
    }

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
                .settingProvider(variableManager -> this.resolveSetting(variableManager, feature))
                .styleProvider(variableManager -> null)
                .ownerIdProvider(variableManager -> variableManager.get(VariableManager.SELF, EObject.class).map(this.objectService::getId).orElse(""))
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(feature))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
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
}
