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
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.If;
import org.eclipse.sirius.components.view.Let;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.DeckBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.CardDropTool;
import org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.CreateCardTool;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.DeleteCardTool;
import org.eclipse.sirius.components.view.deck.EditCardTool;
import org.eclipse.sirius.components.view.deck.EditLaneTool;
import org.eclipse.sirius.components.view.deck.LaneDescription;
import org.eclipse.sirius.components.view.deck.LaneDropTool;

/**
 * Builder of the "Deck" view description.
 *
 * @author fbarbin
 */
public class ViewDeckDescriptionBuilder {

    private static final String AQL_NEW_TITLE = "aql:newTitle";

    private static final String TAGS = "tags";

    private static final String TAG_PREFIX_DAILY = "daily";

    private static final String TAG_PREFIX_KANBAN = "kanban";

    private static final String NAME = "name";

    private static final String DESCRIPTION = "description";

    private static final String AQL_SELF = "aql:self";

    private static final String AQL_SELF_NAME = "aql:self.name";

    private static final String AQL_SELF_DESCRIPTION = "aql:self.description";

    private final DeckBuilders deckBuilders;

    private final ViewBuilders viewBuilders;

    public ViewDeckDescriptionBuilder() {
        this.deckBuilders = new DeckBuilders();
        this.viewBuilders = new ViewBuilders();
    }

    public void addRepresentationDescriptions(View view) {
        view.getColorPalettes().add(this.createColorPalette());

        this.createDailyDeckDescription(view);
        this.createOKRDeckDescription(view);
        this.createKanbanDeckDescription(view);
    }

    private void createDailyDeckDescription(View view) {
        DeckDescription deckDescription = this.createDeckDescription(TAG_PREFIX_DAILY);
        LaneDescription laneDescription = this.createLaneDescription(TAG_PREFIX_DAILY);
        CardDescription cardDescription = this.createCardDescription(view, TAG_PREFIX_DAILY);
        deckDescription.getLaneDescriptions().add(laneDescription);
        laneDescription.getOwnedCardDescriptions().add(cardDescription);
        view.getDescriptions().add(deckDescription);
    }

    private void createKanbanDeckDescription(View view) {
        DeckDescription deckDescription = this.createDeckDescription(TAG_PREFIX_KANBAN);
        LaneDescription laneDescription = this.createLaneDescription(TAG_PREFIX_KANBAN);
        CardDescription cardDescription = this.createCardDescription(view, TAG_PREFIX_KANBAN);
        deckDescription.getLaneDescriptions().add(laneDescription);
        laneDescription.getOwnedCardDescriptions().add(cardDescription);
        view.getDescriptions().add(deckDescription);
    }

    private void createOKRDeckDescription(View view) {
        DeckDescription deckDescription = this.deckBuilders.newDeckDescription()
                .name("Deck OKR Representation")
                .domainType("task::Project")
                .titleExpression("New OKR Representation")
                .laneDescriptions(this.createObjectiveLaneDescription())
                .laneDropTool(this.createObjectiveLaneDropTool())
                .build();

        view.getDescriptions().add(deckDescription);
    }

    private CardDescription createCardDescription(View view, String tagPrefix) {
        EditCardTool editCardTool = this.createEditCardTool();
        DeleteCardTool deleteCardTool = this.createDeleteCardTool();

        ConditionalDeckElementDescriptionStyle conditionStyleForTaskWithSubTasks = this.deckBuilders.newConditionalDeckElementDescriptionStyle()
            .condition(String.format("aql:self.subTasks->select(task | task.tags->exists(tag | tag.prefix == '%s'))->size()>0", tagPrefix))
            .backgroundColor(view.getColorPalettes().get(0).getColors().get(0))
            .build();

        ConditionalDeckElementDescriptionStyle conditionStyleForTaskWithParentWithoutSameTag = this.deckBuilders.newConditionalDeckElementDescriptionStyle()
            .condition(String.format("aql:if self.eContainer().oclIsKindOf(task::AbstractTask) then not self.eContainer().tags->exists(tag | tag.prefix == '%s') else false endif", tagPrefix))
            .backgroundColor(view.getColorPalettes().get(0).getColors().get(1))
            .build();

        return this.deckBuilders.newCardDescription()
            .name("Card Description")
            .semanticCandidatesExpression("aql:self.getTasksWithTag()")
            .titleExpression(AQL_SELF_NAME)
            .labelExpression("aql:self.computeTaskDurationDays()")
            .descriptionExpression(AQL_SELF_DESCRIPTION)
            .editTool(editCardTool)
            .deleteTool(deleteCardTool)
            .conditionalStyles(conditionStyleForTaskWithSubTasks, conditionStyleForTaskWithParentWithoutSameTag)
            .build();
    }

