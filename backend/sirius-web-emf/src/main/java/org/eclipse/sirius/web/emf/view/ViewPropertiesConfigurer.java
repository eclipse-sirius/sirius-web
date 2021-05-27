/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.view;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.web.api.configuration.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.web.api.configuration.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.web.forms.components.SelectComponent;
import org.eclipse.sirius.web.forms.description.AbstractControlDescription;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.forms.description.GroupDescription;
import org.eclipse.sirius.web.forms.description.PageDescription;
import org.eclipse.sirius.web.forms.description.SelectDescription;
import org.eclipse.sirius.web.forms.description.TextfieldDescription;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.images.CustomImage;
import org.eclipse.sirius.web.services.api.images.ICustomImagesService;
import org.eclipse.sirius.web.view.ConditionalNodeStyle;
import org.eclipse.sirius.web.view.NodeStyle;
import org.springframework.stereotype.Component;

/**
 * Customizes the properties view for some of the View DSL elements.
 *
 * @author pcdavid
 */
@Component
public class ViewPropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    private static final String EMPTY = ""; //$NON-NLS-1$

    private static final String UNNAMED = "<unnamed>"; //$NON-NLS-1$

    private final Function<VariableManager, List<Object>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().collect(Collectors.toList());

    private final ICustomImagesService customImagesService;

    public ViewPropertiesConfigurer(ICustomImagesService customImagesService) {
        this.customImagesService = Objects.requireNonNull(customImagesService);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.getConditionalNodeStyleProperties());
        registry.add(this.getNodeStyleProperties());
    }

    private FormDescription getConditionalNodeStyleProperties() {
        UUID formDescriptionId = UUID.nameUUIDFromBytes("conditionalnodestyle".getBytes()); //$NON-NLS-1$

        // @formatter:off
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, NodeStyle.class)
                                                                                                     .map(obj -> EcoreUtil.getURI(obj).toString())
                                                                                                     .orElse(null);

        List<AbstractControlDescription> controls = List.of(
                this.createTextField("conditionalnodestyle.condition", "Condition", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((ConditionalNodeStyle) style).getCondition(),
                        (style, newCondition) -> ((ConditionalNodeStyle) style).setCondition(newCondition)),
                this.createTextField("conditionalnodestyle.color", "Color", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((NodeStyle) style).getColor(),
                        (style, newColor) -> ((NodeStyle) style).setColor(newColor)),
                this.createTextField("conditionalnodestyle.borderColor", "Border Color", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((NodeStyle) style).getBorderColor(),
                        (style, newColor) -> ((NodeStyle) style).setBorderColor(newColor)),
                this.createTextField("conditionalnodestyle.fontSize", "Font Size", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> String.valueOf(((NodeStyle) style).getFontSize()),
                        (style, newColor) -> {
                            try {
                                ((NodeStyle) style).setFontSize(Integer.parseInt(newColor));
                            } catch (NumberFormatException nfe) {
                                // Ignore.
                            }
                        }),
                this.createShapeSelectionField());

        return FormDescription.newFormDescription(formDescriptionId)
                .label("Conditional Node Style") //$NON-NLS-1$
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, NodeStyle.class).map(style -> style.getColor()).orElse(UNNAMED))
                .canCreatePredicate(this.getVariableEqualsPredicate(IRepresentationDescription.CLASS, ConditionalNodeStyle.class))
                .idProvider(new GetOrCreateRandomIdProvider())
                .targetObjectIdProvider(targetObjectIdProvider)
                .pageDescriptions(List.of(this.createSimplePageDescription(this.createSimpleGroupDescription(controls), this.getVariableEqualsPredicate(VariableManager.SELF, ConditionalNodeStyle.class))))
                .groupDescriptions(List.of(this.createSimpleGroupDescription(controls)))
                .build();
        // @formatter:on
    }

    private FormDescription getNodeStyleProperties() {
        UUID formDescriptionId = UUID.nameUUIDFromBytes("nodestyle".getBytes()); //$NON-NLS-1$

        // @formatter:off
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, NodeStyle.class)
                                                                                                     .map(obj -> EcoreUtil.getURI(obj).toString())
                                                                                                     .orElse(null);

        List<AbstractControlDescription> controls = List.of(
                this.createTextField("nodestyle.color", "Color", //$NON-NLS-1$ //$NON-NLS-2$
                                     style -> ((NodeStyle) style).getColor(),
                                     (style, newColor) -> ((NodeStyle) style).setColor(newColor)),
                this.createTextField("nodestyle.borderColor", "Border Color", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((NodeStyle) style).getBorderColor(),
                        (style, newColor) -> ((NodeStyle) style).setBorderColor(newColor)),
                this.createTextField("nodestyle.fontSize", "Font Size", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> String.valueOf(((NodeStyle) style).getFontSize()),
                        (style, newColor) -> {
                            try {
                                ((NodeStyle) style).setFontSize(Integer.parseInt(newColor));
                            } catch (NumberFormatException nfe) {
                                // Ignore.
                            }
                        }),
                this.createShapeSelectionField());

        return FormDescription.newFormDescription(formDescriptionId)
                .label("Node Style") //$NON-NLS-1$
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, NodeStyle.class).map(style -> style.getColor()).orElse(UNNAMED))
                .canCreatePredicate(this.getVariableEqualsPredicate(IRepresentationDescription.CLASS, NodeStyle.class))
                .idProvider(new GetOrCreateRandomIdProvider())
                .targetObjectIdProvider(targetObjectIdProvider)
                .pageDescriptions(List.of(this.createSimplePageDescription(this.createSimpleGroupDescription(controls), this.getVariableEqualsPredicate(VariableManager.SELF, NodeStyle.class))))
                .groupDescriptions(List.of(this.createSimpleGroupDescription(controls)))
                .build();
        // @formatter:on
    }

    private Predicate<VariableManager> getVariableEqualsPredicate(String variableName, Object value) {
        return variableManager -> variableManager.get(variableName, Object.class).filter(value::equals).isPresent();
    }

    private PageDescription createSimplePageDescription(GroupDescription groupDescription, Predicate<VariableManager> canCreatePredicate) {
        // @formatter:off
        return PageDescription.newPageDescription("page") //$NON-NLS-1$
                              .idProvider(variableManager -> "page") //$NON-NLS-1$
                              .labelProvider(variableManager -> "Properties") //$NON-NLS-1$
                              .semanticElementsProvider(this.semanticElementsProvider)
                              .canCreatePredicate(canCreatePredicate)
                              .groupDescriptions(List.of(groupDescription))
                              .build();
        // @formatter:on
    }

    private GroupDescription createSimpleGroupDescription(List<AbstractControlDescription> controls) {
        // @formatter:off
        return GroupDescription.newGroupDescription("group") //$NON-NLS-1$
                               .idProvider(variableManager -> "group") //$NON-NLS-1$
                               .labelProvider(variableManager -> "General") //$NON-NLS-1$
                               .semanticElementsProvider(this.semanticElementsProvider)
                               .controlDescriptions(controls)
                               .build();
        // @formatter:on
    }

    private TextfieldDescription createTextField(String id, String title, Function<Object, String> reader, BiConsumer<Object, String> writer) {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(reader).orElse(EMPTY);
        BiFunction<VariableManager, String, Status> newValueHandler = (variableManager, newValue) -> {
            var optionalDiagramMapping = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalDiagramMapping.isPresent()) {
                writer.accept(optionalDiagramMapping.get(), newValue);
                return Status.OK;
            } else {
                return Status.ERROR;
            }
        };
        // @formatter:off
        return TextfieldDescription.newTextfieldDescription(id)
                                   .idProvider(variableManager -> id)
                                   .labelProvider(variableManager -> title)
                                   .valueProvider(valueProvider)
                                   .newValueHandler(newValueHandler)
                                   .build();
        // @formatter:on
    }

    private SelectDescription createShapeSelectionField() {
        // @formatter:off
        return SelectDescription.newSelectDescription("nodestyle.shapeSelector") //$NON-NLS-1$
                                .idProvider(variableManager -> "nodestyle.shapeSelector") //$NON-NLS-1$
                                .labelProvider(variableManager -> "Shape") //$NON-NLS-1$
                                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, NodeStyle.class).map(NodeStyle::getShape).orElse(EMPTY))
                                .optionsProvider(variableManager -> this.customImagesService.getAvailableImages().stream().sorted(Comparator.comparing(CustomImage::getLabel)).collect(Collectors.toList()))
                                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, CustomImage.class).map(CustomImage::getId).map(UUID::toString).orElse(EMPTY))
                                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, CustomImage.class).map(CustomImage::getLabel).orElse(EMPTY))
                                .newValueHandler(this.getNewShapeValueHandler())
                                .build();
        // @formatter:on
    }

    private BiFunction<VariableManager, String, Status> getNewShapeValueHandler() {
        return (variableManager, newValue) -> {
            var optionalNodeStyle = variableManager.get(VariableManager.SELF, NodeStyle.class);
            if (optionalNodeStyle.isPresent()) {
                if (newValue != null && newValue.isBlank()) {
                    newValue = null;
                }
                optionalNodeStyle.get().setShape(newValue);
                return Status.OK;
            }
            return Status.ERROR;
        };
    }

}
