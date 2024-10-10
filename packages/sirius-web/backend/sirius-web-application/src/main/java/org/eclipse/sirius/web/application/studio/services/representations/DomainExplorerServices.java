/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.studio.services.representations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.CoreImageConstants;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * AQL Services used inside trees descriptions.
 *
 * @author Jerome Gout
 */
@Service
public class DomainExplorerServices {

    public static final String DOCUMENT_KIND = "siriusWeb://document";

    public static final String SETTING = "setting:";

    public static final String SETTING_ID_SEPARATOR = "::";

    private final IObjectService objectService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IURLParser urlParser;

    private final List<IRepresentationImageProvider> representationImageProviders;

    public DomainExplorerServices(IObjectService objectService, IRepresentationMetadataSearchService representationMetadataSearchService, IURLParser urlParser, List<IRepresentationImageProvider> representationImageProviders) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.representationImageProviders = Objects.requireNonNull(representationImageProviders);
    }

    public String getKind(Object self) {
        String kind = "";
        if (self instanceof RepresentationMetadata representationMetadata) {
            kind = representationMetadata.getKind();
        } else if (self instanceof Resource) {
            kind = DOCUMENT_KIND;
        } else if (self instanceof SettingWrapper) {
            kind = "setting";
        } else {
            kind = this.objectService.getKind(self);
        }
        return kind;
    }

    public boolean hasChildren(Object self, IEditingContext editingContext) {
        boolean hasChildren = false;
        if (self instanceof Resource resource) {
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof EObject eObject) {
            hasChildren = !eObject.eContents().isEmpty();

            if (!hasChildren) {
                var optionalEditingContextId = new UUIDParser().parse(editingContext.getId());
                if (optionalEditingContextId.isPresent()) {
                    var projectId = optionalEditingContextId.get();
                    String id = this.objectService.getId(eObject);
                    hasChildren = this.representationMetadataSearchService.existAnyRepresentationForProjectAndTargetObjectId(AggregateReference.to(projectId), id);
                }
            }

            if (!hasChildren && self instanceof Entity) {
                hasChildren = true;
            }
        } else if (self instanceof SettingWrapper wrapper) {
            var value = wrapper.setting.get(true);
            if (value instanceof Collection<?> collection) {
                hasChildren = !collection.isEmpty();
            }
        }
        return hasChildren;
    }

    public List<Object> getElements(IEditingContext editingContext) {
        List<Object> elements = new ArrayList<>();

        var optionalResourceSet = Optional.of(editingContext).filter(IEditingContext.class::isInstance)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet);

        var resources = optionalResourceSet.map(resourceSet -> resourceSet.getResources().stream()
                .filter(resource -> resource.getContents().stream().anyMatch(Domain.class::isInstance))
                .sorted(Comparator.nullsLast(Comparator.comparing(this::getResourceLabel, String.CASE_INSENSITIVE_ORDER)))
                .toList()
        ).orElseGet(ArrayList::new);
        elements.addAll(resources);
        return elements;
    }

    public List<Object> getChildren(Object self, IEditingContext editingContext, List<String> expandedIds) {
        List<Object> result = new ArrayList<>();

        if (editingContext != null) {
            String id = this.getTreeItemId(self);
            if (expandedIds.contains(id)) {
                if (self instanceof Resource resource) {
                    result.addAll(resource.getContents());
                } else if (self instanceof EObject) {
                    var optionalEditingContextId = new UUIDParser().parse(editingContext.getId());
                    if (optionalEditingContextId.isPresent()) {
                        var projectId = optionalEditingContextId.get();
                        var representationMetadata = new ArrayList<>(this.representationMetadataSearchService.findAllMetadataByProjectAndTargetObjectId(AggregateReference.to(projectId), id));
                        representationMetadata.sort(Comparator.comparing(org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata::getLabel));
                        result.addAll(representationMetadata);
                    }
                    List<Object> contents = this.objectService.getContents(self);
                    if (self instanceof Entity entity) {
                        result.add(new SettingWrapper(((InternalEObject) entity).eSetting(entity.eClass().getEStructuralFeature("superTypes"))));
                    }
                    result.addAll(contents);
                } else if (self instanceof SettingWrapper wrapper) {
                    var value = wrapper.setting.get(true);
                    if (value instanceof Collection<?> collection) {
                        result.addAll(collection);
                    }
                }
            }
        }
        return result;
    }

    public Object getParent(Object self, IEditingContext editingContext, String treeItemId) {
        Object result = null;

        if (self instanceof RepresentationMetadata) {
            var optionalRepresentationMetadata = new UUIDParser().parse(treeItemId).flatMap(this.representationMetadataSearchService::findMetadataById);
            var repId = optionalRepresentationMetadata.map(org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata::getTargetObjectId).orElse(null);
            result = this.objectService.getObject(editingContext, repId);
        } else if (self instanceof EObject eObject) {
            Object semanticContainer = eObject.eContainer();
            if (semanticContainer == null) {
                semanticContainer = eObject.eResource();
            }
            result = semanticContainer;
        } else if (self instanceof SettingWrapper wrapper) {
            // the parent of the superTypes node is the object associated to this Setting
            result = wrapper.setting.getEObject();
        }
        return result;
    }

    public String getTreeItemLabel(Object self) {
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
        } else if (self instanceof SettingWrapper wrapper) {
            label = wrapper.setting.getEStructuralFeature().getName();
        }
        return label;
    }

    public String getTreeItemId(Object self) {
        String id = null;
        if (self instanceof RepresentationMetadata representationMetadata) {
            id = representationMetadata.getId();
        } else if (self instanceof Resource resource) {
            id = resource.getURI().path().substring(1);
        } else if (self instanceof EObject) {
            id = this.objectService.getId(self);
        } else if (self instanceof SettingWrapper wrapper) {
            id = SETTING + this.objectService.getId(wrapper.setting.getEObject()) + SETTING_ID_SEPARATOR + wrapper.setting.getEStructuralFeature().getName();
        }
        return id;
    }

    public Object getTreeItemObject(IEditingContext editingContext, String treeItemId) {
        Object result = null;

        if (treeItemId.startsWith(SETTING)) {
            // the tree item is a setting, get the object and then the structural feature associated
            var objectId = treeItemId.substring(SETTING.length(), treeItemId.indexOf(SETTING_ID_SEPARATOR));
            var featureName = treeItemId.substring(treeItemId.indexOf(SETTING_ID_SEPARATOR) + SETTING_ID_SEPARATOR.length());
            var optObject = this.objectService.getObject(editingContext, objectId);
            if (optObject.isPresent()) {
                InternalEObject internalObject = (InternalEObject) optObject.get();
                result = new SettingWrapper(internalObject.eSetting(internalObject.eClass().getEStructuralFeature(featureName)));
            }
        } else {
            var optionalObject = this.objectService.getObject(editingContext, treeItemId);
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

    public List<String> getIconURL(Object self) {
        List<String> imageURL = List.of(CoreImageConstants.DEFAULT_SVG);
        if (self instanceof EObject) {
            imageURL = this.objectService.getImagePath(self);
        } else if (self instanceof RepresentationMetadata representationMetadata) {
            imageURL = this.representationImageProviders.stream()
                    .map(representationImageProvider -> representationImageProvider.getImageURL(representationMetadata.getKind()))
                    .flatMap(Optional::stream)
                    .toList();
        } else if (self instanceof Resource) {
            imageURL = List.of("/explorer/Resource.svg");
        }
        return imageURL;
    }

    public boolean isDeletable(Object self) {
        return !(self instanceof SettingWrapper);
    }

    public boolean isEditable(Object self) {
        return !(self instanceof SettingWrapper);
    }

    public boolean isSelectable(Object self) {
        return !(self instanceof SettingWrapper);
    }

    private String getResourceLabel(Resource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst()
                .map(ResourceMetadataAdapter::getName)
                .orElse(resource.getURI().lastSegment());
    }

    public Object toggleAbstractEntity(Object self) {
        if (self instanceof Entity entity) {
            entity.setAbstract(!entity.isAbstract());
        }
        return self;
    }

    /**
     * Wrapper for {@link org.eclipse.emf.ecore.EStructuralFeature.Setting} to avoid AQL interpreter sees this element as an list.
     */
    private record SettingWrapper(Setting setting) {

    }
}
