/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.flow.starter.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Test used to validate the action handled by EditingContextActionHandler.
 *
 * @author frouene
 */
public class FlowEditingContextActionHandlerTests {

    @ParameterizedTest
    @ValueSource(strings = { "empty_flow", "robot_flow", "big_guy_flow" })
    void testCanHandle(String actionId) {
        FlowEditingContextActionHandler handler = new FlowEditingContextActionHandler();
        assertThat(handler.canHandle(null, actionId)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "empty", "", "wrong_action", "other", "EMPTY", "empty_domain", "papaya_domain", "empty_view", "papaya_view" })
    void testCanNotHandle(String actionId) {
        FlowEditingContextActionHandler handler = new FlowEditingContextActionHandler();
        assertThat(handler.canHandle(null, actionId)).isFalse();
    }
}
