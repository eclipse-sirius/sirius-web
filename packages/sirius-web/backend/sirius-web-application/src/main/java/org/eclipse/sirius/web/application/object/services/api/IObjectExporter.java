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
package org.eclipse.sirius.web.application.object.services.api;

import java.util.List;
import java.util.Optional;

/**
 * Used to export objects using a specific media type.
 *
 * @author gdaniel
 */
public interface IObjectExporter {

    boolean canHandle(List<Object> objects, String mediaType);

    Optional<byte[]> getBytes(List<Object> objects, String mediaType);

}
