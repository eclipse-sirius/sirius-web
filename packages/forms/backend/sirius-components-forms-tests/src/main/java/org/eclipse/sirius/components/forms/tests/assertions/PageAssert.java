/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.forms.tests.assertions;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.forms.Page;

/**
 * Custom assertion class used to perform some tests on a page.
 *
 * @author lfasani
 */
public class PageAssert extends AbstractAssert<PageAssert, Page> {
    public PageAssert(Page page) {
        super(page, PageAssert.class);
    }

}
