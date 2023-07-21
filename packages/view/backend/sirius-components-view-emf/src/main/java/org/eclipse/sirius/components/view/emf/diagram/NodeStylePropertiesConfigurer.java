/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.ImageDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.forms.description.TextareaDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.BorderStyle;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.emf.AQLTextfieldCustomizer;
import org.eclipse.sirius.components.view.emf.CustomImageMetadata;
import org.eclipse.sirius.components.view.emf.ICustomImageMetadataSearchService;
import org.springframework.stereotype.Component;

/**
 * Customizes the properties view for some of the View DSL elements.
 *
 * @author pcdavid
 */
@Component
public class NodeStylePropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    private static final String EMPTY = "";

    private final Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList();

    private final List<IParametricSVGImageRegistry> parametricSVGImageRegistries;

    private final ICustomImageMetadataSearchService customImageSearchService;

    private final IValidationService validationService;

    private final AQLTextfieldCustomizer aqlTextfieldCustomizer;

    private final IObjectService objectService;

    public NodeStylePropertiesConfigurer(ICustomImageMetadataSearchService customImageSearchService, IValidationService validationService,
            List<IParametricSVGImageRegistry> parametricSVGImageRegistries, AQLTextfieldCustomizer aqlTextfieldCustomizer, IObjectService objectService) {
        this.validationService = Objects.requireNonNull(validationService);
        this.customImageSearchService = Objects.requireNonNull(customImageSearchService);
        this.parametricSVGImageRegistries = parametricSVGImageRegistries;
        this.aqlTextfieldCustomizer = Objects.requireNonNull(aqlTextfieldCustomizer);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.getRectangularNodeStyleProperties());
        registry.add(this.getIconLabelNodeStyleProperties());
        registry.add(this.getImageNodeStyleProperties());
    }

    private PageDescription getImageNodeStyleProperties() {
        String id = UUID.nameUUIDFromBytes("nodestyle".getBytes()).toString();

        List<AbstractControlDescription> controls = new ArrayList<>();
        controls.add(this.createShapeSelectionField(DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE));
        controls.add(this.createShapePreviewField());
        controls.addAll(this.getGeneralControlDescription());

        GroupDescription groupDescription = this.createSimpleGroupDescription(controls);

        // @formatter:off
        Predicate<VariableManager> canCreatePagePredicate = variableManager ->  variableManager.get(VariableManager.SELF, Object.class)
                    .filter(ImageNodeStyleDescription.class::isInstance)
                    .isPresent();

        return this.createSimplePageDescription(id, groupDescription, canCreatePagePredicate);
    }

    private PageDescription getIconLabelNodeStyleProperties() {
        String id = UUID.nameUUIDFromBytes("iconlabelnodestyle".getBytes()).toString();

        List<AbstractControlDescription> controls = this.getGeneralControlDescription();

        GroupDescription groupDescription = this.createSimpleGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager ->  variableManager.get(VariableManager.SELF, Object.class)
                .filter(IconLabelNodeStyleDescription.class::isInstance)
                .isPresent();

        return this.createSimplePageDescription(id, groupDescription, canCreatePagePredicate);
    }

    private PageDescription getRectangularNodeStyleProperties() {

        String id = UUID.nameUUIDFromBytes("rectangularnodestyle".getBytes()).toString();

        List<AbstractControlDescription> controls = new ArrayList<>();
        controls.add(this.createCheckbox("nodestyle.isWithHeader", "With Header",
                style -> ((RectangularNodeStyleDescription) style).isWithHeader(),
                (style, newWithHeaderValue) -> ((RectangularNodeStyleDescription) style).setWithHeader(newWithHeaderValue),
                DiagramPackage.Literals.RECTANGULAR_NODE_STYLE_DESCRIPTION__WITH_HEADER));
        controls.addAll(this.getGeneralControlDescription());

        GroupDescription groupDescription = this.createSimpleGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager ->  variableManager.get(VariableManager.SELF, Object.class)
                .filter(RectangularNodeStyleDescription.class::isInstance)
                .isPresent();

        return this.createSimplePageDescription(id, groupDescription, canCreatePagePredicate);
    }

    private List<AbstractControlDescription> getGeneralControlDescription() {
        return List.of(
                this.createExpressionField("nodestyle.widthExpression", "Width Expression",
                        style -> ((NodeStyleDescription) style).getWidthComputationExpression(),
                        (style, newWidthExpression) -> ((NodeStyleDescription) style).setWidthComputationExpression(newWidthExpression),
                        DiagramPackage.Literals.NODE_STYLE_DESCRIPTION__WIDTH_COMPUTATION_EXPRESSION),
                this.createExpressionField("nodestyle.heightExpression", "Height Expression",
                                           style -> ((NodeStyleDescription) style).getHeightComputationExpression(),
                                           (style, newHeightExpression) -> ((NodeStyleDescription) style).setHeightComputationExpression(newHeightExpression),
                                           DiagramPackage.Literals.NODE_STYLE_DESCRIPTION__HEIGHT_COMPUTATION_EXPRESSION),
                this.createCheckbox("nodestyle.showIcon", "Show Icon",
                                    style -> ((NodeStyleDescription) style).isShowIcon(),
                                    (style, newValue) -> ((NodeStyleDescription) style).setShowIcon(newValue),
                                    DiagramPackage.Literals.NODE_STYLE_DESCRIPTION__SHOW_ICON),
                this.createUserColorSelectionField("nodestyle.labelColor", "Label Color", DiagramPackage.Literals.NODE_STYLE_DESCRIPTION__LABEL_COLOR
                        , NodeStyleDescription.class
                        , NodeStyleDescription::getLabelColor
                        , NodeStyleDescription::setLabelColor),
                this.createUserColorSelectionField("nodestyle.color", "Color", DiagramPackage.Literals.STYLE__COLOR
                        , NodeStyleDescription.class
                        , NodeStyleDescription::getColor
                        , NodeStyleDescription::setColor),
                this.createUserColorSelectionField("nodestyle.borderColor"
                        , "Border Color"
                        , DiagramPackage.Literals.BORDER_STYLE__BORDER_COLOR
                        , BorderStyle.class
                        , BorderStyle::getBorderColor
                        , BorderStyle::setBorderColor),
                this.createTextField("nodestyle.borderRadius", "Border Radius",
                                     style -> String.valueOf(((NodeStyleDescription) style).getBorderRadius()),
                                     (style, newBorderRadius) -> {
                                         try {
                                             ((NodeStyleDescription) style).setBorderRadius(Integer.parseInt(newBorderRadius));
                                         } catch (NumberFormatException nfe) {
                                             // Ignore.
                                         }
                                     },
                                     DiagramPackage.Literals.BORDER_STYLE__BORDER_RADIUS),
                this.createTextField("nodestyle.borderSize", "Border Size",
                                     style -> String.valueOf(((NodeStyleDescription) style).getBorderSize()),
                                     (style, newBorderSize) -> {
                                         try {
                                             ((NodeStyleDescription) style).setBorderSize(Integer.parseInt(newBorderSize));
                                         } catch (NumberFormatException nfe) {
                                             // Ignore.
                                         }
                                     },
                                     DiagramPackage.Literals.BORDER_STYLE__BORDER_SIZE),
                this.createBorderLineStyleSelectionField("nodestyle.borderstyle", DiagramPackage.Literals.BORDER_STYLE__BORDER_LINE_STYLE),
                this.createTextField("nodestyle.fontSize", "Font Size",
                                     style -> String.valueOf(((LabelStyle) style).getFontSize()),
                                     (style, newColor) -> {
                                         try {
                                             ((LabelStyle) style).setFontSize(Integer.parseInt(newColor));
                                         } catch (NumberFormatException nfe) {
                                             // Ignore.
                                         }
                                     },
                                     ViewPackage.Literals.LABEL_STYLE__FONT_SIZE),
                this.createCheckbox("nodestyle.italic", "Italic",
                                    style -> ((LabelStyle) style).isItalic(),
                                    (style, newItalic) -> ((LabelStyle) style).setItalic(newItalic),
                                    ViewPackage.Literals.LABEL_STYLE__ITALIC),
                this.createCheckbox("nodestyle.bold", "Bold",
                                    style -> ((LabelStyle) style).isBold(),
                                    (style, newBold) -> ((LabelStyle) style).setBold(newBold),
                                    ViewPackage.Literals.LABEL_STYLE__BOLD),
                this.createCheckbox("nodestyle.underline", "Underline",
                                    style -> ((LabelStyle) style).isUnderline(),
                                    (style, newUnderline) -> ((LabelStyle) style).setUnderline(newUnderline),
                                    ViewPackage.Literals.LABEL_STYLE__UNDERLINE),
                this.createCheckbox("nodestyle.strikeThrough", "Strike Through",
                                    style -> ((LabelStyle) style).isStrikeThrough(),
                                    (style, newStrikeThrough) -> ((LabelStyle) style).setStrikeThrough(newStrikeThrough),
                                    ViewPackage.Literals.LABEL_STYLE__STRIKE_THROUGH));
    }

    private PageDescription createSimplePageDescription(String id, GroupDescription groupDescription, Predicate<VariableManager> canCreatePredicate) {
        // @formatter:off
        return PageDescription.newPageDescription(id)
                .idProvider(variableManager -> "page")
                .labelProvider(variableManager -> "Properties")
                .semanticElementsProvider(this.semanticElementsProvider)
                .canCreatePredicate(canCreatePredicate)
                .groupDescriptions(List.of(groupDescription))
                .build();
        // @formatter:on
    }

    private GroupDescription createSimpleGroupDescription(List<AbstractControlDescription> controls) {
        // @formatter:off
        return GroupDescription.newGroupDescription("group")
                .idProvider(variableManager -> "group")
                .labelProvider(variableManager -> "General")
                .semanticElementsProvider(this.semanticElementsProvider)
                .controlDescriptions(controls)
                .build();
        // @formatter:on
    }

    private TextfieldDescription createTextField(String id, String title, Function<Object, String> reader, BiConsumer<Object, String> writer, Object feature) {
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

        // @formatter:off
        return TextfieldDescription.newTextfieldDescription(id)
                .idProvider(variableManager -> id)
                .labelProvider(variableManager -> title)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(this.getDiagnosticsProvider(feature))
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .build();
        // @formatter:on
    }

    private TextareaDescription createExpressionField(String id, String title, Function<Object, String> reader, BiConsumer<Object, String> writer, Object feature) {
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

        // @formatter:off
        return TextareaDescription.newTextareaDescription(id)
                .idProvider(variableManager -> id)
                .labelProvider(variableManager -> title)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(this.getDiagnosticsProvider(feature))
                .completionProposalsProvider(this.aqlTextfieldCustomizer.getCompletionProposalsProvider())
                .styleProvider(this.aqlTextfieldCustomizer.getStyleProvider())
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .build();
        // @formatter:on
    }

    private CheckboxDescription createCheckbox(String id, String title, Function<Object, Boolean> reader, BiConsumer<Object, Boolean> writer, Object feature) {
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
        // @formatter:off
        return CheckboxDescription.newCheckboxDescription(id)
                .idProvider(variableManager -> id)
                .labelProvider(variableManager -> title)
                .valueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(this.getDiagnosticsProvider(feature))
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .build();
        // @formatter:on
    }

    private SelectDescription createBorderLineStyleSelectionField(String id, Object feature) {
        // @formatter:off
        return SelectDescription.newSelectDescription(id)
                                .idProvider(variableManager -> id)
                                .labelProvider(variableManager -> "Border Line Style")
                                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, BorderStyle.class).map(BorderStyle::getBorderLineStyle).map(LineStyle::toString).orElse(EMPTY))
                                .optionsProvider(variableManager -> LineStyle.VALUES.stream().toList())
                                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, LineStyle.class).map(LineStyle::getLiteral).orElse(EMPTY))
                                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, LineStyle.class).map(LineStyle::getName).orElse(EMPTY))
                                .optionIconURLProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, Object.class).map(this.objectService::getImagePath).orElse(""))
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
                                .diagnosticsProvider(this.getDiagnosticsProvider(feature))
                                .kindProvider(this::kindProvider)
                                .messageProvider(this::messageProvider)
                                .build();
        // @formatter:on
    }

    private <T> SelectDescription createUserColorSelectionField(String id, String label, Object feature, Class<T> styleType
            , Function<T, UserColor> colorGetter, BiConsumer<T, UserColor> colorSetter) {
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
                                                                                    .ifPresentOrElse(userColor -> colorSetter.accept(style, userColor), () -> colorSetter.accept(style, null));
                                                                            }
                                                                            return new Success();
                                                                        }).orElseGet(() -> new Failure(""))
                                )
                                .diagnosticsProvider(this.getDiagnosticsProvider(feature))
                                .kindProvider(this::kindProvider)
                                .messageProvider(this::messageProvider)
                                .build();
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

    private SelectDescription createShapeSelectionField(Object feature) {
        // @formatter:off
        return SelectDescription.newSelectDescription("nodestyle.shapeSelector")
                .idProvider(variableManager -> "nodestyle.shapeSelector")
                .labelProvider(variableManager -> "Shape")
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, ImageNodeStyleDescription.class).map(ImageNodeStyleDescription::getShape).orElse(EMPTY))
                .optionsProvider(variableManager -> {
                    Optional<String> optionalEditingContextId = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId);

                    Stream<CustomImageMetadata> parametricSVGs = this.parametricSVGImageRegistries.stream()
                        .flatMap(service-> service.getImages().stream())
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
                .optionIconURLProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, Object.class).map(this.objectService::getImagePath).orElse(""))
                .newValueHandler(this.getNewShapeValueHandler())
                .diagnosticsProvider(this.getDiagnosticsProvider(feature))
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .build();
        // @formatter:on
    }

    private ImageDescription createShapePreviewField() {
        // @formatter:off
        return ImageDescription.newImageDescription("nodestyle.shapePreview")
                .idProvider(variableManager -> "nodestyle.shapePreview")
                .labelProvider(variableManager -> "Shape Preview")
                .urlProvider(variableManager -> {
                    var optionalShape = variableManager.get(VariableManager.SELF, ImageNodeStyleDescription.class).map(ImageNodeStyleDescription::getShape);
                    if (optionalShape.isPresent()) {
                        return String.format("/custom/%s", optionalShape.get());
                    }
                    return "";
                })
                .maxWidthProvider(variableManager -> "300px")
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .build();
        // @formatter:on
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

    private Function<VariableManager, List<?>> getDiagnosticsProvider(Object feature) {
        return variableManager -> {
            var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);

            if (optionalSelf.isPresent()) {
                EObject self = optionalSelf.get();
                List<Object> diagnostics = this.validationService.validate(self, feature);
                return diagnostics;
            }

            return List.of();
        };
    }

    private String kindProvider(Object object) {
        String kind = "Unknown";
        if (object instanceof Diagnostic diagnostic) {
            switch (diagnostic.getSeverity()) {
                case org.eclipse.emf.common.util.Diagnostic.ERROR:
                    kind = "Error";
                    break;
                case org.eclipse.emf.common.util.Diagnostic.WARNING:
                    kind = "Warning";
                    break;
                case org.eclipse.emf.common.util.Diagnostic.INFO:
                    kind = "Info";
                    break;
                default:
                    kind = "Unknown";
                    break;
            }
        }
        return kind;
    }

    private String messageProvider(Object object) {
        if (object instanceof Diagnostic diagnostic) {
            return diagnostic.getMessage();
        }
        return "";
    }

}
