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
package org.eclipse.sirius.web.application.object.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.application.object.services.api.IDefaultReadOnlyObjectPredicate;
import org.eclipse.sirius.web.application.object.services.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.web.application.object.services.api.IReadOnlyObjectPredicateDelegate;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IReadOnlyObjectPredicate} which delegates to {@link IReadOnlyObjectPredicateDelegate} or fallback to
 * {@link IDefaultReadOnlyObjectPredicate}.
 *
 * @author gdaniel
 */
@Service
public class ComposedReadOnlyObjectPredicate implements IReadOnlyObjectPredicate {

    private final List<IReadOnlyObjectPredicateDelegate> readOnlyObjectPredicateDelegate;

    private final IDefaultReadOnlyObjectPredicate defaultReadOnlyObjectPredicate;

    public ComposedReadOnlyObjectPredicate(List<IReadOnlyObjectPredicateDelegate> readOnlyObjectPredicateDelegate, IDefaultReadOnlyObjectPredicate defaultReadOnlyObjectPredicate) {
        this.readOnlyObjectPredicateDelegate = Objects.requireNonNull(readOnlyObjectPredicateDelegate);
        this.defaultReadOnlyObjectPredicate = Objects.requireNonNull(defaultReadOnlyObjectPredicate);
    }

    @Override
    public boolean test(Object object) {
        var optionalDelegate = this.readOnlyObjectPredicateDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().test(object);
        }
        return this.defaultReadOnlyObjectPredicate.test(object);
    }

}
