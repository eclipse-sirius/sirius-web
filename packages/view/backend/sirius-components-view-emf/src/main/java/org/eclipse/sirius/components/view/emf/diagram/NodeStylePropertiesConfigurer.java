/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.ImageDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.BorderStyle;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.emf.CustomImageMetadata;
import org.eclipse.sirius.components.view.emf.ICustomImageMetadataSearchService;
import org.eclipse.sirius.components.view.emf.compatibility.IPropertiesConfigurerService;
import org.eclipse.sirius.components.view.emf.compatibility.IPropertiesWidgetCreationService;
import org.eclipse.sirius.components.view.emf.compatibility.PropertiesConfigurerService;
import org.springframework.stereotype.Component;

/**
 * Customizes the properties view for some of the View DSL elements.
 *
 * @author pcdavid
 */
@Component
public class NodeStylePropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    private static final String EMPTY = "";
    private static final String CUSTOM = "/custom/%s";
    private final List<IParametricSVGImageRegistry> parametricSVGImageRegistries;
    private final ICustomImageMetadataSearchService customImageSearchService;
    private final IPropertiesConfigurerService propertiesConfigurerService;
    private final IPropertiesWidgetCreationService propertiesWidgetCreationService;
    private final IObjectService objectService;

    public NodeStylePropertiesConfigurer(ICustomImageMetadataSearchService customImageSearchService,
            List<IParametricSVGImageRegistry> parametricSVGImageRegistries, PropertiesConfigurerService propertiesConfigurerService, IPropertiesWidgetCreationService propertiesWidgetCreationService, IObjectService objectService) {
        this.customImageSearchService = Objects.requireNonNull(customImageSearchService);
        this.parametricSVGImageRegistries = parametricSVGImageRegistries;
        this.propertiesConfigurerService = Objects.requireNonNull(propertiesConfigurerService);
        this.propertiesWidgetCreationService = Objects.requireNonNull(propertiesWidgetCreationService);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.getRectangularNodeStyleProperties());
        registry.add(this.getIconLabelNodeStyleProperties());
        registry.add(this.getImageNodeStyleProperties());
        registry.add(this.getListLayoutStrategyProperties());
    }

    private PageDescription getListLayoutStrategyProperties() {
        String id = UUID.nameUUIDFromBytes("listLayoutStrategy".getBytes()).toString();

        List<AbstractControlDescription> controls = new ArrayList<>();
        var areChildNodesDraggableExpression = this.propertiesWidgetCreationService.createExpressionField("listLayoutStrategy.areChildNodesDraggableExpression",
                "Are Child Nodes Draggable Expression",
                desc -> String.valueOf(((ListLayoutStrategyDescription) desc).getAreChildNodesDraggableExpression()),
                (desc, newValue) -> ((ListLayoutStrategyDescription) desc).setAreChildNodesDraggableExpression(newValue),
                DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION__ARE_CHILD_NODES_DRAGGABLE_EXPRESSION);
        controls.add(areChildNodesDraggableExpression);
        var topGapExpression = this.propertiesWidgetCreationService.createExpressionField("listLayoutStrategy.topGapExpression",
                "Top Gap Expression",
                desc -> String.valueOf(((ListLayoutStrategyDescription) desc).getTopGapExpression()),
                (desc, newValue) -> ((ListLayoutStrategyDescription) desc).setTopGapExpression(newValue),
                DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION__TOP_GAP_EXPRESSION);
        controls.add(topGapExpression);
        var bottomGapExpression = this.propertiesWidgetCreationService.createExpressionField("listLayoutStrategy.bottomGapExpression",
                "Bottom Gap Expression",
                desc -> String.valueOf(((ListLayoutStrategyDescription) desc).getBottomGapExpression()),
                (desc, newValue) -> ((ListLayoutStrategyDescription) desc).setBottomGapExpression(newValue),
                DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION__BOTTOM_GAP_EXPRESSION);
        controls.add(bottomGapExpression);

        var growableNodes = this.propertiesWidgetCreationService.createReferenceWidget("listLayoutStrategy.growableNodes",
                "Growable Nodes",
                DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION__GROWABLE_NODES,
                this::getSubNodes);
        controls.add(growableNodes);

        GroupDescription groupDescription = this.propertiesWidgetCreationService.createSimpleGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(ListLayoutStrategyDescription.class::isInstance)
                .isPresent();

        return this.propertiesWidgetCreationService.createSimplePageDescription(id, groupDescription, canCreatePagePredicate);
    }

    private List<NodeDescription> getSubNodes(VariableManager variableManager) {
        return variableManager.get("self", ListLayoutStrategyDescription.class)
                .map(EObject::eContainer)
                .filter(NodeDescription.class::isInstance)
                .map(NodeDescription.class::cast)
                .map(NodeDescription::getChildrenDescriptions)
                .orElseGet(BasicEList::new);
    }

    private PageDescription getImageNodeStyleProperties() {
        String id = UUID.nameUUIDFromBytes("nodestyle".getBytes()).toString();

        List<AbstractControlDescription> controls = new ArrayList<>();
        controls.add(this.createShapeSelectionField());
        controls.add(this.createShapePreviewField());
        controls.addAll(this.getGeneralControlDescription(NodeType.NODE_IMAGE));
        var isPositionDependentRotation = this.propertiesWidgetCreationService.createCheckbox("nodestyle.positionDependentRotation", "Position-Dependent Rotation",
                style -> ((ImageNodeStyleDescription) style).isPositionDependentRotation(),
                (style, newValue) -> ((ImageNodeStyleDescription) style).setPositionDependentRotation(newValue),
                DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION,
                Optional.of(variableManager -> "Only used for border node, if set to true, a rotation will be applied if the border node is moved to another side of its parent." +
                        "The image default orientation is from the left side."));
        controls.add(isPositionDependentRotation);

        GroupDescription groupDescription = this.propertiesWidgetCreationService.createSimpleGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(ImageNodeStyleDescription.class::isInstance)
                .isPresent();

        return this.propertiesWidgetCreationService.createSimplePageDescription(id, groupDescription, canCreatePagePredicate);
    }

    private PageDescription getIconLabelNodeStyleProperties() {
        String id = UUID.nameUUIDFromBytes("iconlabelnodestyle".getBytes()).toString();

        List<AbstractControlDescription> controls = this.getGeneralControlDescription(NodeType.NODE_ICON_LABEL);

        GroupDescription groupDescription = this.propertiesWidgetCreationService.createSimpleGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(IconLabelNodeStyleDescription.class::isInstance)
                .isPresent();

        return this.propertiesWidgetCreationService.createSimplePageDescription(id, groupDescription, canCreatePagePredicate);
    }

    private PageDescription getRectangularNodeStyleProperties() {

        String id = UUID.nameUUIDFromBytes("rectangularnodestyle".getBytes()).toString();

        List<AbstractControlDescription> controls = new ArrayList<>(this.getGeneralControlDescription(NodeType.NODE_RECTANGLE));

        GroupDescription groupDescription = this.propertiesWidgetCreationService.createSimpleGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(RectangularNodeStyleDescription.class::isInstance)
                .isPresent();

        return this.propertiesWidgetCreationService.createSimplePageDescription(id, groupDescription, canCreatePagePredicate);
    }

    private List<AbstractControlDescription> getGeneralControlDescription(String nodeType) {
        List<AbstractControlDescription> controls = new ArrayList<>();

        Function<VariableManager, List<?>> colorOptionsProvider = variableManager -> this.getColorsFromColorPalettesStream(variableManager).toList();

        if (!Objects.equals(nodeType, NodeType.NODE_IMAGE)) {
            var color = this.propertiesWidgetCreationService.createReferenceWidget("nodestyle.color", "Color", DiagramPackage.Literals.STYLE__COLOR, colorOptionsProvider);
            controls.add(color);
        }
        var borderColor = this.propertiesWidgetCreationService.createReferenceWidget("nodestyle.borderColor", "Border Color", DiagramPackage.Literals.BORDER_STYLE__BORDER_COLOR, colorOptionsProvider);
        controls.add(borderColor);

        var borderRadius = this.propertiesWidgetCreationService.createTextField("nodestyle.borderRadius", "Border Radius",
                style -> String.valueOf(((NodeStyleDescription) style).getBorderRadius()),
                (style, newBorderRadius) -> {
                    try {
                        ((NodeStyleDescription) style).setBorderRadius(Integer.parseInt(newBorderRadius));
                    } catch (NumberFormatException nfe) {
                        // Ignore.
                    }
                },
                DiagramPackage.Literals.BORDER_STYLE__BORDER_RADIUS);
        controls.add(borderRadius);

        var borderSize = this.propertiesWidgetCreationService.createTextField("nodestyle.borderSize", "Border Size",
                style -> String.valueOf(((NodeStyleDescription) style).getBorderSize()),
                (style, newBorderSize) -> {
                    try {
                        ((NodeStyleDescription) style).setBorderSize(Integer.parseInt(newBorderSize));
                    } catch (NumberFormatException nfe) {
                        // Ignore.
                    }
                },
                DiagramPackage.Literals.BORDER_STYLE__BORDER_SIZE);
        controls.add(borderSize);

        var borderStyle = this.createBorderLineStyleSelectionField();
        controls.add(borderStyle);

        return controls;
    }

    private SelectDescription createBorderLineStyleSelectionField() {
        return SelectDescription.newSelectDescription("nodestyle.borderstyle")
                .idProvider(variableManager -> "nodestyle.borderstyle")
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .labelProvider(variableManager -> "Border Line Style")
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, BorderStyle.class).map(BorderStyle::getBorderLineStyle).map(LineStyle::toString)
                        .orElse(EMPTY))
                .optionsProvider(variableManager -> LineStyle.VALUES.stream().toList())
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, LineStyle.class).map(LineStyle::getLiteral).orElse(EMPTY))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, LineStyle.class).map(LineStyle::getName).orElse(EMPTY))
                .optionIconURLProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, Object.class).map(this.objectService::getImagePath)
                        .orElse(List.of()))
                .newValueHandler((variableManager, newValue) -> {
                    var optionalBorderStyle = variableManager.get(VariableManager.SELF, BorderStyle.class);
                    if (optionalBorderStyle.isPresent()) {
                        if (newValue != null && LineStyle.get(newValue) != null) {
                            optionalBorderStyle.get().setBorderLineStyle(LineStyle.get(newValue));
                        } else {
                            optionalBorderStyle.get().setBorderLineStyle(LineStyle.SOLID);
                        }
                        return new Success();
                    }
                    return new Failure("");
                })
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(DiagramPackage.Literals.BORDER_STYLE__BORDER_LINE_STYLE))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
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


    private SelectDescription createShapeSelectionField() {
        return SelectDescription.newSelectDescription("nodestyle.shapeSelector")
                .idProvider(variableManager -> "nodestyle.shapeSelector")
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .labelProvider(variableManager -> "Shape")
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, ImageNodeStyleDescription.class).map(ImageNodeStyleDescription::getShape).orElse(EMPTY))
                .optionsProvider(variableManager -> {
                    Optional<String> optionalEditingContextId = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId);

                    Stream<CustomImageMetadata> parametricSVGs = this.parametricSVGImageRegistries.stream()
                            .flatMap(service -> service.getImages().stream())
                            .map(image -> new CustomImageMetadata(image.getId(), optionalEditingContextId, image.getLabel(), "image/svg+xml"));

                    List<CustomImageMetadata> customImages = optionalEditingContextId.map(this.customImageSearchService::getAvailableImages).orElse(List.of());

                    return Stream.concat(parametricSVGs, customImages.stream())
                            .sorted(Comparator.comparing(CustomImageMetadata::getLabel))
                            .toList();
                })
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, CustomImageMetadata.class)
                        .map(CustomImageMetadata::getId)
                        .map(UUID::toString)
                        .orElse(EMPTY))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, CustomImageMetadata.class)
                        .map(CustomImageMetadata::getLabel)
                        .orElse(EMPTY))
                .optionIconURLProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, Object.class).map(this.objectService::getImagePath)
                        .orElse(List.of()))
                .newValueHandler(this.getNewShapeValueHandler())
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
    }

    private ImageDescription createShapePreviewField() {
        return ImageDescription.newImageDescription("nodestyle.shapePreview")
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .idProvider(variableManager -> "nodestyle.shapePreview")
                .labelProvider(variableManager -> "Shape Preview")
                .urlProvider(variableManager -> {
                    var optionalShape = variableManager.get(VariableManager.SELF, ImageNodeStyleDescription.class).map(ImageNodeStyleDescription::getShape);
                    if (optionalShape.isPresent()) {
                        return String.format(CUSTOM, optionalShape.get());
                    }
                    return "";
                })
                .maxWidthProvider(variableManager -> "300px")
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .build();
    }


    private BiFunction<VariableManager, String, IStatus> getNewShapeValueHandler() {
        return (variableManager, newValue) -> {
            var optionalNodeStyle = variableManager.get(VariableManager.SELF, ImageNodeStyleDescription.class);
            if (optionalNodeStyle.isPresent()) {
                String newShape = newValue;
                if (newValue != null && newValue.isBlank()) {
                    newShape = null;
                }
                optionalNodeStyle.get().setShape(newShape);
                return new Success();
            }
            return new Failure("");
        };
    }

}
