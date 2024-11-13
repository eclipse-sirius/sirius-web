/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.project.data.versioning.services.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import org.eclipse.sirius.web.application.project.data.versioning.services.RestDataVersionPayloadSerializer;

/**
 * Interface of the delegate service used by {@link RestDataVersionPayloadSerializer}.
 *
 * @author arichard
 */
public interface IRestDataVersionPayloadSerializerService {

    boolean canHandle(Object object);

    void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException;
}
