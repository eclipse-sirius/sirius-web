/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import java.util.Optional;
import java.util.UUID;
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
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
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
    private final IPropertiesConfigurerService propertiesConfigurerService;
    private final IPropertiesWidgetCreationService propertiesWidgetCreationService;

    public EdgeStylePropertiesConfigurer(ICustomImageMetadataSearchService customImageSearchService,
            PropertiesConfigurerService propertiesConfigurerService, IPropertiesWidgetCreationService propertiesWidgetCreationService) {
        this.customImageSearchService = Objects.requireNonNull(customImageSearchService);
        this.propertiesConfigurerService = Objects.requireNonNull(propertiesConfigurerService);
        this.propertiesWidgetCreationService = Objects.requireNonNull(propertiesWidgetCreationService);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {

        String formDescriptionId = UUID.nameUUIDFromBytes("edgestyle".getBytes()).toString();

        List<AbstractControlDescription> controls = new ArrayList<>(this.getGeneralControlDescription());

        Predicate<VariableManager> canCreatePagePredicate = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(EdgeStyle.class::isInstance)
                .isPresent();

        GroupDescription groupDescription = this.propertiesWidgetCreationService.createSimpleGroupDescription(controls);

        registry.add(this.propertiesWidgetCreationService.createSimplePageDescription(formDescriptionId, groupDescription, canCreatePagePredicate));

    }

    private List<AbstractControlDescription> getGeneralControlDescription() {
        List<AbstractControlDescription> controls = new ArrayList<>();

        var fontSize = this.propertiesWidgetCreationService.createTextField("nodestyle.fontSize", "Font Size",
                style -> String.valueOf(((LabelStyle) style).getFontSize()),
                (style, newColor) -> {
                    try {
                        ((LabelStyle) style).setFontSize(Integer.parseInt(newColor));
                    } catch (NumberFormatException nfe) {
                        // Ignore.
                    }
                },
                ViewPackage.Literals.LABEL_STYLE__FONT_SIZE);
        controls.add(fontSize);

        var italic = this.propertiesWidgetCreationService.createCheckbox("nodestyle.italic", "Italic",
                style -> ((LabelStyle) style).isItalic(),
                (style, newItalic) -> ((LabelStyle) style).setItalic(newItalic),
                ViewPackage.Literals.LABEL_STYLE__ITALIC, Optional.empty());
        controls.add(italic);

        var bold = this.propertiesWidgetCreationService.createCheckbox("nodestyle.bold", "Bold",
                style -> ((LabelStyle) style).isBold(),
                (style, newBold) -> ((LabelStyle) style).setBold(newBold),
                ViewPackage.Literals.LABEL_STYLE__BOLD, Optional.empty());
        controls.add(bold);

        var underline = this.propertiesWidgetCreationService.createCheckbox("nodestyle.underline", "Underline",
                style -> ((LabelStyle) style).isUnderline(),
                (style, newUnderline) -> ((LabelStyle) style).setUnderline(newUnderline),
                ViewPackage.Literals.LABEL_STYLE__UNDERLINE, Optional.empty());
        controls.add(underline);

        var strikeThrough = this.propertiesWidgetCreationService.createCheckbox("nodestyle.strikeThrough", "Strike Through",
                style -> ((LabelStyle) style).isStrikeThrough(),
                (style, newStrikeThrough) -> ((LabelStyle) style).setStrikeThrough(newStrikeThrough),
                ViewPackage.Literals.LABEL_STYLE__STRIKE_THROUGH, Optional.empty());
        controls.add(strikeThrough);

        var showIcon = this.propertiesWidgetCreationService.createCheckbox("nodestyle.showIcon", "Show Icon",
                style -> ((EdgeStyle) style).isShowIcon(),
                (style, newValue) -> ((EdgeStyle) style).setShowIcon(newValue),
                DiagramPackage.Literals.EDGE_STYLE__SHOW_ICON,
                Optional.of(variableManager -> "Show an icon near the label, use the default one if no custom icon is set."));
        controls.add(showIcon);


        controls.add(this.createIconSelectionField());
        controls.add(this.createLineStyleSelectionField());
        controls.add(this.createSourceArrowStyleSelectionField());
        controls.add(this.createTargetArrowStyleSelectionField());

        Function<VariableManager, List<?>> colorOptionsProvider = variableManager -> this.getColorsFromColorPalettesStream(variableManager).toList();
        var userColor = this.propertiesWidgetCreationService.createReferenceWidget("edgestyle.Color", "Color", DiagramPackage.Literals.STYLE__COLOR, colorOptionsProvider);
        controls.add(userColor);

        return controls;
    }

    private Stream<UserColor> getColorsFromColorPalettesStream(VariableManager variableManager) {
        return variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
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

    private SelectDescription createSourceArrowStyleSelectionField() {
        return SelectDescription.newSelectDescription("edgestyle.sourceArrowStyleSelector")
                .idProvider(variableManager -> "edgestyle.sourceArrowStyleSelector")
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .labelProvider(variableManager -> "Source Arrow Syle")
                .styleProvider(vm -> SelectStyle.newSelectStyle().showIcon(true).build())
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, EdgeStyle.class).map(EdgeStyle::getSourceArrowStyle)
                        .map(ArrowStyle::getValue)
                        .map(String::valueOf)
                        .orElse(EMPTY))
                .optionsProvider(variableManager -> ArrowStyle.VALUES)
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, ArrowStyle.class)
                        .map(ArrowStyle::getValue)
                        .map(String::valueOf)
                        .orElse(EMPTY))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, ArrowStyle.class)
                        .map(Enumerator::getName)
                        .orElse(EMPTY))
                .optionIconURLProvider(variableManager -> List.of())
                .newValueHandler(this.getSourceArrowValueHandler())
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(DiagramPackage.Literals.EDGE_STYLE__SOURCE_ARROW_STYLE))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
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

    private SelectDescription createTargetArrowStyleSelectionField() {
        return SelectDescription.newSelectDescription("edgestyle.targetArrowStyleSelector")
                .idProvider(variableManager -> "edgestyle.targetArrowStyleSelector")
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .labelProvider(variableManager -> "Target Arrow Syle")
                .styleProvider(vm -> SelectStyle.newSelectStyle().showIcon(true).build())
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, EdgeStyle.class).map(EdgeStyle::getTargetArrowStyle)
                        .map(ArrowStyle::getValue)
                        .map(String::valueOf)
                        .orElse(EMPTY))
                .optionsProvider(variableManager -> ArrowStyle.VALUES)
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, ArrowStyle.class)
                        .map(ArrowStyle::getValue)
                        .map(String::valueOf)
                        .orElse(EMPTY))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, ArrowStyle.class)
                        .map(Enumerator::getName)
                        .orElse(EMPTY))
                .optionIconURLProvider(variableManager -> List.of())
                .newValueHandler(this.getTargetArrowValueHandler())
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(DiagramPackage.Literals.EDGE_STYLE__TARGET_ARROW_STYLE))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
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

    private SelectDescription createLineStyleSelectionField() {
        return SelectDescription.newSelectDescription("edgestyle.lineStyleSelector")
                .idProvider(variableManager -> "edgestyle.lineStyleSelector")
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .labelProvider(variableManager -> "Line Syle")
                .styleProvider(vm -> SelectStyle.newSelectStyle().showIcon(true).build())
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, EdgeStyle.class).map(EdgeStyle::getLineStyle)
                        .map(LineStyle::getValue)
                        .map(String::valueOf)
                        .orElse(EMPTY))
                .optionsProvider(variableManager -> LineStyle.VALUES)
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, LineStyle.class)
                        .map(LineStyle::getValue)
                        .map(String::valueOf)
                        .orElse(EMPTY))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, LineStyle.class)
                        .map(Enumerator::getName)
                        .orElse(EMPTY))
                .optionIconURLProvider(variableManager -> List.of())
                .newValueHandler(this.getLineStyleValueHandler())
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(DiagramPackage.Literals.LINE_STYLE))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
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

    private SelectDescription createIconSelectionField() {
        return SelectDescription.newSelectDescription("edgestyle.iconLabelSelector")
                .idProvider(variableManager -> "edgestyle.iconLabelSelector")
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
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
                        .map(customImageMetadataEntity -> List.of(String.format("/custom/%s", customImageMetadataEntity.getId().toString())))
                        .orElse(List.of()))
                .newValueHandler(this.getIconLabelValueHandler())
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(DiagramPackage.Literals.EDGE_STYLE__LABEL_ICON))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .helpTextProvider(variableManager -> "Set a custom icon for the label, use in association with Show Icon property")
                .build();
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
