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
package org.eclipse.sirius.web.application.studio.services.representations;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.tree.TreeBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.emf.tree.ITreeIdProvider;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Used to provide the view tree description for Domain elements using tree DSL builders.
 *
 * @author Jerome Gout
 */
@Service
public class DomainViewTreeDescriptionProvider implements IEditingContextProcessor {

    public static final String DOMAIN_EXPLORER_DESCRIPTION_NAME = "Domain explorer by DSL";

    private static final String DOMAIN_VIEW_EXPLORER_ID = "DomainExplorer";

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    private final View view;

    private final ITreeIdProvider treeIdProvider;

    private TreeDescription viewDescription;

    public DomainViewTreeDescriptionProvider(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate, ITreeIdProvider treeIdProvider) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
        this.treeIdProvider = Objects.requireNonNull(treeIdProvider);
        this.view = this.createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext && this.studioCapableEditingContextPredicate.test(editingContext)) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    private View createView() {
        var domainExplorerDescription = this.domainExplorerDescription();

        var domainView = new ViewBuilders()
                .newView()
                .descriptions(domainExplorerDescription)
                .build();

        domainView.eAllContents().forEachRemaining(eObject -> {
            var id = UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());
            eObject.eAdapters().add(new IDAdapter(id));
        });

        String resourcePath = UUID.nameUUIDFromBytes(DOMAIN_VIEW_EXPLORER_ID.getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter(DOMAIN_VIEW_EXPLORER_ID));
        resource.getContents().add(domainView);

        return domainView;
    }

    public String getRepresentationDescriptionId() {
        return this.treeIdProvider.getId(this.viewDescription);
    }

    private TreeDescription domainExplorerDescription() {
        this.viewDescription = new TreeBuilders()
                .newTreeDescription()
                .name(DOMAIN_EXPLORER_DESCRIPTION_NAME)
                .domainType("domain::Domain")
                .titleExpression("aql:'Domain explorer DSL'")
                .kindExpression("aql:self.getKind()")
                .iconURLExpression("aql:self.getIconURL()")
                .deletableExpression("aql:self.isDeletable()")
                .editableExpression("aql:self.isEditable()")
                .selectableExpression("aql:self.isSelectable()")
                .hasChildrenExpression("aql:self.hasChildren()")
                .elementsExpression("aql:editingContext.getElements()")
                .childrenExpression("aql:self.getChildren(editingContext, expanded)")
                .parentExpression("aql:self.getParent(editingContext, id)")
                .treeItemLabelExpression("aql:self.getTreeItemLabel()")
                .treeItemIdExpression("aql:self.getTreeItemId()")
                .treeItemObjectExpression("aql:editingContext.getTreeItemObject(id)")
                .preconditionExpression("aql:false") // -> set canCreate to false to avoid to be display in New representation menu
                .build();
        return this.viewDescription;
    }
}
