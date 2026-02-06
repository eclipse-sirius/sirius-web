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

/**
 * A contribution to the query result split button.
 *
 * @param label the label to display in the split button popup menu.
 * @param component the component to render when the contribution is selected in the popup menu.
 *
 * Note that for consistency purpose it is recommended to use a mui button for the component, since itt will be integrated in the query view split button.
 *
 * @since v2026.3.0
 */
export interface QueryResultButtonContribution {
  label: string;
  component: React.ComponentType<QueryResultButtonComponentProps>;
}

export interface QueryResultButtonComponentProps {
  objectIds: string[];
}
