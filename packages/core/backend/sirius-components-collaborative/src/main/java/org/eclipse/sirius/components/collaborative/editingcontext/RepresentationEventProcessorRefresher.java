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

import java.util.Objects;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IChangeDescriptionConsumer;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Used to refresh all the representation event processors.
 *
 * @author sbegaudeau
 * @since v2025.10.0
 */
@Service
public class RepresentationEventProcessorRefresher implements IChangeDescriptionConsumer {

    private static final String LOG_TIMING_FORMAT = "%1$6s";

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final MeterRegistry meterRegistry;

    private final Logger logger = LoggerFactory.getLogger(RepresentationEventProcessorRefresher.class);

    public RepresentationEventProcessorRefresher(IRepresentationEventProcessorRegistry representationEventProcessorRegistry, MeterRegistry meterRegistry) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
    }

    @Override
    @SuppressWarnings("checkstyle:IllegalCatch")
    public void accept(Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        var refreshRepresentationSample = Timer.start(this.meterRegistry);

        try {
            RepresentationEventProcessorEntry representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), changeDescription.getSourceId());
            if (representationEventProcessorEntry != null) {
                IRepresentationEventProcessor representationEventProcessor = representationEventProcessorEntry.getRepresentationEventProcessor();

                long start = System.currentTimeMillis();
                representationEventProcessor.refresh(changeDescription);
                long end = System.currentTimeMillis();

                this.logger.atDebug()
                        .setMessage("EditingContext {}: {}ms to refresh the {} with id {}")
                        .addArgument(editingContext.getId())
                        .addArgument(() -> String.format(LOG_TIMING_FORMAT, end - start))
                        .addArgument(representationEventProcessor.getClass().getSimpleName())
                        .addArgument(representationEventProcessor.getRepresentation().getId())
                        .log();
            }
            this.refreshOtherRepresentations(editingContext, changeDescription);
        } catch (Exception exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        var timer = this.meterRegistry.timer(Monitoring.TIMER_REFRESH_REPRESENTATION, "changeDescription", changeDescription.getSourceId());
        refreshRepresentationSample.stop(timer);
    }

    /**
     * Refresh all the representations except the one with the given representationId.
     *
     * @param changeDescription
     *         The description of change to consider in order to determine if the representation should be refreshed
     */
    private void refreshOtherRepresentations(IEditingContext editingContext, ChangeDescription changeDescription) {
        this.representationEventProcessorRegistry.values(editingContext.getId()).stream()
                .filter(registry -> !Objects.equals(changeDescription.getSourceId(), registry.getRepresentation().getId()))
                .forEach(representationEventProcessor -> {
                    long start = System.currentTimeMillis();
                    representationEventProcessor.refresh(changeDescription);
                    long end = System.currentTimeMillis();

                    this.logger.atDebug()
                            .setMessage("EditingContext {}: {}ms to refresh the {} with id {}")
                            .addArgument(editingContext.getId())
                            .addArgument(() -> String.format(LOG_TIMING_FORMAT, end - start))
                            .addArgument(representationEventProcessor.getClass().getSimpleName())
                            .addArgument(representationEventProcessor.getRepresentation().getId())
                            .log();
                });
    }
}
