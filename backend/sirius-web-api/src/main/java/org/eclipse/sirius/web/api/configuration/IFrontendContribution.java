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
package org.eclipse.sirius.web.api.configuration;

import java.util.List;

import org.eclipse.sirius.web.annotations.PublicApi;

/**
 * Allows the contribution of additional pages to the frontend.
 *
 * @author pcdavid
 */
@PublicApi
public interface IFrontendContribution {
    String getStaticAssetsPath();

    default boolean isMain() {
        return false;
    }

    default String getIndexPath() {
        return this.getStaticAssetsPath() + "index.html"; //$NON-NLS-1$
    }

    List<String> getPathPatterns();
}
