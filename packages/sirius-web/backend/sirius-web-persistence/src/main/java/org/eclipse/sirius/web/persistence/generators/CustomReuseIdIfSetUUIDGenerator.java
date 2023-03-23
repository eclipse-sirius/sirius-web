/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.persistence.generators;

import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

/**
 * Custom UUID generator to generate a new UUID only if the entity does not have already one.
 *
 * @author frouene
 */
public class CustomReuseIdIfSetUUIDGenerator extends UUIDGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Object id = session.getEntityPersister(null, object).getIdentifier(object, session);
        if (Objects.nonNull(id)) {
            return id;
        }
        return super.generate(session, object);
    }
}