    private ColorPalette createColorPalette() {
        return this.viewBuilders.newColorPalette()
            .colors(this.createFixedColor("Deck_LightYellow", "#f7efb1"),
                    this.createFixedColor("Deck_LightRed", "#f7b1bc"))
            .build();
    }

    private FixedColor createFixedColor(String name, String value) {
        return this.viewBuilders
            .newFixedColor()
            .name(value)
            .value(value)
            .build();
    }

    private LaneDescription createLaneDescription(String prefix) {
        CreateCardTool createCardTool = this.createTaskCardTool();
        CardDropTool cardDropTool = this.createTaskInTagCardDropTool();
        EditLaneTool editLaneTool = this.createEditTagLaneTool();
        return this.deckBuilders.newLaneDescription()
                .name("Lane Description")
                .semanticCandidatesExpression(String.format("aql:self.ownedTags->select(tag | tag.prefix == '%s')", prefix))
                .labelExpression(String.format(
                        "aql:self.getTasksWithTag()->size() + ' / ' + self.eContainer().oclAsType(task::Project).ownedTasks->select(task | task.tags->exists(tag | tag.prefix == '%s'))->size()",
                        prefix))
                .titleExpression("aql:self.suffix")
                .createTool(createCardTool)
                .cardDropTool(cardDropTool)
                .editTool(editLaneTool)
                .build();
    }

    private LaneDescription createObjectiveLaneDescription() {
        EditCardTool editCardTool = this.createEditCardTool();
        DeleteCardTool deleteCardTool = this.createDeleteCardTool();

        CardDescription cardDescription =  this.deckBuilders.newCardDescription()
                .name("Key Result Cards")
                .semanticCandidatesExpression("aql:self.ownedKeyResults")
                .titleExpression(AQL_SELF_NAME)
                .labelExpression("aql:self.subTasks->size() + ' owned tasks'")
                .descriptionExpression(AQL_SELF_DESCRIPTION)
                .editTool(editCardTool)
                .deleteTool(deleteCardTool)
                .build();

        return this.deckBuilders.newLaneDescription()
                .name("Objective Lanes")
                .semanticCandidatesExpression("aql:self.ownedObjectives")
                .labelExpression("")
                .titleExpression(AQL_SELF_NAME)
                .ownedCardDescriptions(cardDescription)
                .cardDropTool(this.createKeyResultCardDropTool())
                .editTool(this.createEditObjectiveLaneTool())
                .createTool(this.createObjectiveCardTool())
                .build();
    }

    private EditLaneTool createEditObjectiveLaneTool() {
        SetValue setValue = this.viewBuilders.newSetValue()
                .featureName(NAME)
                .valueExpression(AQL_NEW_TITLE)
                .build();

        return this.deckBuilders.newEditLaneTool()
                .name("Edit Objective Title")
                .body(setValue)
                .build();
    }

    private EditLaneTool createEditTagLaneTool() {
        SetValue setValue = this.viewBuilders.newSetValue()
                .featureName("suffix")
                .valueExpression(AQL_NEW_TITLE)
                .build();

        return this.deckBuilders.newEditLaneTool()
                .name("Edit Lane Title")
                .body(setValue)
                .build();
    }

    private DeckDescription createDeckDescription(String prefix) {
        LaneDropTool laneDropTool = this.createTagLaneDropTool();
        String representationTypeName = prefix.substring(0, 1).toUpperCase() + prefix.substring(1);
        return this.deckBuilders.newDeckDescription()
                .name(String.format("Deck %s Representation", representationTypeName))
                .domainType("task::Project")
                .titleExpression(String.format("New %s Representation", representationTypeName))
                .laneDropTool(laneDropTool)
                .build();
    }

