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
package org.eclipse.sirius.web.forms.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;

import org.eclipse.sirius.web.tests.architecture.AbstractCodingRulesTestCases;
import org.junit.Test;

/**
 * Coding rules tests.
 *
 * @author sbegaudeau
 */
public class CodingRulesTestCases extends AbstractCodingRulesTestCases {

    @Override
    protected String getProjectRootPackage() {
        return ArchitectureConstants.SIRIUS_WEB_FORMS_ROOT_PACKAGE;
    }

    @Override
    protected JavaClasses getClasses() {
        return ArchitectureConstants.CLASSES;
    }

    @Test
    @Override
    public void noClassesShouldUseEMF() {
        super.noClassesShouldUseEMF();
    }

    @Test
    @Override
    public void noClassesShouldUseApacheCommons() {
        super.noClassesShouldUseApacheCommons();
    }

}
