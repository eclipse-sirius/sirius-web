/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.web.infrastructure.configuration.mvc;

import org.springframework.web.servlet.function.ServerRequest;

import jakarta.validation.constraints.NotNull;

/**
 * A service to process the index.html file before sending it to the client.
 *
 * @author tgiraudet
 */
public interface IIndexProcessor {

    String process(@NotNull ServerRequest request, @NotNull String indexHtml);

}
