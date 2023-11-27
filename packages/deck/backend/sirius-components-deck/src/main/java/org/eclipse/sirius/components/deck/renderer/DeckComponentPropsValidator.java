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
package org.eclipse.sirius.components.deck.renderer;

import org.eclipse.sirius.components.deck.renderer.component.CardComponent;
import org.eclipse.sirius.components.deck.renderer.component.CardComponentProps;
import org.eclipse.sirius.components.deck.renderer.component.DeckComponent;
import org.eclipse.sirius.components.deck.renderer.component.DeckComponentProps;
import org.eclipse.sirius.components.deck.renderer.component.LaneComponent;
import org.eclipse.sirius.components.deck.renderer.component.LaneComponentProps;
import org.eclipse.sirius.components.representations.IComponentPropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of a component type related to the Deck representation.
 *
 * @author fbarbin
 */
public class DeckComponentPropsValidator implements IComponentPropsValidator {

    @Override
    public boolean validateComponentProps(Class<?> componentType, IProps props) {
        boolean checkValidProps = false;
        if (DeckComponent.class.equals(componentType)) {
            checkValidProps = props instanceof DeckComponentProps;
        } else if (LaneComponent.class.equals(componentType)) {
            checkValidProps = props instanceof LaneComponentProps;
        } else if (CardComponent.class.equals(componentType)) {
            checkValidProps = props instanceof CardComponentProps;
        }
        return checkValidProps;
    }
}
