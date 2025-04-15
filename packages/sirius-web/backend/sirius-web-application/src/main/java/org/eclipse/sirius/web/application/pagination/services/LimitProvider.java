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
package org.eclipse.sirius.web.application.pagination.services;

import java.util.Optional;

import org.eclipse.sirius.web.application.pagination.services.api.ILimitProvider;
import org.springframework.stereotype.Service;

/**
 * Used to compute the limit.
 *
 * @author arichard
 */
@Service
public class LimitProvider implements ILimitProvider {

    @Override
    public int getLimit(int defaultPageSize, Optional<Integer> first, Optional<Integer> last, Optional<String> after, Optional<String> before) {
        int limit = 0;
        if (after.isPresent() && before.isEmpty()) {
            if (last.isPresent()) {
                limit = 0;
            } else if (first.isPresent()) {
                limit = first.get();
            } else {
                limit = defaultPageSize;
            }
        } else if (before.isPresent() && after.isEmpty()) {
            if (first.isPresent()) {
                limit = 0;
            } else if (last.isPresent()) {
                limit = last.get();
            } else {
                limit = defaultPageSize;
            }
        } else if (before.isEmpty() && after.isEmpty()) {
            if (first.isPresent() && last.isPresent()) {
                limit = 0;
            } else if (first.isPresent()) {
                limit = first.get();
            } else if (last.isPresent()) {
                limit = last.get();
            } else {
                limit = defaultPageSize;
            }
        }
        return limit;
    }
}
