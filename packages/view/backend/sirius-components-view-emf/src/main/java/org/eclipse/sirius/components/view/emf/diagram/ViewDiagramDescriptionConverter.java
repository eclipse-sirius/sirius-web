/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.diagrams.ArrangeLayoutDirection;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.OutsideLabelLocation;
import org.eclipse.sirius.components.diagrams.UserResizableDirection;
import org.eclipse.sirius.components.diagrams.components.LabelIdProvider;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.description.IEdgeEditLabelHandler;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.OutsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.elements.EdgeElementProps;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.emf.DomainClassPredicate;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.FreeFormLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelStyle;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.eclipse.sirius.components.view.emf.ViewIconURLsProvider;
import org.eclipse.sirius.components.view.emf.diagram.api.IToolConverter;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolExecutor;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based diagram description into an equivalent {@link DiagramDescription}.
 *
 * @author pcdavid
 */
@Service
public class ViewDiagramDescriptionConverter implements IRepresentationDescriptionConverter {

    public static final String CONVERTED_NODES_VARIABLE = "convertedNodes";

    private static final String DEFAULT_DIAGRAM_LABEL = "Diagram";

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final IToolConverter toolConverter;

    private final IToolExecutor toolExecutor;

    private final IDiagramIdProvider diagramIdProvider;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    private final Function<VariableManager, String> semanticTargetLabelProvider;

    private final List<INodeStyleProvider> nodeStyleProviders;

    public ViewDiagramDescriptionConverter(IIdentityService identityService, ILabelService labelService, IToolConverter toolConverter, IToolExecutor toolExecutor, List<INodeStyleProvider> nodeStyleProviders, IDiagramIdProvider diagramIdProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.toolConverter = Objects.requireNonNull(toolConverter);
        this.toolExecutor = Objects.requireNonNull(toolExecutor);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.nodeStyleProviders = Objects.requireNonNull(nodeStyleProviders);
        this.semanticTargetIdProvider = variableManager -> this.self(variableManager).map(this.identityService::getId).orElse(null);
        this.semanticTargetKindProvider = variableManager -> this.self(variableManager).map(this.identityService::getKind).orElse(null);
        this.semanticTargetLabelProvider = variableManager -> this.self(variableManager).map(this.labelService::getStyledLabel).map(Object::toString).orElse(null);
    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.diagram.DiagramDescription;
    }

    @Override
    public IRepresentationDescription convert(RepresentationDescription viewRepresentationDescription, List<RepresentationDescription> allRepresentationDescriptions, AQLInterpreter interpreter) {
        final org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = (org.eclipse.sirius.components.view.diagram.DiagramDescription) viewRepresentationDescription;
        ViewDiagramDescriptionConverterContext converterContext = new ViewDiagramDescriptionConverterContext(interpreter);
        StylesFactory stylesFactory = new StylesFactory(this.nodeStyleProviders, this.labelService, interpreter);

        // Nodes must be fully converted first.
        List<NodeDescription> nodeDescriptions = viewDiagramDescription.getNodeDescriptions().stream().map(node -> this.convert(node, converterContext, stylesFactory)).toList();
        // Edges that have a node as source or target
        List<org.eclipse.sirius.components.view.diagram.EdgeDescription> edgeDescriptionsWithNodesAsSourceOrTarget = viewDiagramDescription.getEdgeDescriptions().stream()
                .filter(this::edgeDescriptionWithNodesAsSourceAndTargetToConsider).toList();
        // Edges that have candidates edges as source or target
        List<org.eclipse.sirius.components.view.diagram.EdgeDescription> edgeDescriptionsWithAnotherEdgeAsSourceOrTarget = viewDiagramDescription.getEdgeDescriptions().stream()
                .filter(edgeDescription -> this.edgeDescriptionWithAnotherEdgeAsSourceOrTargetToConsider(edgeDescription, edgeDescriptionsWithNodesAsSourceOrTarget)).toList();
        // Edges that have a node as source or target must be fully converted first
        List<EdgeDescription> edgeDescriptions = Stream.concat(edgeDescriptionsWithNodesAsSourceOrTarget.stream(), edgeDescriptionsWithAnotherEdgeAsSourceOrTarget.stream())
                .map(edge -> this.convert(edge, converterContext, stylesFactory)).toList();

        var builder = DiagramDescription.newDiagramDescription(this.diagramIdProvider.getId(viewDiagramDescription))
                .label(Optional.ofNullable(viewDiagramDescription.getName()).orElse(DEFAULT_DIAGRAM_LABEL))
                .labelProvider(variableManager -> this.computeDiagramLabel(viewDiagramDescription, variableManager, interpreter))
                .canCreatePredicate(new IViewDiagramCreationPredicate() {
                    @Override
                    public boolean test(VariableManager variableManager) {
                        return ViewDiagramDescriptionConverter.this.canCreateDiagram(viewDiagramDescription, variableManager, interpreter);
                    }

                    @Override
                    public org.eclipse.sirius.components.view.diagram.DiagramDescription getSourceDiagramDescription() {
                        return viewDiagramDescription;
                    }
                })
                .autoLayout(viewDiagramDescription.isAutoLayout())
                .arrangeLayoutDirection(ArrangeLayoutDirection.valueOf(viewDiagramDescription.getArrangeLayoutDirection().getLiteral()))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .nodeDescriptions(nodeDescriptions)
                .edgeDescriptions(edgeDescriptions)
                .palettes(this.toolConverter.createPaletteBasedToolSections(viewDiagramDescription, converterContext))
                .dropHandler(this.createDiagramDropHandler(viewDiagramDescription, converterContext))
                .iconURLsProvider(new ViewIconURLsProvider(interpreter, viewDiagramDescription.getIconExpression()));

        new ToolFinder().findDropNodeTool(viewDiagramDescription).ifPresent(dropNoteTool -> {
            builder.dropNodeHandler(this.createDropNodeHandler(dropNoteTool, converterContext));
        });

        return builder.build();
    }

