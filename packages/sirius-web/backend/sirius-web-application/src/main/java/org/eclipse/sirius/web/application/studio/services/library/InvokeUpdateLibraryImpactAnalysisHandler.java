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
package org.eclipse.sirius.web.application.studio.services.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.ImpactAnalysisReport;
import org.eclipse.sirius.components.collaborative.dto.InvokeImpactAnalysisSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextSnapshotService;
import org.eclipse.sirius.web.application.library.dto.InvokeUpdateLibraryImpactAnalysisInput;
import org.eclipse.sirius.web.application.studio.services.library.api.IUpdateLibraryExecutor;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle update library "impact analysis" events.
 *
 * @author gdaniel
 */
@Service
public class InvokeUpdateLibraryImpactAnalysisHandler implements IEditingContextEventHandler {

    private final IUpdateLibraryExecutor updateLibraryExecutor;

    private final IEditingContextSnapshotService editingContextSnapshotService;

    private final ILabelService labelService;

    private final IMessageService messageService;

    private final Counter counter;

    public InvokeUpdateLibraryImpactAnalysisHandler(IUpdateLibraryExecutor updateLibraryExecutor, IEditingContextSnapshotService editingContextSnapshotService, ILabelService labelService, IMessageService messageService, MeterRegistry meterRegistry) {
        this.updateLibraryExecutor = Objects.requireNonNull(updateLibraryExecutor);
        this.editingContextSnapshotService = Objects.requireNonNull(editingContextSnapshotService);
        this.labelService = Objects.requireNonNull(labelService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof InvokeUpdateLibraryImpactAnalysisInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), InvokeUpdateLibraryImpactAnalysisInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof InvokeUpdateLibraryImpactAnalysisInput invokeUpdateLibraryImpactAnalysisInput && editingContext instanceof EditingContext siriusWebEditingContext) {
            var editingContextSnapshot = this.editingContextSnapshotService.createSnapshot(siriusWebEditingContext);
            if (editingContextSnapshot.isPresent()) {

                IStatus updateLibraryResult = this.updateLibraryExecutor.updateLibrary(invokeUpdateLibraryImpactAnalysisInput, siriusWebEditingContext, invokeUpdateLibraryImpactAnalysisInput.libraryId());

                if (updateLibraryResult instanceof Success success) {
                    Map<EObject, Collection<Setting>> removedProxies = (Map<EObject, Collection<Setting>>) success.getParameters().get(UpdateLibraryExecutor.REMOVED_PROXIES_PARAMETER_KEY);

                    List<String> messages = new ArrayList<>();
                    messages.addAll(this.computeBrokenProxyReport(removedProxies));

                    payload = new InvokeImpactAnalysisSuccessPayload(input.id(), new ImpactAnalysisReport(0, 0, 0, messages), success.getMessages());
                } else if (updateLibraryResult instanceof Failure failure) {
                    payload = new ErrorPayload(invokeUpdateLibraryImpactAnalysisInput.id(), failure.getMessages());
                }
                this.editingContextSnapshotService.restoreSnapshot(siriusWebEditingContext, editingContextSnapshot.get());
            } else {
                payload = new ErrorPayload(input.id(), this.messageService.unexpectedError());
            }
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private List<String> computeBrokenProxyReport(Map<EObject, Collection<Setting>> unresolvedProxiesAfter) {
        List<String> report = new ArrayList<>();
        unresolvedProxiesAfter.entrySet().stream()
            .forEach(entry -> {
                EObject proxyObject = entry.getKey();
                Collection<Setting> settings = entry.getValue();
                for (Setting setting : settings) {
                    StringBuilder brokenProxiesBuilder = new StringBuilder();
                    brokenProxiesBuilder
                        .append("[BROKEN] ")
                        .append(this.getResourceAndEObjectLabel(setting.getEObject()))
                        .append(".")
                        .append(setting.getEStructuralFeature().getName())
                        .append(" (previously set to ")
                        .append(this.labelService.getStyledLabel(proxyObject))
                        .append(")");
                    report.add(brokenProxiesBuilder.toString());
                }
            });
        return report;
    }

    private String getResourceAndEObjectLabel(EObject eObject) {
        return this.labelService.getStyledLabel(eObject.eResource()).toString()
                + " - "
                + this.labelService.getStyledLabel(eObject).toString();
    }
}
