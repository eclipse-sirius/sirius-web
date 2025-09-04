/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.util.List;

import org.eclipse.sirius.components.collaborative.api.IStdDeserializerProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.ICustomNodeStyleDeserializer;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.springframework.stereotype.Service;

/**
 * The deserializer provider for {@link INodeStyle}.
 *
 * @author gcoutable
 */
@Service
public class NodeStyleStdDeserializerProvider implements IStdDeserializerProvider<INodeStyle> {

    private final List<ICustomNodeStyleDeserializer> customNodeStyleDeserializers;

    public NodeStyleStdDeserializerProvider(List<ICustomNodeStyleDeserializer> customNodeStyleDeserializers) {
        this.customNodeStyleDeserializers = customNodeStyleDeserializers;
    }

    @Override
    public StdDeserializer<INodeStyle> getDeserializer() {
        return new INodeStyleDeserializer(this.customNodeStyleDeserializers);
    }

    @Override
    public Class<INodeStyle> getType() {
        return INodeStyle.class;
    }

}