    /**
     * Used to filter the edge descriptions that are using node descriptions as their sources and targets.
     *
     * @param edgeDescription
     *         The edge description
     * @return true if we will use the edge description, false otherwise
     */
    private boolean edgeDescriptionWithNodesAsSourceAndTargetToConsider(org.eclipse.sirius.components.view.diagram.EdgeDescription edgeDescription) {
        return edgeDescription.getTargetDescriptions().stream().allMatch(org.eclipse.sirius.components.view.diagram.NodeDescription.class::isInstance)
                && edgeDescription.getSourceDescriptions().stream().allMatch(org.eclipse.sirius.components.view.diagram.NodeDescription.class::isInstance);
    }

    /**
     * Used to filter the edge descriptions that are supported by Sirius Web.
     * We will only consider edge descriptions that are using node descriptions as their sources and targets
     * or another edge that is using node descriptions as their sources and targets
     *
     * @param edgeDescription
     *         The edge description
     * @param candidateEdgeDescriptions
     *         The edges that can be used as source or target
     * @return true if we will use the edge description, false otherwise
     */
    private boolean edgeDescriptionWithAnotherEdgeAsSourceOrTargetToConsider(org.eclipse.sirius.components.view.diagram.EdgeDescription edgeDescription, List<org.eclipse.sirius.components.view.diagram.EdgeDescription> candidateEdgeDescriptions) {
        return !candidateEdgeDescriptions.contains(edgeDescription) &&
                (edgeDescription.getTargetDescriptions().stream()
                        .allMatch(description -> description instanceof org.eclipse.sirius.components.view.diagram.NodeDescription
                                || (description instanceof org.eclipse.sirius.components.view.diagram.EdgeDescription && candidateEdgeDescriptions.contains(description)))
                        && edgeDescription.getSourceDescriptions().stream()
                        .allMatch(description -> description instanceof org.eclipse.sirius.components.view.diagram.NodeDescription
                                || (description instanceof org.eclipse.sirius.components.view.diagram.EdgeDescription && candidateEdgeDescriptions.contains(description))));
    }

    private Function<VariableManager, IStatus> createDiagramDropHandler(org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription,
            ViewDiagramDescriptionConverterContext converterContext) {
        return variableManager -> {
            Optional<DropTool> optionalDropTool = new ToolFinder().findDropTool(viewDiagramDescription);
            if (optionalDropTool.isPresent()) {
                var childVariableManager = variableManager.createChild();
                var convertedNodes = Collections.unmodifiableMap(converterContext.getConvertedNodes());
                childVariableManager.put(CONVERTED_NODES_VARIABLE, convertedNodes);

                return this.toolExecutor.executeTool(optionalDropTool.get(), converterContext.getInterpreter(), childVariableManager);
            } else {
                return new Failure("No drop handler configured");
            }
        };
    }

