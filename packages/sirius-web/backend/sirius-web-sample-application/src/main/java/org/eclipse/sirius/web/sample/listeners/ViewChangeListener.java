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
package org.eclipse.sirius.web.sample.listeners;

import java.util.ArrayList;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.collaborative.dto.RepresentationRefreshedEvent;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * The view change listener that updates views and representation descriptions in an editing context.
 *
 * @author aresekb
 */
@Component
@ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
public class ViewChangeListener {

    private final Logger logger = LoggerFactory.getLogger(ViewChangeListener.class);

    private final IEditingContextSearchService editingContextSearchService;

    private final IObjectService objectService;

    private final IViewConverter viewConverter;

    public ViewChangeListener(IEditingContextSearchService editingContextSearchService, IObjectService objectService, IViewConverter viewConverter) {
        this.editingContextSearchService = editingContextSearchService;
        this.objectService = objectService;
        this.viewConverter = viewConverter;
    }

    @EventListener
    public synchronized void handleRepresentationRefreshedEvent(RepresentationRefreshedEvent event) {
        var editingContextId = event.getProjectId();
        var optionalEditingContext = this.editingContextSearchService.findById(editingContextId);
        if (optionalEditingContext.isEmpty()) {
            this.logger.warn("Editing context {} not found", editingContextId);
            return;
        }
        var editingContext = (EditingContext) optionalEditingContext.get();

        var targetObjectId = event.getRepresentation().getTargetObjectId();
        var optionalTargetObject = this.objectService.getObject(editingContext, targetObjectId);
        if (optionalTargetObject.isEmpty()) {
            return;
        }
        var targetObject = optionalTargetObject.get();

        if (targetObject instanceof EObject eObject) {
            var optionalView = EMFHelpers.findContainer(eObject, View.class);
            if (optionalView.isPresent()) {
                var view = optionalView.get();
                editingContext.getViews().put(this.objectService.getId(view), view);

                var packageRegistry = editingContext.getDomain().getResourceSet().getPackageRegistry();
                var accessibleEPackages = packageRegistry.values().stream()
                        .filter(EPackage.class::isInstance)
                        .map(EPackage.class::cast)
                        .toList();
                this.viewConverter.convert(new ArrayList<>(editingContext.getViews().values()), accessibleEPackages).stream()
                        .filter(Objects::nonNull)
                        .forEach(representationDescription -> editingContext.getRepresentationDescriptions().put(representationDescription.getId(), representationDescription));
            }
        }
    }
}
