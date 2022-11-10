/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.forms.CompletionProposal;

/**
 * The payload for the "completionProposals" query on success.
 *
 * @author pcdavid
 */
public final class CompletionRequestSuccessPayload implements IPayload {
    private final UUID id;

    private final List<CompletionProposal> proposals;

    public CompletionRequestSuccessPayload(UUID id, List<CompletionProposal> proposals) {
        this.id = Objects.requireNonNull(id);
        this.proposals = List.copyOf(proposals);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public List<CompletionProposal> getProposals() {
        return this.proposals;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, proposals: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.proposals);
    }

}
