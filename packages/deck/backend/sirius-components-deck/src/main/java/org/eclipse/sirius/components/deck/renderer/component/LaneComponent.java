/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.deck.renderer.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.deck.description.LaneDescription;
import org.eclipse.sirius.components.deck.renderer.elements.LaneElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render lanes.
 *
 * @author fbarbin
 */
public class LaneComponent implements IComponent {

    private final LaneComponentProps props;

    public LaneComponent(LaneComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        LaneDescription laneDescription = this.props.laneDescription();

        List<Element> children = new ArrayList<>();

        List<Object> semanticElements = laneDescription.semanticElementsProvider().apply(variableManager);
        for (Object semanticElement : semanticElements) {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, semanticElement);
            Element nodeElement = this.doRender(childVariableManager);
            children.add(nodeElement);
        }
        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private Element doRender(VariableManager childVariableManager) {

        LaneDescription laneDescription = this.props.laneDescription();
        String laneId = UUID.randomUUID().toString();
        List<Element> childrenElements = this.getChildren(childVariableManager, laneDescription, laneId);
        String targetObjectId = laneDescription.targetObjectIdProvider().apply(childVariableManager);
        String targetObjectKind = laneDescription.targetObjectKindProvider().apply(childVariableManager);
        String targetObjectLabel = laneDescription.targetObjectLabelProvider().apply(childVariableManager);
        String title = laneDescription.titleProvider().apply(childVariableManager);
        String label = laneDescription.labelProvider().apply(childVariableManager);

        LaneElementProps laneElementProps = new LaneElementProps(laneId, laneDescription.id(), targetObjectId, targetObjectKind, targetObjectLabel, title, label, childrenElements);
        return new Element(LaneElementProps.TYPE, laneElementProps);
    }

    private List<Element> getChildren(VariableManager variableManager, LaneDescription laneDescription, String laneId) {
        return laneDescription.cardDescriptions()//
                .stream()//
                .map(cardDescription -> {
                    CardComponentProps cardComponentProps = new CardComponentProps(variableManager, cardDescription, laneId);
                    return new Element(CardComponent.class, cardComponentProps);
                })//
                .toList();

    }

}
