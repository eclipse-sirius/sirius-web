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
package org.eclipse.sirius.web.persistence.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.eclipse.sirius.web.persistence.entities.VisibilityEntity;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

/**
 * Maps the {@link VisibilityEntity} Java enum onto the corresponding custom PostgreSQL ENUM type.
 *
 * @author pcdavid
 */
public class VisibilityEnumType extends EnumType<VisibilityEntity> {
    private static final long serialVersionUID = -3084425979013752103L;

    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.OTHER);
        } else {
            statement.setObject(index, value.toString(), Types.OTHER);
        }
    }

}
