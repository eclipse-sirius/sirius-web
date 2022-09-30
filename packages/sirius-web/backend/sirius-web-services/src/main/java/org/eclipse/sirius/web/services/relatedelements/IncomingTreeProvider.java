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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.sirius.components.compatibility.emf.properties.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.TreeNode;
import org.eclipse.sirius.components.forms.components.TreeComponent;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Provides the definition of the tree widget for the "Incoming" panel in the "Related Elements" view.
 *
 * @author pcdavid
 */
public class IncomingTreeProvider {

    private static final String WIDGET_ID = "related/incoming"; //$NON-NLS-1$

    private static final String TITLE = "Incoming"; //$NON-NLS-1$

    private static final String WIDGET_ICON_URL = "/images/west_black_24dp.svg"; //$NON-NLS-1$

    private static final String INCOMING_REFERENCE_ICON_URL = "/images/west_black_24dp.svg"; //$NON-NLS-1$

    private static final String INCOMING_REFERENCES_KIND = "siriusWeb://category/incoming-references"; //$NON-NLS-1$

    private final IObjectService objectService;

    private final AdapterFactory adapterFactory;

    private final IPropertiesValidationProvider propertiesValidationProvider = new IPropertiesValidationProvider.NoOp();

    public IncomingTreeProvider(IObjectService objectService, AdapterFactory adapterFactory) {
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
                .childrenProvider(this::getIncomingChildren)
                .expandedNodeIdsProvider(this::collectAllNodeIds)
                .build();
        // @formatter:on
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
                // @formatter:off
                var settings = xref.getInverseReferences(root).stream()
                                   .sorted(Comparator.comparing(setting -> setting.getEStructuralFeature().getName()))
                                   .collect(Collectors.toList());
                Map<EReference, List<EObject>> sourceByReference = new LinkedHashMap<>();
                for (Setting setting : settings) {
                    sourceByReference.computeIfAbsent((EReference) setting.getEStructuralFeature(), ref -> new ArrayList<>()).add(setting.getEObject());
                }
                // @formatter:on
                result = sourceByReference.entrySet().stream().map(entry -> new IncomingReferences(entry.getKey(), entry.getValue())).collect(Collectors.toList());
            }
        } else if (self instanceof IncomingReferences) {
            result = ((IncomingReferences) self).getSources();
        }
        return result;
    }

    private String getNodeId(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof IncomingReferences) {
            result = "reference/" + ((IncomingReferences) self).getReference().getName(); //$NON-NLS-1$
        } else if (self != null) {
            result = this.objectService.getId(self);
        }
        return result;
    }

    private String getNodeLabel(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof IncomingReferences) {
            EReference eReference = ((IncomingReferences) self).getReference();
            EObject eObject = ((IncomingReferences) self).getSources().get(0);
            result = eReference.getName();
            if (eReference.isContainment()) {
                result = "owned " + result; //$NON-NLS-1$
                Adapter adapter = this.adapterFactory.adapt(eObject, IItemLabelProvider.class);
                if (adapter instanceof ItemProviderAdapter) {
                    ItemProviderAdapter editingDomainItemProvider = (ItemProviderAdapter) adapter;
                    String key = String.format("_UI_%s_%s_feature", eReference.getEContainingClass().getName(), eReference.getName()); //$NON-NLS-1$
                    result = editingDomainItemProvider.getString(key);
                }
            } else {
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
            }
        } else if (self != null) {
            result = this.objectService.getLabel(self);
        }
        return result;
    }

    private String getNodeImageURL(VariableManager variableManager) {
        String result = null;
        var self = variableManager.get(VariableManager.SELF, Object.class).orElse(null);
        if (self instanceof IncomingReferences) {
            result = INCOMING_REFERENCE_ICON_URL;
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
