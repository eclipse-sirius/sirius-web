/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf.form;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ComposedSwitch;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.compatibility.emf.DomainClassPredicate;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.ContainerBorderStyle;
import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based form description into an equivalent {@link FormDescription}.
 *
 * @author fbarbin
 */
@Service
public class ViewFormDescriptionConverter implements IRepresentationDescriptionConverter {

    public static final String NEW_VALUE = "newValue";

    private static final String DEFAULT_FORM_LABEL = "Form";

    private static final String DEFAULT_GROUP_LABEL = "";

    private static final String DEFAULT_PAGE_LABEL = "";

    private final IObjectService objectService;

    private final IEditService editService;

    private final IFormIdProvider formIdProvider;

    private final List<IWidgetConverterProvider> customWidgetConverterProviders;

    private final IFeedbackMessageService feedbackMessageService;

    public ViewFormDescriptionConverter(IObjectService objectService, IEditService editService, IFormIdProvider formIdProvider, List<IWidgetConverterProvider> customWidgetConverterProviders, IFeedbackMessageService feedbackMessageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
        this.customWidgetConverterProviders = Objects.requireNonNull(customWidgetConverterProviders);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.form.FormDescription;
    }

    @Override
    public IRepresentationDescription convert(RepresentationDescription representationDescription, List<RepresentationDescription> allRepresentationDescriptions, AQLInterpreter interpreter) {
        org.eclipse.sirius.components.view.form.FormDescription viewFormDescription = (org.eclipse.sirius.components.view.form.FormDescription) representationDescription;
        List<Switch<AbstractWidgetDescription>> widgetConverters = this.customWidgetConverterProviders.stream().map(provider -> provider.getWidgetConverter(interpreter,
                this.editService, this.objectService, this.feedbackMessageService)).toList();
        Switch<AbstractWidgetDescription> dispatcher = new ViewFormDescriptionConverterSwitch(interpreter, this.editService, this.objectService, new ComposedSwitch<>(widgetConverters), this.feedbackMessageService);

        List<PageDescription> pageDescriptions = viewFormDescription.getPages()
                .stream()
                .map(p -> this.instantiatePage(p, dispatcher, interpreter))
                .toList();

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> this.self(variableManager)
                .map(this.objectService::getId)
                .orElse(null);

        return FormDescription.newFormDescription(this.formIdProvider.getId(viewFormDescription))
                .label(Optional.ofNullable(viewFormDescription.getName()).orElse(DEFAULT_FORM_LABEL))
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> this.computeFormLabel(viewFormDescription, variableManager, interpreter))
                .canCreatePredicate(variableManager -> this.canCreate(viewFormDescription.getDomainType(), viewFormDescription.getPreconditionExpression(), variableManager, interpreter))
                .targetObjectIdProvider(targetObjectIdProvider)
                .pageDescriptions(pageDescriptions)
                .build();
    }

    private PageDescription instantiatePage(org.eclipse.sirius.components.view.form.PageDescription viewPageDescription, Switch<AbstractWidgetDescription> dispatcher,
            AQLInterpreter interpreter) {

        List<GroupDescription> groupDescriptions = viewPageDescription.getGroups().stream()
                .map(g -> this.instantiateGroup(g, dispatcher, interpreter))
                .toList();

        List<ButtonDescription> toolbarActionDescriptions = viewPageDescription.getToolbarActions().stream()
                .map(dispatcher::doSwitch)
                .filter(ButtonDescription.class::isInstance)
                .map(ButtonDescription.class::cast)
                .toList();

        String descriptionId = this.getDescriptionId(viewPageDescription);
        return PageDescription.newPageDescription(descriptionId)
                .idProvider(this.getIdProvider(descriptionId))
                .labelProvider(variableManager -> this.computePageLabel(viewPageDescription, variableManager, interpreter))
                .semanticElementsProvider(variableManager -> this.getSemanticElementsProvider(viewPageDescription, variableManager, interpreter))
                .canCreatePredicate(variableManager -> this.canCreate(viewPageDescription.getDomainType(), viewPageDescription.getPreconditionExpression(), variableManager, interpreter))
                .groupDescriptions(groupDescriptions)
                .toolbarActionDescriptions(toolbarActionDescriptions)
                .build();
    }

    private GroupDescription instantiateGroup(org.eclipse.sirius.components.view.form.GroupDescription viewGroupDescription, Switch<AbstractWidgetDescription> dispatcher, AQLInterpreter interpreter) {
        List<AbstractControlDescription> controlDescriptions = viewGroupDescription.getWidgets().stream()
                .map(dispatcher::doSwitch)
                .filter(Objects::nonNull)
                .map(AbstractControlDescription.class::cast)
                .toList();

        List<ButtonDescription> toolbarActionDescriptions = viewGroupDescription.getToolbarActions().stream()
                .map(dispatcher::doSwitch)
                .filter(ButtonDescription.class::isInstance)
                .map(ButtonDescription.class::cast)
                .toList();

        String descriptionId = this.getDescriptionId(viewGroupDescription);

        Function<VariableManager, ContainerBorderStyle> borderStyleProvider = variableManager -> {
            var effectiveStyle = viewGroupDescription.getConditionalBorderStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), variableManager, interpreter))
                    .map(org.eclipse.sirius.components.view.form.ContainerBorderStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewGroupDescription::getBorderStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ContainerBorderStyleProvider(effectiveStyle).apply(variableManager);
        };

        return GroupDescription.newGroupDescription(descriptionId)
                .idProvider(this.getIdProvider(descriptionId))
                .labelProvider(variableManager -> this.computeGroupLabel(viewGroupDescription, variableManager, interpreter))
                .semanticElementsProvider(variableManager -> this.getSemanticElementsProvider(viewGroupDescription, variableManager, interpreter))
                .controlDescriptions(controlDescriptions)
                .toolbarActionDescriptions(toolbarActionDescriptions)
                .displayModeProvider(variableManager -> this.getGroupDisplayMode(viewGroupDescription))
                .borderStyleProvider(borderStyleProvider)
                .build();
    }

    private Function<VariableManager, String> getIdProvider(String descriptionId) {
        return variableManager -> {
            String selfId = variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse("");
            return UUID.nameUUIDFromBytes((selfId + descriptionId).getBytes()).toString();
        };
    }

    private String computeFormLabel(org.eclipse.sirius.components.view.form.FormDescription viewFormDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        return this.evaluateString(interpreter, variableManager, viewFormDescription.getTitleExpression()).orElse(DEFAULT_FORM_LABEL);
    }

    private String computePageLabel(org.eclipse.sirius.components.view.form.PageDescription viewPageDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        return this.evaluateString(interpreter, variableManager, viewPageDescription.getLabelExpression()).orElse(DEFAULT_PAGE_LABEL);
    }

    private String computeGroupLabel(org.eclipse.sirius.components.view.form.GroupDescription viewGroupDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        return this.evaluateString(interpreter, variableManager, viewGroupDescription.getLabelExpression()).orElse(DEFAULT_GROUP_LABEL);
    }

    private List<?> getSemanticElementsProvider(org.eclipse.sirius.components.view.form.GroupDescription viewGroupDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Result result = interpreter.evaluateExpression(variableManager.getVariables(), viewGroupDescription.getSemanticCandidatesExpression());
        List<Object> candidates = result.asObjects().orElse(List.of());
        return candidates.stream()
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .toList();
    }

    private List<?> getSemanticElementsProvider(org.eclipse.sirius.components.view.form.PageDescription viewPageDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Result result = interpreter.evaluateExpression(variableManager.getVariables(), viewPageDescription.getSemanticCandidatesExpression());
        List<Object> candidates = result.asObjects().orElse(List.of());
        return candidates.stream()
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .toList();
    }

    private Optional<String> evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString();
    }

    private boolean canCreate(String domainType, String preconditionExpression, VariableManager variableManager, AQLInterpreter interpreter) {
        boolean result = false;
        Optional<EClass> optionalEClass = variableManager.get(VariableManager.SELF, EObject.class)
                .map(EObject::eClass)
                .filter(new DomainClassPredicate(domainType));
        if (optionalEClass.isPresent()) {
            if (preconditionExpression != null && !preconditionExpression.isBlank()) {
                result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression).asBoolean().orElse(false);
            } else {
                result = true;
            }
        }
        return result;
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }

    private String getDescriptionId(EObject description) {
        String descriptionURI = EcoreUtil.getURI(description).toString();
        return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    }

    private GroupDisplayMode getGroupDisplayMode(org.eclipse.sirius.components.view.form.GroupDescription viewGroupDescription) {
        org.eclipse.sirius.components.view.form.GroupDisplayMode viewDisplayMode = viewGroupDescription.getDisplayMode();
        return GroupDisplayMode.valueOf(viewDisplayMode.getLiteral());
    }

    private boolean matches(String condition, VariableManager variableManager, AQLInterpreter interpreter) {
        return interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }
}
