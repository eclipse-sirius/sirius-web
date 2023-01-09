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
package org.eclipse.sirius.web.sample.configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Relation;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgeStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.SynchronizationPolicy;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateInitializer;
import org.eclipse.sirius.web.services.documents.DocumentMetadataAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Provides Studio-specific project templates initializers.
 *
 * @author pcdavid
 */
@Configuration
public class StudioProjectTemplatesInitializer implements IProjectTemplateInitializer {

    private static final String DOMAIN_DOCUMENT_NAME = "DomainNewModel";

    private static final String VIEW_DOCUMENT_NAME = "ViewNewModel";

    private final Logger logger = LoggerFactory.getLogger(StudioProjectTemplatesInitializer.class);

    private final IProjectRepository projectRepository;

    private final IDocumentRepository documentRepository;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final StereotypeBuilder stereotypeBuilder;

    public StudioProjectTemplatesInitializer(IProjectRepository projectRepository, IDocumentRepository documentRepository,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramCreationService diagramCreationService, MeterRegistry meterRegistry) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.stereotypeBuilder = new StereotypeBuilder("studio-template-initializer", meterRegistry);
    }

    @Override
    public boolean canHandle(String templateId) {
        return StudioProjectTemplatesProvider.STUDIO_TEMPLATE_ID.equals(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String templateId, IEditingContext editingContext) {
        if (StudioProjectTemplatesProvider.STUDIO_TEMPLATE_ID.equals(templateId)) {
            return this.initializeStudioProject(editingContext);
        }
        return Optional.empty();
    }

    private Optional<RepresentationMetadata> initializeStudioProject(IEditingContext editingContext) {
        Optional<RepresentationMetadata> result = Optional.empty();
        // @formatter:off
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain);
        // @formatter:on
        Optional<UUID> editingContextUUID = new IDParser().parse(editingContext.getId());
        if (optionalEditingDomain.isPresent() && editingContextUUID.isPresent()) {
            AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
            ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();

            var optionalDomainDocumentEntity = this.projectRepository.findById(editingContextUUID.get()).map(projectEntity -> {
                DocumentEntity documentEntity = new DocumentEntity();
                documentEntity.setProject(projectEntity);
                documentEntity.setName(DOMAIN_DOCUMENT_NAME);
                documentEntity.setContent(this.getDomainContent());

                documentEntity = this.documentRepository.save(documentEntity);
                return documentEntity;
            });

            String domainName = "sampledomain";

            if (optionalDomainDocumentEntity.isPresent()) {
                DocumentEntity documentEntity = optionalDomainDocumentEntity.get();

                JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resource.load(inputStream, null);

                    var optionalTopographyDiagram = this.findDiagramDescription(editingContext, "Domain");
                    if (optionalTopographyDiagram.isPresent()) {
                        DiagramDescription topographyDiagram = optionalTopographyDiagram.get();
                        Object semanticTarget = resource.getContents().get(0);
                        domainName = ((Domain) semanticTarget).getName();
                        Diagram diagram = this.diagramCreationService.create(topographyDiagram.getLabel(), semanticTarget, topographyDiagram, editingContext);
                        result = Optional.of(new RepresentationMetadata(diagram.getId(), diagram.getKind(), diagram.getLabel(), diagram.getDescriptionId(), diagram.getTargetObjectId()));
                    }
                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }

                resource.eAdapters().add(new DocumentMetadataAdapter(DOMAIN_DOCUMENT_NAME));

                resourceSet.getResources().add(resource);
            }

            final String theDomainName = domainName;
            var optionalViewDocumentEntity = this.projectRepository.findById(editingContextUUID.get()).map(projectEntity -> {
                DocumentEntity documentEntity = new DocumentEntity();
                documentEntity.setProject(projectEntity);
                documentEntity.setName(VIEW_DOCUMENT_NAME);
                documentEntity.setContent(this.getViewContent(theDomainName));

                documentEntity = this.documentRepository.save(documentEntity);
                return documentEntity;
            });
            if (optionalViewDocumentEntity.isPresent()) {
                DocumentEntity documentEntity = optionalViewDocumentEntity.get();
                JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
                resource.eAdapters().add(new DocumentMetadataAdapter(VIEW_DOCUMENT_NAME));
                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resource.load(inputStream, null);
                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }
                resourceSet.getResources().add(resource);
            }
        }
        return result;
    }

    private String getDomainContent() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(new SampleDomainNameProvider().getSampleDomainName());

        Entity root = DomainFactory.eINSTANCE.createEntity();
        root.setName("Root");
        domain.getTypes().add(root);

        Entity entity1 = DomainFactory.eINSTANCE.createEntity();
        entity1.setName("Entity1");
        domain.getTypes().add(entity1);

        Relation entity1s = DomainFactory.eINSTANCE.createRelation();
        entity1s.setName("entity1s");
        entity1s.setContainment(true);
        entity1s.setOptional(true);
        entity1s.setMany(true);
        entity1s.setTargetType(entity1);
        root.getRelations().add(entity1s);

        Entity entity2 = DomainFactory.eINSTANCE.createEntity();
        entity2.setName("Entity2");
        domain.getTypes().add(entity2);

        Relation entity2s = DomainFactory.eINSTANCE.createRelation();
        entity2s.setName("entity2s");
        entity2s.setContainment(true);
        entity2s.setOptional(true);
        entity2s.setMany(true);
        entity2s.setTargetType(entity2);
        root.getRelations().add(entity2s);

        Relation linkedTo = DomainFactory.eINSTANCE.createRelation();
        linkedTo.setName("linkedTo");
        linkedTo.setContainment(true);
        linkedTo.setOptional(true);
        linkedTo.setMany(true);
        linkedTo.setTargetType(entity2);
        entity1.getRelations().add(linkedTo);

        this.addAttribute(entity1, "name", DataType.STRING);
        this.addAttribute(entity1, "attribute2", DataType.BOOLEAN);
        this.addAttribute(entity1, "attribute3", DataType.NUMBER);
        this.addAttribute(entity2, "name", DataType.STRING);

        return this.stereotypeBuilder.getStereotypeBody(domain);
    }

    private void addAttribute(Entity entity, String name, DataType type) {
        Attribute attr = DomainFactory.eINSTANCE.createAttribute();
        attr.setName(name);
        attr.setOptional(true);
        attr.setType(type);
        entity.getAttributes().add(attr);
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private String getViewContent(String domainName) {
        View view = ViewFactory.eINSTANCE.createView();

        org.eclipse.sirius.components.view.DiagramDescription viewDiagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();
        viewDiagramDescription.setName("Root Diagram Description");
        viewDiagramDescription.setDomainType(domainName + "::Root");
        viewDiagramDescription.setTitleExpression("aql:'New Representation'");
        view.getDescriptions().add(viewDiagramDescription);

        NodeDescription entity1Node = ViewFactory.eINSTANCE.createNodeDescription();
        entity1Node.setName("Entity1 Node");
        entity1Node.setDomainType(domainName + "::Entity1");
        entity1Node.setSemanticCandidatesExpression("aql:self.eContents()");
        entity1Node.setLabelExpression("aql:self.name");
        entity1Node.setSynchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED);
        viewDiagramDescription.getNodeDescriptions().add(entity1Node);

        RectangularNodeStyleDescription entity1Style = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        entity1Style.setWidthComputationExpression("1");
        entity1Style.setHeightComputationExpression("1");
        entity1Style.setColor("#E5F5F8");
        entity1Style.setBorderColor("#33B0C3");
        entity1Style.setBorderRadius(3);
        entity1Node.setStyle(entity1Style);

        NodeDescription entity2Node = ViewFactory.eINSTANCE.createNodeDescription();
        entity2Node.setName("Entity2 Node");
        entity2Node.setDomainType(domainName + "::Entity2");
        entity2Node.setSemanticCandidatesExpression("aql:self.eContents()");
        entity2Node.setLabelExpression("aql:self.name");
        entity2Node.setSynchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED);
        viewDiagramDescription.getNodeDescriptions().add(entity2Node);

        RectangularNodeStyleDescription entity2Style = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        entity2Style.setWidthComputationExpression("1");
        entity2Style.setHeightComputationExpression("1");
        entity2Style.setColor("#B1D8B7");
        entity2Style.setBorderColor("#76B947");
        entity2Style.setBorderRadius(3);
        entity2Node.setStyle(entity2Style);

        EdgeDescription linkedToEdge = ViewFactory.eINSTANCE.createEdgeDescription();
        linkedToEdge.setName("LinkedTo Edge");
        linkedToEdge.setSemanticCandidatesExpression("");
        linkedToEdge.setLabelExpression("");
        linkedToEdge.getSourceNodeDescriptions().add(entity1Node);
        linkedToEdge.setSourceNodesExpression("aql:self");
        linkedToEdge.getTargetNodeDescriptions().add(entity2Node);
        linkedToEdge.setTargetNodesExpression("aql:self.linkedTo");
        viewDiagramDescription.getEdgeDescriptions().add(linkedToEdge);

        EdgeStyle edgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        edgeStyle.setColor("#002639");
        linkedToEdge.setStyle(edgeStyle);

        return this.stereotypeBuilder.getStereotypeBody(view);
    }

    private Optional<DiagramDescription> findDiagramDescription(IEditingContext editingContext, String label) {
        // @formatter:off
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .filter(diagramDescrpition -> Objects.equals(label, diagramDescrpition.getLabel()))
                .findFirst();
        // @formatter:on
    }

}
