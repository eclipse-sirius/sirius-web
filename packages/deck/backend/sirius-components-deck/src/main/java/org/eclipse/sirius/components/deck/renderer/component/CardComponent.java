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

import org.eclipse.sirius.components.deck.description.CardDescription;
import org.eclipse.sirius.components.deck.renderer.elements.CardElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render cards.
 *
 * @author fbarbin
 */
public class CardComponent implements IComponent {

    private final CardComponentProps props;

    public CardComponent(CardComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        CardDescription cardDescription = this.props.cardDescription();

        List<Element> children = new ArrayList<>();

        List<Object> semanticElements = cardDescription.semanticElementsProvider().apply(variableManager);
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
        CardDescription cardDescription = this.props.cardDescription();
        String targetObjectId = cardDescription.targetObjectIdProvider().apply(childVariableManager);
        String targetObjectKind = cardDescription.targetObjectKindProvider().apply(childVariableManager);
        String targetObjectLabel = cardDescription.targetObjectLabelProvider().apply(childVariableManager);
        String title = cardDescription.titleProvider().apply(childVariableManager);
        String label = cardDescription.labelProvider().apply(childVariableManager);
        String description = cardDescription.descriptionProvider().apply(childVariableManager);

        CardElementProps cardElementProps = new CardElementProps(UUID.randomUUID().toString(), cardDescription.id(), targetObjectId, targetObjectKind, targetObjectLabel, title, label, description);
        return new Element(CardElementProps.TYPE, cardElementProps);
    }
}
