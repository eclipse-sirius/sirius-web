/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.ImageDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.BorderStyle;
import org.eclipse.sirius.components.view.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.emf.CustomImage;
import org.eclipse.sirius.components.view.emf.ICustomImageSearchService;
import org.springframework.stereotype.Component;

/**
 * Customizes the properties view for some of the View DSL elements.
 *
 * @author pcdavid
 */
@Component
public class NodeStylePropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    private static final String EMPTY = ""; //$NON-NLS-1$

    private static final String UNNAMED = "<unnamed>"; //$NON-NLS-1$

    private final Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().collect(Collectors.toList());

    private final List<IParametricSVGImageRegistry> parametricSVGImageRegistries;

    private final ICustomImageSearchService customImageSearchService;

    private final IValidationService validationService;

    public NodeStylePropertiesConfigurer(ICustomImageSearchService customImageSearchService, IValidationService validationService, List<IParametricSVGImageRegistry> parametricSVGImageRegistries) {
        this.validationService = Objects.requireNonNull(validationService);
        this.customImageSearchService = Objects.requireNonNull(customImageSearchService);
        this.parametricSVGImageRegistries = parametricSVGImageRegistries;
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.getRectangularNodeStyleProperties());
        registry.add(this.getIconLabelNodeStyleProperties());
        registry.add(this.getImageNodeStyleProperties());
    }

    private FormDescription getImageNodeStyleProperties() {
        String formDescriptionId = UUID.nameUUIDFromBytes("nodestyle".getBytes()).toString(); //$NON-NLS-1$

        List<AbstractControlDescription> controls = new ArrayList<>();
        controls.add(this.createShapeSelectionField(ViewPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE));
        controls.add(this.createShapePreviewField());
        controls.addAll(this.getGeneralControlDescription());

        GroupDescription groupDescription = this.createSimpleGroupDescription(controls);

        // @formatter:off
        Predicate<VariableManager> canCreatePagePredicate = variableManager ->  variableManager.get(VariableManager.SELF, Object.class)
                    .filter(self -> self instanceof List<?>)
                    .map(self -> (List<?>) self)
                    .flatMap(self -> self.stream().findFirst())
                    .filter(ImageNodeStyleDescription.class::isInstance)
                    .isPresent();

        return FormDescription.newFormDescription(formDescriptionId)
                .label("Image Node Style") //$NON-NLS-1$
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, ImageNodeStyleDescription.class).map(ImageNodeStyleDescription::getShape).orElse(UNNAMED))
                .canCreatePredicate(variableManager -> true)
                .idProvider(new GetOrCreateRandomIdProvider())
                .targetObjectIdProvider(this.getTargetObjectIdProvider())
                .pageDescriptions(List.of(this.createSimplePageDescription(groupDescription, canCreatePagePredicate)))
                .groupDescriptions(List.of(groupDescription))
                .build();
        // @formatter:on
    }

    private FormDescription getIconLabelNodeStyleProperties() {
        String formDescriptionId = UUID.nameUUIDFromBytes("iconlabelnodestyle".getBytes()).toString(); //$NON-NLS-1$

        // @formatter:off
        List<AbstractControlDescription> controls = this.getGeneralControlDescription();

        GroupDescription groupDescription = this.createSimpleGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager ->  variableManager.get(VariableManager.SELF, Object.class)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .filter(self -> self instanceof IconLabelNodeStyleDescription)
                .isPresent();

        return FormDescription.newFormDescription(formDescriptionId)
                .label("IconLabel Node Style") //$NON-NLS-1$
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, NodeStyleDescription.class).map(NodeStyleDescription::getColor).orElse(UNNAMED))
                .canCreatePredicate(variableManager -> true)
                .idProvider(new GetOrCreateRandomIdProvider())
                .targetObjectIdProvider(this.getTargetObjectIdProvider())
                .pageDescriptions(List.of(this.createSimplePageDescription(groupDescription, canCreatePagePredicate)))
                .groupDescriptions(List.of(groupDescription))
                .build();
        // @formatter:on
    }

    private FormDescription getRectangularNodeStyleProperties() {
        String formDescriptionId = UUID.nameUUIDFromBytes("rectangularnodestyle".getBytes()).toString(); //$NON-NLS-1$

        // @formatter:off
        List<AbstractControlDescription> controls = new ArrayList<>();
        controls.add(this.createCheckbox("nodestyle.isWithHeader", "With Header", //$NON-NLS-1$ //$NON-NLS-2$
                style -> ((RectangularNodeStyleDescription) style).isWithHeader(),
                (style, newWithHeaderValue) -> ((RectangularNodeStyleDescription) style).setWithHeader(newWithHeaderValue),
                ViewPackage.Literals.RECTANGULAR_NODE_STYLE_DESCRIPTION__WITH_HEADER));
        controls.addAll(this.getGeneralControlDescription());

        GroupDescription groupDescription = this.createSimpleGroupDescription(controls);

        Predicate<VariableManager> canCreatePagePredicate = variableManager ->  variableManager.get(VariableManager.SELF, Object.class)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .filter(self -> self instanceof RectangularNodeStyleDescription)
                .isPresent();

        return FormDescription.newFormDescription(formDescriptionId)
                .label("Rectangular Node Style") //$NON-NLS-1$
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, NodeStyleDescription.class).map(NodeStyleDescription::getColor).orElse(UNNAMED))
                .canCreatePredicate(variableManager -> true)
                .idProvider(new GetOrCreateRandomIdProvider())
                .targetObjectIdProvider(this.getTargetObjectIdProvider())
                .pageDescriptions(List.of(this.createSimplePageDescription(groupDescription, canCreatePagePredicate)))
                .groupDescriptions(List.of(groupDescription))
                .build();
        // @formatter:on
    }

    private List<AbstractControlDescription> getGeneralControlDescription() {
     // @formatter:off
        List<AbstractControlDescription> controls = List.of(
                this.createTextField("nodestyle.sizeExpression", "Size Expression", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((NodeStyleDescription) style).getSizeComputationExpression(),
                        (style, newSizeExpression) -> ((NodeStyleDescription) style).setSizeComputationExpression(newSizeExpression),
                        ViewPackage.Literals.NODE_STYLE_DESCRIPTION__SIZE_COMPUTATION_EXPRESSION),
                this.createCheckbox("nodestyle.showIcon", "Show Icon", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((NodeStyleDescription) style).isShowIcon(),
                        (style, newValue) -> ((NodeStyleDescription) style).setShowIcon(newValue),
                        ViewPackage.Literals.NODE_STYLE_DESCRIPTION__SHOW_ICON),
                this.createTextField("nodestyle.labelColor", "Label Color", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((NodeStyleDescription) style).getLabelColor(),
                        (style, newLabelColor) -> ((NodeStyleDescription) style).setLabelColor(newLabelColor),
                        ViewPackage.Literals.NODE_STYLE_DESCRIPTION__LABEL_COLOR),
                this.createTextField("nodestyle.color", "Color", //$NON-NLS-1$ //$NON-NLS-2$
                                     style -> ((NodeStyleDescription) style).getColor(),
                                     (style, newColor) -> ((NodeStyleDescription) style).setColor(newColor),
                                     ViewPackage.Literals.STYLE__COLOR),
                this.createTextField("nodestyle.borderColor", "Border Color", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((NodeStyleDescription) style).getBorderColor(),
                        (style, newColor) -> ((NodeStyleDescription) style).setBorderColor(newColor),
                        ViewPackage.Literals.BORDER_STYLE__BORDER_COLOR),
                this.createTextField("nodestyle.borderRadius", "Border Radius", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> String.valueOf(((NodeStyleDescription) style).getBorderRadius()),
                        (style, newBorderRadius) -> {
                            try {
                                ((NodeStyleDescription) style).setBorderRadius(Integer.parseInt(newBorderRadius));
                            } catch (NumberFormatException nfe) {
                                // Ignore.
                            }
                        },
                        ViewPackage.Literals.BORDER_STYLE__BORDER_RADIUS),
                this.createTextField("nodestyle.borderSize", "Border Size", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> String.valueOf(((NodeStyleDescription) style).getBorderSize()),
                        (style, newBorderSize) -> {
                            try {
                                ((NodeStyleDescription) style).setBorderSize(Integer.parseInt(newBorderSize));
                            } catch (NumberFormatException nfe) {
                                // Ignore.
                            }
                        },
                        ViewPackage.Literals.BORDER_STYLE__BORDER_SIZE),
                this.createBorderLineStyleSelectionField("nodestyle.borderstyle", ViewPackage.Literals.BORDER_STYLE__BORDER_LINE_STYLE), //$NON-NLS-1$
                this.createTextField("nodestyle.fontSize", "Font Size", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> String.valueOf(((LabelStyle) style).getFontSize()),
                        (style, newColor) -> {
                            try {
                                ((LabelStyle) style).setFontSize(Integer.parseInt(newColor));
                            } catch (NumberFormatException nfe) {
                                // Ignore.
                            }
                        },
                        ViewPackage.Literals.LABEL_STYLE__FONT_SIZE),
                this.createCheckbox("nodestyle.italic", "Italic", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((LabelStyle) style).isItalic(),
                        (style, newItalic) -> ((LabelStyle) style).setItalic(newItalic),
                        ViewPackage.Literals.LABEL_STYLE__ITALIC),
                this.createCheckbox("nodestyle.bold", "Bold", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((LabelStyle) style).isBold(),
                        (style, newBold) -> ((LabelStyle) style).setBold(newBold),
                        ViewPackage.Literals.LABEL_STYLE__BOLD),
                this.createCheckbox("nodestyle.underline", "Underline", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((LabelStyle) style).isUnderline(),
                        (style, newUnderline) -> ((LabelStyle) style).setUnderline(newUnderline),
                        ViewPackage.Literals.LABEL_STYLE__UNDERLINE),
                this.createCheckbox("nodestyle.strikeThrough", "Strike Through", //$NON-NLS-1$ //$NON-NLS-2$
                        style -> ((LabelStyle) style).isStrikeThrough(),
                        (style, newStrikeThrough) -> ((LabelStyle) style).setStrikeThrough(newStrikeThrough),
                        ViewPackage.Literals.LABEL_STYLE__STRIKE_THROUGH));
        // @formatter:on
        return controls;
    }

    private Function<VariableManager, String> getTargetObjectIdProvider() {
        // @formatter:off
        return variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .map(obj -> EcoreUtil.getURI(obj).toString())
                .orElse(null);
        // @formatter:on
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

    private TextfieldDescription createTextField(String id, String title, Function<Object, String> reader, BiConsumer<Object, String> writer, Object feature) {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(reader).orElse(EMPTY);
        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            var optionalDiagramMapping = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalDiagramMapping.isPresent()) {
                writer.accept(optionalDiagramMapping.get(), newValue);
                return new Success();
            } else {
                return new Failure(""); //$NON-NLS-1$
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

    private CheckboxDescription createCheckbox(String id, String title, Function<Object, Boolean> reader, BiConsumer<Object, Boolean> writer, Object feature) {
        Function<VariableManager, Boolean> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(reader).orElse(Boolean.FALSE);
        BiFunction<VariableManager, Boolean, IStatus> newValueHandler = (variableManager, newValue) -> {
            var optionalDiagramMapping = variableManager.get(VariableManager.SELF, Object.class);
            if (optionalDiagramMapping.isPresent()) {
                writer.accept(optionalDiagramMapping.get(), newValue);
                return new Success();
            } else {
                return new Failure(""); //$NON-NLS-1$
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
                                .labelProvider(variableManager -> "Border Line Style") //$NON-NLS-1$
                                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, BorderStyle.class).map(BorderStyle::getBorderLineStyle).map(LineStyle::toString).orElse(EMPTY))
                                .optionsProvider(variableManager -> LineStyle.VALUES.stream().collect(Collectors.toList()))
                                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, LineStyle.class).map(LineStyle::getLiteral).orElse(EMPTY))
                                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, LineStyle.class).map(LineStyle::getName).orElse(EMPTY))
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
                                        return new Failure(""); //$NON-NLS-1$
                                })
                                .diagnosticsProvider(this.getDiagnosticsProvider(feature))
                                .kindProvider(this::kindProvider)
                                .messageProvider(this::messageProvider)
                                .build();
        // @formatter:on
    }

    private SelectDescription createShapeSelectionField(Object feature) {
        // @formatter:off
        return SelectDescription.newSelectDescription("nodestyle.shapeSelector") //$NON-NLS-1$
                .idProvider(variableManager -> "nodestyle.shapeSelector") //$NON-NLS-1$
                .labelProvider(variableManager -> "Shape") //$NON-NLS-1$
                .valueProvider(variableManager -> variableManager.get(VariableManager.SELF, ImageNodeStyleDescription.class).map(ImageNodeStyleDescription::getShape).orElse(EMPTY))
                .optionsProvider(variableManager -> {
                    List<CustomImage> parametricSVGs = this.parametricSVGImageRegistries.stream()
                        .flatMap(service-> service.getImages().stream())
                        .map(image -> new CustomImage(image.getId(), image.getLabel(), "image/svg+xml")) //$NON-NLS-1$
                        .collect(Collectors.toList());

                    List<CustomImage> customImages = List.of();
                    Optional<IEditingContext> optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
                    if (optionalEditingContext.isPresent()) {
                        customImages =  this.customImageSearchService.getAvailableImages(optionalEditingContext.get().getId()).stream()
                                .sorted(Comparator.comparing(CustomImage::getLabel))
                                .collect(Collectors.toList());
                    }
                    return Stream.concat(parametricSVGs.stream(), customImages.stream())
                            .collect(Collectors.toList());
                })
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, CustomImage.class)
                        .map(CustomImage::getId)
                        .map(UUID::toString)
                        .orElse(EMPTY))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, CustomImage.class)
                        .map(CustomImage::getLabel)
                        .orElse(EMPTY))
                .newValueHandler(this.getNewShapeValueHandler())
                .diagnosticsProvider(this.getDiagnosticsProvider(feature))
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .build();
        // @formatter:on
    }

    private ImageDescription createShapePreviewField() {
        // @formatter:off
        return ImageDescription.newImageDescription("nodestyle.shapePreview") //$NON-NLS-1$
                .idProvider(variableManager -> "nodestyle.shapePreview") //$NON-NLS-1$
                .labelProvider(variableManager -> "Shape Preview") //$NON-NLS-1$
                .urlProvider(variableManager -> {
                    Optional<IEditingContext> optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
                    if (optionalEditingContext.isPresent()) {
                        var optionalShape = variableManager.get(VariableManager.SELF, ImageNodeStyleDescription.class).map(ImageNodeStyleDescription::getShape);
                        if (optionalShape.isPresent()) {
                            return String.format("/custom/%s/%s", optionalEditingContext.get().getId(), optionalShape.get()); //$NON-NLS-1$
                        }
                    }
                    return "";   //$NON-NLS-1$
                })
                .maxWidthProvider(variableManager -> "300px") //$NON-NLS-1$
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
            return new Failure(""); //$NON-NLS-1$
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
        String kind = "Unknown"; //$NON-NLS-1$
        if (object instanceof Diagnostic) {
            Diagnostic diagnostic = (Diagnostic) object;
            switch (diagnostic.getSeverity()) {
            case org.eclipse.emf.common.util.Diagnostic.ERROR:
                kind = "Error"; //$NON-NLS-1$
                break;
            case org.eclipse.emf.common.util.Diagnostic.WARNING:
                kind = "Warning"; //$NON-NLS-1$
                break;
            case org.eclipse.emf.common.util.Diagnostic.INFO:
                kind = "Info"; //$NON-NLS-1$
                break;
            default:
                kind = "Unknown"; //$NON-NLS-1$
                break;
            }
        }
        return kind;
    }

    private String messageProvider(Object object) {
        if (object instanceof Diagnostic) {
            Diagnostic diagnostic = (Diagnostic) object;
            return diagnostic.getMessage();
        }
        return ""; //$NON-NLS-1$
    }

}
