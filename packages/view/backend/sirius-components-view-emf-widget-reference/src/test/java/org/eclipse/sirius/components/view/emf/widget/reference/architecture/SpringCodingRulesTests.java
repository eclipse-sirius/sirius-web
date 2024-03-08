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
package org.eclipse.sirius.components.view.emf.widget.reference.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;

import org.eclipse.sirius.components.spring.tests.architecture.AbstractSpringCodingRulesTests;

/**
 * Spring coding rules tests.
 *
 * @author frouene
 */
public class SpringCodingRulesTests extends AbstractSpringCodingRulesTests {

    @Override
    protected String getProjectRootPackage() {
        return ArchitectureConstants.SIRIUS_COMPONENTS_VIEW_EMF_WIDGET_REFERENCE_ROOT_PACKAGE;
    }

    @Override
    protected JavaClasses getClasses() {
        return ArchitectureConstants.CLASSES;
    }

}
