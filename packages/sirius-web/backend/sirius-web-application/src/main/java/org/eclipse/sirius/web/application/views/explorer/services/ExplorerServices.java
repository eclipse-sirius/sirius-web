/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.CoreImageConstants;
import org.eclipse.sirius.components.core.api.IDefaultObjectSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.object.services.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerServices;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationIconURL;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.stereotype.Service;

/**
 * Services used to perform operations in the explorer.
 *
 * @author gdaniel
 */
@Service
public class ExplorerServices implements IExplorerServices {

    private final IObjectService objectService;

    private final IURLParser urlParser;

    private final List<IRepresentationImageProvider> representationImageProviders;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final IDefaultObjectSearchService defaultObjectSearchService;

    public ExplorerServices(IObjectService objectService, IURLParser urlParser, List<IRepresentationImageProvider> representationImageProviders, IRepresentationMetadataSearchService representationMetadataSearchService, IReadOnlyObjectPredicate readOnlyObjectPredicate, IDefaultObjectSearchService defaultObjectSearchService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.defaultObjectSearchService = Objects.requireNonNull(defaultObjectSearchService);
    }

    @Override
    public String getTreeItemId(Object self) {
        String id = null;
        if (self instanceof RepresentationMetadata representationMetadata) {
            id = representationMetadata.getId().toString();
        } else if (self instanceof Resource resource) {
            id = resource.getURI().path().substring(1);
        } else if (self instanceof EObject) {
            id = this.objectService.getId(self);
        }
        return id;
    }

    @Override
    public String getKind(Object self) {
        String kind = "";
        if (self instanceof RepresentationMetadata representationMetadata) {
            kind = representationMetadata.getKind();
        } else if (self instanceof Resource) {
            kind = ExplorerDescriptionProvider.DOCUMENT_KIND;
        } else {
            kind = this.objectService.getKind(self);
        }
        return kind;
    }

    @Override
    public String getLabel(Object self) {
        String label = "";
        if (self instanceof RepresentationMetadata representationMetadata) {
            label = representationMetadata.getLabel();
        } else if (self instanceof Resource resource) {
            label = this.getResourceLabel(resource);
        } else if (self instanceof EObject) {
            label = this.objectService.getLabel(self);
            if (label.isBlank()) {
                var kind = this.objectService.getKind(self);
                label = this.urlParser.getParameterValues(kind).get(SemanticKindConstants.ENTITY_ARGUMENT).get(0);
            }
        }
        return label;
    }

    private String getResourceLabel(Resource resource) {
        return resource.eAdapters().stream().filter(ResourceMetadataAdapter.class::isInstance).map(ResourceMetadataAdapter.class::cast).findFirst().map(ResourceMetadataAdapter::getName)
                .orElse(resource.getURI().lastSegment());
    }

    @Override
    public boolean isEditable(Object self) {
        boolean editable = false;
        if (!this.readOnlyObjectPredicate.test(self)) {
            if (self instanceof RepresentationMetadata) {
                editable = true;
            } else if (self instanceof Resource) {
                editable = true;
            } else if (self instanceof EObject) {
                editable = this.objectService.isLabelEditable(self);
            }
        }
        return editable;
    }

    @Override
    public boolean isDeletable(Object self) {
        return !this.readOnlyObjectPredicate.test(self);
    }

    @Override
    public boolean isSelectable(Object self) {
        return true;
    }

    @Override
    public List<String> getImageURL(Object self) {
        List<String> imageURL = List.of(CoreImageConstants.DEFAULT_SVG);
        if (self instanceof EObject) {
            imageURL = this.objectService.getImagePath(self);
        } else if (self instanceof RepresentationMetadata representationMetadata) {
            if (!representationMetadata.getIconURLs().isEmpty()) {
                imageURL = representationMetadata.getIconURLs().stream()
                        .map(RepresentationIconURL::url)
                        .toList();
            } else {
                imageURL = this.representationImageProviders.stream()
                        .flatMap(provider -> provider.getImageURL(representationMetadata.getKind()).stream())
                        .toList();
            }
        } else if (self instanceof Resource) {
            imageURL = List.of("/explorer/Resource.svg");
        }
        return imageURL;
    }

