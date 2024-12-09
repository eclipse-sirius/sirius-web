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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.api.IRepresentationImageProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerServices;
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

    private final IExplorerServices explorerServices;

    public DomainExplorerServices(IObjectService objectService, IRepresentationMetadataSearchService representationMetadataSearchService, IURLParser urlParser, List<IRepresentationImageProvider> representationImageProviders, IExplorerServices explorerServices) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.explorerServices = Objects.requireNonNull(explorerServices);
    }

    public String getKind(Object self) {
        String kind = "";
        if (self instanceof SettingWrapper) {
            kind = "setting";
        } else {
            kind = this.explorerServices.getKind(self);
        }
        return kind;
    }

    public boolean hasChildren(IEditingContext editingContext, Object self) {
        boolean hasChildren = false;
        if (self instanceof SettingWrapper wrapper) {
            var value = wrapper.setting.get(true);
            if (value instanceof Collection<?> collection) {
                hasChildren = !collection.isEmpty();
            }
        } else {
            hasChildren = this.explorerServices.hasChildren(self, editingContext);
            if (!hasChildren && self instanceof Entity) {
                hasChildren = true;
            }
        }
        return hasChildren;
    }

    public List<Object> getElements(IEditingContext editingContext) {
        List<Object> elements = new ArrayList<>();
        this.explorerServices.getDefaultElements(editingContext).stream()
                .filter(resource -> resource.getContents().stream().anyMatch(Domain.class::isInstance))
                .forEach(elements::add);
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
        if (self instanceof SettingWrapper wrapper) {
         // the parent of the superTypes node is the object associated to this Setting
            result = wrapper.setting.getEObject();
        } else {
            result = this.explorerServices.getParent(self, treeItemId, editingContext);
        }
        return result;
    }

    public String getTreeItemLabel(Object self) {
        String label = "";
        if (self instanceof SettingWrapper wrapper) {
            label = wrapper.setting.getEStructuralFeature().getName();
        } else {
            label = this.explorerServices.getLabel(self);
        }
        return label;
    }

    public String getTreeItemId(Object self) {
        String id = null;
        if (self instanceof SettingWrapper wrapper) {
            id = SETTING + this.objectService.getId(wrapper.setting.getEObject()) + SETTING_ID_SEPARATOR + wrapper.setting.getEStructuralFeature().getName();
        } else {
            id = this.explorerServices.getTreeItemId(self);
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
            result = this.explorerServices.getTreeItemObject(treeItemId, editingContext);
        }

        return result;
    }

    public List<String> getIconURL(Object self) {
        return this.explorerServices.getImageURL(self);
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
