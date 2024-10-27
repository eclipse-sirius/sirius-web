/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceValueOptionsQueryInput;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ReferenceValueOptionsQueryPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.widget.reference.ReferenceValue;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Unit tests of the reference value options retrieval.
 *
 * @author frouene
 */
public class ReferenceValueOptionsEventHandlerTests {

    private static final UUID FORM_ID = UUID.randomUUID();

    private static final String REF_WIDGET_ID = "RefWidget id";

    private static final String CHANGE_DESCRIPTION_PARAMETER_KEY = "change_description_parameter_key";

    @Test
    public void testGetReferenceValueOptions() {
        String referenceValueId = "ReferenceValue Id";
        String changeKind = ChangeKind.NOTHING;

        var input = new ReferenceValueOptionsQueryInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID()
                .toString(), REF_WIDGET_ID);

        ReferenceValue referenceValue = ReferenceValue.newReferenceValue(referenceValueId)
                .label("")
                .kind("")
                .build();

        EObject owner = EcorePackage.Literals.ECLASS;
        EStructuralFeature.Setting setting = ((InternalEObject) owner).eSetting(EcorePackage.Literals.ECLASS__EALL_STRUCTURAL_FEATURES);

        ReferenceWidget referenceWidget = ReferenceWidget.newReferenceWidget(referenceValueId)
                .diagnostics(Collections.emptyList())
                .referenceValues(List.of())
                .referenceOptionsProvider(() -> List.of(referenceValue))
                .label("")
                .readOnly(false)
                .descriptionId("")
                .ownerId("")
                .ownerKind("")
                .referenceKind("")
                .many(false)
                .containment(false)
                .build();

        Group group = Group.newGroup("groupId")
                .label("group label")
                .widgets(Collections.singletonList(referenceWidget))
                .build();

        Page page = Page.newPage("pageId")
                .label("page label")
                .groups(Collections.singletonList(group))
                .build();

        Form form = Form.newForm(FORM_ID.toString())
                .targetObjectId("targetObjectId")
                .descriptionId(UUID.randomUUID().toString())
                .pages(Collections.singletonList(page))
                .build();

        IFormQueryService formQueryService = new IFormQueryService.NoOp() {
            @Override
            public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
                return Optional.of(referenceWidget);
            }
        };

        ReferenceValueOptionsEventHandler handler = new ReferenceValueOptionsEventHandler(formQueryService, new ICollaborativeFormMessageService.NoOp(), new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        Sinks.One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), form, input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ReferenceValueOptionsQueryPayload.class);
        assertThat(((ReferenceValueOptionsQueryPayload) payload).options()).hasSize(1);
        var option = ((ReferenceValueOptionsQueryPayload) payload).options().get(0);
        assertThat(option.getId()).isEqualTo(referenceValueId);
    }
}
