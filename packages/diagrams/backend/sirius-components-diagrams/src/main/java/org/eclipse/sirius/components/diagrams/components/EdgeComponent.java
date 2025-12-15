/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.elements.EdgeElementProps;
import org.eclipse.sirius.components.diagrams.elements.EdgeElementProps.Builder;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.events.FadeDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeEvent;
import org.eclipse.sirius.components.diagrams.events.RemoveEdgeEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.EditAppearanceEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.IEdgeAppearanceChange;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.diagrams.variables.DiagramRenderingOperations;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render an edge.
 *
 * @author sbegaudeau
 * @author tgiraudet
 */
public class EdgeComponent implements IComponent {

    private static final String INVALID_ID = "INVALID_ID";

    private final EdgeComponentProps props;

    public EdgeComponent(EdgeComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        EdgeDescription edgeDescription = this.props.getEdgeDescription();
        DiagramRenderingCache cache = this.props.getCache();
        List<IDiagramEvent> diagramEvents = this.props.getDiagramEvents();

        List<Element> children = new ArrayList<>();

        boolean hasCandidates = this.hasCandidates(edgeDescription.getSourceDescriptions(), cache)
                && this.hasCandidates(edgeDescription.getTargetDescriptions(), cache);

        if (hasCandidates) {
            VariableManager semanticElementsVariableManager = new VariableManager();
            variableManager.getVariables().forEach(semanticElementsVariableManager::put);
            semanticElementsVariableManager.put(DiagramDescription.CACHE, cache);

            Map<String, Integer> edgeIdPrefixToCount = new HashMap<>();
            List<String> lastPreviousRenderedEdgeIds = new ArrayList<>();
            this.props.getOperationValidator().validate(DiagramRenderingOperations.EDGE_SEMANTIC_CANDIDATES, semanticElementsVariableManager.getVariables());
            List<?> semanticElements = edgeDescription.getSemanticElementsProvider().apply(semanticElementsVariableManager);
            for (Object semanticElement : semanticElements) {
                List<Element> edgesToRender = this.renderEdge(variableManager, edgeDescription, diagramEvents, edgeIdPrefixToCount, lastPreviousRenderedEdgeIds, semanticElement);
                children.addAll(edgesToRender);
            }
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private List<Element> renderEdge(VariableManager variableManager, EdgeDescription edgeDescription, List<IDiagramEvent> diagramEvents, Map<String, Integer> edgeIdPrefixToCount,
            List<String> lastPreviousRenderedEdgeIds, Object semanticElement) {
        List<Element> edgeElements = new ArrayList<>();
        DiagramRenderingCache cache = this.props.getCache();

        VariableManager edgeVariableManager = variableManager.createChild();
        edgeVariableManager.put(VariableManager.SELF, semanticElement);
        edgeVariableManager.put(DiagramDescription.CACHE, cache);

        List<Element> sourceElements = edgeDescription.getSourceProvider().apply(edgeVariableManager);
        if (!sourceElements.isEmpty()) {

            for (Element sourceElement : sourceElements) {
                var targetVariableManager = edgeVariableManager.createChild();
                targetVariableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, this.props.getCache().getElementToObject().get(sourceElement));
                targetVariableManager.put(EdgeDescription.GRAPHICAL_EDGE_SOURCE, sourceElement);
                List<Element> targetElements = edgeDescription.getTargetProvider().apply(targetVariableManager);
                for (Element targetElement : targetElements) {
                    var edgeInstanceVariableManager = edgeVariableManager.createChild();
                    edgeInstanceVariableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, this.props.getCache().getElementToObject().get(sourceElement));
                    edgeInstanceVariableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, this.props.getCache().getElementToObject().get(targetElement));
                    edgeInstanceVariableManager.put(EdgeDescription.GRAPHICAL_EDGE_SOURCE, sourceElement);
                    edgeInstanceVariableManager.put(EdgeDescription.GRAPHICAL_EDGE_TARGET, targetElement);

                    this.props.getOperationValidator().validate(DiagramRenderingOperations.EDGE_PRECONDITION, edgeInstanceVariableManager.getVariables());
                    var shouldRender = edgeDescription.getShouldRenderPredicate().test(edgeInstanceVariableManager);
                    if (shouldRender) {
                        this.doRenderEdge(edgeInstanceVariableManager, edgeDescription, sourceElement, targetElement, diagramEvents, edgeIdPrefixToCount, lastPreviousRenderedEdgeIds)
                                .ifPresent((edge) -> {
                                    edgeElements.add(edge);
                                    cache.put(edgeDescription.getId(), edge);
                                    cache.put(semanticElement, edge);
                                });
                    }
                }
            }
        }
        return edgeElements;
    }

    private Optional<Element> doRenderEdge(VariableManager edgeVariableManager, EdgeDescription edgeDescription, Element sourceElement, Element targetElement, List<IDiagramEvent> diagramEvents,
            Map<String, Integer> edgeIdPrefixToCount, List<String> lastPreviousRenderedEdgeIds) {
        String targetObjectId = edgeDescription.getTargetObjectIdProvider().apply(edgeVariableManager);
        String targetObjectKind = edgeDescription.getTargetObjectKindProvider().apply(edgeVariableManager);
        String targetObjectLabel = edgeDescription.getTargetObjectLabelProvider().apply(edgeVariableManager);

        String edgeIdPrefix = this.computeEdgeIdPrefix(edgeDescription, sourceElement, targetElement);
        int count = edgeIdPrefixToCount.getOrDefault(edgeIdPrefix, 0);

        Function<Integer, String> edgeIdProvider = this.getEdgeIdProvider(edgeDescription, sourceElement, targetElement);
        String id = edgeIdProvider.apply(count);
        String sourceId = this.getId(sourceElement);
        String targetId = this.getId(targetElement);

        Optional<Edge> optionalPreviousEdge = this.props.getEdgesRequestor().getById(id);
        Builder edgeElementPropsBuilder = EdgeElementProps.newEdgeElementProps(id);

        Set<ViewModifier> modifiers = this.computeModifiers(diagramEvents, optionalPreviousEdge, id);
        edgeElementPropsBuilder.modifiers(modifiers);
        ViewModifier state = this.computeState(diagramEvents, sourceElement, sourceId, targetElement, targetId, modifiers);
        edgeElementPropsBuilder.state(state);

        for (IDiagramEvent diagramEvent : diagramEvents) {
            if (diagramEvent instanceof RemoveEdgeEvent removeEdgeEvent) {
                optionalPreviousEdge = this.getPreviousEdge(id, lastPreviousRenderedEdgeIds, removeEdgeEvent, edgeIdProvider, count);
            } else if (diagramEvent instanceof ReconnectEdgeEvent reconnectEdgeEvent) {
                optionalPreviousEdge = this.getPreviousEdge(id, lastPreviousRenderedEdgeIds, reconnectEdgeEvent, edgeIdProvider, count, optionalPreviousEdge, edgeElementPropsBuilder);
            }
        }

        SynchronizationPolicy synchronizationPolicy = edgeDescription.getSynchronizationPolicy();
        boolean shouldRender = synchronizationPolicy == SynchronizationPolicy.SYNCHRONIZED || (synchronizationPolicy == SynchronizationPolicy.UNSYNCHRONIZED && optionalPreviousEdge.isPresent());

        if (shouldRender) {
            Optional<EdgeAppearance> optionalPreviousAppearance = optionalPreviousEdge.map(previousEdge ->
                    new EdgeAppearance(previousEdge.getStyle(), previousEdge.getCustomizedStyleProperties())
            );

            EdgeStyle providedStyle = edgeDescription.getStyleProvider().apply(edgeVariableManager);

            List<IEdgeAppearanceChange> appearanceChanges = diagramEvents.stream()
                    .filter(EditAppearanceEvent.class::isInstance)
                    .map(EditAppearanceEvent.class::cast)
                    .flatMap(appearanceEvent -> appearanceEvent.changes().stream())
                    .filter(IEdgeAppearanceChange.class::isInstance)
                    .map(IEdgeAppearanceChange.class::cast)
                    .filter(appearanceChange -> Objects.equals(id, appearanceChange.edgeId()))
                    .toList();

            EdgeAppearance appearance = this.props.getEdgeAppearanceHandlers().stream()
                    .filter(handler -> handler.canHandle(providedStyle))
                    .findFirst()
                    .map(handler -> handler.handle(providedStyle, appearanceChanges, optionalPreviousAppearance))
                    .orElse(new EdgeAppearance(providedStyle, new LinkedHashSet<>()));

            String edgeType = optionalPreviousEdge
                    .map(Edge::getType)
                    .orElse("edge:straight");

            List<Element> labelChildren = this.getLabelsChildren(optionalPreviousEdge, edgeDescription, edgeVariableManager, id);
            EdgeElementProps edgeElementProps = edgeElementPropsBuilder
                    .type(edgeType)
                    .descriptionId(edgeDescription.getId())
                    .targetObjectId(targetObjectId)
                    .targetObjectKind(targetObjectKind)
                    .targetObjectLabel(targetObjectLabel)
                    .sourceId(sourceId)
                    .targetId(targetId)
                    .style(appearance.style())
                    .children(labelChildren)
                    .centerLabelEditable(edgeDescription.getLabelEditHandler() != null)
                    .deletable(edgeDescription.getDeleteHandler() != null)
                    .customizedStyleProperties(appearance.customizedStyleProperties())
                    .build();

            Element edgeElement = new Element(EdgeElementProps.TYPE, edgeElementProps);
            edgeIdPrefixToCount.put(edgeIdPrefix, ++count);
            if (optionalPreviousEdge.isPresent()) {
                lastPreviousRenderedEdgeIds.add(optionalPreviousEdge.get().getId());
            }

            return Optional.of(edgeElement);
        }

        return Optional.empty();
    }

    /**
     * Compute the modifiers set applied on the new edge. The set is by default the set of the previous edge or is empty
     * if it does not exist.
     *
     * If a diagram event is specified and this one requests a modification of the modifier set, applied the event on
     * the default set.
     *
     * @param diagramEvents
     *         The diagram events modifying the default modifier set of the edge
     * @param optionalPreviousEdge
     *         The previous edge from which get the old modifier set. If empty, the old modifier set is set to an
     *         empty Set
     * @param id
     *         The ID of the current edge
     */
    private Set<ViewModifier> computeModifiers(List<IDiagramEvent> diagramEvents, Optional<Edge> optionalPreviousEdge, String id) {
        Set<ViewModifier> modifiers = new HashSet<>(optionalPreviousEdge.map(Edge::getModifiers).orElse(Set.of()));
        for (IDiagramEvent diagramEvent : diagramEvents) {
            if (diagramEvent instanceof HideDiagramElementEvent hideDiagramElementEvent) {
                if (hideDiagramElementEvent.getElementIds().contains(id)) {
                    if (hideDiagramElementEvent.hideElement()) {
                        modifiers.add(ViewModifier.Hidden);
                    } else {
                        modifiers.remove(ViewModifier.Hidden);
                    }
                }
            } else if (diagramEvent instanceof FadeDiagramElementEvent fadeDiagramElementEvent) {
                if (fadeDiagramElementEvent.getElementIds().contains(id)) {
                    if (fadeDiagramElementEvent.fadeElement()) {
                        modifiers.add(ViewModifier.Faded);
                    } else {
                        modifiers.remove(ViewModifier.Faded);
                    }
                }
            }
        }
        return modifiers;
    }

    /**
     * Compute the state of the current edge.
     *
     * This state is computed from the modifier set of the current edge and the states of the source and target elements.
     * If the new state of one of these elements is {@link ViewModifier#Hidden}, the state of this new edge is
     * {@link ViewModifier#Hidden} too. If these element are not hidden, the state of the current edge is the
     * dominant state of the set or the default modifier if empty.
     *
     * @param diagramEvents
     *         The diagram events, they are used to know the new state of the source and target elements
     * @param sourceElement
     *         The source element element
     * @param sourceId
     *         The source element ID
     * @param targetElement
     *         The target element element
     * @param targetId
     *         The target element ID
     * @param modifiers
     *         The modifier set of the building edge
     */
    private ViewModifier computeState(List<IDiagramEvent> diagramEvents, Element sourceElement, String sourceId, Element targetElement, String targetId, Set<ViewModifier> modifiers) {
        ViewModifier state = new ViewStateProvider().getState(modifiers);

        ViewModifier sourceState = this.getStateFromElement(sourceElement);
        ViewModifier targetState = this.getStateFromElement(targetElement);


        boolean isSourceHidden = diagramEvents.stream()
                .filter(HideDiagramElementEvent.class::isInstance)
                .map(HideDiagramElementEvent.class::cast)
                .filter(hideDiagramElementEvent -> hideDiagramElementEvent.getElementIds().contains(sourceId))
                .map(HideDiagramElementEvent::hideElement)
                .reduce((a, b) -> b)
                .orElse(sourceState == ViewModifier.Hidden);

        boolean isTargetHidden = diagramEvents.stream()
                .filter(HideDiagramElementEvent.class::isInstance)
                .map(HideDiagramElementEvent.class::cast)
                .filter(hideDiagramElementEvent -> hideDiagramElementEvent.getElementIds().contains(targetId))
                .map(HideDiagramElementEvent::hideElement)
                .reduce((a, b) -> b)
                .orElse(targetState == ViewModifier.Hidden);

        if (isSourceHidden || isTargetHidden) {
            state = ViewModifier.Hidden;
        }
        return state;
    }

    /**
     * Returns the previous edge identified by the edge id, resets the anchor position of the edge end being
     * reconnected. Since a reconnected edge has changed its source or target, its id has also changed. The edge id of
     * the reconnect edge event should be updated accordingly.
     *
     * @param edgeId
     *         The id of the edge being rendered
     * @param lastPreviousRenderedEdgeIds
     *         The list of id of last previous rendered edges
     * @param reconnectEdgeEvent
     *         the reconnect edge event
     * @param edgeIdProvider
     *         the function used to compute the edge id by apply a count.
     * @param count
     *         The count used to compute the id of the edge being rendered
     * @param potentialPreviousEdge
     *         The potential previous edge that could exist for the edge being rendered
     * @param edgeElementPropsBuilder
     *         the edge element props build used to set the edge end anchor position according to the previous edge
     * @return The optional previous edge
     */
    private Optional<Edge> getPreviousEdge(String edgeId, List<String> lastPreviousRenderedEdgeIds, ReconnectEdgeEvent reconnectEdgeEvent, Function<Integer, String> edgeIdProvider, int count,
            Optional<Edge> potentialPreviousEdge, EdgeElementProps.Builder edgeElementPropsBuilder) {

        Optional<Edge> optionalPreviousEdge = potentialPreviousEdge;

        if (edgeId.equals(reconnectEdgeEvent.getEdgeId()) || lastPreviousRenderedEdgeIds.contains(edgeId)) {
            // The edge being rendered has been reconnected or has already been rendered. Thus, the
            // previous edge correspond to next sibling (count + 1).
            String potentialPreviousEdgeId = edgeIdProvider.apply(count + 1);
            optionalPreviousEdge = this.props.getEdgesRequestor().getById(potentialPreviousEdgeId);
        }

        if (optionalPreviousEdge.isEmpty()) {
            // We are creating an edge but since we are handling a reconnect edge event, we are
            // currently rendering the reconnected edge. Thus, we need to find the previous edge which
            // is the edgeId of the reconnect event.

            String potentialPreviousEdgeId = reconnectEdgeEvent.getEdgeId();
            optionalPreviousEdge = this.props.getEdgesRequestor().getById(potentialPreviousEdgeId);

            // Since the id of the reconnected edge has been updated, update the reconnected edge event edge id for next
            // refresh operations.
            reconnectEdgeEvent.setEdgeId(edgeId);
        }

        return optionalPreviousEdge;
    }

    private Function<Integer, String> getEdgeIdProvider(EdgeDescription edgeDescription, Element sourceElement, Element targetElement) {
        return (count) -> this.computeEdgeId(edgeDescription, sourceElement, targetElement, count);
    }

    /**
     * Returns the previous edge identified by the given edge id.
     *
     * If the edge being rendered is referenced by the remove edge event or is present in the list of already rendered
     * edges, then, the previous edge will be more likely the previous edge of the next sibling. The potential previous
     * edge is the first edge with an id not referenced by the remove edge event nor contained in the list of already
     * rendered edges.
     *
     * @param edgeId
     *         The id of the edge being rendered
     * @param lastPreviousRenderedEdgeIds
     *         The list of id of last previous rendered edges
     * @param removeEdgeEvent
     *         The remove edge event used to check if the edge being rendered does not exist anymore
     * @param edgeIdProvider
     *         The function used to compute the id by applying a count
     * @param baseCount
     *         The count
     * @return The optional previous edge.
     */
    private Optional<Edge> getPreviousEdge(String edgeId, List<String> lastPreviousRenderedEdgeIds, RemoveEdgeEvent removeEdgeEvent, Function<Integer, String> edgeIdProvider, int baseCount) {
        String potentialPreviousEdgeId = edgeId;
        int count = baseCount;
        boolean foundPotentialPrevious = false;
        boolean hasBeenRemoved = false;
        boolean hasBeenRendered = false;

        while (!foundPotentialPrevious) {
            hasBeenRemoved = false;
            hasBeenRendered = false;
            if (removeEdgeEvent.getEdgeIds().contains(potentialPreviousEdgeId)) {
                count++;
                potentialPreviousEdgeId = edgeIdProvider.apply(count);
                hasBeenRemoved = true;
            }

            if (lastPreviousRenderedEdgeIds.contains(potentialPreviousEdgeId)) {
                count++;
                potentialPreviousEdgeId = edgeIdProvider.apply(count);
                hasBeenRendered = true;
            }
            foundPotentialPrevious = !hasBeenRemoved && !hasBeenRendered;
        }

        return this.props.getEdgesRequestor().getById(potentialPreviousEdgeId);
    }

    private List<Element> getLabelsChildren(Optional<Edge> optionalPreviousEdge, EdgeDescription edgeDescription, VariableManager edgeVariableManager, String edgeId) {
        List<Element> edgeChildren = new ArrayList<>();

        VariableManager labelVariableManager = edgeVariableManager.createChild();

        Optional<Label> optionalPreviousBeginLabel = optionalPreviousEdge.map(Edge::getBeginLabel);
        Optional.ofNullable(edgeDescription.getBeginLabelDescription()).map(labelDescription -> {
            LabelComponentProps labelComponentProps = new LabelComponentProps(labelVariableManager, labelDescription, LabelType.EDGE_BEGIN.getValue(), edgeId, LabelIdProvider.EDGE_BEGIN_LABEL_SUFFIX, optionalPreviousBeginLabel, this.props.getDiagramEvents());
            return new Element(LabelComponent.class, labelComponentProps);
        }).ifPresent(edgeChildren::add);

        Optional<Label> optionalPreviousCenterLabel = optionalPreviousEdge.map(Edge::getCenterLabel);
        Optional.ofNullable(edgeDescription.getCenterLabelDescription()).map(labelDescription -> {
            LabelComponentProps labelComponentProps = new LabelComponentProps(labelVariableManager, labelDescription, LabelType.EDGE_CENTER.getValue(), edgeId, LabelIdProvider.EDGE_CENTER_LABEL_SUFFIX, optionalPreviousCenterLabel, this.props.getDiagramEvents());
            return new Element(LabelComponent.class, labelComponentProps);
        }).ifPresent(edgeChildren::add);

        Optional<Label> optionalPreviousEndLabel = optionalPreviousEdge.map(Edge::getEndLabel);
        Optional.ofNullable(edgeDescription.getEndLabelDescription()).map(labelDescription -> {
            LabelComponentProps labelComponentProps = new LabelComponentProps(labelVariableManager, labelDescription, LabelType.EDGE_END.getValue(), edgeId, LabelIdProvider.EDGE_END_LABEL_SUFFIX, optionalPreviousEndLabel, this.props.getDiagramEvents());
            return new Element(LabelComponent.class, labelComponentProps);
        }).ifPresent(edgeChildren::add);

        return edgeChildren;
    }

    private String computeEdgeId(EdgeDescription edgeDescription, Element sourceElement, Element targetElement, int count) {
        var descriptionId = edgeDescription.getId();
        var sourceId = INVALID_ID;
        if (sourceElement.getProps() instanceof EdgeElementProps edgeElementProps) {
            sourceId = edgeElementProps.getId();
        } else if (sourceElement.getProps() instanceof NodeElementProps nodeElementProps) {
            sourceId = nodeElementProps.getId();
        }

        var targetId = INVALID_ID;
        if (targetElement.getProps() instanceof EdgeElementProps edgeElementProps) {
            targetId = edgeElementProps.getId();
        } else if (targetElement.getProps() instanceof NodeElementProps nodeElementProps) {
            targetId = nodeElementProps.getId();
        }
        return this.computeEdgeId(descriptionId, sourceId, targetId, count);
    }

    private String computeEdgeId(String edgeDescriptionId, String sourceId, String targetId, int count) {
        String rawIdentifier = edgeDescriptionId + ": " + sourceId + " --> " + targetId + " - " + count;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
    }

    private String computeEdgeIdPrefix(EdgeDescription edgeDescription, Element sourceElement, Element targetElement) {
        var descriptionId = edgeDescription.getId();
        var sourceId = INVALID_ID;
        if (sourceElement.getProps() instanceof EdgeElementProps edgeElementProps) {
            sourceId = edgeElementProps.getId();
        } else if (sourceElement.getProps() instanceof NodeElementProps nodeElementProps) {
            sourceId = nodeElementProps.getId();
        }

        var targetId = INVALID_ID;
        if (targetElement.getProps() instanceof EdgeElementProps edgeElementProps) {
            targetId = edgeElementProps.getId();
        } else if (targetElement.getProps() instanceof NodeElementProps nodeElementProps) {
            targetId = nodeElementProps.getId();
        }

        String rawPrefix = descriptionId + sourceId + targetId;
        return UUID.nameUUIDFromBytes(rawPrefix.getBytes()).toString();
    }

    private boolean hasCandidates(List<IDiagramElementDescription> diagramElementDescriptions, DiagramRenderingCache cache) {
        return diagramElementDescriptions.stream()
                .map(IDiagramElementDescription::getId)
                .map(cache.getDiagramElementDescriptionIdToElements()::get)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .findAny()
                .isPresent();
    }

    private String getId(Element element) {
        if (element.getProps() instanceof EdgeElementProps edgeElementProps) {
            return edgeElementProps.getId();
        }

        return Optional.of(element.getProps())
                .filter(NodeElementProps.class::isInstance)
                .map(NodeElementProps.class::cast)
                .map(NodeElementProps::getId)
                .orElse(UUID.randomUUID().toString());
    }

    private ViewModifier getStateFromElement(Element element) {
        if (element.getProps() instanceof EdgeElementProps edgeElementProps) {
            return edgeElementProps.getState();
        }

        return Optional.of(element.getProps())
                .filter(NodeElementProps.class::isInstance)
                .map(NodeElementProps.class::cast)
                .map(NodeElementProps::getState)
                .orElse(ViewModifier.Normal);
    }

}
