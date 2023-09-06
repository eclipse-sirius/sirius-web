/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { TreeView } from '@eclipse-sirius/sirius-components-trees';
import { makeStyles } from '@material-ui/core/styles';
import { useState } from 'react';
import { ModelBrowserFilterBar } from './ModelBrowserFilterBar';
import { ModelBrowserTreeViewProps, ModelBrowserTreeViewState } from './ModelBrowserTreeView.types';

const useTreeStyle = makeStyles((theme) => ({
  title: {
    opacity: 0.6,
    fontSize: theme.typography.caption.fontSize,
  },
  borderStyle: {
    border: '1px solid',
    borderColor: theme.palette.grey[500],
    height: 300,
    overflow: 'auto',
  },
}));

export const ModelBrowserTreeView = ({
  editingContextId,
  selection,
  setSelection,
  widget,
  markedItemIds,
  enableMultiSelection,
  title,
  leafType,
  typeName,
}: ModelBrowserTreeViewProps) => {
  const classes = useTreeStyle();

  const [state, setState] = useState<ModelBrowserTreeViewState>({ filterBarText: '' });

  return (
    <>
      <ModelBrowserFilterBar
        onTextChange={(event) => setState({ filterBarText: event.target.value })}
        onTextClear={() => setState({ filterBarText: '' })}
        text={state.filterBarText}
      />
      <span className={classes.title}>{title}</span>
      <div className={classes.borderStyle}>
        <TreeView
          editingContextId={editingContextId}
          readOnly={true}
          selection={selection}
          setSelection={setSelection}
          treeId={`modelBrowser://${leafType}?typeName=${encodeURIComponent(typeName)}&targetType=${encodeURIComponent(
            widget.reference.referenceKind
          )}&ownerId=${widget.ownerId}`}
          enableMultiSelection={enableMultiSelection}
          synchronizedWithSelection={true}
          textToFilter={state.filterBarText}
          textToHighlight={state.filterBarText}
          markedItemIds={markedItemIds}
        />
      </div>
    </>
  );
};
