/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

/**
 * Service used to manage the form description editor event flux.
 *
 * @author arichard
 */
public class FormDescriptionEditorEventFlux {

    private final Logger logger = LoggerFactory.getLogger(FormDescriptionEditorEventFlux.class);

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private FormDescriptionEditor currentFormDescriptionEditor;

    public FormDescriptionEditorEventFlux(FormDescriptionEditor currentFormDescriptionEditor) {
        this.currentFormDescriptionEditor = Objects.requireNonNull(currentFormDescriptionEditor);
    }

    public void formDescriptionEditorRefreshed(IInput input, FormDescriptionEditor newFormDescriptionEditor) {
        this.currentFormDescriptionEditor = newFormDescriptionEditor;
        if (this.sink.currentSubscriberCount() > 0) {
            EmitResult emitResult = this.sink.tryEmitNext(new FormDescriptionEditorRefreshedEventPayload(input.id(), this.currentFormDescriptionEditor));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a FormDescriptionEditorRefreshedEventPayload: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    public Flux<IPayload> getFlux(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new FormDescriptionEditorRefreshedEventPayload(input.id(), this.currentFormDescriptionEditor));
        return Flux.concat(initialRefresh, this.sink.asFlux());
    }

    public void dispose() {
        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }
    }

}