    private LaneDropTool createTagLaneDropTool() {
        ChangeContext changeContext = this.viewBuilders.newChangeContext()
                .expression("aql:self.moveTagAtIndex(index)")
                .build();

        return this.deckBuilders.newLaneDropTool()
                .name("Lane Drop Tool")
                .body(changeContext)
                .build();
    }

    private CreateCardTool createTaskCardTool() {

        SetValue setNameValue = this.viewBuilders.newSetValue()
                .featureName(NAME)
                .valueExpression("aql:title")
                .build();

        SetValue setDescriptionValue = this.viewBuilders.newSetValue()
                .featureName(DESCRIPTION)
                .valueExpression("aql:description")
                .build();

        SetValue setTagValue = this.viewBuilders.newSetValue()
                .featureName(TAGS)
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
                .valueExpression(AQL_SELF)
                .children(projectChangeContext)
                .build();

        return this.deckBuilders.newCreateCardTool()
                .name("Create Card Tool")
                .body(let)
                .build();
    }

    private CreateCardTool createObjectiveCardTool() {

        SetValue setNameValue = this.viewBuilders.newSetValue()
                .featureName(NAME)
                .valueExpression("aql:title")
                .build();

        SetValue setDescriptionValue = this.viewBuilders.newSetValue()
                .featureName(DESCRIPTION)
                .valueExpression("aql:description")
                .build();

        ChangeContext newInstanceChangeContext = this.viewBuilders.newChangeContext()
                .expression("aql:newInstance")
                .children(setNameValue, setDescriptionValue)
                .build();

        CreateInstance createInstance = this.viewBuilders.newCreateInstance()
                .typeName("task::KeyResult")
                .referenceName("ownedKeyResults")
                .children(newInstanceChangeContext)
                .build();

        return this.deckBuilders.newCreateCardTool()
                .name("Create KeyResult Tool")
                .body(createInstance)
                .build();
    }

    private EditCardTool createEditCardTool() {

        SetValue setNameValue = this.viewBuilders.newSetValue()
                .featureName(NAME)
                .valueExpression(AQL_NEW_TITLE)
                .build();

        SetValue setDescriptionValue = this.viewBuilders.newSetValue()
                .featureName(DESCRIPTION)
                .valueExpression("aql:newDescription")
                .build();

        return this.deckBuilders.newEditCardTool()
                .name("Edit Card Tool")
                .body(setNameValue, setDescriptionValue)
                .build();
    }

    private CardDropTool createTaskInTagCardDropTool() {
        UnsetValue removeFromOldLane = this.viewBuilders.newUnsetValue()
                .featureName(TAGS)
                .elementExpression("aql:Sequence{oldLaneTarget}")
                .build();

        SetValue addToNewLane = this.viewBuilders.newSetValue()
                .featureName(TAGS)
                .valueExpression("aql:Sequence{newLaneTarget}")
                .build();

        If ifOperation = this.viewBuilders.newIf()
                .conditionExpression("aql:oldLaneTarget != newLaneTarget")
                .children(removeFromOldLane, addToNewLane)
                .build();

        ChangeContext changeContext = this.viewBuilders.newChangeContext()
                .expression("aql:self.moveTaskInTag(index, newLaneTarget)")
                .children(ifOperation)
                .build();

        return this.deckBuilders.newCardDropTool()
                .name("Card Drop Tool")
                .body(changeContext)
                .build();
    }

    private LaneDropTool createObjectiveLaneDropTool() {
        ChangeContext changeContext = this.viewBuilders.newChangeContext()
                .expression("aql:self.moveObjectiveAtIndex(index)")
                .build();

        return this.deckBuilders.newLaneDropTool()
                .name("Objective Drop Tool")
                .body(changeContext)
                .build();
    }

    private CardDropTool createKeyResultCardDropTool() {
        ChangeContext changeContext = this.viewBuilders.newChangeContext()
                .expression("aql:self.moveKeyResultIntoObjective(newLaneTarget, index)")
                .build();

        return this.deckBuilders.newCardDropTool()
                .name("Key Result Drop Tool")
                .body(changeContext)
                .build();
    }

    private DeleteCardTool createDeleteCardTool() {
        DeleteElement deleteElement = this.viewBuilders.newDeleteElement()
                .build();
        return this.deckBuilders.newDeleteCardTool()
                .name("Delete Card Tool")
                .body(deleteElement)
                .build();
    }
}
