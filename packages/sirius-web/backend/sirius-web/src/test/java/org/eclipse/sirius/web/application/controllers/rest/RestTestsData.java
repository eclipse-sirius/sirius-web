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
package org.eclipse.sirius.web.application.controllers.rest;

import org.eclipse.sirius.web.data.TestIdentifiers;

/**
 * Tests Data for REST APIs Tests.
 *
 * @author arichard
 */
public class RestTestsData {

    public static final String ECORE_SAMPLE_PKG = """
            {
                "@id": "%s",
                "@type": "EPackage",
                "name": "Sample",
                "nsURI": null,
                "nsPrefix": null,
                "eAnnotations": [],
                "eClassifiers": [
                    {
                        "@id": "%s"
                    }
                ],
                "eSubpackages": [],
                "eSuperPackage": null
            }
            """.formatted(TestIdentifiers.EPACKAGE_OBJECT, TestIdentifiers.ECLASS_OBJECT);

    public static final String ECORE_SAMPLE_SUBPKG = """
            {
                "@id": "3a4d2a45-5de0-4f56-8f23-4fb1474a9fea",
                "@type": "EPackage",
                "name": "SampleSubPackage",
                "nsURI": null,
                "nsPrefix": null,
                "eAnnotations": [],
                "eClassifiers": [],
                "eSubpackages": [],
                "eSuperPackage": null
            }
            """;

    public static final String ECORE_SAMPLE_ECLASS = """
            {
                "@id": "%s",
                "@type": "EClass",
                "name": "SampleEClass",
                "instanceClassName": null,
                "instanceClass": null,
                "defaultValue": null,
                "instanceTypeName": null,
                "abstract": "false",
                "interface": "false",
                "eAnnotations": [],
                "ePackage": { "@id": "%s" },
                "eTypeParameters": [],
                "eSuperTypes": [],
                "eOperations": [],
                "eAllAttributes": [],
                "eAllReferences": [],
                "eReferences": [],
                "eAttributes": [],
                "eAllContainments": [],
                "eAllOperations": [],
                "eAllStructuralFeatures": [],
                "eAllSuperTypes": [],
                "eIDAttribute": null,
                "eStructuralFeatures": [],
                "eGenericSuperTypes": [],
                "eAllGenericSuperTypes": []
            }
            """.formatted(TestIdentifiers.ECLASS_OBJECT, TestIdentifiers.EPACKAGE_OBJECT);
}
