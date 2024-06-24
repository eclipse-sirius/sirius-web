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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.sirius.components.core.CoreImageConstants;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.TreeNode;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.components.TreeComponent;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.views.relatedelements.services.api.IIncomingTreeDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the definition of the tree widget for the "Incoming" panel in the "Related Elements" view.
 *
 * @author pcdavid
 */
@Service
public class IncomingTreeDescriptionProvider implements IIncomingTreeDescriptionProvider {

    private static final String WIDGET_ID = "related/incoming";

    private static final String TITLE = "Incoming";

    private static final String WIDGET_ICON_URL = "/related-elements/west_black_24dp.svg";

    private static final String INCOMING_REFERENCE_ICON_URL = "/related-elements/west_black_24dp.svg";

    private static final String INCOMING_REFERENCES_KIND = "siriusWeb://category/incoming-references";

    private final IObjectService objectService;

    private final ComposedAdapterFactory adapterFactory;

    public IncomingTreeDescriptionProvider(IObjectService objectService, ComposedAdapterFactory adapterFactory) {
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
                .childrenProvider(this::getIncomingChildren)
                .expandedNodeIdsProvider(this::collectAllNodeIds)
                .isCheckableProvider(variableManager -> false)
                .checkedValueProvider(variableManager -> false)
                .newCheckedValueHandler((variableManager, newValue) -> new Success())
                .build();
    }

    private String getNodeId(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof IncomingReferences incomingReferences) {
            result = "reference/" + incomingReferences.eReference().getName();
        } else if (self != null) {
            result = this.objectService.getId(self);
        }
        return result;
    }

    private String getNodeLabel(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof IncomingReferences incomingReferences) {
            EReference eReference = incomingReferences.eReference();
            EObject eObject = incomingReferences.sources().get(0);
            result = eReference.getName();
            if (eReference.isContainment()) {
                result = "owned " + result;
                Adapter adapter = this.adapterFactory.adapt(eObject, IItemLabelProvider.class);
                if (adapter instanceof ItemProviderAdapter editingDomainItemProvider) {
                    String key = String.format("_UI_%s_%s_feature", eReference.getEContainingClass().getName(), eReference.getName());
                    try {
                        result = editingDomainItemProvider.getString(key);
                    } catch (MissingResourceException mre) {
                        // Expected for dynamic instances.
                    }
                }
            } else {
                Adapter adapter = this.adapterFactory.adapt(eObject, IItemPropertySource.class);
                if (adapter instanceof IItemPropertySource itemPropertySource) {
                    IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eReference);
                    if (descriptor != null) {
                        result = descriptor.getDisplayName(eReference);
                    } else {
                        result = eReference.getName();
                    }
                }
            }
        } else if (self != null) {
            result = this.objectService.getLabel(self);
        }
        return result;
    }

    private List<String> getNodeImageURL(VariableManager variableManager) {
        List<String> result = List.of(CoreImageConstants.DEFAULT_SVG);
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof IncomingReferences) {
            result = List.of(INCOMING_REFERENCE_ICON_URL);
        } else if (self != null) {
            result = this.objectService.getImagePath(self);
        }
        return result;
    }

    private String getNodeKind(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof IncomingReferences) {
            result = INCOMING_REFERENCES_KIND;
        } else if (self != null) {
            result = this.objectService.getKind(self);
        }
        return result;
    }

    private boolean isNodeSelectable(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, EObject.class).isPresent();
    }

    private List<?> getIncomingChildren(VariableManager variableManager) {
        var self = variableManager.get(VariableManager.SELF, Object.class);
        var root = variableManager.get(TreeComponent.ROOT_VARIABLE, EObject.class);
        var ancestors = variableManager.get(TreeComponent.ANCESTORS_VARIABLE, List.class);
        if (root.isPresent() && self.isPresent() && ancestors.isPresent()) {
            return this.getIncomingChildren(self.get(), root.get(), ancestors.get());
        } else {
            return List.of();
        }
    }

    private List<?> getIncomingChildren(Object self, EObject root, List<?> ancestors) {
        List<?> result = List.of();
        if (ancestors.isEmpty()) {
            ECrossReferenceAdapter xref = ECrossReferenceAdapter.getCrossReferenceAdapter(root);
            if (xref != null) {
                var settings = xref.getInverseReferences(root).stream()
                        .sorted(Comparator.comparing(setting -> setting.getEStructuralFeature().getName()))
                        .toList();
                Map<EReference, List<EObject>> sourceByReference = new LinkedHashMap<>();
                for (EStructuralFeature.Setting setting : settings) {
                    sourceByReference.computeIfAbsent((EReference) setting.getEStructuralFeature(), ref -> new ArrayList<>()).add(setting.getEObject());
                }
                result = sourceByReference.entrySet().stream().map(entry -> new IncomingReferences(entry.getKey(), entry.getValue())).toList();
            }
        } else if (self instanceof IncomingReferences incomingReferences) {
            result = incomingReferences.sources();
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
