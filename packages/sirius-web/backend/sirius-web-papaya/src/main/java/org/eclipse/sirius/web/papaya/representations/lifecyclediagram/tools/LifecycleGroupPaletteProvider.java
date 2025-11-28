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
package org.eclipse.sirius.web.papaya.representations.lifecyclediagram.tools;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.view.builder.generated.diagram.GroupPaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.CreateInstanceBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.LetBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.diagram.GroupPalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;

import java.util.UUID;

/**
 * Gorup palette for the lifecycle diagram.
 *
 * @author mcharfadi
 */
public class LifecycleGroupPaletteProvider {

    private static NodeTool causeByGroupTool;

    public static String getCausedByGroupToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(causeByGroupTool).toString().getBytes()).toString();
    }

    public GroupPalette getGroupPalette() {
        causeByGroupTool = new NodeToolBuilder()
                .name("Add causedBy subscription")
                .iconURLsExpression("/icons/papaya/full/obj16/Subscription.svg")
                .preconditionExpression("aql:self->filter(papaya::Controller)->size()==1 and self->filter(papaya::Command)->size()==1 and self->filter(papaya::Channel)->size()==1 and self->filter(papaya::Event)->size()==1")
                .body(new LetBuilder()
                        .variableName("controller")
                        .valueExpression("aql:self->filter(papaya::Controller)->first()")
                        .children(new LetBuilder()
                                .variableName("command")
                                .valueExpression("aql:self->filter(papaya::Command)->first()")
                                .children(new LetBuilder()
                                        .variableName("channel")
                                        .valueExpression("aql:self->filter(papaya::Channel)->first()")
                                        .children(new LetBuilder()
                                                .variableName("event")
                                                .valueExpression("aql:self->filter(papaya::Event)->first()")
                                                .children(new ChangeContextBuilder()
                                                                .expression("aql:event")
                                                                .children(new SetValueBuilder()
                                                                        .featureName("causedBy")
                                                                        .valueExpression("aql:command")
                                                                        .build())
                                                                .build(),
                                                        new ChangeContextBuilder()
                                                                .expression("aql:controller")
                                                                .children(new CreateInstanceBuilder()
                                                                        .typeName("papaya::Subscription")
                                                                        .referenceName("subscriptions")
                                                                        .variableName("subscription")
                                                                        .children(new ChangeContextBuilder()
                                                                                .expression("aql:subscription")
                                                                                .children(new SetValueBuilder()
                                                                                                .featureName("message")
                                                                                                .valueExpression("aql:command")
                                                                                                .build(),
                                                                                        new SetValueBuilder()
                                                                                                .featureName("channel")
                                                                                                .valueExpression("aql:channel")
                                                                                                .build())
                                                                                .build())
                                                                        .build())
                                                                .build())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        //Tool to create links between a Controller, Channel, Command and event
        return new GroupPaletteBuilder()
            .nodeTools(causeByGroupTool)
            .build();
    }

}
