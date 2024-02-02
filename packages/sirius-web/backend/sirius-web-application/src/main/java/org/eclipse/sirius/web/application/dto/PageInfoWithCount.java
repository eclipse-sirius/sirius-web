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
package org.eclipse.sirius.web.application.dto;

import graphql.relay.ConnectionCursor;
import graphql.relay.PageInfo;

/**
 * Our custom version of the page info which includes the count too.
 *
 * @author sbegaudeau
 */
public class PageInfoWithCount implements PageInfo {

    private final ConnectionCursor startCursor;

    private final ConnectionCursor endCursor;

    private final boolean hasPreviousPage;

    private final boolean hasNextPage;

    private final long count;

    public PageInfoWithCount(ConnectionCursor startCursor, ConnectionCursor endCursor, boolean hasPreviousPage, boolean hasNextPage, long count) {
        this.startCursor = startCursor;
        this.endCursor = endCursor;
        this.hasPreviousPage = hasPreviousPage;
        this.hasNextPage = hasNextPage;
        this.count = count;
    }

    @Override
    public ConnectionCursor getStartCursor() {
        return this.startCursor;
    }

    @Override
    public ConnectionCursor getEndCursor() {
        return this.endCursor;
    }

    @Override
    public boolean isHasPreviousPage() {
        return this.hasPreviousPage;
    }

    @Override
    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public long count() {
        return this.count;
    }

}
