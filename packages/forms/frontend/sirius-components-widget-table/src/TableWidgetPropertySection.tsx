/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import {
  GQLWidget,
  PropertySectionComponent,
  PropertySectionComponentProps,
  PropertySectionLabel,
} from '@eclipse-sirius/sirius-components-forms';
import { TableContent } from '@eclipse-sirius/sirius-components-tables';
import { makeStyles } from 'tss-react/mui';
import { GQLTableWidget } from './TableWidgetFragment.types';

const useStyle = makeStyles()(() => ({
  main: {
    display: 'grid',
  },
}));

const isTableWidget = (widget: GQLWidget): widget is GQLTableWidget => widget.__typename === 'TableWidget';

export const TableWidgetPropertySection: PropertySectionComponent<GQLWidget> = ({
  widget,
  ...props
}: PropertySectionComponentProps<GQLWidget>) => {
  if (isTableWidget(widget)) {
    return <RawTableWidgetPropertySection widget={widget} {...props} />;
  }
  return null;
};

const RawTableWidgetPropertySection: PropertySectionComponent<GQLTableWidget> = ({
  editingContextId,
  formId,
  widget,
  readOnly,
}: PropertySectionComponentProps<GQLTableWidget>) => {
  const { classes } = useStyle();

  return (
    <div className={classes.main}>
      <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} />
      <TableContent
        editingContextId={editingContextId}
        representationId={formId}
        table={widget.table}
        readOnly={readOnly || widget.readOnly}
        onPaginationChange={() => {}}
        onGlobalFilterChange={() => {}}
        onColumnFiltersChange={() => {}}
        onExpandedElementChange={() => {}}
        onRowFiltersChange={() => {}}
        onSortingChange={() => {}}
        enableColumnVisibility={false}
        enableColumnResizing={false}
        enableColumnFilters={false}
        enableRowSizing={false}
        enableGlobalFilter={false}
        enablePagination={false}
        enableColumnOrdering={false}
        enableSelectionSynchronization={false}
        expandedRowIds={[]}
        pageSize={0}
        rowFilters={[]}
        activeRowFilterIds={[]}
        enableSorting={false}
      />
    </div>
  );
};
