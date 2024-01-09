/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.web.services.representations;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.task.IDeckIdProvider;
import org.eclipse.sirius.components.view.emf.task.IGanttIdProvider;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.gantt.GanttDescription;
import org.eclipse.sirius.web.services.api.representations.IInMemoryViewRegistry;
import org.eclipse.sirius.web.services.editingcontext.api.IViewLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Used to find view representation descriptions.
 *
 * @author arichard
 */
@Service
@ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
public class ViewRepresentationDescriptionSearchService implements IViewRepresentationDescriptionSearchService {
    private final IViewLoader viewLoader;

    private final IInMemoryViewRegistry inMemoryViewRegistry;

    private final IDiagramIdProvider diagramIdProvider;

    private final IURLParser urlParser;

    private final IFormIdProvider formIdProvider;

    private final IGanttIdProvider ganttIdProvider;

    private final IDeckIdProvider deckIdProvider;

    private final IObjectService objectService;

    public ViewRepresentationDescriptionSearchService(ViewRepresentationDescriptionSearchServiceParameters parameters, IInMemoryViewRegistry inMemoryViewRegistry) {
        this.urlParser = Objects.requireNonNull(parameters.getUrlParser());
        this.viewLoader = Objects.requireNonNull(parameters.getViewLoader());
        this.diagramIdProvider = Objects.requireNonNull(parameters.getDiagramIdProvider());
        this.formIdProvider = Objects.requireNonNull(parameters.getFormIdProvider());
        this.objectService = Objects.requireNonNull(parameters.getObjectService());
        this.ganttIdProvider = Objects.requireNonNull(parameters.getGanttIdProvider());
        this.deckIdProvider = Objects.requireNonNull(parameters.getDeckIdProvider());
        this.inMemoryViewRegistry = Objects.requireNonNull(inMemoryViewRegistry);
    }

    @Override
    public Optional<RepresentationDescription> findById(String representationDescriptionId) {
        Optional<String> sourceId = this.getSourceId(representationDescriptionId);
        if (sourceId.isPresent()) {
            List<View> views = this.getViewsFromSourceId(sourceId.get());
            if (!views.isEmpty()) {
                var searchedView = views.stream()
                        .flatMap(view -> view.getDescriptions().stream())
                        .filter(representationDescription -> representationDescriptionId.equals(this.getRepresentationDescriptionId(representationDescription)))
                        .findFirst();
                if (searchedView.isPresent()) {
                    return searchedView;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<NodeDescription> findViewNodeDescriptionById(String nodeDescriptionId) {
        Optional<String> sourceId = this.getSourceId(nodeDescriptionId);
        Optional<String> sourceElementId = this.getSourceElementId(nodeDescriptionId);
        if (sourceId.isPresent() && sourceElementId.isPresent()) {
            List<View> views = this.getViewsFromSourceId(sourceId.get());
            if (!views.isEmpty()) {
                return views.stream()
                        .flatMap(view -> view.getDescriptions().stream())
                        .filter(DiagramDescription.class::isInstance)
                        .map(DiagramDescription.class::cast)
                        .map(diagramDescription -> this.findNodeDescriptionById(diagramDescription, sourceElementId.get()))
                        .flatMap(Optional::stream)
                        .findFirst();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<EdgeDescription> findViewEdgeDescriptionById(String edgeDescriptionId) {
        Optional<String> sourceId = this.getSourceId(edgeDescriptionId);
        Optional<String> sourceElementId = this.getSourceElementId(edgeDescriptionId);
        if (sourceId.isPresent() && sourceElementId.isPresent()) {
            List<View> views = this.getViewsFromSourceId(sourceId.get());
            if (!views.isEmpty()) {
                return views.stream()
                        .flatMap(view -> view.getDescriptions().stream())
                        .filter(DiagramDescription.class::isInstance)
                        .map(DiagramDescription.class::cast)
                        .map(diagramDescription -> this.findEdgeDescriptionById(diagramDescription, sourceElementId.get()))
                        .flatMap(Optional::stream)
                        .findFirst();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<FormElementDescription> findViewFormElementDescriptionById(String formDescriptionId) {
        Optional<String> sourceId = this.getSourceId(formDescriptionId);
        Optional<String> sourceElementId = this.getSourceElementId(formDescriptionId);
        if (sourceId.isPresent() && sourceElementId.isPresent()) {
            List<View> views = this.getViewsFromSourceId(sourceId.get());
            if (!views.isEmpty()) {
                return views.stream()
                        .flatMap(view -> view.getDescriptions().stream())
                        .filter(FormDescription.class::isInstance)
                        .map(FormDescription.class::cast)
                        .map(formDescription -> this.findFormElementDescriptionById(formDescription, sourceElementId.get()))
                        .flatMap(Optional::stream)
                        .findFirst();
            }
        }
        return Optional.empty();
    }

    private List<View> getViewsFromSourceId(String sourceId) {
        List<View> views = this.inMemoryViewRegistry.findViewById(sourceId).stream().toList();
        if (!views.isEmpty()) {
            return views;
        }

        return this.viewLoader.load().stream()
                .filter(view -> view.eResource().getURI().segment(0).equals(sourceId))
                .toList();
    }

    private String getRepresentationDescriptionId(RepresentationDescription description) {
        String result;
        if (description instanceof DiagramDescription diagramDescription) {
            result = this.diagramIdProvider.getId(diagramDescription);
        } else if (description instanceof FormDescription formDescription) {
            result = this.formIdProvider.getId(formDescription);
        } else if (description instanceof GanttDescription ganttDescription) {
            result = this.ganttIdProvider.getId(ganttDescription);
        } else if (description instanceof DeckDescription deckDescription) {
            result = this.deckIdProvider.getId(deckDescription);
        } else {
            String descriptionURI = EcoreUtil.getURI(description).toString();
            result = UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
        }
        return result;
    }

    public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, String nodeDescriptionId) {
        return this.findNodeDescription(nodeDesc -> Objects.equals(this.objectService.getId(nodeDesc), nodeDescriptionId), diagramDescription.getNodeDescriptions());
    }

    private Optional<NodeDescription> findNodeDescription(Predicate<NodeDescription> condition, List<NodeDescription> candidates) {
        Optional<NodeDescription> result = Optional.empty();
        ListIterator<NodeDescription> candidatesListIterator = candidates.listIterator();
        while (result.isEmpty() && candidatesListIterator.hasNext()) {
            NodeDescription node = candidatesListIterator.next();
            if (condition.test(node)) {
                result = Optional.of(node);
            } else {
                List<NodeDescription> nodeDescriptionChildren = Stream.concat(node.getBorderNodesDescriptions().stream(), node.getChildrenDescriptions().stream()).toList();
                result = this.findNodeDescription(condition, nodeDescriptionChildren);
            }
        }
        return result;
    }

    private Optional<EdgeDescription> findEdgeDescriptionById(DiagramDescription diagramDescription, String edgeDescriptionId) {
        return diagramDescription.getEdgeDescriptions().stream().filter(edgeDescription -> this.objectService.getId(edgeDescription).equals(edgeDescriptionId)).findFirst();
    }

    private Optional<FormElementDescription> findFormElementDescriptionById(FormDescription formDescription, String formElementId) {
        TreeIterator<EObject> contentIterator = formDescription.eAllContents();
        while (contentIterator.hasNext()) {
            EObject eObject = contentIterator.next();
            if (eObject instanceof FormElementDescription desc && this.objectService.getId(desc).equals(formElementId)) {
                return Optional.of(desc);
            }
        }
        return Optional.empty();
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IDiagramIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IDiagramIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }

}
