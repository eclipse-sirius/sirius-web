/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { TreeView, TreeItemActionProps } from '@eclipse-sirius/sirius-components-trees';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import UnfoldMoreIcon from '@material-ui/icons/UnfoldMore';
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
  widget,
  markedItemIds,
  enableMultiSelection,
  title,
  leafType,
  ownerKind,
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
          treeId={`modelBrowser://${leafType}?ownerKind=${encodeURIComponent(
            ownerKind
          )}&targetType=${encodeURIComponent(widget.reference.referenceKind)}&ownerId=${
            widget.ownerId
          }&descriptionId=${encodeURIComponent(widget.descriptionId)}&isContainment=${widget.reference.containment}`}
          enableMultiSelection={enableMultiSelection}
          synchronizedWithSelection={true}
          activeFilterIds={[]}
          textToFilter={state.filterBarText}
          textToHighlight={state.filterBarText}
          markedItemIds={markedItemIds}
          treeItemActionRender={(props) => <WidgetReferenceTreeItemAction {...props} />}
        />
      </div>
    </>
  );
};

const WidgetReferenceTreeItemAction = ({ onExpandAll, item, isHovered }: TreeItemActionProps) => {
  if (!onExpandAll || !item || !item.hasChildren || !isHovered) {
    return null;
  }
  return (
    <IconButton
      size="small"
      data-testid="expand-all"
      title="expand all"
      onClick={() => {
        onExpandAll(item);
      }}>
      <UnfoldMoreIcon style={{ fontSize: 12 }} />
    </IconButton>
  );
};
