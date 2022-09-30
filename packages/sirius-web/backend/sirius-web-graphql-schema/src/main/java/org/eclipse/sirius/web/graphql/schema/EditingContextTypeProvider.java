/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.web.graphql.schema;

/**
 * This class is used to create the definition of the EditingContext type and its related types.
 *
 * @author sbegaudeau
 */
public class EditingContextTypeProvider {

    public static final String TYPE = "EditingContext"; //$NON-NLS-1$

    public static final String STEREOTYPE_DESCRIPTIONS_FIELD = "stereotypeDescriptions"; //$NON-NLS-1$

    public static final String CHILD_CREATION_DESCRIPTIONS_FIELD = "childCreationDescriptions"; //$NON-NLS-1$

    public static final String KIND_ARGUMENT = "kind"; //$NON-NLS-1$

    public static final String ROOT_OBJECT_CREATION_DESCRIPTIONS_FIELD = "rootObjectCreationDescriptions"; //$NON-NLS-1$

    public static final String DOMAIN_ID_ARGUMENT = "domainId"; //$NON-NLS-1$

    public static final String SUGGESTED_ARGUMENT = "suggested"; //$NON-NLS-1$

    public static final String DOMAINS_FIELD = "domains"; //$NON-NLS-1$

    public static final String REPRESENTATION_DESCRIPTIONS_FIELD = "representationDescriptions"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_REPRESENTATION_DESCRIPTIONS_CONNECTION = "EditingContextRepresentationDescriptionConnection"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_REPRESENTATION_DESCRIPTIONS_EDGE = "EditingContextRepresentationDescriptionEdge"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_STEREOTYPE_DESCRIPTIONS_CONNECTION = "EditingContextStereotypeDescriptionConnection"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_STEREOTYPE_DESCRIPTIONS_EDGE = "EditingContextStereotypeDescriptionEdge"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_REPRESENTATION_CONNECTION = "EditingContextRepresentationConnection"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_REPRESENTATION_EDGE = "EditingContextRepresentationEdge"; //$NON-NLS-1$

    public static final String REPRESENTATIONS_FIELD = "representations"; //$NON-NLS-1$

    public static final String REPRESENTATION_FIELD = "representation"; //$NON-NLS-1$

    public static final String REPRESENTATION_ID_ARGUMENT = "representationId"; //$NON-NLS-1$

}
