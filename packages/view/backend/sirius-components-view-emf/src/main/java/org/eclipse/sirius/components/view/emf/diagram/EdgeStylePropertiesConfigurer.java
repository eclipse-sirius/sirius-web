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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.forms.SelectStyle;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.Style;
import org.eclipse.sirius.components.view.emf.CustomImageMetadata;
import org.eclipse.sirius.components.view.emf.ICustomImageMetadataSearchService;
import org.eclipse.sirius.components.view.emf.compatibility.IPropertiesConfigurerService;
import org.eclipse.sirius.components.view.emf.compatibility.IPropertiesWidgetCreationService;
import org.eclipse.sirius.components.view.emf.compatibility.PropertiesConfigurerService;
import org.springframework.stereotype.Component;

/**
 * Customizes the properties view for some of the View DSL elements.
 *
 * @author mcharfadi
 */
@Component
public class EdgeStylePropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    private static final String EMPTY = "";

    private static final String INT_PATTERN = "\\d+";

    private final ICustomImageMetadataSearchService customImageSearchService;

    private final IObjectService objectService;

    private IPropertiesConfigurerService propertiesConfigurerService;

    private IPropertiesWidgetCreationService propertiesWidgetCreationService;

    public EdgeStylePropertiesConfigurer(ICustomImageMetadataSearchService customImageSearchService,
            IObjectService objectService, PropertiesConfigurerService propertiesConfigurerService, IPropertiesWidgetCreationService propertiesWidgetCreationService) {
        this.customImageSearchService = Objects.requireNonNull(customImageSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.propertiesConfigurerService = Objects.requireNonNull(propertiesConfigurerService);
        this.propertiesWidgetCreationService = Objects.requireNonNull(propertiesWidgetCreationService);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {

        String formDescriptionId = UUID.nameUUIDFromBytes("edgestyle".getBytes()).toString();

        List<AbstractControlDescription> controls = new ArrayList<>(this.getGeneralControlDescription());

        Predicate<VariableManager> canCreatePagePredicate = variableManager ->  variableManager.get(VariableManager.SELF, Object.class)
                .filter(EdgeStyle.class::isInstance)
                .isPresent();

        GroupDescription groupDescription = this.propertiesWidgetCreationService.createSimpleGroupDescription(controls);

        registry.add(this.propertiesWidgetCreationService.createSimplePageDescription(formDescriptionId, groupDescription, canCreatePagePredicate));

    }

    private List<AbstractControlDescription> getGeneralControlDescription() {
        return List.of(
                this.createUserColorSelectionField("edgestyle.Color", "Color", DiagramPackage.Literals.STYLE__COLOR
                        , Style.class
                        , Style::getColor
                        , Style::setColor),
                this.propertiesWidgetCreationService.createCheckbox("edgestyle.italic", "Italic",
                                    style -> ((LabelStyle) style).isItalic(),
                                    (style, newItalic) -> ((LabelStyle) style).setItalic(newItalic),
                                    ViewPackage.Literals.LABEL_STYLE__ITALIC),
                this.propertiesWidgetCreationService.createCheckbox("edgestyle.bold", "Bold",
                                    style -> ((LabelStyle) style).isBold(),
                                    (style, newBold) -> ((LabelStyle) style).setBold(newBold),
                                    ViewPackage.Literals.LABEL_STYLE__BOLD),
                this.propertiesWidgetCreationService.createCheckbox("edgestyle.underline", "Underline",
                                    style -> ((LabelStyle) style).isUnderline(),
                                    (style, newUnderline) -> ((LabelStyle) style).setUnderline(newUnderline),
                                    ViewPackage.Literals.LABEL_STYLE__UNDERLINE),
                this.propertiesWidgetCreationService.createCheckbox("edgestyle.strikeThrough", "Strike Through",
                                    style -> ((LabelStyle) style).isStrikeThrough(),
                                    (style, newStrikeThrough) -> ((LabelStyle) style).setStrikeThrough(newStrikeThrough),
                                    ViewPackage.Literals.LABEL_STYLE__STRIKE_THROUGH),
                this.propertiesWidgetCreationService.createCheckbox("edgestyle.showIcon", "Show Icon",
                        style -> ((EdgeStyle) style).isShowIcon(),
                        (style, newValue) -> ((EdgeStyle) style).setShowIcon(newValue),
                        DiagramPackage.Literals.EDGE_STYLE__SHOW_ICON),
                this.createIconSelectionField(
                        DiagramPackage.Literals.EDGE_STYLE__LABEL_ICON),
                this.createLineStyleSelectionField(
                        DiagramPackage.Literals.LINE_STYLE),
                this.createSourceArrowStyleSelectionField(
                        DiagramPackage.Literals.EDGE_STYLE__SOURCE_ARROW_STYLE),
                this.createTargetArrowStyleSelectionField(
                        DiagramPackage.Literals.EDGE_STYLE__TARGET_ARROW_STYLE));
    }

    private <T> SelectDescription createUserColorSelectionField(String id, String label, Object feature, Class<T> styleType
            , Function<T, UserColor> colorGetter, BiConsumer<T, UserColor> colorSetter) {
        // @formatter:off
        return SelectDescription.newSelectDescription(id)
                                .idProvider(variableManager -> id)
                                .labelProvider(variableManager -> label)
                                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, styleType)
                                                                                 .map(colorGetter)
                                                                                 .map(UserColor::getName)
                                                                                 .orElse(EMPTY))
                                .optionsProvider(variableManager -> this.getColorsFromColorPalettesStream(variableManager).toList())
                                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, UserColor.class)
                                                                                    .map(UserColor::getName)
                                                                                    .orElse(EMPTY))
                                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, UserColor.class)
                                                                                       .map(UserColor::getName)
                                                                                       .orElse(EMPTY))
                                .optionIconURLProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, Object.class).map(this.objectService::getImagePath).orElse(""))
                                .newValueHandler((variableManager, newValue) ->
                                                         variableManager.get(VariableManager.SELF, styleType)
                                                                        .<IStatus>map((style) -> {
                                                                            if (newValue != null) {
                                                                                this.getColorsFromColorPalettesStream(variableManager)
                                                                                    .filter(userColor -> newValue.equals(userColor.getName()))
                                                                                    .findFirst()
                                                                                    .ifPresent(userColor -> colorSetter.accept(style, userColor));
                                                                            }
                                                                            return new Success();
                                                                        }).orElseGet(() -> new Failure(""))
                                )
                                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(feature))
                                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                                .build();
        // @formatter:on
    }

    private Stream<UserColor> getColorsFromColorPalettesStream(VariableManager variableManager) {
        return variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                              .filter(EditingContext.class::isInstance)
                              .map(EditingContext.class::cast)
                              .map(EditingContext::getDomain)
                              .map(EditingDomain::getResourceSet)
                              .map(ResourceSet::getResources)
                              .stream()
                              .flatMap(EList::stream)
                              .map(Resource::getContents)
                              .flatMap(EList::stream)
                              .filter(View.class::isInstance)
                              .map(View.class::cast)
                              .map(View::getColorPalettes)
                              .flatMap(EList::stream)
                              .map(ColorPalette::getColors)
                              .flatMap(EList::stream);
    }

    private SelectDescription createSourceArrowStyleSelectionField(Object feature) {
        // @formatter:off
        return SelectDescription.newSelectDescription("edgestyle.sourceArrowStyleSelector")
                .idProvider(variableManager -> "edgestyle.sourceArrowStyleSelector")
                .labelProvider(variableManager -> "Source Arrow Syle")
                .styleProvider(vm -> SelectStyle.newSelectStyle().showIcon(true).build())
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, EdgeStyle.class).map(EdgeStyle::getSourceArrowStyle)
                        .map(ArrowStyle::getValue)
                        .map(value -> String.valueOf(value))
                        .orElse(EMPTY))
                .optionsProvider(variableManager -> ArrowStyle.VALUES)
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, ArrowStyle.class)
                        .map(ArrowStyle::getValue)
                        .map(value -> String.valueOf(value))
                        .orElse(EMPTY))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, ArrowStyle.class)
                        .map(Enumerator::getName)
                        .orElse(EMPTY))
                .optionIconURLProvider(variableManager -> "")
                .newValueHandler(this.getSourceArrowValueHandler())
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(feature))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
        // @formatter:on
    }

    private BiFunction<VariableManager, String, IStatus> getSourceArrowValueHandler() {
        return (variableManager, newValue) -> {
            var optionalEdgeStyle = variableManager.get(VariableManager.SELF, EdgeStyle.class);
            if (optionalEdgeStyle.isPresent() && newValue != null && newValue.matches(INT_PATTERN)) {
                int newArrowStyle = Integer.parseInt(newValue);
                ArrowStyle arrowStyle = ArrowStyle.get(newArrowStyle);
                if (arrowStyle != null) {
                    optionalEdgeStyle.get().setSourceArrowStyle(arrowStyle);
                    return new Success();
                }
            }
            return new Failure("");
        };
    }

    private SelectDescription createTargetArrowStyleSelectionField(Object feature) {
        // @formatter:off
        return SelectDescription.newSelectDescription("edgestyle.targetArrowStyleSelector")
                .idProvider(variableManager -> "edgestyle.targetArrowStyleSelector")
                .labelProvider(variableManager -> "Target Arrow Syle")
                .styleProvider(vm -> SelectStyle.newSelectStyle().showIcon(true).build())
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, EdgeStyle.class).map(EdgeStyle::getTargetArrowStyle)
                        .map(ArrowStyle::getValue)
                        .map(value -> String.valueOf(value))
                        .orElse(EMPTY))
                .optionsProvider(variableManager -> ArrowStyle.VALUES)
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, ArrowStyle.class)
                        .map(ArrowStyle::getValue)
                        .map(value -> String.valueOf(value))
                        .orElse(EMPTY))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, ArrowStyle.class)
                        .map(Enumerator::getName)
                        .orElse(EMPTY))
                .optionIconURLProvider(variableManager -> "")
                .newValueHandler(this.getTargetArrowValueHandler())
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(feature))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
        // @formatter:on
    }

    private BiFunction<VariableManager, String, IStatus> getTargetArrowValueHandler() {
        return (variableManager, newValue) -> {
            var optionalEdgeStyle = variableManager.get(VariableManager.SELF, EdgeStyle.class);
            if (optionalEdgeStyle.isPresent() && newValue != null && newValue.matches(INT_PATTERN)) {
                int newArrowStyle = Integer.parseInt(newValue);
                ArrowStyle arrowStyle = ArrowStyle.get(newArrowStyle);
                if (arrowStyle != null) {
                    optionalEdgeStyle.get().setTargetArrowStyle(arrowStyle);
                    return new Success();
                }
            }
            return new Failure("");
        };
    }

    private SelectDescription createLineStyleSelectionField(Object feature) {
        // @formatter:off
        return SelectDescription.newSelectDescription("edgestyle.lineStyleSelector")
                .idProvider(variableManager -> "edgestyle.lineStyleSelector")
                .labelProvider(variableManager -> "Line Syle")
                .styleProvider(vm -> SelectStyle.newSelectStyle().showIcon(true).build())
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, EdgeStyle.class).map(EdgeStyle::getLineStyle)
                        .map(LineStyle::getValue)
                        .map(value -> String.valueOf(value))
                        .orElse(EMPTY))
                .optionsProvider(variableManager -> LineStyle.VALUES)
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, LineStyle.class)
                        .map(LineStyle::getValue)
                        .map(value -> String.valueOf(value))
                        .orElse(EMPTY))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, LineStyle.class)
                        .map(Enumerator::getName)
                        .orElse(EMPTY))
                .optionIconURLProvider(variableManager -> "")
                .newValueHandler(this.getLineStyleValueHandler())
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(feature))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
        // @formatter:on
    }

    private BiFunction<VariableManager, String, IStatus> getLineStyleValueHandler() {
        return (variableManager, newValue) -> {
            var optionalEdgeStyle = variableManager.get(VariableManager.SELF, EdgeStyle.class);
            if (optionalEdgeStyle.isPresent() && newValue != null && newValue.matches(INT_PATTERN)) {
                int newLineStyle = Integer.parseInt(newValue);
                LineStyle lineStyle = LineStyle.get(newLineStyle);
                if (lineStyle != null) {
                    optionalEdgeStyle.get().setLineStyle(lineStyle);
                    return new Success();
                }
            }
            return new Failure("");
        };
    }

    private SelectDescription createIconSelectionField(Object feature) {
        // @formatter:off
        return SelectDescription.newSelectDescription("edgestyle.iconLabelSelector")
                .idProvider(variableManager -> "edgestyle.iconLabelSelector")
                .labelProvider(variableManager -> "Custom Icon")
                .styleProvider(vm -> SelectStyle.newSelectStyle().showIcon(true).build())
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, EdgeStyle.class).map(EdgeStyle::getLabelIcon).orElse(EMPTY))
                .optionsProvider(variableManager -> variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                        .map(IEditingContext::getId)
                        .map(this.customImageSearchService::getAvailableImages)
                        .orElse(List.of())
                )
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, CustomImageMetadata.class)
                        .map(customImageMetadataEntity -> String.format("/custom/%s", customImageMetadataEntity.getId().toString()))
                        .orElse(EMPTY))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, CustomImageMetadata.class)
                        .map(CustomImageMetadata::getLabel)
                        .orElse(EMPTY))
                .optionIconURLProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, CustomImageMetadata.class)
                        .map(customImageMetadataEntity -> String.format("/custom/%s", customImageMetadataEntity.getId().toString()))
                        .orElse(EMPTY))
                .newValueHandler(this.getIconLabelValueHandler())
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(feature))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
        // @formatter:on
    }

    private BiFunction<VariableManager, String, IStatus> getIconLabelValueHandler() {
        return (variableManager, newValue) -> {
            var optionalEdgeStyle = variableManager.get(VariableManager.SELF, EdgeStyle.class);
            if (optionalEdgeStyle.isPresent()) {
                String newIcon = newValue;
                if (newValue != null && newValue.isBlank()) {
                    newIcon = null;
                }
                optionalEdgeStyle.get().setLabelIcon(newIcon);
                return new Success();
            }
            return new Failure("");
        };
    }
}
