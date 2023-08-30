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
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
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
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
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
public class ModelBrowsersDescriptionProvider implements IRepresentationDescriptionRegistryConfigurer {

    public static final String CONTAINER_DESCRIPTION_ID = UUID.nameUUIDFromBytes("model_browser_container_tree_description".getBytes()).toString();

    public static final String REFERENCE_DESCRIPTION_ID = UUID.nameUUIDFromBytes("model_browser_reference_tree_description".getBytes()).toString();

    public static final String DOCUMENT_KIND = "siriusWeb://document";

    public static final String TREE_KIND = "modelBrowser://";

    private final IObjectService objectService;

    private final IURLParser urlParser;

    private final IEMFKindService emfKindService;

    public ModelBrowsersDescriptionProvider(IObjectService objectService, IURLParser urlParser, IEMFKindService emfKindService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.emfKindService = Objects.requireNonNull(emfKindService);
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        registry.add(this.getModelBrowserDescription(
                CONTAINER_DESCRIPTION_ID,
                variableManager -> variableManager.get("treeId", String.class)
                        .map(treeId -> treeId.startsWith("modelBrowser://container"))
                        .orElse(false),
                variableManager -> {
                    EClass referenceKind = this.resolveReferenceEClass(variableManager).orElse(null);
                    return this.isContainerSelectable(variableManager, referenceKind);
                }));
        registry.add(this.getModelBrowserDescription(REFERENCE_DESCRIPTION_ID,
                variableManager -> variableManager.get("treeId", String.class)
                        .map(treeId -> treeId.startsWith("modelBrowser://reference"))
                        .orElse(false),
                variableManager -> {
                    EReference reference = this.resolveReference(variableManager).orElse(null);
                    return this.isReferenceSelectable(variableManager, reference);
                }));
    }

    public TreeDescription getModelBrowserDescription(String descriptionId, Predicate<VariableManager> canCreatePredicate,
            Function<VariableManager, Boolean> isSelectableProvider) {

        return TreeDescription.newTreeDescription(descriptionId)
                .label("Model Browser")
                .idProvider(new GetOrCreateRandomIdProvider())
                .treeItemIdProvider(this::getTreeItemId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .imageURLProvider(this::getImageURL)
                .editableProvider(this::isEditable)
                .deletableProvider(this::isDeletable)
                .selectableProvider(isSelectableProvider)
                .elementsProvider(this::getElements)
                .hasChildrenProvider(variableManager -> this.hasChildren(variableManager, isSelectableProvider))
                .childrenProvider(variableManager -> this.getChildren(variableManager, isSelectableProvider))
                // This predicate will NOT be used while creating the model browser, but we don't want to see the description of the
                // model browser in the list of representations that can be created. Thus, we will return false all the time.
                .canCreatePredicate(canCreatePredicate)
                .deleteHandler(this::getDeleteHandler)
                .renameHandler(this::getRenameHandler)
                .build();
    }

    private boolean isContainerSelectable(VariableManager variableManager, EClass referenceKind) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        boolean isSelectable = false;
        if (self instanceof Resource) {
            isSelectable = true;
        } else if (self instanceof EObject selfEObject && referenceKind != null) {
            var optionalEditingDomain = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class).map(EditingContext::getDomain);
            if (optionalEditingDomain.isPresent()) {
                Collection<?> newChildDescriptors = optionalEditingDomain.get().getNewChildDescriptors(selfEObject, null);

                isSelectable = newChildDescriptors.stream()
                        .filter(CommandParameter.class::isInstance)
                        .map(CommandParameter.class::cast)
                        .anyMatch(commandParameter -> referenceKind.isInstance(commandParameter.getValue()));
            }
        }
        return isSelectable;
    }

    private boolean isReferenceSelectable(VariableManager variableManager, EReference reference) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent() && reference != null) {
            return reference.getEType().isInstance(optionalSelf.get()) && this.resolveOwnerEObject(variableManager)
                    .map(eObject -> !EcoreUtil.isAncestor(optionalSelf.get(), eObject))
                    .orElse(true);
        } else {
            return false;
        }
    }

    private Optional<EObject> resolveOwnerEObject(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND) && optionalEditingContext.isPresent()) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String ownerId = parameters.get("ownerId").get(0);

            return this.objectService.getObject(optionalEditingContext.get(), ownerId).filter(EObject.class::isInstance).map(EObject.class::cast);
        } else {
            return Optional.empty();
        }
    }

    private Optional<EClass> resolveReferenceEClass(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith("modelBrowser://") && optionalEditingContext.isPresent()) {
            Registry ePackageRegistry = optionalEditingContext.get().getDomain().getResourceSet().getPackageRegistry();
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String kind = parameters.get("typeName").get(0);

            String ePackageName = this.emfKindService.getEPackageName(kind);
            String eClassName = this.emfKindService.getEClassName(kind);

            return this.findEPackage(ePackageRegistry, ePackageName)
                    .map(ePackage -> ePackage.getEClassifier(eClassName))
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast);
        } else {
            return Optional.empty();
        }
    }

    private Optional<EReference> resolveReference(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND) && optionalEditingContext.isPresent()) {
            Registry ePackageRegistry = optionalEditingContext.get().getDomain().getResourceSet().getPackageRegistry();
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String kind = parameters.get("typeName").get(0);

            String ePackageName = this.emfKindService.getEPackageName(kind);
            String eClassName = this.emfKindService.getEClassName(kind);
            String referenceName = parameters.get("featureName").get(0);

            return this.findEPackage(ePackageRegistry, ePackageName)
                    .map(ePackage -> ePackage.getEClassifier(eClassName))
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .map(klass -> klass.getEStructuralFeature(referenceName))
                    .filter(EReference.class::isInstance)
                    .map(EReference.class::cast);
        } else {
            return Optional.empty();
        }
    }

    public Optional<EPackage> findEPackage(Registry ePackageRegistry, String ePackageName) {
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
        String kind;
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

    private boolean hasChildren(VariableManager variableManager, Function<VariableManager, Boolean> isSelectableProvider) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        boolean hasChildren = false;
        if (self instanceof Resource resource) {
            hasChildren = !resource.getContents().isEmpty();
        } else if (self instanceof EObject eObject) {
            hasChildren = !eObject.eContents().isEmpty();
            hasChildren = hasChildren && this.hasCompatibleDescendants(variableManager, eObject, false, isSelectableProvider);
        }
        return hasChildren;
    }


    private boolean hasCompatibleDescendants(VariableManager variableManager, EObject eObject, boolean isDescendant, Function<VariableManager, Boolean> isSelectableProvider) {
        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(VariableManager.SELF, eObject);
        return isDescendant && isSelectableProvider.apply(childVariableManager) || eObject.eContents().stream()
                .anyMatch(eContent -> this.hasCompatibleDescendants(childVariableManager, eContent, true, isSelectableProvider));
    }

    private List<Object> getChildren(VariableManager variableManager, Function<VariableManager, Boolean> isSelectableProvider) {
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
        result.removeIf(object -> {
            if (object instanceof EObject eObject) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VariableManager.SELF, eObject);
                return !isSelectableProvider.apply(childVariableManager) && !this.hasChildren(childVariableManager, isSelectableProvider);
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
