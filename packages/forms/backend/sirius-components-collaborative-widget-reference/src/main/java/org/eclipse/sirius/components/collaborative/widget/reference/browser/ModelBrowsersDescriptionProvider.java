/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.browser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.compatibility.services.ImageConstants;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.core.api.SemanticKindConstants;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.components.widget.reference.IReferenceWidgetRootCandidateSearchProvider;
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

    private final List<IReferenceWidgetRootCandidateSearchProvider> candidateProviders;

    private final ReferenceWidgetDefaultCandidateSearchProvider defaultCandidateProvider;

    public ModelBrowsersDescriptionProvider(IObjectService objectService, IURLParser urlParser, IEMFKindService emfKindService, List<IReferenceWidgetRootCandidateSearchProvider> candidateProviders) {
        this.objectService = Objects.requireNonNull(objectService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.candidateProviders = Objects.requireNonNull(candidateProviders);
        this.defaultCandidateProvider = new ReferenceWidgetDefaultCandidateSearchProvider();
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        registry.add(this.getModelBrowserDescription(CONTAINER_DESCRIPTION_ID,
                variableManager -> variableManager.get("treeId", String.class).map(treeId -> treeId.startsWith("modelBrowser://container")).orElse(false), variableManager -> {
                    EClass referenceKind = this.resolveReferenceEClass(variableManager).orElse(null);
                    return this.isContainerSelectable(variableManager, referenceKind);
                }, this::getCreationScopeElements));
        registry.add(this.getModelBrowserDescription(REFERENCE_DESCRIPTION_ID,
                variableManager -> variableManager.get("treeId", String.class).map(treeId -> treeId.startsWith("modelBrowser://reference")).orElse(false), variableManager -> {
                    EClass targetType = this.resolveTargetType(variableManager).orElse(null);
                    boolean isContainment = this.resolveIsContainment(variableManager);
                    return this.isTypeSelectable(variableManager, targetType, isContainment);
                }, this::getSearchScopeElements));
    }

    public TreeDescription getModelBrowserDescription(String descriptionId, Predicate<VariableManager> canCreatePredicate, Function<VariableManager, Boolean> isSelectableProvider,
            Function<VariableManager, List<?>> elementsProvider) {

        return TreeDescription.newTreeDescription(descriptionId)
                .label("Model Browser")
                .idProvider(new GetOrCreateRandomIdProvider())
                .treeItemIdProvider(this::getTreeItemId)
                .kindProvider(this::getKind)
                .labelProvider(this::getLabel)
                .iconURLProvider(this::getImageURL)
                .editableProvider(this::isEditable)
                .deletableProvider(this::isDeletable)
                .selectableProvider(isSelectableProvider)
                .elementsProvider(elementsProvider)
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
            var optionalEditingDomain = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class).map(IEMFEditingContext::getDomain);
            if (optionalEditingDomain.isPresent()) {
                Collection<?> newChildDescriptors = optionalEditingDomain.get().getNewChildDescriptors(selfEObject, null);

                isSelectable = newChildDescriptors.stream().filter(CommandParameter.class::isInstance).map(CommandParameter.class::cast)
                        .anyMatch(commandParameter -> referenceKind.isInstance(commandParameter.getValue()));
            }
        }
        return isSelectable;
    }

    private boolean isTypeSelectable(VariableManager variableManager, EClass targetType, boolean isContainment) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent() && targetType != null) {
            return targetType.isInstance(optionalSelf.get())
                    && this.resolveOwnerEObject(variableManager).map(eObject -> !(isContainment && EcoreUtil.isAncestor(optionalSelf.get(), eObject))).orElse(true);
        } else {
            return false;
        }
    }

    private Optional<EObject> resolveOwnerEObject(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);
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
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND) && optionalEditingContext.isPresent()) {
            Registry ePackageRegistry = optionalEditingContext.get().getDomain().getResourceSet().getPackageRegistry();
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String refContainer = parameters.get("ownerKind").get(0);

            String ePackageName = this.emfKindService.getEPackageName(refContainer);
            String eClassName = this.emfKindService.getEClassName(refContainer);

            return this.findEPackage(ePackageRegistry, ePackageName).map(ePackage -> ePackage.getEClassifier(eClassName)).filter(EClass.class::isInstance).map(EClass.class::cast);
        } else {
            return Optional.empty();
        }
    }

    private Optional<EClass> resolveTargetType(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND) && optionalEditingContext.isPresent()) {
            Registry ePackageRegistry = optionalEditingContext.get().getDomain().getResourceSet().getPackageRegistry();
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String kind = parameters.get("targetType").get(0);

            String ePackageName = this.emfKindService.getEPackageName(kind);
            String eClassName = this.emfKindService.getEClassName(kind);

            return this.findEPackage(ePackageRegistry, ePackageName).map(ePackage -> ePackage.getEClassifier(eClassName)).filter(EClass.class::isInstance).map(EClass.class::cast);
        } else {
            return Optional.empty();
        }
    }

    private boolean resolveIsContainment(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND)) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String isContainment = parameters.get("isContainment").get(0);
            return Boolean.parseBoolean(isContainment);
        } else {
            return false;
        }
    }

    public Optional<EPackage> findEPackage(Registry ePackageRegistry, String ePackageName) {
        return ePackageRegistry.values().stream().map(object -> {
            if (object instanceof EPackage.Descriptor) {
                return ((EPackage.Descriptor) object).getEPackage();
            }
            return object;
        }).filter(EPackage.class::isInstance).map(EPackage.class::cast).filter(ePackage -> ePackage.getName().equals(ePackageName)).findFirst();
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

    private StyledString getLabel(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        String label = "";
        if (self instanceof RepresentationMetadata representationMetadata) {
            label = representationMetadata.getLabel();
        } else if (self instanceof Resource resource) {
            label = this.getResourceLabel(resource);
        } else if (self instanceof EObject) {
            StyledString styledString = this.objectService.getStyledLabel(self);
            if (!styledString.toString().isBlank()) {
                return styledString;
            }
            else {
                var kind = this.objectService.getKind(self);
                label = this.urlParser.getParameterValues(kind).get(SemanticKindConstants.ENTITY_ARGUMENT).get(0);
            }
        }

        return StyledString.of(label);
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

    private List<String> getImageURL(VariableManager variableManager) {
        Object self = variableManager.getVariables().get(VariableManager.SELF);
        List<String> imageURL = List.of(ImageConstants.DEFAULT_SVG);
        if (self instanceof EObject) {
            imageURL = this.objectService.getImagePath(self);
        } else if (self instanceof Resource) {
            imageURL = List.of(ImageConstants.RESOURCE_SVG);
        }
        return imageURL;
    }

    private List<? extends Object> getSearchScopeElements(VariableManager variableManager) {
        var optionalTreeId = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class);
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class);
        if (optionalTreeId.isPresent() && optionalTreeId.get().startsWith(TREE_KIND) && optionalEditingContext.isPresent()) {
            Map<String, List<String>> parameters = new URLParser().getParameterValues(optionalTreeId.get());
            String descriptionId = parameters.get("descriptionId").get(0);
            String ownerId = parameters.get("ownerId").get(0);
            var semanticOwner = this.objectService.getObject(optionalEditingContext.get(), ownerId).get();

            return this.candidateProviders.stream()
                    .filter(provider -> provider.canHandle(descriptionId))
                    .findFirst()
                    .orElse(this.defaultCandidateProvider)
                    .getRootElementsForReference(semanticOwner, descriptionId, optionalEditingContext.get());
        }
        return Collections.emptyList();
    }

    private List<? extends Object> getCreationScopeElements(VariableManager variableManager) {
        var optionalResourceSet = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet);

        if (optionalResourceSet.isPresent()) {
            var resourceSet = optionalResourceSet.get();
            return resourceSet.getResources().stream()
                    .filter(resource -> resource.getURI() != null && IEMFEditingContext.RESOURCE_SCHEME.equals(resource.getURI().scheme()))
                    .sorted(Comparator.nullsLast(Comparator.comparing(this::getResourceLabel, String.CASE_INSENSITIVE_ORDER)))
                    .toList();
        }
        return List.of();
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
        return isDescendant && isSelectableProvider.apply(childVariableManager)
                || eObject.eContents().stream().anyMatch(eContent -> this.hasCompatibleDescendants(childVariableManager, eContent, true, isSelectableProvider));
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
