/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.IStdDeserializerProvider;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.springframework.stereotype.Service;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * The deserializer provider for {@link ILayoutStrategy}.
 *
 * @author gcoutable
 */
@Service
public class LayoutStrategyStdDeserializerProvider implements IStdDeserializerProvider<ILayoutStrategy> {

    @Override
    public StdDeserializer<ILayoutStrategy> getDeserializer() {
        return new ILayoutStrategyDeserializer();
    }

    @Override
    public Class<ILayoutStrategy> getType() {
        return ILayoutStrategy.class;
    }

}
