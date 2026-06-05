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
package org.eclipse.sirius.components.collaborative.omnibox;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.omnibox.api.IDefaultOmniboxCommandOrderer;
import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandOrderer;
import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandOrdererDelegate;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IOmniboxCommandOrderer} which delegates to {@link IOmniboxCommandOrdererDelegate}
 * or fallback to {@link IDefaultOmniboxCommandOrderer}.
 *
 * @author gdaniel
 */
@Service
public class ComposedOmniboxCommandOrderer implements IOmniboxCommandOrderer {

    private final List<IOmniboxCommandOrdererDelegate> omniboxCommandOrdererDelegates;

    private final IDefaultOmniboxCommandOrderer defaultOmniboxCommandOrderer;

    public ComposedOmniboxCommandOrderer(List<IOmniboxCommandOrdererDelegate> omniboxCommandOrdererDelegates, IDefaultOmniboxCommandOrderer defaultOmniboxCommandOrderer) {
        this.omniboxCommandOrdererDelegates = Objects.requireNonNull(omniboxCommandOrdererDelegates);
        this.defaultOmniboxCommandOrderer = Objects.requireNonNull(defaultOmniboxCommandOrderer);
    }

    @Override
    public List<OmniboxCommand> order(List<OmniboxCommand> omniboxCommands) {
        Optional<IOmniboxCommandOrdererDelegate> optionalOmniboxCommandOrdererDelegate = this.omniboxCommandOrdererDelegates.stream()
                .filter(omniboxCommandOrdererDelegate -> omniboxCommandOrdererDelegate.canHandle(omniboxCommands))
                .findFirst();
        if (optionalOmniboxCommandOrdererDelegate.isPresent()) {
            return optionalOmniboxCommandOrdererDelegate.get().order(omniboxCommands);
        } else {
            return this.defaultOmniboxCommandOrderer.order(omniboxCommands);
        }
    }
}
