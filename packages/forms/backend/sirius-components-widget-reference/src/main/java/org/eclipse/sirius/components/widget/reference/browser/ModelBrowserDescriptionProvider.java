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
package org.eclipse.sirius.components.widget.reference.browser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.compatibility.services.ImageConstants;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of the model browser tree.
 *
 * @author pcdavid
 */
@Service
public class ModelBrowserDescriptionProvider implements IRepresentationDescriptionRegistryConfigurer {

    public static final String DESCRIPTION_ID = UUID.nameUUIDFromBytes("model_browser_tree_description".getBytes()).toString();

    public static final String DOCUMENT_KIND = "siriusWeb://document";

    private final IObjectService objectService;

    private final IURLParser urlParser;

    public ModelBrowserDescriptionProvider(IObjectService objectService, IURLParser urlParser) {
        this.objectService = Objects.requireNonNull(objectService);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        registry.add(this.getModelBrowserDescription());
    }

    public TreeDescription getModelBrowserDescription() {
        // This predicate will NOT be used while creating the model browser but we don't want to see the description of the
        // model browser in the list of representations that can be created. Thus, we will return false all the time.
        Predicate<VariableManager> canCreatePredicate = variableManager -> variableManager.get("treeId", String.class).map(treeId -> treeId.startsWith("modelBrowser://"))
                .orElse(false);

        return TreeDescription.newTreeDescription(DESCRIPTION_ID)
                .label("Model Browser")
                .idProvider(new GetOrCreateRandomIdProvider())
                .treeItemIdProvider(this::getTreeItemId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .imageURLProvider(this::getImageURL)
                .editableProvider(this::isEditable)
                .deletableProvider(this::isDeletable)
                .selectableProvider(this::isSelectable)
                .elementsProvider(this::getElements)
                .hasChildrenProvider(this::hasChildren)
                .childrenProvider(this::getChildren)
                .canCreatePredicate(canCreatePredicate)
                .deleteHandler(this::getDeleteHandler)
                .renameHandler(this::getRenameHandler)
                .build();
    }

    private boolean isSelectable(VariableManager variableManager) {
        var optionalReference = this.resolveReference(variableManager);
        return optionalReference.isPresent() && this.isSelectable(variableManager, optionalReference.get());
    }

    private boolean isSelectable(VariableManager variableManager, EReference reference) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent() && reference != null) {
            return reference.getEType().isInstance(optionalSelf.get());
        } else {
            return false;
        }
    }

    private Optional<EReference> resolveReference(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith("modelBrowser://") && optionalEditingContext.isPresent()) {
            Registry ePackageRegistry = optionalEditingContext.get().getDomain().getResourceSet().getPackageRegistry();
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String[] qualifiedTypeName = parameters.get("typeName").get(0).split("::");

            String domain = qualifiedTypeName[0];
            String typeName = qualifiedTypeName[1];
            String referenceName = parameters.get("featureName").get(0);

            return this.findEPackage(ePackageRegistry, domain)
                    .map(ePackage -> ePackage.getEClassifier(typeName))
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .map(klass -> klass.getEStructuralFeature(referenceName))
                    .filter(EReference.class::isInstance)
                    .map(EReference.class::cast);
        } else {
            return Optional.empty();
        }
    }

    public Optional<EPackage> findEPackage(EPackage.Registry ePackageRegistry, String ePackageName) {
        return ePackageRegistry.values().stream()
                .map(object -> {
                    if (object instanceof EPackage.Descriptor) {
                        return ((EPackage.Descriptor) object).getEPackage();
                    }
                    return object;
                })
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .filter(ePackage -> ePackage.getName().equals(ePackageName))
                .findFirst();
    }

    private String getTreeItemId(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        String id = null;
        if (self instanceof Resource resource) {
            id = resource.getURI().path().substring(1);
        } else if (self instanceof EObject) {
            id = this.objectService.getId(self);
        }
        return id;
    }

    private String getKind(VariableManager variableManager) {
        String kind = "";
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        if (self instanceof Resource) {
            kind = DOCUMENT_KIND;
        } else {
            kind = this.objectService.getKind(self);
        }
        return kind;
    }

    private String getLabel(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        String label = "";
        if (self instanceof Resource resource) {
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
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst()
                .map(ResourceMetadataAdapter::getName)
                .orElse(resource.getURI().lastSegment());
    }

    private boolean isEditable(VariableManager variableManager) {
        return false;

    }

    private boolean isDeletable(VariableManager variableManager) {
        return false;
    }

    private String getImageURL(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        String imageURL = null;
        if (self instanceof EObject) {
            imageURL = this.objectService.getImagePath(self);
        } else if (self instanceof Resource) {
            imageURL = ImageConstants.RESOURCE_SVG;
        }
        return Optional.ofNullable(imageURL).orElse(ImageConstants.DEFAULT_SVG);
    }

    private List<Resource> getElements(VariableManager variableManager) {
        var optionalEditingContext = Optional.of(variableManager.getVariables().get(IEditingContext.EDITING_CONTEXT));
        var optionalResourceSet = optionalEditingContext.filter(IEditingContext.class::isInstance)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain)
                .map(EditingDomain::getResourceSet);

        if (optionalResourceSet.isPresent()) {
            var resourceSet = optionalResourceSet.get();
            return resourceSet.getResources().stream()
                    .filter(res -> res.getURI() != null && EditingContext.RESOURCE_SCHEME.equals(res.getURI().scheme()))
                    .sorted(Comparator.nullsLast(Comparator.comparing(this::getResourceLabel, String.CASE_INSENSITIVE_ORDER)))
                    .toList();
        }
        return new ArrayList<>();
    }

    private boolean hasChildren(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        boolean hasChildren = false;
        if (self instanceof Resource resource) {
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof EObject eObject) {
            hasChildren = !eObject.eContents().isEmpty();
            hasChildren = hasChildren && this.hasCompatibleDescendants(variableManager, eObject, this.resolveReference(variableManager).orElse(null));
        }
        return hasChildren;
    }

    private boolean hasChildren(VariableManager variableManager, EReference reference) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        boolean hasChildren = false;
        if (self instanceof Resource resource) {
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof EObject eObject) {
            hasChildren = !eObject.eContents().isEmpty();
            hasChildren = hasChildren && this.hasCompatibleDescendants(variableManager, eObject, reference);
        }
        return hasChildren;
    }

    private boolean hasCompatibleDescendants(VariableManager variableManager, EObject eObject, EReference reference) {
        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, eObject);
        return this.isSelectable(childVariableManager, reference) || eObject.eContents().stream()
                .anyMatch(eContent -> this.hasCompatibleDescendants(childVariableManager, eContent, reference));
    }

    private List<Object> getChildren(VariableManager variableManager) {
        List<Object> result = new ArrayList<>();

        List<String> expandedIds = new ArrayList<>();
        Object objects = variableManager.getVariables().get(TreeRenderer.EXPANDED);
        if (objects instanceof List<?> list) {
            expandedIds = list.stream().filter(String.class::isInstance).map(String.class::cast).toList();
        }

        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);

        if (optionalEditingContext.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();

            String id = this.getTreeItemId(variableManager);
            if (expandedIds.contains(id)) {
                Object self = variableManager.getVariables().get(VariableManager.SELF);

                if (self instanceof Resource resource) {
                    result.addAll(resource.getContents());
                } else if (self instanceof EObject) {
                    List<Object> contents = this.objectService.getContents(editingContext, id);
                    result.addAll(contents);
                }
            }
        }
        EReference reference = this.resolveReference(variableManager).orElse(null);
        result.removeIf(object -> {
            if (object instanceof EObject eObject) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VariableManager.SELF, eObject);
                return !this.isSelectable(childVariableManager, reference) && !this.hasChildren(childVariableManager, reference);
            } else {
                return false;
            }
        });
        return result;
    }

    private IStatus getDeleteHandler(VariableManager variableManager) {
        return new Failure("");
    }

    private IStatus getRenameHandler(VariableManager variableManager, String newLabel) {
        return new Failure("");
    }
}
