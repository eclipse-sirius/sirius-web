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
package org.eclipse.sirius.web.tests.undoredo;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.SuccessPayload;

/**
 * Custom assertion class used to perform tests on an undo result.
 *
 * @author tgiraudet
 */
public class UndoAssert {

    private final String result;

    public UndoAssert(String result) {
        this.result = Objects.requireNonNull(result);
    }

    public UndoAssert isSuccess() {
        String typename = JsonPath.read(this.result, "$.data.undo.__typename");
        assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        return this;
    }
}
