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
import { MultilineElementTransformer } from "@lexical/markdown";
import {
  $createTableCellNode,
  $createTableNode,
  $createTableRowNode,
  $isTableCellNode,
  $isTableNode,
  $isTableRowNode,
  TableCellHeaderStates,
  TableCellNode,
  TableNode,
  TableRowNode,
} from "@lexical/table";
import { $createParagraphNode, $createTextNode, LexicalNode } from "lexical";

const TABLE_ROW_REG_EXP = /^\s*\|?.+\|.+\|?\s*$/;
const TABLE_SEPARATOR_CELL_REG_EXP = /^:?-{3,}:?$/;

const splitTableRow = (line: string): string[] =>
  line
    .trim()
    .replace(/^\|/, "")
    .replace(/\|$/, "")
    .split("|")
    .map((cell) => cell.trim());

const isSeparatorCells = (cells: string[]): boolean =>
  cells.every((cell) => TABLE_SEPARATOR_CELL_REG_EXP.test(cell));

const createTableCell = (text: string, isHeader: boolean): TableCellNode => {
  const cell = $createTableCellNode(
    isHeader ? TableCellHeaderStates.ROW : TableCellHeaderStates.NO_STATUS
  );
  const paragraph = $createParagraphNode();
  paragraph.append($createTextNode(text));
  cell.append(paragraph);
  return cell;
};

const createTableNode = (rows: string[][]): TableNode => {
  const tableNode = $createTableNode();
  const columnCount = rows[0]?.length ?? 0;

  rows.forEach((row, rowIndex) => {
    const tableRowNode = $createTableRowNode();
    for (let columnIndex = 0; columnIndex < columnCount; columnIndex++) {
      tableRowNode.append(
        createTableCell(row[columnIndex] ?? "", rowIndex === 0)
      );
    }
    tableNode.append(tableRowNode);
  });

  return tableNode;
};

export const GFM_TABLE: MultilineElementTransformer = {
  dependencies: [TableNode, TableRowNode, TableCellNode],
  export: (node: LexicalNode): string | null => {
    if (!$isTableNode(node)) {
      return null;
    }

    const rows = node.getChildren().filter($isTableRowNode);
    if (rows.length === 0) {
      return null;
    }

    const cellsByRow = rows.map((row) =>
      row
        .getChildren()
        .filter($isTableCellNode)
        .map((cell) => escapeCell(cell.getTextContent()))
    );
    const columnCount = cellsByRow[0]?.length ?? 0;
    if (columnCount === 0) {
      return null;
    }

    const tableRows = cellsByRow.map((cells) =>
      Array.from({ length: columnCount }, (_, index) => cells[index] ?? "")
    );
    const separatorRow = Array.from({ length: columnCount }, () => "---");
    tableRows.splice(1, 0, separatorRow);

    return tableRows.map((row) => `| ${row.join(" | ")} |`).join("\n");
  },
  handleImportAfterStartMatch: ({
    lines,
    rootNode,
    startLineIndex,
  }): [boolean, number] | null => {
    const headerCells = splitTableRow(lines[startLineIndex]!);
    const separatorCells = splitTableRow(lines[startLineIndex + 1] ?? "");
    if (
      !isSeparatorCells(separatorCells) ||
      separatorCells.length !== headerCells.length
    ) {
      return null;
    }

    const rows = [headerCells];
    let lastTableLineIndex = startLineIndex + 1;
    for (
      let lineIndex = startLineIndex + 2;
      lineIndex < lines.length;
      lineIndex++
    ) {
      const line = lines[lineIndex]!;
      if (!TABLE_ROW_REG_EXP.test(line)) {
        break;
      }
      const cells = splitTableRow(line);
      rows.push(cells);
      lastTableLineIndex = lineIndex;
    }

    rootNode.append(createTableNode(rows));
    return [true, lastTableLineIndex];
  },
  regExpStart: TABLE_ROW_REG_EXP,
  replace: (): false => {
    return false;
  },
  type: "multiline-element",
};

// Keep table cells on one markdown line
const escapeCell = (text: string): string => text.replace(/\r?\n/g, " ").trim();