    private String computeDiagramLabel(org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String title = variableManager.get(DiagramDescription.LABEL, String.class)
                .orElseGet(() -> this.evaluateString(interpreter, variableManager, viewDiagramDescription.getTitleExpression()));
        if (title == null || title.isBlank()) {
            return DEFAULT_DIAGRAM_LABEL;
        } else {
            return title;
        }
    }

    private boolean canCreateDiagram(org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        boolean result = false;
        Optional<EClass> optionalEClass = variableManager.get(IRepresentationDescription.CLASS, EClass.class)
                .filter(new DomainClassPredicate(viewDiagramDescription.getDomainType()));
        if (optionalEClass.isPresent()) {
            String preconditionExpression = viewDiagramDescription.getPreconditionExpression();
            if (preconditionExpression != null && !preconditionExpression.isBlank()) {
                result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression).asBoolean().orElse(false);
            } else {
                result = true;
            }
        }
        return result;
    }

    private NodeDescription convert(org.eclipse.sirius.components.view.diagram.NodeDescription viewNodeDescription, ViewDiagramDescriptionConverterContext converterContext, StylesFactory stylesFactory) {
        // Convert our children first, we need their converted values to build our NodeDescription
        var childNodeDescriptions = viewNodeDescription.getChildrenDescriptions().stream()
                .map(childNodeDescription -> this.convert(childNodeDescription, converterContext, stylesFactory))
                .toList();

        var borderNodeDescriptions = viewNodeDescription.getBorderNodesDescriptions().stream()
                .map(borderNodeDescription -> this.convert(borderNodeDescription, converterContext, stylesFactory))
                .toList();
        SynchronizationPolicy synchronizationPolicy = SynchronizationPolicy.valueOf(viewNodeDescription.getSynchronizationPolicy().getName());
        UserResizableDirection userResizableDirection = UserResizableDirection.valueOf(viewNodeDescription.getUserResizable().getLiteral());

        AQLInterpreter interpreter = converterContext.getInterpreter();
        Function<VariableManager, String> typeProvider = variableManager -> {
            var effectiveStyle = this.findEffectiveStyle(viewNodeDescription, interpreter, variableManager);
            return stylesFactory.getNodeType(effectiveStyle);
        };

        Function<VariableManager, INodeStyle> styleProvider = variableManager -> {
            var effectiveStyle = this.findEffectiveStyle(viewNodeDescription, interpreter, variableManager);
            Optional<String> optionalEditingContextId = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId);
            ILayoutStrategy childrenLayoutStrategy = new FreeFormLayoutStrategy(); //FreeForm as default value

            LayoutStrategyDescription childrenLayoutStrategyFromViewModel = effectiveStyle.getChildrenLayoutStrategy();
            if (childrenLayoutStrategyFromViewModel instanceof ListLayoutStrategyDescription listLayoutStrategyDescription) {
                childrenLayoutStrategy = this.getListLayoutStrategy(listLayoutStrategyDescription, variableManager, interpreter);
            } else if (childrenLayoutStrategyFromViewModel instanceof FreeFormLayoutStrategyDescription) {
                childrenLayoutStrategy = new FreeFormLayoutStrategy();
            }
            return stylesFactory.createNodeStyle(effectiveStyle, optionalEditingContextId, childrenLayoutStrategy);
        };

        Predicate<VariableManager> isCollapsedByDefaultPredicate = variableManager -> this.computeBooleanProvider(viewNodeDescription.getIsCollapsedByDefaultExpression(), interpreter, variableManager);

        Predicate<VariableManager> isHiddenByDefaultPredicate = variableManager -> this.computeBooleanProvider(viewNodeDescription.getIsHiddenByDefaultExpression(), interpreter, variableManager);

        Predicate<VariableManager> isFadedByDefaultPredicate = variableManager -> this.computeBooleanProvider(viewNodeDescription.getIsFadedByDefaultExpression(), interpreter, variableManager);

        Function<VariableManager, Integer> defaultWidthProvider = variableManager -> this.computeDefaultSizeProvider(viewNodeDescription.getDefaultWidthExpression(), interpreter,
                variableManager);
        Function<VariableManager, Integer> defaultHeightProvider = variableManager -> this.computeDefaultSizeProvider(viewNodeDescription.getDefaultHeightExpression(), interpreter,
                variableManager);

        List<String> reusedChildNodeDescriptionIds = viewNodeDescription.getReusedChildNodeDescriptions().stream()
                .map(this.diagramIdProvider::getId)
                .toList();
        List<String> reusedBorderNodeDescriptionIds = viewNodeDescription.getReusedBorderNodeDescriptions().stream()
                .map(this.diagramIdProvider::getId)
                .toList();

        Predicate<VariableManager> shouldRenderPredicate = variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), viewNodeDescription.getPreconditionExpression());
            return result.asBoolean().orElse(true);
        };

        var insideLabel = this.getInsideLabelDescription(viewNodeDescription, interpreter, stylesFactory);

        NodeDescription.Builder builder = NodeDescription.newNodeDescription(this.diagramIdProvider.getId(viewNodeDescription))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .targetObjectLabelProvider(this.semanticTargetLabelProvider)
                .semanticElementsProvider(this.getSemanticElementsProvider(viewNodeDescription, interpreter))
                .synchronizationPolicy(synchronizationPolicy)
                .typeProvider(typeProvider)
                .outsideLabelDescriptions(this.getOutsideLabelDescriptions(viewNodeDescription, interpreter, stylesFactory))
                .styleProvider(styleProvider)
                .childNodeDescriptions(childNodeDescriptions)
                .borderNodeDescriptions(borderNodeDescriptions)
                .collapsible(viewNodeDescription.isCollapsible())
                .reusedChildNodeDescriptionIds(reusedChildNodeDescriptionIds)
                .reusedBorderNodeDescriptionIds(reusedBorderNodeDescriptionIds)
                .userResizable(userResizableDirection)
                .deleteHandler(this.createDeleteHandler(viewNodeDescription, converterContext))
                .shouldRenderPredicate(shouldRenderPredicate)
                .isCollapsedByDefaultPredicate(isCollapsedByDefaultPredicate)
                .isHiddenByDefaultPredicate(isHiddenByDefaultPredicate)
                .isFadedByDefaultPredicate(isFadedByDefaultPredicate)
                .defaultWidthProvider(defaultWidthProvider)
                .defaultHeightProvider(defaultHeightProvider)
                .keepAspectRatio(viewNodeDescription.isKeepAspectRatio());
        if (insideLabel != null) {
            builder.insideLabelDescription(insideLabel);
        }
        new ToolFinder().findDropNodeTool(viewNodeDescription).ifPresent(dropNoteTool -> builder.dropNodeHandler(this.createDropNodeHandler(dropNoteTool, converterContext)));
        new ToolFinder().findNodeLabelEditTool(viewNodeDescription)
                .ifPresent(labelEditTool -> builder.labelEditHandler(this.createNodeLabelEditHandler(viewNodeDescription, converterContext)));
        NodeDescription result = builder.build();
        converterContext.getConvertedNodes().put(viewNodeDescription, result);
        return result;
    }

    private ILayoutStrategy getListLayoutStrategy(ListLayoutStrategyDescription listLayoutStrategyDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Result resultAreChildNodesDraggable = interpreter.evaluateExpression(variableManager.getVariables(), listLayoutStrategyDescription.getAreChildNodesDraggableExpression());
        var builder = ListLayoutStrategy.newListLayoutStrategy()
                .areChildNodesDraggable(resultAreChildNodesDraggable.asBoolean().orElse(true))
                .growableNodeIds(listLayoutStrategyDescription.getGrowableNodes().stream().map(this.diagramIdProvider::getId).toList());
        if (listLayoutStrategyDescription.getBottomGapExpression() != null && !listLayoutStrategyDescription.getBottomGapExpression().isBlank()) {
            Result resultBottomGap = interpreter.evaluateExpression(variableManager.getVariables(), listLayoutStrategyDescription.getBottomGapExpression());
            builder.bottomGap(resultBottomGap.asInt().orElse(0));
        }
        if (listLayoutStrategyDescription.getTopGapExpression() != null && !listLayoutStrategyDescription.getTopGapExpression().isBlank()) {
            Result resultTopGap = interpreter.evaluateExpression(variableManager.getVariables(), listLayoutStrategyDescription.getTopGapExpression());
            builder.topGap(resultTopGap.asInt().orElse(0));
        }

        return builder.build();
    }

    private Boolean computeBooleanProvider(String booleanExpression, AQLInterpreter interpreter, VariableManager variableManager) {
        if (booleanExpression != null && !booleanExpression.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), booleanExpression);
            if (result.getStatus().compareTo(Status.WARNING) <= 0) {
                return result.asBoolean().orElse(false);
            }
        }
        return false;
    }

    private Integer computeDefaultSizeProvider(String defaultSizeExpression, AQLInterpreter interpreter, VariableManager variableManager) {
        if (defaultSizeExpression != null && !defaultSizeExpression.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), defaultSizeExpression);
            if (result.getStatus().compareTo(Status.WARNING) <= 0 && result.asInt().isPresent()) {
                return result.asInt().getAsInt();
            }
        }
        return null;
    }

    private NodeStyleDescription findEffectiveStyle(org.eclipse.sirius.components.view.diagram.NodeDescription viewNodeDescription, AQLInterpreter interpreter, VariableManager variableManager) {
        return viewNodeDescription.getConditionalStyles().stream().filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                .map(ConditionalNodeStyle::getStyle).findFirst()
                .orElseGet(viewNodeDescription::getStyle);
    }

    private boolean matches(AQLInterpreter interpreter, String condition, VariableManager variableManager) {
        return interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }

    private InsideLabelDescription getInsideLabelDescription(org.eclipse.sirius.components.view.diagram.NodeDescription viewNodeDescription, AQLInterpreter interpreter, StylesFactory stylesFactory) {
        var viewInsideLabelDescription = viewNodeDescription.getInsideLabel();
        if (viewInsideLabelDescription == null) {
            return null;
        }

        Function<VariableManager, LabelStyleDescription> styleDescriptionProvider = variableManager -> {
            var effectiveStyle = this.findEffectiveInsideLabelStyle(viewInsideLabelDescription, interpreter, variableManager);
            return stylesFactory.createInsideLabelStyle(effectiveStyle);
        };

        Function<VariableManager, Boolean> isHeaderProvider = variableManager -> {
            var effectiveStyle = this.findEffectiveInsideLabelStyle(viewInsideLabelDescription, interpreter, variableManager);
            if (effectiveStyle != null) {
                return effectiveStyle.isWithHeader();
            }
            return false;
        };

        Function<VariableManager, HeaderSeparatorDisplayMode> headerSeparatorDisplayModeProvider = variableManager -> {
            var effectiveStyle = this.findEffectiveInsideLabelStyle(viewInsideLabelDescription, interpreter, variableManager);
            if (effectiveStyle != null) {
                return HeaderSeparatorDisplayMode.valueOf(effectiveStyle.getHeaderSeparatorDisplayMode().getLiteral());
            }
            return HeaderSeparatorDisplayMode.NEVER;
        };


        return InsideLabelDescription.newInsideLabelDescription(EcoreUtil.getURI(viewNodeDescription).toString() + LabelIdProvider.INSIDE_LABEL_SUFFIX)
                .textProvider(variableManager -> this.evaluateString(interpreter, variableManager, viewInsideLabelDescription.getLabelExpression()))
                .styleDescriptionProvider(styleDescriptionProvider)
                .isHeaderProvider(isHeaderProvider)
                .headerSeparatorDisplayModeProvider(headerSeparatorDisplayModeProvider)
                .insideLabelLocation(InsideLabelLocation.valueOf(viewInsideLabelDescription.getPosition().getLiteral()))
                .overflowStrategy(LabelOverflowStrategy.valueOf(viewInsideLabelDescription.getOverflowStrategy().getLiteral()))
                .textAlign(LabelTextAlign.valueOf(viewInsideLabelDescription.getTextAlign().getLiteral()))
                .build();
    }

    private List<OutsideLabelDescription> getOutsideLabelDescriptions(org.eclipse.sirius.components.view.diagram.NodeDescription viewNodeDescription, AQLInterpreter interpreter, StylesFactory stylesFactory) {
        return viewNodeDescription.getOutsideLabels().stream().map(outsideLabelDescription -> {

            Function<VariableManager, LabelStyleDescription> styleDescriptionProvider = variableManager -> {
                var effectiveStyle = this.findEffectiveOutsideLabelStyle(outsideLabelDescription, interpreter, variableManager);
                return stylesFactory.createOutsideLabelStyle(effectiveStyle);
            };

            return OutsideLabelDescription.newOutsideLabelDescription(EcoreUtil.getURI(viewNodeDescription)
                            .toString() + LabelIdProvider.OUTSIDE_LABEL_SUFFIX + outsideLabelDescription.getPosition().getLiteral())
                    .textProvider(variableManager -> this.evaluateString(interpreter, variableManager, outsideLabelDescription.getLabelExpression()))
                    .styleDescriptionProvider(styleDescriptionProvider)
                    .outsideLabelLocation(OutsideLabelLocation.BOTTOM_MIDDLE)
                    .overflowStrategy(LabelOverflowStrategy.valueOf(outsideLabelDescription.getOverflowStrategy().getLiteral()))
                    .textAlign(LabelTextAlign.valueOf(outsideLabelDescription.getTextAlign().getLiteral()))
                    .build();
        }).toList();
    }

    private InsideLabelStyle findEffectiveInsideLabelStyle(org.eclipse.sirius.components.view.diagram.InsideLabelDescription insideLabelDescription, AQLInterpreter interpreter,
            VariableManager variableManager) {
        return insideLabelDescription.getConditionalStyles().stream().filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                .map(ConditionalInsideLabelStyle::getStyle).findFirst()
                .orElseGet(insideLabelDescription::getStyle);
    }

    private OutsideLabelStyle findEffectiveOutsideLabelStyle(org.eclipse.sirius.components.view.diagram.OutsideLabelDescription outsideLabelDescription, AQLInterpreter interpreter,
            VariableManager variableManager) {
        return outsideLabelDescription.getConditionalStyles().stream().filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                .map(ConditionalOutsideLabelStyle::getStyle).findFirst()
                .orElseGet(outsideLabelDescription::getStyle);
    }

    private Optional<LabelDescription> getSpecificEdgeLabelDescription(org.eclipse.sirius.components.view.diagram.EdgeDescription viewEdgeDescription, String labelExpression, String labelSuffix,
            AQLInterpreter interpreter, StylesFactory stylesFactory) {
        if (labelExpression == null || labelExpression.isBlank()) {
            return Optional.empty();
        }

        Function<VariableManager, LabelStyleDescription> styleDescriptionProvider = variableManager -> {
            var effectiveStyle = viewEdgeDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                    .map(org.eclipse.sirius.components.view.diagram.EdgeStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewEdgeDescription::getStyle);

            return stylesFactory.createEdgeLabelStyleDescription(effectiveStyle);
        };

        return Optional.of(LabelDescription.newLabelDescription(EcoreUtil.getURI(viewEdgeDescription).toString() + labelSuffix)
                .textProvider(variableManager -> this.evaluateString(interpreter, variableManager, labelExpression))
                .styleDescriptionProvider(styleDescriptionProvider)
                .build());
    }

    private Function<VariableManager, List<?>> getSemanticElementsProvider(DiagramElementDescription elementDescription, AQLInterpreter interpreter) {
        return variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), elementDescription.getSemanticCandidatesExpression());
            List<Object> candidates = result.asObjects().orElse(List.of());
            return candidates.stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .filter(candidate -> new DomainClassPredicate(Optional.ofNullable(elementDescription.getDomainType()).orElse("")).test(candidate.eClass()))
                    .toList();
        };
    }

    private EdgeDescription convert(org.eclipse.sirius.components.view.diagram.EdgeDescription viewEdgeDescription, ViewDiagramDescriptionConverterContext converterContext, StylesFactory stylesFactory) {
        Function<VariableManager, List<?>> semanticElementsProvider;
        AQLInterpreter interpreter = converterContext.getInterpreter();
        if (viewEdgeDescription.isIsDomainBasedEdge()) {
            // Same logic as for nodes.
            semanticElementsProvider = this.getSemanticElementsProvider(viewEdgeDescription, interpreter);
        } else {
            //
            var sourceNodeDescriptions = viewEdgeDescription.getSourceDescriptions().stream().map(converterContext.getConvertedNodes()::get);
            semanticElementsProvider = new RelationBasedSemanticElementsProvider(sourceNodeDescriptions.map(NodeDescription::getId).toList());
        }

        Predicate<VariableManager> shouldRenderPredicate = variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), viewEdgeDescription.getPreconditionExpression());
            return result.asBoolean().orElse(true);
        };

        Function<VariableManager, List<Element>> sourceProvider = null;
        if (viewEdgeDescription.isIsDomainBasedEdge()) {
            sourceProvider = variableManager -> {
                var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
                if (optionalCache.isEmpty()) {
                    return List.of();
                }
                DiagramRenderingCache cache = optionalCache.get();
                String sourceFinderExpression = viewEdgeDescription.getSourceExpression();

                Result result = interpreter.evaluateExpression(variableManager.getVariables(), sourceFinderExpression);
                List<Object> semanticCandidates = result.asObjects().orElse(List.of());
                var diagramElementCandidates = semanticCandidates.stream().flatMap(semanticObject -> cache.getElementsRepresenting(semanticObject).stream());

                return diagramElementCandidates
                        .filter(diagramElement -> viewEdgeDescription.getSourceDescriptions().stream().anyMatch(description -> this.isFromDescription(diagramElement, description)))
                        .filter(Objects::nonNull)
                        .toList();
            };
        } else {
            sourceProvider = variableManager -> {
                var optionalObject = variableManager.get(VariableManager.SELF, Object.class);
                var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
                if (optionalObject.isEmpty() || optionalCache.isEmpty()) {
                    return List.of();
                }

                DiagramRenderingCache cache = optionalCache.get();
                Object object = optionalObject.get();

                return cache.getElementsRepresenting(object).stream()
                        .filter(this.isFromCompatibleSourceMapping(viewEdgeDescription))
                        .filter(Objects::nonNull)
                        .toList();
            };
        }

        Function<VariableManager, List<Element>> targetProvider = new TargetProvider(this.diagramIdProvider, viewEdgeDescription, interpreter);

        Function<VariableManager, EdgeStyle> styleProvider = variableManager -> {
            var effectiveStyle = viewEdgeDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(interpreter, style.getCondition(), variableManager))
                    .map(org.eclipse.sirius.components.view.diagram.EdgeStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewEdgeDescription::getStyle);
            return stylesFactory.createEdgeStyle(effectiveStyle);
        };

        SynchronizationPolicy synchronizationPolicy = SynchronizationPolicy.valueOf(viewEdgeDescription.getSynchronizationPolicy().getName());

        var builder = EdgeDescription.newEdgeDescription(this.diagramIdProvider.getId(viewEdgeDescription))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .targetObjectLabelProvider(this.semanticTargetLabelProvider)
                .sourceDescriptions(viewEdgeDescription.getSourceDescriptions().stream().map(converterContext.getConvertedElements()::get).toList())
                .targetDescriptions(viewEdgeDescription.getTargetDescriptions().stream().map(converterContext.getConvertedElements()::get).toList())
                .semanticElementsProvider(semanticElementsProvider)
                .shouldRenderPredicate(shouldRenderPredicate)
                .synchronizationPolicy(synchronizationPolicy)
                .sourceProvider(sourceProvider)
                .targetProvider(targetProvider)
                .styleProvider(styleProvider)
                .deleteHandler(this.createDeleteHandler(viewEdgeDescription, converterContext));

        this.getSpecificEdgeLabelDescription(viewEdgeDescription, viewEdgeDescription.getBeginLabelExpression(), LabelIdProvider.EDGE_BEGIN_LABEL_SUFFIX, interpreter, stylesFactory)
                .ifPresent(builder::beginLabelDescription);
        this.getSpecificEdgeLabelDescription(viewEdgeDescription, viewEdgeDescription.getCenterLabelExpression(), LabelIdProvider.EDGE_CENTER_LABEL_SUFFIX, interpreter, stylesFactory)
                .ifPresent(builder::centerLabelDescription);
        this.getSpecificEdgeLabelDescription(viewEdgeDescription, viewEdgeDescription.getEndLabelExpression(), LabelIdProvider.EDGE_END_LABEL_SUFFIX, interpreter, stylesFactory)
                .ifPresent(builder::endLabelDescription);
        new ToolFinder().findEdgeLabelEditTool(viewEdgeDescription)
                .ifPresent(labelEditTool -> builder.labelEditHandler(this.createEdgeLabelEditHandler(viewEdgeDescription, converterContext)));
        EdgeDescription result = builder.build();
        converterContext.getConvertedEdges().put(viewEdgeDescription, result);
        return result;
    }

    private Function<VariableManager, IStatus> createDeleteHandler(DiagramElementDescription diagramElementDescription, ViewDiagramDescriptionConverterContext converterContext) {
        Function<VariableManager, IStatus> handler = variableManager -> {
            IStatus result;
            VariableManager childVariableManager = variableManager.createChild();
            var convertedNodes = Collections.unmodifiableMap(converterContext.getConvertedNodes());
            childVariableManager.put(CONVERTED_NODES_VARIABLE, convertedNodes);

            var optionalDeleteTool = new ToolFinder().findDeleteTool(diagramElementDescription);
            if (optionalDeleteTool.isPresent()) {
                result = this.toolExecutor.executeTool(optionalDeleteTool.get(), converterContext.getInterpreter(), childVariableManager);
            } else {
                result = new Failure("No deletion tool configured");
            }
            return result;
        };

        return new IViewNodeDeleteHandler() {
            @Override
            public boolean hasSemanticDeleteTool() {
                return new ToolFinder().findDeleteTool(diagramElementDescription).isPresent();
            }

            @Override
            public IStatus apply(VariableManager variableManager) {
                return handler.apply(variableManager);
            }
        };
    }

    private BiFunction<VariableManager, String, IStatus> createNodeLabelEditHandler(org.eclipse.sirius.components.view.diagram.NodeDescription nodeDescription,
            ViewDiagramDescriptionConverterContext converterContext) {
        var optionalTool = new ToolFinder().findNodeLabelEditTool(nodeDescription);
        return optionalTool.<BiFunction<VariableManager, String, IStatus>>map(labelEditTool -> (variableManager, newLabel) -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put("arg0", newLabel);
            childVariableManager.put("newLabel", newLabel);
            var convertedNodes = Collections.unmodifiableMap(converterContext.getConvertedNodes());
            childVariableManager.put(CONVERTED_NODES_VARIABLE, convertedNodes);

            return this.toolExecutor.executeTool(labelEditTool, converterContext.getInterpreter(), childVariableManager);
        }).orElse(null);
    }

    private IEdgeEditLabelHandler createEdgeLabelEditHandler(org.eclipse.sirius.components.view.diagram.EdgeDescription edgeDescription, ViewDiagramDescriptionConverterContext converterContext) {
        IEdgeEditLabelHandler handler = (variableManager, edgeLabelKind, newLabel) -> {
            IStatus result;
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put("arg0", newLabel);
            childVariableManager.put("newLabel", newLabel);
            var convertedNodes = Collections.unmodifiableMap(converterContext.getConvertedNodes());
            childVariableManager.put(CONVERTED_NODES_VARIABLE, convertedNodes);

            Optional<LabelEditTool> optionalTool = new ToolFinder().findLabelEditTool(edgeDescription, edgeLabelKind);
            if (optionalTool.isPresent()) {
                result = this.toolExecutor.executeTool(optionalTool.get(), converterContext.getInterpreter(), childVariableManager);
            } else {
                result = new Failure("No label edition tool configured");
            }
            return result;
        };
        return new IViewEdgeLabelEditHandler() {
            @Override
            public IStatus editLabel(VariableManager variableManager, EdgeLabelKind edgeLabelKind, String newLabel) {
                return handler.editLabel(variableManager, edgeLabelKind, newLabel);
            }

            @Override
            public boolean hasLabelEditTool(EdgeLabelKind labelKind) {
                return new ToolFinder().findLabelEditTool(edgeDescription, labelKind).isPresent();
            }
        };
    }

    private Function<VariableManager, IStatus> createDropNodeHandler(DropNodeTool dropNodeTool, ViewDiagramDescriptionConverterContext converterContext) {
        return variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            var convertedNodes = Collections.unmodifiableMap(converterContext.getConvertedNodes());
            childVariableManager.put(CONVERTED_NODES_VARIABLE, convertedNodes);

            return this.toolExecutor.executeTool(dropNodeTool, converterContext.getInterpreter(), childVariableManager);
        };
    }

    private Predicate<Element> isFromCompatibleSourceMapping(org.eclipse.sirius.components.view.diagram.EdgeDescription edgeDescription) {
        return diagramElement -> edgeDescription.getSourceDescriptions().stream().anyMatch(description -> this.isFromDescription(diagramElement, description));
    }

    private boolean isFromDescription(Element diagramElement, DiagramElementDescription diagramElementDescription) {
        return diagramElement.getProps() instanceof NodeElementProps nodeElementProps && Objects.equals(this.diagramIdProvider.getId(diagramElementDescription), nodeElementProps.getDescriptionId())
                || diagramElement.getProps() instanceof EdgeElementProps edgeElementProps && Objects.equals(this.diagramIdProvider.getId(diagramElementDescription), edgeElementProps.getDescriptionId());
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }

    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse("");
    }
}
