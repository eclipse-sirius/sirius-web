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
package org.eclipse.sirius.components.task.starter.configuration.view;

import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.Let;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.DeckBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.CreateCardTool;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.DeleteCardTool;
import org.eclipse.sirius.components.view.deck.EditCardTool;
import org.eclipse.sirius.components.view.deck.LaneDescription;

/**
 * Builder of the "Deck" view description.
 *
 * @author fbarbin
 */
public class ViewDeckDescriptionBuilder {

    public static final String DECK_REP_DESC_NAME = "Deck Daily Representation";

    private final DeckBuilders deckBuilders;

    private final ViewBuilders viewBuilders;

    public ViewDeckDescriptionBuilder() {
        this.deckBuilders = new DeckBuilders();
        this.viewBuilders = new ViewBuilders();
    }

    public void addRepresentationDescription(View view) {
        DeckDescription deckDescription = this.createDeckDescription();
        LaneDescription laneDescription = this.createLaneDescription();
        CardDescription cardDescription = this.createCardDescription();
        deckDescription.getLaneDescriptions().add(laneDescription);
        laneDescription.getOwnedCardDescriptions().add(cardDescription);
        view.getDescriptions().add(deckDescription);
    }

    private CardDescription createCardDescription() {
        EditCardTool editCardTool = this.createEditCardTool();
        DeleteCardTool deleteCardTool = this.createDeleteCardTool();
        return this.deckBuilders.newCardDescription()
                .semanticCandidatesExpression("aql:self.getTasksWithTag()")
                .titleExpression("aql:self.name")
                .labelExpression("'30 min'")
                .descriptionExpression("aql:self.description")
                .editTool(editCardTool)
                .deleteTool(deleteCardTool)
                .build();
    }

    private LaneDescription createLaneDescription() {
        CreateCardTool createCardTool = this.createCardTool();
        return this.deckBuilders.newLaneDescription()
                .semanticCandidatesExpression("aql:self.ownedTags->select(tag | tag.prefix == 'daily')")
                .labelExpression("aql:self.getTasksWithTag()->size() + ' / ' + self.eContainer().oclAsType(task::Project).ownedTasks->select(task | task.tags->exists(tag | tag.prefix == 'daily'))->size()")
                .titleExpression("aql:self.suffix")
                .createTool(createCardTool)
                .build();
    }

    private DeckDescription createDeckDescription() {
        return this.deckBuilders.newDeckDescription()
                .name(DECK_REP_DESC_NAME)
                .domainType("task::Project")
                .titleExpression("New Daily Representation")
                .build();
    }

    private CreateCardTool createCardTool() {

        SetValue setNameValue = this.viewBuilders.newSetValue()
                .featureName("name")
                .valueExpression("aql:title")
                .build();

        SetValue setDescriptionValue = this.viewBuilders.newSetValue()
                .featureName("description")
                .valueExpression("aql:description")
                .build();

        SetValue setTagValue = this.viewBuilders.newSetValue()
                .featureName("tags")
                .valueExpression("aql:Sequence{tag}")
                .build();

        ChangeContext newInstanceChangeContext = this.viewBuilders.newChangeContext()
                .expression("aql:newInstance")
                .children(setNameValue, setDescriptionValue, setTagValue)
                .build();

        CreateInstance createInstance = this.viewBuilders.newCreateInstance()
                .typeName("task::Task")
                .referenceName("ownedTasks")
                .children(newInstanceChangeContext)
                .build();

        ChangeContext projectChangeContext = this.viewBuilders.newChangeContext()
                .expression("aql:self.eContainer().oclAsType(task::Project)")
                .children(createInstance)
                .build();

        Let let = this.viewBuilders.newLet()
                .variableName("tag")
                .valueExpression("aql:self")
                .children(projectChangeContext)
                .build();

        return this.deckBuilders.newCreateCardTool()
                .body(let)
                .build();
    }

    private EditCardTool createEditCardTool() {

        SetValue setNameValue = this.viewBuilders.newSetValue()
                .featureName("name")
                .valueExpression("aql:newTitle")
                .build();

        SetValue setDescriptionValue = this.viewBuilders.newSetValue()
                .featureName("description")
                .valueExpression("aql:newDescription")
                .build();

        return this.deckBuilders.newEditCardTool()
                .body(setNameValue, setDescriptionValue)
                .build();
    }

    private DeleteCardTool createDeleteCardTool() {
        DeleteElement deleteElement = this.viewBuilders.newDeleteElement()
                .build();
        return this.deckBuilders.newDeleteCardTool()
                .body(deleteElement)
                .build();

    }

}
