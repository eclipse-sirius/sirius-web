/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.interpreter;

/**
 * The result status.
 *
 * @author hmarchadour
 */
public enum Status {
    // @formatter:off
    OK(0),

    INFO(1),

    WARNING(2),

    ERROR(4),

    CANCEL(8);
    // @formatter:on

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    public static Status getStatus(int code) {
        Status result;
        switch (code) {
            case 0:
                result = OK;
                break;
            case 1:
                result = INFO;
                break;
            case 2:
                result = WARNING;
                break;
            case 4:
                result = ERROR;
                break;
            case 8:
                result = CANCEL;
                break;
            default:
                result = ERROR;
                break;
        }
        return result;
    }
}
