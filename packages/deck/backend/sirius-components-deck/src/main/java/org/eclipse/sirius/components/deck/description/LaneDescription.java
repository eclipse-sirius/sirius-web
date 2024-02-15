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
package org.eclipse.sirius.components.deck.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.PublicApi;
import org.eclipse.sirius.components.deck.DeckElementStyle;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The Lane description to represent Lane elements.
 *
 * @author fbarbin
 */
@PublicApi
public record LaneDescription(String id, Function<VariableManager, String> targetObjectKindProvider, Function<VariableManager, String> targetObjectLabelProvider,
        Function<VariableManager, String> targetObjectIdProvider, Function<VariableManager, List<Object>> semanticElementsProvider, Function<VariableManager, String> titleProvider,
        Function<VariableManager, String> labelProvider, List<CardDescription> cardDescriptions, Consumer<VariableManager> editLaneProvider, Consumer<VariableManager> createCardProvider,
        Consumer<VariableManager> dropCardProvider, Function<VariableManager, Boolean> collapsibleProvider, Function<VariableManager, DeckElementStyle> styleProvider) {

    public static final String OLD_LANE = "oldLane";

    public static final String OLD_LANE_TARGET = "oldLaneTarget";

    public static final String NEW_LANE = "newLane";

    public static final String INDEX = "index";

    public static final String NEW_LANE_TARGET = "newLaneTarget";

    public LaneDescription {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectKindProvider);
        Objects.requireNonNull(targetObjectLabelProvider);
        Objects.requireNonNull(targetObjectIdProvider);
        Objects.requireNonNull(semanticElementsProvider);
        Objects.requireNonNull(titleProvider);
        Objects.requireNonNull(labelProvider);
        Objects.requireNonNull(cardDescriptions);
        Objects.requireNonNull(editLaneProvider);
        Objects.requireNonNull(createCardProvider);
        Objects.requireNonNull(dropCardProvider);
        Objects.requireNonNull(collapsibleProvider);
        Objects.requireNonNull(styleProvider);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }
}
