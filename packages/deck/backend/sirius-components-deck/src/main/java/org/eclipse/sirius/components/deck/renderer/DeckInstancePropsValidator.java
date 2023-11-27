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

import org.eclipse.sirius.components.deck.renderer.elements.CardElementProps;
import org.eclipse.sirius.components.deck.renderer.elements.DeckElementProps;
import org.eclipse.sirius.components.deck.renderer.elements.LaneElementProps;
import org.eclipse.sirius.components.representations.IInstancePropsValidator;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to validate the properties of the instance types related to the Deck representation.
 *
 * @author fbarbin
 */
public class DeckInstancePropsValidator implements IInstancePropsValidator {

    @Override
    public boolean validateInstanceProps(String type, IProps props) {
        boolean checkValidProps = false;

        if (DeckElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof DeckElementProps;
        } else if (LaneElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof LaneElementProps;
        } else if (CardElementProps.TYPE.equals(type)) {
            checkValidProps = props instanceof CardElementProps;
        }
        return checkValidProps;
    }

}
