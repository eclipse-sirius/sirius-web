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

import org.eclipse.sirius.components.deck.description.LaneDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

import java.util.Objects;

/**
 * The props of the lane component.
 *
 * @author fbarbin
 */
public record LaneComponentProps(VariableManager variableManager, LaneDescription laneDescription, String parentElementId) implements IProps {

    public LaneComponentProps {
        Objects.requireNonNull(variableManager);
        Objects.requireNonNull(laneDescription);
        Objects.requireNonNull(parentElementId);
    }
}
