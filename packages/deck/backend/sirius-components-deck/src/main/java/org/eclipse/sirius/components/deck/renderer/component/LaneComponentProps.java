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
package org.eclipse.sirius.components.deck.renderer.component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.deck.Lane;
import org.eclipse.sirius.components.deck.description.LaneDescription;
import org.eclipse.sirius.components.deck.renderer.events.IDeckEvent;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The props of the lane component.
 *
 * @author fbarbin
 */
public record LaneComponentProps(VariableManager variableManager, LaneDescription laneDescription, String parentElementId, List<Lane> previousLanes, Optional<IDeckEvent> optionalDeckEvent) implements IProps {

    public LaneComponentProps {
        Objects.requireNonNull(variableManager);
        Objects.requireNonNull(laneDescription);
        Objects.requireNonNull(parentElementId);
        Objects.requireNonNull(previousLanes);
        Objects.requireNonNull(optionalDeckEvent);
    }
}
