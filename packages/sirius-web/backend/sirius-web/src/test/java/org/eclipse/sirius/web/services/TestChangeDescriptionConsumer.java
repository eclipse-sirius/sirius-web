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
package org.eclipse.sirius.web.services;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IChangeDescriptionConsumer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Used during tests to check the produced {@link ChangeDescription}s.
 *
 * @author gdaniel
 */
@Service
public class TestChangeDescriptionConsumer implements IChangeDescriptionConsumer {

    private ChangeDescription preAcceptChangeDescription;

    private ChangeDescription acceptChangeDescription;

    private ChangeDescription postAcceptChangeDescription;

    @Override
    public void preAccept(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        this.preAcceptChangeDescription = changeDescription;
    }

    @Override
    public void accept(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        this.acceptChangeDescription = changeDescription;
    }

    @Override
    public void postAccept(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        this.postAcceptChangeDescription = changeDescription;
    }

    public ChangeDescription getPreAcceptChangeDescription() {
        return this.preAcceptChangeDescription;
    }

    public ChangeDescription getAcceptChangeDescription() {
        return this.acceptChangeDescription;
    }

    public ChangeDescription getPostAcceptChangeDescription() {
        return this.postAcceptChangeDescription;
    }

    public void reset() {
        this.preAcceptChangeDescription = null;
        this.acceptChangeDescription = null;
        this.postAcceptChangeDescription = null;
    }
}
