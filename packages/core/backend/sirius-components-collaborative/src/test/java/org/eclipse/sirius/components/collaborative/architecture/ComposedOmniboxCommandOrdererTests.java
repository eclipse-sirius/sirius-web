/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.architecture;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.sirius.components.collaborative.omnibox.ComposedOmniboxCommandOrderer;
import org.eclipse.sirius.components.collaborative.omnibox.api.IDefaultOmniboxCommandOrderer;
import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandOrdererDelegate;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the default {@link org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandOrderer} implementation.
 *
 * @author gdaniel
 */
public class ComposedOmniboxCommandOrdererTests {

    @Test
    @DisplayName("Given an omnibox command orderer delegate, when commands are ordered, then the delegate is used")
    public void givenOmniboxCommandOrdererDelegateWhenCommandsAreOrderedThenTheDelegateIsUsed() {
        var delegateCommand = new OmniboxCommand("delegate", "Delegate", List.of(), "");
        IOmniboxCommandOrdererDelegate omniboxCommandOrdererDelegate = new IOmniboxCommandOrdererDelegate.NoOp() {

            @Override
            public boolean canHandle(List<OmniboxCommand> omniboxCommands) {
                return true;
            }

            @Override
            public List<OmniboxCommand> order(List<OmniboxCommand> omniboxCommands) {
                return List.of(delegateCommand);
            }
        };

        IDefaultOmniboxCommandOrderer defaultOmniboxCommandOrderer = new IDefaultOmniboxCommandOrderer.NoOp();

        ComposedOmniboxCommandOrderer composedOmniboxCommandOrderer = new ComposedOmniboxCommandOrderer(List.of(omniboxCommandOrdererDelegate), defaultOmniboxCommandOrderer);

        var result = composedOmniboxCommandOrderer.order(List.of());
        assertThat(result).containsExactly(delegateCommand);
    }

    @Test
    @DisplayName("Given an omnibox command orderer delegate that is not relevant, when commands are ordered, then the default one is used")
    public void givenOmniboxCommandOrdererDelegateThatIsNotRelevantWhenCommandsAreOrderedThenTheDefaultOneIsUsed() {
        IOmniboxCommandOrdererDelegate omniboxCommandOrdererDelegate = new IOmniboxCommandOrdererDelegate.NoOp();
        var defaultCommand = new OmniboxCommand("default", "Default", List.of(), "");
        IDefaultOmniboxCommandOrderer defaultOmniboxCommandOrderer = new IDefaultOmniboxCommandOrderer.NoOp() {

            @Override
            public List<OmniboxCommand> order(List<OmniboxCommand> omniboxCommands) {
                return List.of(defaultCommand);
            }
        };

        ComposedOmniboxCommandOrderer composedOmniboxCommandOrderer = new ComposedOmniboxCommandOrderer(List.of(omniboxCommandOrdererDelegate), defaultOmniboxCommandOrderer);

        var result = composedOmniboxCommandOrderer.order(List.of());
        assertThat(result).containsExactly(defaultCommand);
    }
}
