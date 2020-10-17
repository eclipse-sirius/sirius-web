/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.representations;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.mapper.IDeserializerProvider;
import org.springframework.stereotype.Service;

/**
 * The deserializer provider for {@link IRepresentation}.
 *
 * @author gcoutable
 */
@Service
public class RepresentationDeserializerProvider implements IDeserializerProvider<IRepresentation> {

    @Override
    public StdDeserializer<IRepresentation> getDeserializer() {
        return new IRepresentationDeserializer();
    }

    @Override
    public Class<IRepresentation> getType() {
        return IRepresentation.class;
    }
}
