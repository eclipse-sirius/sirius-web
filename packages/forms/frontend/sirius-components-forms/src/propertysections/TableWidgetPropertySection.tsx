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
import { TableContent } from '@eclipse-sirius/sirius-components-tables';
import { makeStyles } from 'tss-react/mui';
import { PropertySectionComponent, PropertySectionComponentProps } from '../form/Form.types';
import { GQLTableWidget } from '../form/FormEventFragments.types';
import { PropertySectionLabel } from './PropertySectionLabel';

const useStyle = makeStyles()(() => ({
  main: {
    display: 'grid',
  },
}));

export const TableWidgetPropertySection: PropertySectionComponent<GQLTableWidget> = ({
  editingContextId,
  formId,
  widget,
}: PropertySectionComponentProps<GQLTableWidget>) => {
  const { classes } = useStyle();

  return (
    <div className={classes.main}>
      <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
      <TableContent
        editingContextId={editingContextId}
        representationId={formId}
        table={widget.table}
        readOnly={widget.readOnly}></TableContent>
    </div>
  );
};
