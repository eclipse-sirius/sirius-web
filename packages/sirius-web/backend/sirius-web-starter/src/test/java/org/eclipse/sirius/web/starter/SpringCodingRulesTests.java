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
package org.eclipse.sirius.web.starter;

import com.tngtech.archunit.core.domain.JavaClasses;

import org.eclipse.sirius.components.spring.tests.architecture.AbstractSpringCodingRulesTests;

/**
 * Spring coding rules of the project.
 *
 * @author sbegaudeau
 */
public class SpringCodingRulesTests extends AbstractSpringCodingRulesTests {
    @Override
    protected String getProjectRootPackage() {
        return ArchitectureConstants.SIRIUS_WEB_STARTER;
    }

    @Override
    protected JavaClasses getClasses() {
        return ArchitectureConstants.CLASSES;
    }
}
