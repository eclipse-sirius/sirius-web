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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IChangeDescriptionConsumer;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IChangeDescriptionListener;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Used to react to change descriptions from event processors.
 *
 * @author mcharfadi
 */
@Service
public class ChangeDescriptionConsumerOrchestrator implements IChangeDescriptionListener {

    private final List<IChangeDescriptionConsumer> changeDescriptionConsumers;

    public ChangeDescriptionConsumerOrchestrator(List<IChangeDescriptionConsumer> changeDescriptionConsumers) {
        this.changeDescriptionConsumers = Objects.requireNonNull(changeDescriptionConsumers);
    }


    @Override
    @SuppressWarnings("checkstyle:IllegalCatch")
    public void onChange(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        if (ChangeKind.NOTHING.equals(changeDescription.getKind())) {
            return;
        }

        this.changeDescriptionConsumers.stream().forEach(consumer -> consumer.preAccept(payloadSink, canBeDisposedSink, editingContext, changeDescription));
        this.changeDescriptionConsumers.stream().forEach(consumer -> consumer.accept(payloadSink, canBeDisposedSink, editingContext, changeDescription));
        this.changeDescriptionConsumers.stream().forEach(consumer -> consumer.postAccept(payloadSink, canBeDisposedSink, editingContext, changeDescription));
    }

}
