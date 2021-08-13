/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.api.configuration;

import org.eclipse.sirius.web.annotations.PublicApi;
import org.eclipse.sirius.web.forms.description.FormDescription;

/**
 * Interface of a registry of {@link FormDescription}s for Links.
 *
 * @author ldelaigue
 */
@PublicApi
public interface ILinksDescriptionRegistry {
    void add(FormDescription formDescription);
}
