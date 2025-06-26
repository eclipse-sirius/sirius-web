/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.trees.tests;

import static org.assertj.core.api.Assertions.fail;

import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.trees.Tree;

/**
 * Used to handle the tree event payload.
 *
 * @author sbegaudeau
 */
public class TreeEventPayloadConsumer {
    public static Consumer<Object> assertRefreshedTreeThat(Consumer<Tree> consumer) {
        return object -> Optional.of(object)
                .filter(TreeRefreshedEventPayload.class::isInstance)
                .map(TreeRefreshedEventPayload.class::cast)
                .map(TreeRefreshedEventPayload::tree)
                .ifPresentOrElse(consumer, () -> fail("Missing tree"));
    }
}
