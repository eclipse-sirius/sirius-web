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
package org.eclipse.sirius.web.validation.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;

import org.eclipse.sirius.web.tests.architecture.AbstractImmutableTests;

/**
 * Architectural tests of the @Immutable classes.
 *
 * @author gcoutable
 */
public class ImmutableTests extends AbstractImmutableTests {
    @Override
    protected String getProjectRootPackage() {
        return ArchitectureConstants.SIRIUS_WEB_VALIDATION_ROOT_PACKAGE;
    }

    @Override
    protected JavaClasses getClasses() {
        return ArchitectureConstants.CLASSES;
    }
}
