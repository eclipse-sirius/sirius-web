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

import { download, generateCsv, mkConfig } from 'export-to-csv';
import { ExportData as CsvData, GQLCell, GQLTable } from './TableContent.types';
const csvConfig = mkConfig({
  fieldSeparator: ';',
  decimalSeparator: '.',
  useKeysAsHeaders: true,
});

export const handleExportData = (table: GQLTable, getCellLabel: (cell: GQLCell) => string) => {
  let csvData: CsvData[];
  const columIdToLabel = {};
  table.columns.map((col) => (columIdToLabel[col.id] = col.label));
  csvData = table.lines.map((line) => {
    let csvDatum: CsvData = {};
    line.cells.forEach((cell) => (csvDatum[columIdToLabel[cell.columnId]] = getCellLabel(cell)));
    return csvDatum;
  });
  const csv = generateCsv(csvConfig)(csvData);
  download(csvConfig)(csv);
};
