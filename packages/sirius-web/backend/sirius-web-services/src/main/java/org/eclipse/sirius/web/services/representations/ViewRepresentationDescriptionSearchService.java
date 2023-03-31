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
package org.eclipse.sirius.web.services.representations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IKindParser;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(ViewRepresentationDescriptionSearchService.class);

    private final IDocumentRepository documentRepository;

    private final EPackage.Registry ePackageRegistry;

    private final IDiagramIdProvider diagramIdProvider;

    private final IKindParser urlParser;

    private final IObjectService objectService;

    public ViewRepresentationDescriptionSearchService(IDocumentRepository documentRepository, EPackage.Registry ePackageRegistry, IDiagramIdProvider diagramIdProvider, IKindParser urlParser, IObjectService objectService) {
        this.urlParser = Objects.requireNonNull(urlParser);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.ePackageRegistry = Objects.requireNonNull(ePackageRegistry);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public Optional<RepresentationDescription> findById(String representationDescriptionId) {
        Map<String, List<String>> parameters = this.urlParser.getParameterValues(representationDescriptionId);
        List<String> values = Optional.ofNullable(parameters.get(IDiagramIdProvider.SOURCE_ID)).orElse(List.of());
        Optional<String> sourceId = values.stream().findFirst();
        if (sourceId.isPresent()) {
            Optional<DocumentEntity> documentEntity = this.documentRepository.findById(UUID.fromString(sourceId.get()));
            if (documentEntity.isPresent()) {
                Resource resource = this.loadDocumentAsEMF(documentEntity.get());
                // @formatter:off
                var searchedView = this.getViewDefinitions(resource)
                        .flatMap(view -> view.getDescriptions().stream())
                        .filter(representationDescription -> representationDescriptionId.equals(this.getRepresentationDescriptionId(representationDescription)))
                        .findFirst();
                // @formatter:on
                if (searchedView.isPresent()) {
                    return searchedView;
                }
            }
        }
        return Optional.empty();
    }
    @Override
    public Optional<NodeDescription> findViewNodeDescriptionById(String nodeDescriptionId) {
        Iterable<DocumentEntity> allDocuments = this.documentRepository.findAllByType(ViewPackage.eNAME, ViewPackage.eNS_URI);
        for (DocumentEntity documentEntity : allDocuments) {
            Resource resource = this.loadDocumentAsEMF(documentEntity);
            // @formatter:off
            var searchedViewNodes = this.getViewDefinitions(resource)
                    .flatMap(view -> view.getDescriptions().stream())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast)
                    .map(diagramDescription -> this.findNodeDescriptionById(diagramDescription, nodeDescriptionId))
                    .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
                    .findAny();
            return searchedViewNodes;
        }
        return Optional.empty();
    }

    @Override
    public Optional<EdgeDescription> findViewEdgeDescriptionById(String edgeDescriptionId) {
        Iterable<DocumentEntity> allDocuments = this.documentRepository.findAllByType(ViewPackage.eNAME, ViewPackage.eNS_URI);
        for (DocumentEntity documentEntity : allDocuments) {
            Resource resource = this.loadDocumentAsEMF(documentEntity);
            // @formatter:off
            var searchedViewNodes = this.getViewDefinitions(resource)
                    .flatMap(view -> view.getDescriptions().stream())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast)
                    .map(diagramDescription -> this.findEdgeDescriptionById(diagramDescription, edgeDescriptionId))
                    .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
                    .findAny();
            return searchedViewNodes;
        }
        return Optional.empty();
    }

    private Resource loadDocumentAsEMF(DocumentEntity documentEntity) {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.setPackageRegistry(this.ePackageRegistry);

        JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
        resourceSet.getResources().add(resource);

        try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
            resource.load(inputStream, null);
        } catch (IOException | IllegalArgumentException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return resource;
    }

    private Stream<View> getViewDefinitions(Resource resource) {
        return resource.getContents().stream().filter(View.class::isInstance).map(View.class::cast);
    }

    private String getRepresentationDescriptionId(RepresentationDescription description) {
        if (description instanceof DiagramDescription diagramDescription) {
            return this.diagramIdProvider.getId(diagramDescription);
        }
        String descriptionURI = EcoreUtil.getURI(description).toString();
        return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    }

    public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, String nodeDescriptionId) {
        return this.findNodeDescription(nodeDesc -> Objects.equals(this.objectService.getId(nodeDesc), nodeDescriptionId), diagramDescription.getNodeDescriptions());
    }

    private Optional<NodeDescription> findNodeDescription(Predicate<NodeDescription> condition, List<NodeDescription> candidates) {
        Optional<NodeDescription> result = Optional.empty();

        for (NodeDescription node : candidates) {
            if (condition.test(node)) {
                result = Optional.of(node);
            } else {
                Stream<NodeDescription> nodeDescriptionChildren = Stream.concat(node.getBorderNodesDescriptions().stream(), node.getChildrenDescriptions().stream());
                result = this.findNodeDescription(condition, nodeDescriptionChildren.toList());
            }
            if (result.isPresent()) {
                break;
            }
        }
        return result;
    }

    public Optional<EdgeDescription> findEdgeDescriptionById(DiagramDescription diagramDescription, String edgeDescriptionId) {
        return this.findEdgeDescription(edgeDesc -> Objects.equals(this.objectService.getId(edgeDesc), edgeDescriptionId), diagramDescription.getEdgeDescriptions().stream());
    }

    private Optional<EdgeDescription> findEdgeDescription(Predicate<EdgeDescription> condition, Stream<EdgeDescription> candidates) {
        return candidates.filter(condition).findFirst();
    }
}
