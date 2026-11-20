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

export interface MeasuredRowHeight {
  rowId: string;
  height: number;
}

export const measureVisibleRowHeights = (tableRoot: HTMLElement, resizableRowIds: Set<string>): MeasuredRowHeight[] => {
  const host = createMeasurementHost(tableRoot);
  try {
    return Array.from(tableRoot.querySelectorAll<HTMLTableRowElement>('tbody tr[data-table-row-id]'))
      .filter((row) => resizableRowIds.has(row.dataset.tableRowId ?? ''))
      .map((row) => {
        const height = Math.max(
          ...Array.from(row.querySelectorAll<HTMLTableCellElement>('td')).map((cell) =>
            measureCell(cell, host, 'measureHeight')
          )
        );
        return { rowId: row.dataset.tableRowId ?? '', height };
      })
      .filter((row) => row.height > 0 && Number.isFinite(row.height));
  } finally {
    host.remove();
  }
};

export const measureVisibleColumnWidth = (tableRoot: HTMLElement, columnId: string): number | null => {
  const cells = Array.from(
    tableRoot.querySelectorAll<HTMLTableCellElement>('tbody tr[data-table-row-id] td[data-table-column-id]')
  ).filter((cell) => cell.dataset.tableColumnId === columnId);
  if (cells.length === 0) {
    return null;
  }

  const host = createMeasurementHost(tableRoot);
  try {
    const header = Array.from(tableRoot.querySelectorAll<HTMLElement>('[data-table-column-header-id]')).find(
      (element) => element.dataset.tableColumnHeaderId === columnId
    );
    const headerWidth = header ? measureCell(header, host, 'measureWidth') : 0;
    return Math.max(headerWidth, ...cells.map((cell) => measureCell(cell, host, 'measureWidth')));
  } finally {
    host.remove();
  }
};

const createMeasurementHost = (tableRoot: HTMLElement): HTMLDivElement => {
  const host = document.createElement('div');
  // The host is configured to let its child(the copied cell) extends without constraint
  host.style.cssText =
    'position:fixed;left:-100000px;top:0;display:grid;align-items:start;justify-content:start;justify-items:start;overflow:visible;visibility:hidden;pointer-events:none;z-index:-1';
  tableRoot.appendChild(host);
  return host;
};

const copyComputedStyle = (source: HTMLElement, copy: HTMLElement): void => {
  const style = window.getComputedStyle(source);
  for (let index = 0; index < style.length; index++) {
    const property = style.item(index);
    copy.style.setProperty(property, style.getPropertyValue(property));
  }
};

const measureCell = (
  cell: HTMLElement,
  host: HTMLElement,
  measureWidthOrHeight: 'measureWidth' | 'measureHeight'
): number => {
  const copy = cell.cloneNode(true) as HTMLElement;
  copyComputedStyle(cell, copy);
  const width = measureWidthOrHeight === 'measureWidth' ? 'max-content' : `${cell.offsetWidth}px`;
  host.style.width = width;
  host.style.gridTemplateColumns = width;
  copy.style.setProperty('box-sizing', 'border-box', 'important');
  copy.style.setProperty('width', width, 'important');
  copy.style.setProperty('min-width', '0', 'important');
  copy.style.setProperty('max-width', 'none', 'important');
  copy.style.setProperty('height', 'auto', 'important');
  copy.style.setProperty('min-height', '0', 'important');
  copy.style.setProperty('max-height', 'none', 'important');
  copy.style.setProperty('overflow', 'visible', 'important');
  copy.style.setProperty('position', 'relative', 'important');
  copy.style.setProperty('transform', 'none', 'important');
  if (measureWidthOrHeight === 'measureWidth') {
    copy
      .querySelectorAll<HTMLElement>('.MuiFormControl-fullWidth, .MuiInputBase-fullWidth, .MuiSelect-select')
      .forEach((element) => {
        element.style.width = 'max-content';
        element.style.minWidth = '0';
      });
    copyInputValues(cell, copy);
  }
  host.appendChild(copy);
  const value =
    measureWidthOrHeight === 'measureWidth' ? copy.getBoundingClientRect().width : copy.getBoundingClientRect().height;
  copy.remove();
  return Math.ceil(value);
};

const copyInputValues = (source: HTMLElement, clone: HTMLElement): void => {
  const sourceInputs = source.querySelectorAll<HTMLInputElement | HTMLTextAreaElement>('input, textarea');
  const cloneInputs = clone.querySelectorAll<HTMLInputElement | HTMLTextAreaElement>('input, textarea');
  sourceInputs.forEach((sourceInput, index) => {
    const clonedInput = cloneInputs[index];
    if (!clonedInput) {
      return;
    }
    clonedInput.value = sourceInput.value;
    if (sourceInput.getAttribute('aria-hidden') === 'true') {
      return;
    }

    const style = window.getComputedStyle(sourceInput);
    const mirror = document.createElement('span');
    mirror.style.cssText = `display:inline-block;width:max-content;white-space:pre;font:${style.font};letter-spacing:${style.letterSpacing};padding:${style.padding};border:${style.border};box-sizing:border-box`;
    mirror.textContent = sourceInput.value || '\u00a0';
    clonedInput.replaceWith(mirror);
  });
};
