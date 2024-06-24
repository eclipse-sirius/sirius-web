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
package org.eclipse.sirius.web.application.views.relatedelements.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.core.CoreImageConstants;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.TreeNode;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.components.TreeComponent;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.views.relatedelements.services.api.IOutgoingTreeDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the definition of the tree widget for the "Outgoing" panel in the "Related Elements" view. It has two
 * levels:
 * <ol>
 * <li>Level 1: one category node per non-containment reference of the selected element which references at least one
 * element. The target element for these nodes is the EReference in question.</li>
 * <li>Level 2: inside a given category (EReference), all the semantic model elements that the selected element points
 * to through this particular reference.</li>
 * </ol>
 *
 * @author pcdavid
 */
@Service
public class OutgoingTreeDescriptionProvider implements IOutgoingTreeDescriptionProvider {

    private static final String WIDGET_ID = "related/outgoing";

    private static final String TITLE = "Outgoing";

    private static final String WIDGET_ICON_URL = "/related-elements/east_black_24dp.svg";

    private static final String OUTGOING_REFERENCE_ICON_URL = "/related-elements/east_black_24dp.svg";

    private static final String OUTGOING_REFERENCE_KIND = "siriusWeb://category/outgoing-references";

    private final IObjectService objectService;

    private final ComposedAdapterFactory adapterFactory;

    public OutgoingTreeDescriptionProvider(IObjectService objectService, ComposedAdapterFactory adapterFactory) {
        this.objectService = Objects.requireNonNull(objectService);
        this.adapterFactory = Objects.requireNonNull(adapterFactory);
    }

    @Override
    public TreeDescription getTreeDescription() {
        return TreeDescription.newTreeDescription(WIDGET_ID)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(variableManager -> "")
                .messageProvider(variableManager -> "")
                .labelProvider(variableManager -> TITLE)
                .iconURLProvider(variableManager -> List.of(WIDGET_ICON_URL))
                .nodeIdProvider(this::getNodeId)
                .nodeLabelProvider(this::getNodeLabel)
                .nodeIconURLProvider(this::getNodeImageURL)
                .nodeEndIconsURLProvider(variableManager -> List.of())
                .nodeKindProvider(this::getNodeKind)
                .nodeSelectableProvider(this::isNodeSelectable)
                .childrenProvider(this::getOutgoingChildren)
                .expandedNodeIdsProvider(this::collectAllNodeIds)
                .isCheckableProvider(variableManager -> false)
                .checkedValueProvider(variableManager -> false)
                .newCheckedValueHandler((variableManager, newValue) -> new Success())
                .build();
    }

    private String getNodeId(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof EReference eReference) {
            result = "reference/" + eReference.getName();
        } else if (self != null) {
            result = this.objectService.getId(self);
        }
        return result;
    }

    private String getNodeLabel(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof EReference eReference) {
            result = this.objectService.getLabel(eReference);
            var optionalRootEObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalRootEObject.isPresent()) {
                result = this.getDisplayName(eReference, optionalRootEObject.get());
            }
        } else if (self != null) {
            result = this.objectService.getLabel(self);
        }
        return result;
    }

    private String getDisplayName(EReference eReference, EObject eObject) {
        String result = null;
        Adapter adapter = this.adapterFactory.adapt(eObject, IItemPropertySource.class);
        if (adapter instanceof IItemPropertySource itemPropertySource) {
            IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eReference);
            if (descriptor != null) {
                result = descriptor.getDisplayName(eReference);
            } else {
                result = eReference.getName();
            }
        }
        return result;
    }

    private List<String> getNodeImageURL(VariableManager variableManager) {
        List<String> result = List.of(CoreImageConstants.DEFAULT_SVG);
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof EReference) {
            result = List.of(OUTGOING_REFERENCE_ICON_URL);
        } else if (self != null) {
            result = this.objectService.getImagePath(self);
        }
        return result;
    }

    private String getNodeKind(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof EReference) {
            result = OUTGOING_REFERENCE_KIND;
        } else if (self != null) {
            result = this.objectService.getKind(self);
        }
        return result;
    }

    private boolean isNodeSelectable(VariableManager variableManager) {
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        return self instanceof EObject && !(self instanceof EReference);
    }

    private List<?> getOutgoingChildren(VariableManager variableManager) {
        var self = variableManager.get(VariableManager.SELF, Object.class);
        var root = variableManager.get(TreeComponent.ROOT_VARIABLE, EObject.class);
        var ancestors = variableManager.get(TreeComponent.ANCESTORS_VARIABLE, List.class);
        if (root.isPresent() && self.isPresent() && ancestors.isPresent()) {
            return this.getOutgoingChildren(self.get(), root.get(), ancestors.get());
        } else {
            return List.of();
        }
    }

    private List<?> getOutgoingChildren(Object self, EObject root, List<?> ancestors) {
        List<?> result = List.of();
        if (ancestors.isEmpty()) {
            var nonContainmentReferences = root.eClass().getEAllReferences().stream()
                    .filter(ref -> !ref.isContainment())
                    .sorted(Comparator.comparing(EStructuralFeature::getName))
                    .toList();

            result = nonContainmentReferences.stream().filter(ref -> {
                if (ref.isMany()) {
                    return !((EList<?>) root.eGet(ref)).isEmpty();
                } else {
                    return root.eGet(ref) != null;
                }
            }).toList();
        } else if (self instanceof EReference eReference) {
            result = this.readReference(root, eReference);
        }
        return result;
    }

    private List<?> readReference(EObject self, EReference ref) {
        List<?> result;
        if (ref.isMany()) {
            result = (EList<?>) self.eGet(ref);
        } else {
            result = Optional.ofNullable(self.eGet(ref)).stream().toList();
        }
        return result;
    }

    private List<String> collectAllNodeIds(VariableManager variableManager) {
        List<String> result = new ArrayList<>();
        for (var element : variableManager.get(TreeComponent.NODES_VARIABLE, List.class).orElse(List.of())) {
            if (element instanceof TreeNode node) {
                result.add(node.getId());
            }
        }
        return result;
    }
}
