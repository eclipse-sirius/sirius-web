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
package org.eclipse.sirius.web.application.studio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.domain.emf.DomainConverter;
import org.eclipse.sirius.components.emf.services.EditingContextCrossReferenceAdapter;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IDocumentToResourceService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to contribute the domain and view models found to the editing context.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextInitializer implements IEditingContextProcessor {

    private final ISemanticDataSearchService semanticDataSearchService;

    private final IDocumentToResourceService documentToResourceService;

    public EditingContextInitializer(ISemanticDataSearchService semanticDataSearchService, IDocumentToResourceService documentToResourceService) {
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.documentToResourceService = Objects.requireNonNull(documentToResourceService);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            List<Domain> domains = new ArrayList<>();
            List<View> views = new ArrayList<>();

            var allSemanticData = this.semanticDataSearchService.findAllByDomains(List.of(DomainPackage.eNS_URI, ViewPackage.eNS_URI));
            for (var semanticData: allSemanticData) {
                ResourceSet resourceSet = new ResourceSetImpl();

                resourceSet.getPackageRegistry().put(DomainPackage.eNS_URI, DomainPackage.eINSTANCE);
                resourceSet.getPackageRegistry().put(ViewPackage.eNS_URI, ViewPackage.eINSTANCE);
                resourceSet.getPackageRegistry().put(DeckPackage.eNS_URI, DeckPackage.eINSTANCE);
                resourceSet.getPackageRegistry().put(DiagramPackage.eNS_URI, DiagramPackage.eINSTANCE);
                resourceSet.getPackageRegistry().put(FormPackage.eNS_URI, FormPackage.eINSTANCE);
                resourceSet.getPackageRegistry().put(GanttPackage.eNS_URI, GanttPackage.eINSTANCE);

                semanticData.getDocuments().forEach(document -> this.documentToResourceService.toResource(resourceSet, document));
                resourceSet.eAdapters().add(new EditingContextCrossReferenceAdapter());

                var treeIterator = resourceSet.getAllContents();
                while (treeIterator.hasNext()) {
                    var next = treeIterator.next();
                    if (next instanceof View view) {
                        views.add(view);
                        treeIterator.prune();
                    } else if (next instanceof Domain domain) {
                        domains.add(domain);
                        treeIterator.prune();
                    }
                }
            }

            siriusWebEditingContext.getViews().addAll(views);

            var resourceSet = siriusWebEditingContext.getDomain().getResourceSet();
            new DomainConverter().convert(domains).forEach(ePackage -> resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage));
        }
    }
}
