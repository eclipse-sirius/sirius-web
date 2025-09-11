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

package org.eclipse.sirius.components.collaborative.diagrams.handlers.appearance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.events.appearance.EditAppearanceEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBoldAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderRadiusAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelFontSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelItalicAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelStrikeThroughAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelUnderlineAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelVisibilityAppearanceChange;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handles diagram events related to editing a label's appearance.
 *
 * @author nvannier
 */
@Service
public class EditLabelAppearanceEventHandler implements IDiagramEventHandler {

    private final ICollaborativeDiagramMessageService messageService;

    private final IDiagramQueryService diagramQueryService;

    private final Counter counter;

    public EditLabelAppearanceEventHandler(ICollaborativeDiagramMessageService messageService, IDiagramQueryService diagramQueryService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof EditLabelAppearanceInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), EditLabelAppearanceInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof EditLabelAppearanceInput editAppearanceInput) {
            Optional<IDiagramElement> optionalDiagramElement = this.diagramQueryService.findDiagramElementById(diagramContext.diagram(), editAppearanceInput.diagramElementId());
            if (optionalDiagramElement.isPresent()) {
                List<IAppearanceChange> appearanceChanges = new ArrayList<>();

                Optional.ofNullable(editAppearanceInput.appearance().bold()).ifPresent(bold -> appearanceChanges.add(new LabelBoldAppearanceChange(editAppearanceInput.labelId(), bold)));
                Optional.ofNullable(editAppearanceInput.appearance().italic()).ifPresent(italic -> appearanceChanges.add(new LabelItalicAppearanceChange(editAppearanceInput.labelId(), italic)));
                Optional.ofNullable(editAppearanceInput.appearance().underline())
                        .ifPresent(underline -> appearanceChanges.add(new LabelUnderlineAppearanceChange(editAppearanceInput.labelId(), underline)));
                Optional.ofNullable(editAppearanceInput.appearance().strikeThrough())
                        .ifPresent(strikeThrough -> appearanceChanges.add(new LabelStrikeThroughAppearanceChange(editAppearanceInput.labelId(), strikeThrough)));
                Optional.ofNullable(editAppearanceInput.appearance().fontSize())
                        .ifPresent(fontSize -> appearanceChanges.add(new LabelFontSizeAppearanceChange(editAppearanceInput.labelId(), fontSize)));
                Optional.ofNullable(editAppearanceInput.appearance().color())
                        .ifPresent(color -> appearanceChanges.add(new LabelColorAppearanceChange(editAppearanceInput.labelId(), color)));
                Optional.ofNullable(editAppearanceInput.appearance().background())
                        .ifPresent(background -> appearanceChanges.add(new LabelBackgroundAppearanceChange(editAppearanceInput.labelId(), background)));
                Optional.ofNullable(editAppearanceInput.appearance().borderColor())
                        .ifPresent(borderColor -> appearanceChanges.add(new LabelBorderColorAppearanceChange(editAppearanceInput.labelId(), borderColor)));
                Optional.ofNullable(editAppearanceInput.appearance().borderRadius())
                        .ifPresent(borderRadius -> appearanceChanges.add(new LabelBorderRadiusAppearanceChange(editAppearanceInput.labelId(), borderRadius)));
                Optional.ofNullable(editAppearanceInput.appearance().borderSize())
                        .ifPresent(borderSize -> appearanceChanges.add(new LabelBorderSizeAppearanceChange(editAppearanceInput.labelId(), borderSize)));
                Optional.ofNullable(editAppearanceInput.appearance().borderStyle())
                        .ifPresent(borderStyle -> appearanceChanges.add(new LabelBorderStyleAppearanceChange(editAppearanceInput.labelId(), borderStyle)));
                Optional.ofNullable(editAppearanceInput.appearance().visibility())
                        .ifPresent(visibility -> appearanceChanges.add(new LabelVisibilityAppearanceChange(editAppearanceInput.labelId(), visibility)));

                diagramContext.diagramEvents().add(new EditAppearanceEvent(appearanceChanges));
                payload = new SuccessPayload(diagramInput.id());
                changeDescription = new ChangeDescription(DiagramChangeKind.DIAGRAM_APPEARANCE_CHANGE, diagramInput.representationId(), diagramInput);
            } else {
                String nodeNotFoundMessage = this.messageService.diagramElementNotFound(editAppearanceInput.diagramElementId());
                payload = new ErrorPayload(diagramInput.id(), nodeNotFoundMessage);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
