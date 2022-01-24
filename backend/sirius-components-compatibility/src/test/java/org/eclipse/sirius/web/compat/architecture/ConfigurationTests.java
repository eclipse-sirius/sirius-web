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
package org.eclipse.sirius.web.compat.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;

import org.eclipse.sirius.web.spring.tests.architecture.AbstractConfigurationTests;

/**
 * Spring configuration tests.
 *
 * @author sbegaudeau
 */
public class ConfigurationTests extends AbstractConfigurationTests {

    @Override
    protected String getProjectRootPackage() {
        return ArchitectureConstants.SIRIUS_WEB_COMPAT_ROOT_PACKAGE;
    }

    @Override
    protected JavaClasses getClasses() {
        return ArchitectureConstants.CLASSES;
    }

}
