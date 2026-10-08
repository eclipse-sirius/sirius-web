/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.application.capability.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.eclipse.sirius.web.application.capability.services.api.IDefaultCapabilityEvaluator;
import org.springframework.stereotype.Service;

/**
 * Used to evaluate all the capability voter to find the result of a vote.
 *
 * @author sbegaudeau
 */
@Service
public class CapabilityEvaluator implements ICapabilityEvaluator {

    private final List<ICapabilityVoter> capabilityVoters;

    private final IDefaultCapabilityEvaluator defaultCapabilityEvaluator;

    public CapabilityEvaluator(List<ICapabilityVoter> capabilityVoters, IDefaultCapabilityEvaluator defaultCapabilityEvaluator) {
        this.capabilityVoters = Objects.requireNonNull(capabilityVoters);
        this.defaultCapabilityEvaluator = Objects.requireNonNull(defaultCapabilityEvaluator);
    }

    @Override
    public boolean hasCapability(String type, String identifier, String capability) {
        var votes = this.capabilityVoters.stream()
                .map(voter -> voter.vote(type, identifier, capability))
                .filter(vote -> !CapabilityVote.ABSTAIN.equals(vote))
                .toList();
        if (!votes.isEmpty()) {
            return votes.stream().allMatch(CapabilityVote.GRANTED::equals);
        }
        return this.defaultCapabilityEvaluator.hasCapability();
    }
}
