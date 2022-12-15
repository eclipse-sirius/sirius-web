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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.tools.ITool;

/**
 * The payload of the "Get Connector Tools" query returned on success.
 *
 * @author nvannier
 */
public class GetConnectorToolsSuccessPayload implements IPayload {
    private final UUID id;

    private final List<ITool> connectorTools;

    public GetConnectorToolsSuccessPayload(UUID id, List<ITool> connectorTools) {
        this.id = id;
        this.connectorTools = connectorTools;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public List<ITool> getConnectorTools() {
        return this.connectorTools;
    }

    @Override
    public String toString() {
        String tools = Arrays.toString(this.connectorTools.toArray());
        String pattern = "{0} '{'id: {1}, tools: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, tools);
    }

}