    @Override
    public Object getTreeItemObject(String treeItemId, IEditingContext editingContext) {
        Object result = null;
        if (editingContext != null && treeItemId != null) {
            // Fast path to avoid potentially costly IObjectSearchServiceDelegates
            var optionalObject = this.defaultObjectSearchService.getObject(editingContext, treeItemId);
            if (optionalObject.isEmpty()) {
                // Slow path: fallback to the full algorithm
                optionalObject = this.objectService.getObject(editingContext, treeItemId);
            }

            if (optionalObject.isPresent()) {
                result = optionalObject.get();
            } else {
                var optionalEditingDomain = Optional.of(editingContext)
                        .filter(IEMFEditingContext.class::isInstance)
                        .map(IEMFEditingContext.class::cast)
                        .map(IEMFEditingContext::getDomain);

                if (optionalEditingDomain.isPresent()) {
                    var editingDomain = optionalEditingDomain.get();
                    ResourceSet resourceSet = editingDomain.getResourceSet();
                    URI uri = new JSONResourceFactory().createResourceURI(treeItemId);

                    result = resourceSet.getResources().stream()
                            .filter(resource -> resource.getURI().equals(uri))
                            .findFirst()
                            .orElse(null);
                }
            }
        }
        return result;
    }

    @Override
    public Object getParent(Object self, String treeItemId, IEditingContext editingContext) {
        Object result = null;

        if (self instanceof RepresentationMetadata && treeItemId != null && editingContext != null) {
            var optionalRepresentationMetadata = new UUIDParser().parse(treeItemId).flatMap(this.representationMetadataSearchService::findMetadataById);
            var targetObjectId = optionalRepresentationMetadata.map(RepresentationMetadata::getTargetObjectId).orElse(null);
            result = this.objectService.getObject(editingContext, targetObjectId).orElse(null);
        } else if (self instanceof EObject eObject) {
            Object semanticContainer = eObject.eContainer();
            if (semanticContainer == null) {
                semanticContainer = eObject.eResource();
            }
            result = semanticContainer;
        }
        return result;
    }

    @Override
    public boolean hasChildren(Object self, IEditingContext editingContext, List<RepresentationMetadata> existingRepresentations) {
        boolean hasChildren = false;
        if (self instanceof Resource resource) {
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof EObject eObject) {
            hasChildren = !eObject.eContents().isEmpty();

            var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
            if (!hasChildren && optionalSemanticDataId.isPresent()) {
                String id = this.objectService.getId(eObject);
                hasChildren = this.findRepresentationsForTargetObjectId(existingRepresentations, id).findAny().isPresent();
            }
        }
        return hasChildren;
    }

    @Override
    public List<Object> getDefaultChildren(Object self, IEditingContext editingContext, List<String> expandedIds, List<RepresentationMetadata> existingRepresentations) {
        List<Object> result = new ArrayList<>();
        if (editingContext != null) {
            String id = this.getTreeItemId(self);
            if (expandedIds.contains(id)) {
                if (self instanceof Resource resource) {
                    result.addAll(resource.getContents());
                } else if (self instanceof EObject) {
                    var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());

                    if (optionalSemanticDataId.isPresent()) {
                        this.findRepresentationsForTargetObjectId(existingRepresentations, id)
                            .sorted(Comparator.comparing(RepresentationMetadata::getLabel))
                            .forEachOrdered(result::add);
                    }

                    List<Object> contents = this.objectService.getContents(self);
                    result.addAll(contents);
                }
            }
        }
        return result;
    }

    private Stream<RepresentationMetadata> findRepresentationsForTargetObjectId(List<RepresentationMetadata> existingRepresentations, String targetObjectd) {
        return existingRepresentations.stream().filter(representationMetadata -> representationMetadata.getTargetObjectId().equals(targetObjectd));
    }

    @Override
    public List<Resource> getDefaultElements(IEditingContext editingContext) {
        var optionalResourceSet = Optional.ofNullable(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet);

        return optionalResourceSet
                .map(resourceSet -> resourceSet.getResources().stream()
                        .sorted(Comparator.nullsLast(Comparator.comparing(this::getResourceLabel, String.CASE_INSENSITIVE_ORDER)))
                        .toList())
                .orElseGet(ArrayList::new);
    }


}
