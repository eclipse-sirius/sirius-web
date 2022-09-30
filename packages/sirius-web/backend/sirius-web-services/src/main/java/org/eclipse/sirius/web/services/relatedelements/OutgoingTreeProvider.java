/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.web.services.relatedelements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.sirius.components.compatibility.emf.properties.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.TreeNode;
import org.eclipse.sirius.components.forms.components.TreeComponent;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.representations.VariableManager;

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
public class OutgoingTreeProvider {
    private static final String WIDGET_ID = "related/outgoing"; //$NON-NLS-1$

    private static final String TITLE = "Outgoing"; //$NON-NLS-1$

    private static final String WIDGET_ICON_URL = "/images/east_black_24dp.svg"; //$NON-NLS-1$

    private static final String OUTGOING_REFERENCE_ICON_URL = "/images/east_black_24dp.svg"; //$NON-NLS-1$

    private static final String OUTGOING_REFERENCE_KIND = "siriusWeb://category/outgoing-references"; //$NON-NLS-1$

    private final IObjectService objectService;

    private final AdapterFactory adapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider = new IPropertiesValidationProvider.NoOp();

    public OutgoingTreeProvider(IObjectService objectService, AdapterFactory adapterFactory) {
        this.objectService = Objects.requireNonNull(objectService);
        this.adapterFactory = Objects.requireNonNull(adapterFactory);
    }

    public TreeDescription getTreeDescription() {
        // @formatter:off
        return TreeDescription.newTreeDescription(WIDGET_ID).idProvider(new WidgetIdProvider())
                .diagnosticsProvider(this.propertiesValidationProvider.getDiagnosticsProvider())
                .kindProvider(this.propertiesValidationProvider.getKindProvider())
                .messageProvider(this.propertiesValidationProvider.getMessageProvider())
                .labelProvider(variableManager -> TITLE)
                .iconURLProvider(variableManager -> WIDGET_ICON_URL)
                .nodeIdProvider(this::getNodeId)
                .nodeLabelProvider(this::getNodeLabel)
                .nodeImageURLProvider(this::getNodeImageURL)
                .nodeKindProvider(this::getNodeKind)
                .nodeSelectableProvider(this::isNodeSelectable)
                .childrenProvider(this::getOutgoingChildren)
                .expandedNodeIdsProvider(this::collectAllNodeIds)
                .build();
        // @formatter:on
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
            // @formatter:off
            var nonContainmentReferences = root.eClass().getEAllReferences().stream()
                    .filter(ref -> !ref.isContainment())
                    .sorted(Comparator.comparing(EStructuralFeature::getName))
                    .collect(Collectors.toList());
            // @formatter:on
            result = nonContainmentReferences.stream().filter(ref -> {
                if (ref.isMany()) {
                    return !((EList<?>) root.eGet(ref)).isEmpty();
                } else {
                    return root.eGet(ref) != null;
                }
            }).collect(Collectors.toList());
        } else if (self instanceof EReference) {
            result = this.readReference(root, (EReference) self);
        }
        return result;
    }

    private List<?> readReference(EObject self, EReference ref) {
        List<?> result;
        if (ref.isMany()) {
            result = (EList<?>) self.eGet(ref);
        } else {
            result = Optional.ofNullable(self.eGet(ref)).stream().collect(Collectors.toList());
        }
        return result;
    }

    private String getNodeId(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof EReference) {
            result = "reference/" + ((EReference) self).getName(); //$NON-NLS-1$
        } else if (self != null) {
            result = this.objectService.getId(self);
        }
        return result;
    }

    private String getNodeLabel(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof EReference) {
            result = this.objectService.getLabel(self);
            var optionalRootEObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalRootEObject.isPresent()) {
                result = this.getDisplayName((EReference) self, optionalRootEObject.get());
            }
        } else if (self != null) {
            result = this.objectService.getLabel(self);
        }
        return result;
    }

    private String getDisplayName(EReference eReference, EObject eObject) {
        String result = null;
        Adapter adapter = this.adapterFactory.adapt(eObject, IItemPropertySource.class);
        if (adapter instanceof IItemPropertySource) {
            IItemPropertySource itemPropertySource = (IItemPropertySource) adapter;
            IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eReference);
            if (descriptor != null) {
                result = descriptor.getDisplayName(eReference);
            } else {
                result = eReference.getName();
            }
        }
        return result;
    }

    private String getNodeImageURL(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof EReference) {
            result = OUTGOING_REFERENCE_ICON_URL;
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

    private List<String> collectAllNodeIds(VariableManager variableManager) {
        List<String> result = new ArrayList<>();
        for (var element : variableManager.get(TreeComponent.NODES_VARIABLE, List.class).orElse(List.of())) {
            if (element instanceof TreeNode) {
                TreeNode node = (TreeNode) element;
                result.add(node.getId());
            }
        }
        return result;
    }

}
