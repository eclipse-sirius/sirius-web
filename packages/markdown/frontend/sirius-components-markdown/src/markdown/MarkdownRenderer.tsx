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
import { CodeNode } from "@lexical/code-core";
import { HorizontalRuleNode } from "@lexical/extension";
import { LinkNode } from "@lexical/link";
import { ListItemNode, ListNode } from "@lexical/list";
import { LexicalComposer } from "@lexical/react/LexicalComposer";
import { useLexicalComposerContext } from "@lexical/react/LexicalComposerContext";
import { LexicalErrorBoundary } from "@lexical/react/LexicalErrorBoundary";
import { MarkdownShortcutPlugin } from "@lexical/react/LexicalMarkdownShortcutPlugin";
import { RichTextPlugin } from "@lexical/react/LexicalRichTextPlugin";
import { TablePlugin } from "@lexical/react/LexicalTablePlugin";
import { TabIndentationPlugin } from "@lexical/react/LexicalTabIndentationPlugin";
import { HeadingNode, QuoteNode } from "@lexical/rich-text";
import { TableCellNode, TableNode, TableRowNode } from "@lexical/table";
import { $setSelection, TextNode } from "lexical";
import { FocusEvent, useCallback, useEffect } from "react";
import { makeStyles } from "tss-react/mui";
import { ListPlugin } from "./ListPlugin";
import {
  ContentEditableProps,
  MarkdownRendererProps,
  OnBlurPluginProps,
  UpdateValuePluginProps,
} from "./MarkdownRenderer.types";
import {
  MARKDOWN_TRANSFORMERS,
  convertFromMarkdownString,
  convertToMarkdownString,
} from "./MarkdownTransformers";
import { ToolbarPlugin } from "./ToolbarPlugin";

const ContentEditable = ({ readOnly }: ContentEditableProps): JSX.Element => {
  const [editor] = useLexicalComposerContext();
  const ref = useCallback(
    (rootElement: null | HTMLElement) => {
      editor.setRootElement(rootElement);
    },
    [editor]
  );
  return <div ref={ref} contentEditable={!readOnly} spellCheck={false}></div>;
};

const UpdateValuePlugin = ({
  markdownText,
}: UpdateValuePluginProps): JSX.Element | null => {
  const [editor] = useLexicalComposerContext();
  useEffect(() => {
    editor.update(() => {
      convertFromMarkdownString(markdownText);
      $setSelection(null);
    });
  }, [editor, markdownText]);
  return null;
};

const OnBlurPlugin = ({ onBlur, children }: OnBlurPluginProps): JSX.Element => {
  const [editor] = useLexicalComposerContext();
  return (
    <div
      onBlur={(event: FocusEvent<HTMLDivElement, Element>) => {
        if (!event.currentTarget.contains(event.relatedTarget)) {
          editor.getEditorState().read(() => {
            const markdown = convertToMarkdownString();
            onBlur(markdown);
          });
        }
      }}
    >
      {children}
    </div>
  );
};

