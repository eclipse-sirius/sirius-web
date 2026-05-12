/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.components.view.emf.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.api.IKeyBindingsProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.view.KeyBinding;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.springframework.stereotype.Service;

/**
 * Service providing the diagram keyBindings value for DiagramDescription based on the View DSL.
 *
 * @author pcdavid
 */
@Service
public class ViewKeyBindingsProvider implements IKeyBindingsProvider {

    private final ViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    public ViewKeyBindingsProvider(ViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService,
            ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate) {
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public List<String> getKeyBindings(IEditingContext editingContext, DiagramDescription diagramDescription) {
        var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
        if (optionalDiagramDescription.isPresent()) {
            org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            return this.findAllKeyBindings(viewDiagramDescription);
        }
        return List.of();
    }

    private List<String> findAllKeyBindings(org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription) {
        List<String> result = new ArrayList<>();
        viewDiagramDescription.eAllContents().forEachRemaining(o -> {
            if (o instanceof KeyBinding binding) {
                var key = binding.getKey();
                if (binding.isMeta()) {
                    key = "Meta+" + key;
                }
                if (binding.isAlt()) {
                    key = "Alt+" + key;
                }
                if (binding.isCtrl()) {
                    key = "Ctrl+" + key;
                }
                result.add(key);
            }
        });
        return result;
    }
}
