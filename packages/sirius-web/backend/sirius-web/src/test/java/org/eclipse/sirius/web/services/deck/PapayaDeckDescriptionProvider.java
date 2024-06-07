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
package org.eclipse.sirius.web.services.deck;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.DeckBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.deck.CreateCardTool;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.emf.deck.IDeckIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based deck description for tests.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class PapayaDeckDescriptionProvider implements IEditingContextProcessor {

    private final IDeckIdProvider deckIdProvider;

    private final View view;

    private DeckDescription deckDescription;

    private CreateCardTool createToDoCardTool;

    public PapayaDeckDescriptionProvider(IDeckIdProvider deckIdProvider) {
        this.deckIdProvider = Objects.requireNonNull(deckIdProvider);
        this.view = this.createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    public String getRepresentationDescriptionId() {
        return this.deckIdProvider.getId(this.deckDescription);
    }

    public String getCreateTodoCardToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.createToDoCardTool).toString().getBytes()).toString();
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View deckView = viewBuilder.build();
        deckView.getDescriptions().add(this.createDeckDescription());

        deckView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("DeckDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("DeckDescription"));
        resource.getContents().add(deckView);

        return deckView;
    }

    private DeckDescription createDeckDescription() {
        var toDoCardDescription = new DeckBuilders().newCardDescription()
                .name("To do Card")
                .titleExpression("aql:self.name")
                .semanticCandidatesExpression("aql:self.tasks->select(task | not task.done)")
                .build();

        this.createToDoCardTool = new DeckBuilders()
                .newCreateCardTool()
                .name("New card")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newCreateInstance()
                                                .referenceName("tasks")
                                                .typeName("papaya::Task")
                                                .variableName("newTask")
                                                .build()
                                )
                                .build()
                )
                .build();

        var toDoLaneDescription = new DeckBuilders().newLaneDescription()
                .name("To do Lane")
                .titleExpression("To do")
                .semanticCandidatesExpression("aql:self")
                .ownedCardDescriptions(toDoCardDescription)
                .createTool(this.createToDoCardTool)
                .build();

        var doneCardDescription = new DeckBuilders().newCardDescription()
                .name("Done Card")
                .titleExpression("aql:self.name")
                .semanticCandidatesExpression("aql:self.tasks->select(task | task.done)")
                .build();

        var doneLaneDescription = new DeckBuilders().newLaneDescription()
                .name("Done Lane")
                .titleExpression("Done")
                .semanticCandidatesExpression("aql:self")
                .ownedCardDescriptions(doneCardDescription)
                .build();

        this.deckDescription = new DeckBuilders().newDeckDescription()
                .name("Deck")
                .titleExpression("aql:'Deck'")
                .domainType("papaya:Project")
                .laneDescriptions(toDoLaneDescription, doneLaneDescription)
                .build();

        return this.deckDescription;
    }
}