const useMarkdownRendererStyles = makeStyles()((theme) => ({
  editorContainer: {
    marginTop: theme.spacing(2),
    color: theme.palette.text.primary,
    position: "relative",
    fontWeight: Number(theme.typography.fontWeightRegular),
    textAlign: "left",
    borderBottom: `1px solid ${theme.palette.divider}`,
    "&:hover": {
      borderBottom: `2px solid ${theme.palette.divider}`,
    },
    "&:focus-within": {
      transition: "transform 200ms cubic-bezier(0.0, 0, 0.2, 1) 0ms",
      borderBottom: `2px solid ${theme.palette.primary.main}`,
    },
  },
  editorPlaceholder: {
    color: theme.palette.text.secondary,
    top: 0,
    overflow: "hidden",
    position: "absolute",
    textOverflow: "ellipsis",
    fontSize: theme.typography.fontSize,
    userSelect: "none",
    display: "inline-block",
    pointerEvents: "none",
  },
  editorParagraph: {
    margin: 0,
    marginBottom: theme.spacing(2),
    position: "relative",
  },
  editorTextBold: {
    fontWeight: "bold",
  },
  editorTextItalic: {
    fontStyle: "italic",
  },
  editorTextUnderline: {
    textDecoration: "underline",
  },
  editorTextStrikethrough: {
    textDecoration: "line-through",
  },
  editorTextUnderlineStrikethrough: {
    textDecoration: "underline line-through",
  },
  editorTextCode: {
    backgroundColor: theme.palette.background.default,
    padding: "1px 0.25rem",
    fontFamily: "Menlo, Consolas, Monaco, monospace",
    fontSize: "94%",
  },
  editorHeading1: {
    fontFamily: theme.typography.h4.fontFamily,
    fontSize: theme.typography.h4.fontSize,
    fontWeight: theme.typography.h4.fontWeight,
    lineHeight: theme.typography.h4.lineHeight,
    letterSpacing: theme.typography.h4.letterSpacing,
    color: theme.palette.text.primary,
    margin: 0,
    marginBottom: theme.spacing(3),
    padding: 0,
  },
  editorHeading2: {
    fontFamily: theme.typography.h5.fontFamily,
    fontSize: theme.typography.h5.fontSize,
    fontWeight: theme.typography.h5.fontWeight,
    lineHeight: theme.typography.h5.lineHeight,
    letterSpacing: theme.typography.h5.letterSpacing,
    color: theme.palette.text.secondary,
    margin: 0,
    marginTop: theme.spacing(2),
    padding: 0,
  },
  editorListOl: {
    padding: 0,
    margin: 0,
    marginLeft: theme.spacing(2),
    listStyle: "decimal",
  },
  editorListUl: {
    padding: 0,
    margin: 0,
    marginLeft: theme.spacing(2),
    listStyle: "circle",
  },
  editorListitem: {
    margin: `${theme.spacing(2)} ${theme.spacing(8)} ${theme.spacing(
      2
    )} ${theme.spacing(8)}`,
  },
  editorNestedListitem: {
    listStyleType: "none",
  },
  editorTable: {
    borderCollapse: "collapse",
    borderSpacing: 0,
    marginBottom: theme.spacing(2),
    tableLayout: "fixed",
    width: "100%",
  },
  editorTableScrollableWrapper: {
    overflowX: "auto",
  },
  editorTableCell: {
    border: `${theme.spacing(0.125)} solid ${theme.palette.divider}`,
    padding: theme.spacing(1),
    minWidth: theme.spacing(10),
    verticalAlign: "top",
  },
  editorTableCellHeader: {
    backgroundColor: theme.palette.action.hover,
    fontWeight: Number(theme.typography.fontWeightMedium),
  },
}));

export const MarkdownRenderer = ({
  value,
  placeholder,
  readOnly,
  onBlur,
}: MarkdownRendererProps) => {
  const { classes } = useMarkdownRendererStyles();
  const theme = {
    placeholder: classes.editorPlaceholder,
    paragraph: classes.editorParagraph,
    heading: {
      h1: classes.editorHeading1,
      h2: classes.editorHeading2,
    },
    list: {
      nested: {
        listitem: classes.editorNestedListitem,
      },
      ol: classes.editorListOl,
      ul: classes.editorListUl,
      listitem: classes.editorListitem,
    },
    table: classes.editorTable,
    tableScrollableWrapper: classes.editorTableScrollableWrapper,
    tableCell: classes.editorTableCell,
    tableCellHeader: `${classes.editorTableCell} ${classes.editorTableCellHeader}`,
    text: {
      bold: classes.editorTextBold,
      italic: classes.editorTextItalic,
      underline: classes.editorTextUnderline,
      strikethrough: classes.editorTextStrikethrough,
      underlineStrikethrough: classes.editorTextUnderlineStrikethrough,
      code: classes.editorTextCode,
    },
  };
  const initialConfig = {
    namespace: "MarkdownRenderer",
    onError: console.error,
    theme,
    nodes: [
      HeadingNode,
      ListNode,
      ListItemNode,
      QuoteNode,
      HorizontalRuleNode,
      TextNode,
      CodeNode,
      LinkNode,
      TableNode,
      TableRowNode,
      TableCellNode,
    ],
  };
  return (
    <LexicalComposer initialConfig={initialConfig}>
      <OnBlurPlugin onBlur={onBlur}>
        <UpdateValuePlugin markdownText={value} />
        {!readOnly ? <ToolbarPlugin readOnly={readOnly} /> : null}
        <div className={classes.editorContainer}>
          <MarkdownShortcutPlugin transformers={MARKDOWN_TRANSFORMERS} />
          <ListPlugin />
          <TablePlugin
            hasCellMerge={false}
            hasCellBackgroundColor={false}
            hasHorizontalScroll
          />
          <RichTextPlugin
            contentEditable={<ContentEditable readOnly={readOnly} />}
            placeholder={
              <div className={classes.editorPlaceholder}>{placeholder}</div>
            }
            ErrorBoundary={LexicalErrorBoundary}
          />
          <TabIndentationPlugin />
        </div>
      </OnBlurPlugin>
    </LexicalComposer>
  );
};
